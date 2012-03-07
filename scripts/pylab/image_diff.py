import Image
import ImageDraw
import sys

RED = (255, 0, 0)

def diff(img1, img2):
    return [(i, j) for i in range(img1.size[0]) 
                       for j in range(img1.size[1]) 
                       if (img1.getpixel((i, j)) != img2.getpixel((i, j)))]

def figure(img, points, fp):
    d = ImageDraw.Draw(img)
    for p in points:
        d.point(p, RED);
    img.save(fp)

def rename(name):
    name = name.replace('\\', '/').split('/')[-1]
    return '%s_diff.%s' % (name.split('.')[0], name.split('.')[1])

if len(sys.argv) != 3:
    sys.argv.append('/home/stone/tmp/package.png')
    sys.argv.append('/home/stone/tmp/package1.png')
img1 = Image.open(sys.argv[1])
img2 = Image.open(sys.argv[2])

figure(img1, diff(img1, img2), rename(sys.argv[1]))
