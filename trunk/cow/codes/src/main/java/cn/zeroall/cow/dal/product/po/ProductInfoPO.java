package cn.zeroall.cow.dal.product.po;

import java.util.Date;

import cn.zeroall.cow.dal.BasePO;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Feb 28, 2009
 */
public class ProductInfoPO implements BasePO {

    private static final long serialVersionUID = 5942732982510577551L;

    private Integer id;// 产品id号
    private Integer typeId; // 产品类目id号
    private String name;// 产品标题
    private String description;// 产品详细描述
    private String attributes;// 产品个性化属性
    private Double marketPrice;// 市场价
    private Double myPrice;// 产品价格
    private String sizes;// 产品尺寸
    private String sizeDescriptions;// 产品尺寸描述
    private Integer view; // 产品查看次数
    private String coverImgName;// 封面图片url
    private Date gmtCreated; // 创建时间
    private Date gmtModified;// 修改时间

    public String getCoverImgName() {
        return coverImgName;
    }

    public void setCoverImgName(String coverImgName) {
        this.coverImgName = coverImgName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
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

    public String getSizes() {
        return sizes;
    }

    public void setSizes(String sizes) {
        this.sizes = sizes;
    }

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

    public String getSizeDescriptions() {
        return sizeDescriptions;
    }

    public void setSizeDescriptions(String sizeDescriptions) {
        this.sizeDescriptions = sizeDescriptions;
    }

}
