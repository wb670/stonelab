package cn.zeroall.cow.dal.product.po;

import java.util.Date;

import cn.zeroall.cow.dal.BasePO;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Feb 28, 2009
 */
public class ProductColorPO implements BasePO {

    private static final long serialVersionUID = 265422959662849734L;

    private Integer id; // id
    private Integer productId;// 产品id号
    private String colorName;// 颜色名称
    private String imgName;// 颜色图片
    private Integer imgGroupId;// 图片组id号
    private Date gmtCreated;// 创建时间
    private Date gmtModified;// 修改时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public Integer getImgGroupId() {
        return imgGroupId;
    }

    public void setImgGroupId(Integer imgGroupId) {
        this.imgGroupId = imgGroupId;
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
