package cn.zeroall.cow.service.order.model;

import java.util.Date;

import javax.persistence.Column;

import cn.zeroall.cow.service.BaseModel;
import cn.zeroall.cow.service.member.model.Region;
import cn.zeroall.cow.service.order.enums.TradeOrderStatus;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> May 19, 2009
 */
public class TradeOrder implements BaseModel {

    private static final long serialVersionUID = 1891785270757272083L;

    private Integer id;
    private TradeOrderStatus status;
    private Integer memberId;
    private Double totalPrice;
    private Integer payId;
    private Integer deliveryId;
    private String memo;
    private String name;
    private String telphone;
    private String mobile;
    private String country;
    private String province;
    private String city;
    private String area;
    private String address;
    private String zipcode;
    private Date gmtCreated;
    private Date gmtModified;

    private Region provinceRegion;
    private Region cityRegion;
    private Region areaRegion;

    private Double productPrice=0.0;// 订单中，所有产品需要的金额
    private Double deliveryPrice=0.0;// 订单中，选择的物流方式需要的金额
    private Double accountPaymentPrice=0.0;// 使用爱可帐户支付的金额（已支付的）
    private Double paymentPrice=0.0;// 选择其他支付方式，需要支付的金额

    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TradeOrderStatus getStatus() {
        return status;
    }

    public void setStatus(TradeOrderStatus status) {
        this.status = status;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getPayId() {
        return payId;
    }

    public void setPayId(Integer payId) {
        this.payId = payId;
    }

    public Integer getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(Integer deliveryId) {
        this.deliveryId = deliveryId;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
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

    public Region getProvinceRegion() {
        return provinceRegion;
    }

    public void setProvinceRegion(Region provinceRegion) {
        this.provinceRegion = provinceRegion;
    }

    public Region getCityRegion() {
        return cityRegion;
    }

    public void setCityRegion(Region cityRegion) {
        this.cityRegion = cityRegion;
    }

    public Region getAreaRegion() {
        return areaRegion;
    }

    public void setAreaRegion(Region areaRegion) {
        this.areaRegion = areaRegion;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public Double getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(Double deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public Double getAccountPaymentPrice() {
        return accountPaymentPrice;
    }

    public void setAccountPaymentPrice(Double accountPaymentPrice) {
        this.accountPaymentPrice = accountPaymentPrice;
    }

    public Double getPaymentPrice() {
        return paymentPrice;
    }

    public void setPaymentPrice(Double paymentPrice) {
        this.paymentPrice = paymentPrice;
    }

}
