package cn.zeroall.cow.service.image;

import java.awt.image.BufferedImage;

import cn.zeroall.cow.service.BaseService;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> May 2, 2009
 */
public interface CodeImageService extends BaseService {

    public BufferedImage genCodeImage(String content);

}
