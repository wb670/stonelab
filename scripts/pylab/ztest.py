import Image

def rgb(d):
    if d > 255:
        d = 255
    if d < 0:
        d = 0
    return int(d * 5)

def yuv2rgb(y, u, v):
    r = y + 1.4075 * (v - 128)
    g = y - 0.3455 * (u - 128) - 0.7169 * (v - 128)
    b = y + 1.779 * (u - 128)
    return (rgb(r), rgb(g), rgb(b))

def yuv442torgb(data):
    pixels = []
    for i in range(len(data) / 4):
        r = i * 4;
        y1, u, y2, v = (data[r], data[r + 1], data[r + 2], data[r + 3])
        pixels.append(yuv2rgb(y1, u, v))
        pixels.append(yuv2rgb(y2, u, v))
    print pixels[0:128]
    return pixels

def convert(yuv, width, height, to):
    im = Image.new('RGB', (width, height))
    pixels = yuv442torgb([ord(i) for i in open(yuv).read()])
    for i, p in enumerate(pixels):
        x, y = i % width, i / width
        im.putpixel((x, y), p)
    im.save(to, 'BMP')

width, height = 640, 480
yuv = '/home/stone/tmp/imgs/img-3.yuv'
to = '/home/stone/tmp/img.bmp'
convert(yuv, width, height, to)
