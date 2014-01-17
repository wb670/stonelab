#!/usr/bin/python
import sys, optparse, Image

TABLE='<table id="image" border="0" cellpadding="0" cellspacing="0">%s</table>'
TR='<tr>%s</tr>'
TD='<td width="1px;" height="1px;" bgcolor="%s"/>'

def rgb2hex(rgb):
    return '#{:02x}{:02x}{:02x}'.format(rgb[0],rgb[1],rgb[2])

def get_image(name, thumbnail=1):
    if(thumbnail >= 1 or thumbnail <= 0):
        return Image.open(name)
    else:
        img = Image.open(name)
        return img.resize((int(img.size[0] * thumbnail),int(img.size[1] * thumbnail)))

def convert(img):
    trs = []
    for height in xrange(img.size[1]):
        tds = []
        for width in xrange(img.size[0]):
            tds.append(TD % rgb2hex(img.getpixel((width, height))))
        trs.append(TR % (''.join(tds)))
    return TABLE % (''.join(trs),)

parser = optparse.OptionParser('Usage: %prog [options] image')
parser.add_option('-c', '--compress', dest='thumbnail', default='1', metavar='float', help='specify the compress value (0, 1)')
parser.add_option('-o', '--out', dest='out', default='out.html', help='specify the output file')
opts, args = parser.parse_args()

if(len(args) != 1):
    parser.print_help()
    sys.exit(-1)

html = open(opts.out,'w')
html.write(convert(get_image(args[0], float(opts.thumbnail))))
html.close()
