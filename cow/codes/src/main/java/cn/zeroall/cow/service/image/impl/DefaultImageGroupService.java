package cn.zeroall.cow.service.image.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;

import cn.zeroall.cow.dal.image.dao.ImgGroupDAO;
import cn.zeroall.cow.dal.image.po.ImgGroupPO;
import cn.zeroall.cow.service.image.ImageGroupService;
import cn.zeroall.cow.service.image.ImageService;
import cn.zeroall.cow.service.image.adapter.ImageGroupAdapter;
import cn.zeroall.cow.service.image.model.Image;
import cn.zeroall.cow.service.image.model.ImageGroup;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Mar 16, 2009
 */
public class DefaultImageGroupService implements ImageGroupService {

    private ImgGroupDAO imgGroupDAO;

    private ImageService imageService;

    @Override
    public List<ImageGroup> getImageGroupDetailsByIds(Integer[] ids) {
        if (ArrayUtils.isEmpty(ids)) {
            return new ArrayList<ImageGroup>();
        }
        // 得到image groups
        List<ImgGroupPO> imgGroupPOList = imgGroupDAO.findByIds(ids);
        List<ImageGroup> imageGroups = new ArrayList<ImageGroup>(imgGroupPOList.size());
        for (ImgGroupPO imgGroupPO : imgGroupPOList) {
            imageGroups.add(new ImageGroupAdapter(imgGroupPO).getImageGroup());
        }
        // 得到images
        List<Image> images = imageService.getImagesByGroupIds(getImageGroupIds(imageGroups));
        Map<Integer, List<Image>> imageMap = getImageMapByGroup(images);
        for (ImageGroup imageGroup : imageGroups) {
            imageGroup.setImages(imageMap.get(imageGroup.getId()));
        }
        // 返回
        return imageGroups;
    }

    private Integer[] getImageGroupIds(List<ImageGroup> imageGroups) {
        Integer[] ids = new Integer[imageGroups.size()];
        for (int i = 0; i < imageGroups.size(); i++) {
            ids[i] = imageGroups.get(i).getId();
        }
        return ids;
    }

    private Map<Integer, List<Image>> getImageMapByGroup(List<Image> images) {
        Map<Integer, List<Image>> imageMap = new HashMap<Integer, List<Image>>();
        for (Image image : images) {
            if (imageMap.containsKey(image.getGroupId())) {
                imageMap.get(image.getGroupId()).add(image);
            } else {
                List<Image> list = new ArrayList<Image>();
                list.add(image);
                imageMap.put(image.getGroupId(), list);
            }
        }
        return imageMap;
    }

    public void setImgGroupDAO(ImgGroupDAO imgGroupDAO) {
        this.imgGroupDAO = imgGroupDAO;
    }

    public void setImageService(ImageService imageService) {
        this.imageService = imageService;
    }

}
