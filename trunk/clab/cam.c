#include <fcntl.h>
#include <sys/ioctl.h>
#include <linux/videodev2.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/mman.h>
#include <string.h>
#include <time.h>

int fd;
char *device_name = "/dev/video0";
char *imgs = "/home/stone/tmp/imgs/img";
FILE *f;
long count;

struct v4l2_capability cap;
struct v4l2_cropcap cropcap;
struct v4l2_format fmt;
struct v4l2_fmtdesc fmtdesc;
struct v4l2_requestbuffers req;
struct v4l2_streamparm param;

struct buffer
{
	void * start;
	size_t length;
};
struct buffer *buffers;
int n_buffers;

#define debug printf

void error_exit(char *msg)
{
	printf("%s", msg);
	exit(EXIT_FAILURE);
}

void open_device()
{
	fd = open(device_name, O_RDWR | O_NONBLOCK);
	debug("open device. fd is %d. \n", fd);
	if (fd == -1)
		error_exit("open device fail. \n");
}

void close_device()
{
	int r = close(fd);
	debug("close device. result is %d. \n", r);
	if (r == -1)
		error_exit("close device fail. \n");
}

void read_capability()
{
	int r = ioctl(fd, VIDIOC_QUERYCAP, &cap);
	debug("read capability. ioctl result is %d. \n", r);
	if (r == -1)
		error_exit("read capability fail. \n");
	printf("camera capabilities info: \n");
	printf("  driver: %s. \n", cap.driver);
	printf("  card: %s. \n", cap.card);
	printf("  bus_info: %s. \n", cap.bus_info);
	printf("  version: %d. \n", cap.version);
	printf("  capabilities: 0x%x. \n", cap.capabilities);
}

void read_crop_capability()
{
	cropcap.type = V4L2_BUF_TYPE_VIDEO_CAPTURE;
	int r = ioctl(fd, VIDIOC_CROPCAP, &cropcap);
	debug("read crop capability. ioctl result is %d. \n", r);
	if (r == -1)
		error_exit("read crop capability fail. \n");
	struct v4l2_rect b = cropcap.bounds;
	struct v4l2_rect rct = cropcap.defrect;
	struct v4l2_fract frct = cropcap.pixelaspect;
	printf("camera crop capabilities info: \n");
	printf("  crop bounds: left(%d), top(%d), width(%d), height(%d). \n", b.left, b.top, b.width, b.height);
	printf("  crop rect:   left(%d), top(%d), width(%d), height(%d). \n", rct.left, rct.top, rct.width, rct.height);
	printf("  pixel fract: numerator(%d), denominator(%d). \n", frct.numerator, frct.denominator);
}

void read_format()
{
	fmtdesc.type = V4L2_BUF_TYPE_VIDEO_CAPTURE;
	int r = ioctl(fd, VIDIOC_ENUM_FMT, &fmtdesc);
	debug("read format description. ioctl result is %d. \n", r);
	if (r == -1)
		error_exit("read format description fail. \n");
	printf("format description info: \n");
	printf("  fmt pixelformat:%d. \n", fmtdesc.pixelformat);
	printf("  fmt description:%s. \n", fmtdesc.description);
}

void set_format()
{
	fmt.type = V4L2_BUF_TYPE_VIDEO_CAPTURE;
	fmt.fmt.pix.width = 640;
	fmt.fmt.pix.height = 480;
	fmt.fmt.pix.field = V4L2_FIELD_INTERLACED;
	fmt.fmt.pix.pixelformat = V4L2_PIX_FMT_YUYV;
	int r = ioctl(fd, VIDIOC_S_FMT, &fmt);
	debug("set format. ioctl result is %d. \n", r);
	if (r == -1)
		error_exit("set format fail. \n");
	printf("set format info: \n");
	printf("  bytesperline: %d. \n", fmt.fmt.pix.bytesperline);
	printf("  sizeimage: %d. \n", fmt.fmt.pix.sizeimage);
}

void read_frmrate()
{
	param.type = V4L2_BUF_TYPE_VIDEO_CAPTURE;
	int r = ioctl(fd, VIDIOC_G_PARM, &param);
	if (r == -1)
		error_exit("read frame rate fail. \n");
	printf("frame rate info: \n");
	printf("  frame rate: %dfps \n",
			param.parm.capture.timeperframe.denominator / param.parm.capture.timeperframe.numerator);
}

