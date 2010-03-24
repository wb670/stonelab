package cn.zeroall.cow.service.product.model;

import java.util.Date;
import java.util.List;

import cn.zeroall.cow.service.BaseModel;
import cn.zeroall.cow.service.common.model.KeyValueAttribute;
import cn.zeroall.cow.service.image.util.SumImageUrlGenUtil;
import cn.zeroall.cow.service.product.enums.ProductSize;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Mar 7, 2009
 */
public class Product implements BaseModel {

    private static final long serialVersionUID = 5770962487087719229L;

    private Integer id;// 产品id号
    private Integer typeId; // 产品类目id号
    private String name;// 产品标题
    private String description;// 产品详细描述
    private List<KeyValueAttribute> attributes;// 产品个性化属性
    private Double marketPrice;// 市场价
    private Double myPrice;// 产品价格
    // private List<ProductSize> sizes;// 产品尺寸
    private List sizes;// 产品尺寸
    private List sizeDescriptions;// 产品尺寸描述

    private Integer view; // 产品查看次数
    private String coverImgName;// 产品封面图片url
    private String sumCoverImgName; // 产品封面缩略图
    private Date gmtCreated; // 创建时间
    private Date gmtModified;// 修改时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCoverImgName() {
        return coverImgName;
    }

    public void setCoverImgName(String coverImgName) {
        this.coverImgName = coverImgName;
        this.sumCoverImgName = SumImageUrlGenUtil.getSumImageUrl(coverImgName);
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
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

    public List<KeyValueAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<KeyValueAttribute> attributes) {
        this.attributes = attributes;
    }

    public Double getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(Double marketPrice) {
        this.marketPrice = marketPrice;
    }

    public Double getMyPrice() {
        return myPrice;
    }

    public void setMyPrice(Double myPrice) {
        this.myPrice = myPrice;
    }

    // public List<ProductSize> getSizes() {
    // return sizes;
    // }
    //
    // public void setSizes(List<ProductSize> sizes) {
    // this.sizes = sizes;
    // }

    public Integer getView() {
        return view;
    }

    public void setView(Integer view) {
        this.view = view;
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

    public String getSumCoverImgName() {
        return sumCoverImgName;
    }

    public void setSumCoverImgName(String sumCoverImgName) {
        this.sumCoverImgName = sumCoverImgName;
    }

    public List getSizes() {
        return sizes;
    }

    public void setSizes(List sizes) {
        this.sizes = sizes;
    }

    public List getSizeDescriptions() {
        return sizeDescriptions;
    }

    public void setSizeDescriptions(List sizeDescriptions) {
        this.sizeDescriptions = sizeDescriptions;
    }

}
