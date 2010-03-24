package cn.zeroall.cow.service.image.adapter;

import net.sf.cglib.beans.BeanCopier;
import cn.zeroall.cow.dal.image.po.ImgGroupPO;
import cn.zeroall.cow.service.image.model.ImageGroup;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Mar 8, 2009
 */
public class ImageGroupAdapter {

    private static final BeanCopier imageGroup2ImgGroupPO = BeanCopier
            .create(ImageGroup.class, ImgGroupPO.class, false);
    private static final BeanCopier imgGroupPO2ImageGroup = BeanCopier
            .create(ImgGroupPO.class, ImageGroup.class, false);

    private ImgGroupPO imgGroupPO;
    private ImageGroup imageGroup;

    public ImageGroupAdapter(ImgGroupPO imgGroupPO) {
        this.imgGroupPO = imgGroupPO;
        initImgGroupPOPO2ImageGroup();
    }

    public ImageGroupAdapter(ImageGroup imageGroup) {
        this.imageGroup = imageGroup;
        initImageGroup2ImgGroupPO();
    }

    public ImgGroupPO getImgGroupPO() {
        return imgGroupPO;
    }

    public ImageGroup getImageGroup() {
        return imageGroup;
    }

    private void initImageGroup2ImgGroupPO() {
        if (imageGroup == null) {
            return;
        }
        imgGroupPO = new ImgGroupPO();
        imageGroup2ImgGroupPO.copy(imageGroup, imgGroupPO, null);
    }

    private void initImgGroupPOPO2ImageGroup() {
        if (imgGroupPO == null) {
            return;
        }
        imageGroup = new ImageGroup();
        imgGroupPO2ImageGroup.copy(imgGroupPO, imageGroup, null);
    }

}
