package cn.zeroall.chameleon.image;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 生成JPEG格式的图片
 * 
 * @author Maurice.J Feb 1, 2009
 */
public class JPEGImageGenerator {

    private float quality = 0.8f;// 图片质量

    /**
     * 生成jpeg格式图片
     * 
     * @param image 图片
     * @param outputStream 输出流
     * @throws ImageException
     */
    public void generate(BufferedImage image, OutputStream outputStream) throws ImageException {
        if (image == null) {
            throw new IllegalArgumentException("image is null.");
        }
        if (outputStream == null) {
            throw new IllegalArgumentException("outputStream is null.");
        }

        try {
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(outputStream);
            JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(image);
            param.setQuality(quality, true);
            encoder.encode(image);
            outputStream.flush();
        } catch (ImageFormatException e) {
            throw new ImageException("generate jpeg image fail.", e);
        } catch (IOException e) {
            throw new ImageException("generate jpeg image fail.", e);
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                // ignore
            }
        }
    }

    /**
     * 生成jpeg格式图片
     * 
     * @param image 图片
     * @param file 文件
     * @throws ImageException
     */
    public void generate(BufferedImage image, String file) throws ImageException {
        if (image == null) {
            throw new IllegalArgumentException("image is null.");
        }
        if (file == null) {
            throw new IllegalArgumentException("file is null.");
        }

        OutputStream out = null;
        try {
            out = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            throw new ImageException("generate jpeg image fail.", e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                // ignore
            }
        }
        generate(image, out);
    }

    public void setQuality(float quality) {
        this.quality = quality;
    }

}
