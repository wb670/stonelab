package cn.zeroall.chameleon.image;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 图片水印工具
 * 
 * @author Maurice.J Feb 1, 2009
 */
public class ImageWaterMarker {

    private String source; // 原始图片文件
    private String waterMark; // 水印图片文件
    private String target; // 水印后图片文件

    private JPEGImageGenerator generator = new JPEGImageGenerator();

    /**
     * 构造器
     * 
     * @param source 原始图片路径
     * @param waterMark 水印图片路径
     * @param target 水印后图片路径
     */
    public ImageWaterMarker(String source, String waterMark, String target) {
	if (source == null || waterMark == null || target == null) {
	    throw new IllegalArgumentException("source or waterMark or target is null.");
	}
	this.source = source;
	this.waterMark = waterMark;
	this.target = target;
    }

    /**
     * 打水印
     * 
     * @throws ImageException
     */
    public void waterMark() throws ImageException {
	BufferedImage sourceImage = null;
	BufferedImage waterMarkImage = null;
	try {
	    sourceImage = ImageIO.read(new File(source));
	    waterMarkImage = ImageIO.read(new File(waterMark));
	} catch (IOException e) {
	    throw new ImageException("get source file or waterMark file fail.", e);
	}

	BufferedImage markedImage = new BufferedImage(sourceImage.getWidth(), sourceImage
		.getHeight(), BufferedImage.TYPE_INT_RGB);
	Graphics2D g = markedImage.createGraphics();
	// 画原始图片
	g.drawImage(sourceImage, 0, 0, sourceImage.getWidth(), sourceImage.getHeight(), null);
	// 画水印图片
	g.drawImage(waterMarkImage, 0, 0, waterMarkImage.getWidth(), waterMarkImage.getHeight(),
		null);
	g.dispose();

	generator.generate(markedImage, target);
    }

    public void setGenerator(JPEGImageGenerator generator) {
	this.generator = generator;
    }

}
