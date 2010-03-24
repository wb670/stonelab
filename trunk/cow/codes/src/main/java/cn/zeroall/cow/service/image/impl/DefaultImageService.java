package cn.zeroall.cow.service.image.impl;

import java.util.ArrayList;
import java.util.List;

import cn.zeroall.cow.dal.image.dao.ImgDAO;
import cn.zeroall.cow.dal.image.po.ImgPO;
import cn.zeroall.cow.service.image.ImageService;
import cn.zeroall.cow.service.image.adapter.ImageAdapter;
import cn.zeroall.cow.service.image.model.Image;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Mar 16, 2009
 */
public class DefaultImageService implements ImageService {

    private ImgDAO imgDAO;

    @Override
    public List<Image> getImagesByIds(Integer[] ids) {
        List<ImgPO> imgPOList = imgDAO.findByIds(ids);
        return toImages(imgPOList);
    }

    @Override
    public List<Image> getImagesByGroupIds(Integer[] groupIds) {
        List<ImgPO> imgPOList = imgDAO.findByGroupIds(groupIds);
        return toImages(imgPOList);
    }

    private List<Image> toImages(List<ImgPO> imgPOList) {
        List<Image> images = new ArrayList<Image>(imgPOList.size());
        for (ImgPO imgPO : imgPOList) {
            images.add(new ImageAdapter(imgPO).getImage());
        }
        return images;
    }

    public void setImgDAO(ImgDAO imgDAO) {
        this.imgDAO = imgDAO;
    }

}
