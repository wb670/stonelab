package cn.zeroall.chameleon.image;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 图片调整大小
 * 
 * @author Maurice.J Jan 27, 2009
 */
public class ImageResizer {

    private static final int SCALE_TYPE = Image.SCALE_SMOOTH; // 图片压缩质量

    private String source; // 原始图片路径
    private String target;// 调整大小后输出图片路径

    private JPEGImageGenerator generator = new JPEGImageGenerator();

    public ImageResizer(String source, String target) {
        if (source == null || target == null) {
            throw new IllegalArgumentException("source or target is null.");
        }
        this.source = source;
        this.target = target;
    }

    /**
     * 根据提供的宽度和高度调整大小
     * 
     * @param scaledWidth 调整后后的宽度
     * @param scaledHeight 调整后的高度
     * @throws ImageException 调整异常
     */
    public void resize(int scaledWidth, int scaledHeight) throws ImageException {
        // 参数校验
        if (scaledWidth <= 0 || scaledHeight <= 0) {
            throw new IllegalArgumentException(
                    "scaledWidth or scaledHeight must be grater than zero,but scaledWidth is "
                            + scaledWidth + "; scaledHeight is " + scaledHeight);
        }
        // 得到原始图片
        BufferedImage input = null;
        try {
            input = getSourceImage(source);
        } catch (IOException e) {
            throw new ImageException("get source file fail.", e);
        }

        // 调整大小
        doResize(input, scaledWidth, scaledHeight);
    }

    /**
     * 根据提供的size进行图片大小调整
     * 
     * @param size 大小
     * @throws ImageException 调整异常
     */
    public void resizeBySize(int size) throws ImageException {
        // 参数校验
        if (size <= 0) {
            throw new IllegalArgumentException("size must be grater than zero,but size is " + size);
        }
        // 得到原始图片
        BufferedImage input = null;
        try {
            input = getSourceImage(source);
        } catch (IOException e) {
            throw new ImageException("get source file fail.", e);
        }

        // 计算调整后的宽度和高度
        float scaled = calcScaled(input.getWidth(), input.getHeight(), size);
        int scaledWidth = (int) (scaled * input.getWidth());
        int scaledHeight = (int) (scaled * input.getHeight());

        // 调整图片大小
        doResize(input, scaledWidth, scaledHeight);
    }

    /**
     * 调整图片大小
     * 
     * @param bufferedImage 缓冲区原始图片
     * @param scaledWidth 调整后的宽度
     * @param scaledHeight 调整后的高度
     * @throws ImageException 调整异常
     */
    private void doResize(BufferedImage bufferedImage, int scaledWidth, int scaledHeight)
            throws ImageException {
        // 将原始图片重画到调整大小后的图片上
        BufferedImage scaled = new BufferedImage(scaledWidth, scaledHeight,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g = scaled.createGraphics();
        g.drawImage(bufferedImage.getScaledInstance(scaledWidth, scaledHeight, SCALE_TYPE), 0, 0,
                null);

        // 将调整后图片生成jpg格式的图片
        genJPGImage(bufferedImage, target);
    }

    /**
     * 计算调整比例
     * 
     * @param width 原始图片宽度
     * @param height 原始图片高度
     * @param size 大小
     * @return 调整比例
     */
    private float calcScaled(int width, int height, int size) {
        if (size >= width && size >= height) {
            return 1.0f;
        }
        return Math.min((float) size / width, (float) size / height);
    }

    /**
     * 得到缓冲区图片
     * 
     * @param file 图片文件
     * @return 缓冲区图片
     * @throws IOException IO异常
     */
    private BufferedImage getSourceImage(String file) throws IOException {
        return ImageIO.read(new File(file));
    }

    /**
     * 生成jpg图片
     * 
     * @param bufferedImage 缓冲区图片
     * @param file 输出文件路径
     * @throws ImageException
     */
    private void genJPGImage(BufferedImage bufferedImage, String file) throws ImageException {
        generator.generate(bufferedImage, file);
    }

    /**
     * set source
     * 
     * @param source
     */
    public void setSource(String source) {
        if (source == null) {
            throw new IllegalArgumentException("source is null.");
        }
        this.source = source;
    }

    /**
     * set target
     * 
     * @param target
     */
    public void setTarget(String target) {
        if (target == null) {
            throw new IllegalArgumentException("target is null.");
        }
        this.target = target;
    }

    /**
     * 设置图片生成器
     * 
     * @param generator
     */
    public void setGenerator(JPEGImageGenerator generator) {
        this.generator = generator;
    }

}
