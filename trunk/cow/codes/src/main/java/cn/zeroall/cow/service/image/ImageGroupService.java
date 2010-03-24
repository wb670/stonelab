package cn.zeroall.cow.service.image;

import java.util.List;

import cn.zeroall.cow.service.image.model.ImageGroup;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Mar 16, 2009
 */
public interface ImageGroupService {

    public List<ImageGroup> getImageGroupDetailsByIds(Integer[] ids);

}
