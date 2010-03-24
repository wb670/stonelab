package cn.zeroall.cow.service.image.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.zeroall.cow.service.BaseModel;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Mar 16, 2009
 */
public class ImageGroup implements BaseModel {

    private static final long serialVersionUID = -7498416974002039728L;

    private Integer id; // id号
    private String name; // 相册名
    private String description; // 相册描述
    private Date gmtCreated; // 创建时间
    private Date gmtModified;// 修改时间

    private List<Image> images = new ArrayList<Image>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

}
