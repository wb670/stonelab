package cn.zeroall.cow.service.image;

import java.util.List;

import cn.zeroall.cow.service.image.model.Image;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Mar 16, 2009
 */
public interface ImageService {

    public List<Image> getImagesByIds(Integer[] ids);

    public List<Image> getImagesByGroupIds(Integer[] groupIds);

}