void queue_buffer(int i)
{
	struct v4l2_buffer buf;
	buf.type = V4L2_BUF_TYPE_VIDEO_CAPTURE;
	buf.memory = V4L2_MEMORY_MMAP;
	buf.index = i;
	int r = ioctl(fd, VIDIOC_QBUF, &buf);
	if (r == -1)
		error_exit("qbuf fail. \n");
}
int dequeue_buffer()
{
	struct v4l2_buffer buf;
	buf.type = V4L2_BUF_TYPE_VIDEO_CAPTURE;
	buf.memory = V4L2_MEMORY_MMAP;
	int r = ioctl(fd, VIDIOC_DQBUF, &buf);
	if (r == -1)
		error_exit("dqbuf fail. \n");
	return buf.index;
}

void request_buffer()
{
	int r;

	req.count = 4;
	req.type = V4L2_BUF_TYPE_VIDEO_CAPTURE;
	req.memory = V4L2_MEMORY_MMAP;
	r = ioctl(fd, VIDIOC_REQBUFS, &req);
	debug("request buffer. ioctl result is %d. req.count is %d. \n", r, req.count);
	if (r == -1 && req.count != 4)
		error_exit("request buffer fail. \n");

	n_buffers = req.count;
	buffers = malloc(sizeof(struct buffer) * n_buffers);
	if (!buffers)
		error_exit("out of memory. \n");

	int i;
	for (i = 0; i < n_buffers; ++i)
	{
		struct v4l2_buffer buf;
		buf.type = V4L2_BUF_TYPE_VIDEO_CAPTURE;
		buf.memory = V4L2_MEMORY_MMAP;
		buf.index = i;

		r = ioctl(fd, VIDIOC_QUERYBUF, &buf);
		if (r == -1)
			error_exit("query buffer fail. \n");

		buffers[i].start = mmap(NULL, buf.length, PROT_READ | PROT_WRITE, MAP_SHARED, fd, buf.m.offset);
		buffers[i].length = buf.length;
	}
}

void process_frame(int i)
{
	fwrite(buffers[i].start, buffers[i].length, 1, f);
}

void read_frame()
{
	int i = dequeue_buffer();
	process_frame(i);
	queue_buffer(i);
}

void loop(int count)
{
	while (count--)
	{
		fd_set fds;
		struct timeval tv;

		FD_ZERO(&fds);
		FD_SET(fd, &fds);
		tv.tv_sec = 2;
		tv.tv_usec = 0;

		int r = select(fd + 1, &fds, NULL, NULL, &tv);

		if (r == -1)
			error_exit("select fail. \n");
		if (r == 0)
			error_exit("select timeout. \n");

		read_frame();
	}
}

void start_capture()
{
	int i;
	for (i = 0; i < n_buffers; ++i)
		queue_buffer(i);
	int type = V4L2_BUF_TYPE_VIDEO_CAPTURE;
	int r = ioctl(fd, VIDIOC_STREAMON, &type);
	if (r == -1)
		error_exit("start capture fail. \n");
}

void stop_capture()
{
	int type = V4L2_BUF_TYPE_VIDEO_CAPTURE;
	int r = ioctl(fd, VIDIOC_STREAMOFF, &type);
	if (r == -1)
		error_exit("stop capture fail. \n");
}

void free_buffer()
{
	int i;
	for (i = 0; i < n_buffers; ++i)
	{
		munmap(buffers[i].start, buffers[i].length);
	}
	free(buffers);
}

int cam_main(int argc, char **argv)
{
	time_t start = time(NULL);

	f = fopen("/home/stone/tmp/cam.yuv", "wr");

	open_device();
	read_capability();
	read_crop_capability();
	read_format();
	read_frmrate();

	set_format();
	request_buffer();

	start_capture();

	loop(1000);

	stop_capture();

	free_buffer();
	close_device();

	fclose(f);

	time_t end = time(NULL);
	printf("take time: %ld s.", end - start);

	return 0;
}
