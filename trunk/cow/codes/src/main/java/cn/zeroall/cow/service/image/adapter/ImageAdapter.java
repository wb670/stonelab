package cn.zeroall.cow.service.image.adapter;

import net.sf.cglib.beans.BeanCopier;
import cn.zeroall.cow.dal.image.po.ImgPO;
import cn.zeroall.cow.service.image.model.Image;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Mar 8, 2009
 */
public class ImageAdapter {

    private static final BeanCopier image2ImgPO = BeanCopier.create(Image.class, ImgPO.class, false);
    private static final BeanCopier imgPO2Image = BeanCopier.create(ImgPO.class, Image.class, false);

    private ImgPO imgPO;
    private Image image;

    public ImageAdapter(ImgPO imgPO) {
        this.imgPO = imgPO;
        initImgPO2Image();
    }

    public ImageAdapter(Image image) {
        this.image = image;
        initImage2ImgPO();
    }

    public ImgPO getImgPO() {
        return imgPO;
    }

    public Image getImage() {
        return image;
    }

    private void initImage2ImgPO() {
        if (image == null) {
            return;
        }
        imgPO = new ImgPO();
        image2ImgPO.copy(image, imgPO, null);
    }

    private void initImgPO2Image() {
        if (imgPO == null) {
            return;
        }
        image = new Image();
        imgPO2Image.copy(imgPO, image, null);
    }

}
