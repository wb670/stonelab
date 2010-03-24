package cn.zeroall.cow.dal.product.po;

import java.util.Date;

import cn.zeroall.cow.dal.BasePO;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Mar 8, 2009
 */
public class ProductOperatorPO implements BasePO {

    private static final long serialVersionUID = 8078863630454646406L;

    private Integer id; // id号
    private Integer productId;// 产品id号
    private String type;// 运营类型
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
