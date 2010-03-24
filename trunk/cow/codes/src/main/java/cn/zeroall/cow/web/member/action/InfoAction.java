package cn.zeroall.cow.web.member.action;

import org.apache.commons.lang.StringUtils;

import cn.zeroall.cow.biz.BizException;
import cn.zeroall.cow.biz.member.MemberCode;
import cn.zeroall.cow.biz.member.ao.MemberAO;
import cn.zeroall.cow.service.member.enums.Sex;
import cn.zeroall.cow.service.member.model.Member;
import cn.zeroall.cow.service.member.model.Region;
import cn.zeroall.cow.web.BaseAction;
import cn.zeroall.cow.web.common.constants.SessionConstant;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Aug 13, 2009
 */
public class InfoAction extends BaseAction {

    private static final long serialVersionUID = -1853775995929869384L;

    private MemberAO memberAO;

    private String name;
    private String sex;
    private String country;
    private String province;
    private String city;
    private String area;
    private String address;
    private String telphone;
    private String mobile;
    private Sex[] sexes = Sex.listAll();

    protected String getLoginId() {
        Member member = (Member) getSession().get(SessionConstant.MEMBER_SESSION);
        return member != null ? member.getLoginId() : null;
    }

    /********************************************
     ** 修改信息页面
     ********************************************/
    public String modify() {
        Member member = memberAO.getMember(getLoginId());
        if (member != null) {
            name = member.getName();
            sex = (member.getSex() != null) ? member.getSex().toString() : null;
            country = member.getCountry();
            province = (member.getProvince() != null) ? String.valueOf(member.getProvince().getCode()) : null;
            city = (member.getCity() != null) ? String.valueOf(member.getCity().getCode()) : null;
            area = (member.getArea() != null) ? String.valueOf(member.getArea().getCode()) : null;
            address = member.getAddress();
            telphone = member.getTelphone();
            mobile = member.getMobile();
        }
        return SUCCESS;
    }

    /********************************************
     ** 修改信息页面
     ********************************************/
    public String doModify() {
        Member member = memberAO.getMember(getLoginId());
        fillMemberAttrs(member);
        memberAO.update(member);
        addActionMessage(MemberCode.MEMBER_MODIFY_OK.getMessage());
        return SUCCESS;
    }

    private void fillMemberAttrs(Member member) {
        member.setName(name);
        member.setSex(getSexEnum());
        member.setCountry(country);
        member.setProvince(getRegion(province));
        member.setCity(getRegion(city));
        member.setArea(getRegion(area));
        member.setAddress(address);
        member.setTelphone(telphone);
        member.setMobile(mobile);
    }

    private Sex getSexEnum() {
        if (StringUtils.isBlank(sex)) {
            return null;
        }
        return Enum.valueOf(Sex.class, sex);
    }

    private Region getRegion(String regionCode) {
        if (StringUtils.isBlank(regionCode)) {
            return null;
        }
        if (!StringUtils.isNumeric(regionCode)) {
            return null;
        }
        return new Region(Integer.valueOf(regionCode));
    }

    /********************************************
     ** 修改密码页面
     ********************************************/
    public String modifyPwd() {
        return SUCCESS;
    }

    /********************************************
     ** 修改密码
     ********************************************/
    private String password;
    private String newpassword;
    private String renewpassword;

    public String doModifyPwd() {
        try {
            memberAO.updatePwd(getLoginId(), password, newpassword);
        } catch (BizException e) {
            addActionError(e.getBizCode().getMessage());
            return INPUT;
        }
        addActionMessage(MemberCode.MEMBER_PWD_MODIFY_OK.getMessage());
        return SUCCESS;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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

    public Sex[] getSexes() {
        return sexes;
    }

    public void setSexes(Sex[] sexes) {
        this.sexes = sexes;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewpassword() {
        return newpassword;
    }

    public void setNewpassword(String newpassword) {
        this.newpassword = newpassword;
    }

    public String getRenewpassword() {
        return renewpassword;
    }

    public void setRenewpassword(String renewpassword) {
        this.renewpassword = renewpassword;
    }

    public void setMemberAO(MemberAO memberAO) {
        this.memberAO = memberAO;
    }

}
