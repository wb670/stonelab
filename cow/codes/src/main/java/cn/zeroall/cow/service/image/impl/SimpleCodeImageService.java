package cn.zeroall.cow.service.image.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import cn.zeroall.cow.service.image.CodeImageService;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> May 2, 2009
 */
public class SimpleCodeImageService implements CodeImageService {

    private int width = 72;
    private int height = 20;

    private int foregroundR = 255;
    private int foregroundG = 255;
    private int foregroundB = 255;

    private int backgroundR = 0;
    private int backgroundG = 0;
    private int backgroundB = 0;

    private String fontName = "purisa";
    private int fontSize = 20;

    private int interval = 9;
    private int locationY = 15;

    @Override
    public BufferedImage genCodeImage(String content) {
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.setColor(getBackgroundColor());
        graphics2D.fillRect(0, 0, width, height);

        graphics2D.setFont(getForegroundFont());
        graphics2D.setColor(getForegroundColor());

        int length = content.length();
        for (int i = 0; i < length; i++) {
            int x = (i + 1) * interval;
            graphics2D.drawString(String.valueOf(content.charAt(i)), x, locationY);
        }

        graphics2D.dispose();
        return bufferedImage;
    }

    private Color getBackgroundColor() {
        return new Color(backgroundR, backgroundG, backgroundB);
    }

    private Color getForegroundColor() {
        return new Color(foregroundR, foregroundG, foregroundB);
    }

    private Font getForegroundFont() {
        return new Font(fontName, Font.BOLD + Font.ITALIC, fontSize);
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setForegroundR(int foregroundR) {
        this.foregroundR = foregroundR;
    }

    public void setForegroundG(int foregroundG) {
        this.foregroundG = foregroundG;
    }

    public void setForegroundB(int foregroundB) {
        this.foregroundB = foregroundB;
    }

    public void setBackgroundR(int backgroundR) {
        this.backgroundR = backgroundR;
    }

    public void setBackgroundG(int backgroundG) {
        this.backgroundG = backgroundG;
    }

    public void setBackgroundB(int backgroundB) {
        this.backgroundB = backgroundB;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public void setLocationY(int locationY) {
        this.locationY = locationY;
    }

}
