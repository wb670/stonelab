package cn.zeroall.cow.service.image.model;

import java.util.Date;

import cn.zeroall.cow.service.BaseModel;
import cn.zeroall.cow.service.image.util.SumImageUrlGenUtil;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Mar 7, 2009
 */
public class Image implements BaseModel {

    private static final long serialVersionUID = 7892010570011590939L;

    private Integer id; // id号
    private Integer groupId;// 颜色id号
    private String title;// 图片标题
    private String imgName;// 图片地址
    private String sumImgName; // 图片缩略图地址
    private Date gmtCreated;// 创建时间
    private Date gmtModified;// 修改时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
        this.sumImgName = SumImageUrlGenUtil.getSumImageUrl(imgName);
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

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getSumImgName() {
        return sumImgName;
    }

    public void setSumImgName(String sumImgName) {
        this.sumImgName = sumImgName;
    }

}
