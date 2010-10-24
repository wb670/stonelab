/*
 * Copyright 1999-2010 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.toolkit.learning.img;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * 交通网图片分析工具
 * 
 * @author <a href="mailto:li.jinl@alibaba-inc.com">Stone.J</a> 2010-10-10
 */
public class TrafficImgCracker {

    private static final String IMG_URL      = "http://www.hzti.com/government/CreateCheckCode.aspx";
    private static final String IMG_REF      = "/home/stone/tmp/imgref/";

    private static final int    X_BASE       = 9;
    private static final int    Y_BASE       = 0;
    private static final int    WIDTH        = 7;
    private static final int    HEIGHT       = 22;
    private static final String IMAGE_FORMAT = "bmp";
    private static final String CODE_RGB     = "ff000000";

    private List<ImageCode>     imageCodes   = new ArrayList<ImageCode>(10);

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("java TrafficImgCracker $file_path");
            System.exit(1);
        }

        String file = args[0];

        TrafficImgCracker cracker = new TrafficImgCracker();
        cracker.init();
        System.out.print(cracker.crack(file));
    }

    /**
     * init
     * 
     * @throws Exception
     */
    public void init() throws Exception {
        File ref = new File(IMG_REF);
        File[] files = ref.listFiles();
        for (File f : files) {
            if (!f.isFile()) {
                continue;
            }
            String code = f.getName().substring(0, f.getName().indexOf("."));
            BufferedImage img = getBufferedImage(new FileInputStream(f));
            int[][] feature = getImageFeature(img);
            // add to image codes
            imageCodes.add(new ImageCode(code, feature));
        }
    }

    /**
     * crack
     * 
     * @param file
     * @return
     * @throws Exception
     */
    public String crack(String file) throws Exception {
        return crack(new FileInputStream(file));
    }

    /**
     * crack image code
     * 
     * @param in image input stream
     * @return code
     */
    public String crack(InputStream in) throws Exception {
        BufferedImage img = getBufferedImage(in);
        StringBuilder ret = new StringBuilder();

        for (int c = 1; c < 5; c++) {
            BufferedImage bi = img.getSubimage(c * X_BASE, Y_BASE, WIDTH, HEIGHT);
            int[][] feature = getImageFeature(bi);

            for (ImageCode ic : imageCodes) {
                int success = 0;
                for (int i = 0; i < WIDTH; i++) {
                    for (int j = 0; j < HEIGHT; j++) {
                        if (feature[i][j] == ic.getFeature()[i][j]) {
                            success++;
                        }
                    }
                }
                // 判断是否匹配上
                if (success * 100.0 / (WIDTH * HEIGHT) > 95) {
                    ret.append(ic.getCode());

                    continue;
                }
            }

        }

        return ret.toString();
    }

    /**
     * learn
     * 
     * @param count learning count
     * @throws Exception
     */
    public void learn(int count) throws Exception {
        for (int c = 0; c < count; c++) {
            BufferedImage img = getBufferedImage(new URL(IMG_URL).openStream());
            // 保存样本
            for (int i = 1; i < 5; i++) {
                BufferedImage bi = img.getSubimage(i * X_BASE, Y_BASE, WIDTH, HEIGHT);
                String name = String.valueOf(c) + "_" + String.valueOf(i);
                ImageIO.write(bi, IMAGE_FORMAT, new File(IMG_REF + "learn/" + name + ".bmp"));
            }
        }
    }

    // 得到图片信息,去除噪点的图片
    private BufferedImage getBufferedImage(InputStream in) throws Exception {
        BufferedImage img = ImageIO.read(in);
        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                // 黑色的才是验证码
                if (!CODE_RGB.equals(Integer.toHexString(img.getRGB(i, j)))) {
                    img.setRGB(i, j, -1);
                }
            }
        }
        in.close();
        return img;
    }

    // 得到图片特征
    private int[][] getImageFeature(BufferedImage img) throws Exception {
        int[][] feature = new int[WIDTH][HEIGHT];
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (img.getWidth() < i || img.getHeight() < j) {
                    continue;
                }
                feature[i][j] = img.getRGB(i, j);
            }
        }
        return feature;
    }

    /**
     * image code bean
     * 
     * @author <a href="mailto:li.jinl@alibaba-inc.com">Stone.J</a> 2010-10-10
     */
    public static class ImageCode {

        private String  code;
        private int[][] feature;

        public ImageCode(String code, int[][] feature){
            this.code = code;
            this.feature = feature;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public int[][] getFeature() {
            return feature;
        }

        public void setFeature(int[][] feature) {
            this.feature = feature;
        }
    }

}
