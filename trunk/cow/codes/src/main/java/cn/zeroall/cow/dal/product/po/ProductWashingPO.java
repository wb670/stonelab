package cn.zeroall.cow.dal.product.po;

import java.util.Date;

import cn.zeroall.cow.dal.BasePO;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Feb 28, 2009
 */
public class ProductWashingPO implements BasePO {

    private static final long serialVersionUID = 3572687059672187263L;

    private Integer id;// id
    private Integer productId;// 产品id号
    private Integer washingId;// 洗涤方式id号
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

    public Integer getWashingId() {
        return washingId;
    }

    public void setWashingId(Integer washingId) {
        this.washingId = washingId;
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
