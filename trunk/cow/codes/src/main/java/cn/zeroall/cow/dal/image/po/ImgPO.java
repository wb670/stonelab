package cn.zeroall.cow.dal.image.po;

import java.util.Date;

import cn.zeroall.cow.dal.BasePO;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Mar 5, 2009
 */
public class ImgPO implements BasePO {

    private static final long serialVersionUID = 1546863274836535467L;

    private Integer id; // id号
    private Integer groupId;// 相册id号
    private String title;// 图片标题
    private String imgName;// 图片地址
    private Integer orderId;// 图片排序值
    private Date gmtCreated;// 创建时间
    private Date gmtModified;// 修改时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
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
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
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

}
