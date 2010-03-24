package cn.zeroall.cow.service.image.util;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> May 2, 2009
 */
public class SumImageUrlGenUtil {

    private static final String LOCATION_SPLIT = "/";
    private static final String SUM_IMAGE_FLAG = "t_";

    public static void main(String[] args) {
        System.out.println(SumImageUrlGenUtil.getSumImageUrl("123.jpg"));
    }

    public static String getSumImageUrl(String imageUrl) {
        if (StringUtils.isBlank(imageUrl)) {
            return null;
        }
        if (!StringUtils.contains(imageUrl, LOCATION_SPLIT)) {
            return SUM_IMAGE_FLAG + imageUrl;
        } else {
            String prefix = StringUtils.substringBeforeLast(imageUrl, LOCATION_SPLIT);
            String imageName = StringUtils.substringAfterLast(imageUrl, LOCATION_SPLIT);
            return new StringBuilder(prefix).append(LOCATION_SPLIT).append(SUM_IMAGE_FLAG).append(imageName).toString();
        }
    }
}
