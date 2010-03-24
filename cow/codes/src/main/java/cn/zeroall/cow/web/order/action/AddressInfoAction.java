package cn.zeroall.cow.web.order.action;

import java.util.List;

import cn.zeroall.cow.biz.order.ao.AddressInfoAO;
import cn.zeroall.cow.biz.order.vo.AddressInfoVO;
import cn.zeroall.cow.service.member.model.Member;
import cn.zeroall.cow.web.BaseAction;
import cn.zeroall.cow.web.common.constants.SessionConstant;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Sep 13, 2009
 */
public class AddressInfoAction extends BaseAction {

    private static final long serialVersionUID = -5428273220690687308L;

    private AddressInfoAO addressInfoAO;

    private List<AddressInfoVO> addresses;

    public String list() {
        addresses = addressInfoAO.getAddresses(getMemberId());
        return SUCCESS;
    }

    protected Integer getMemberId() {
        Member member = (Member) getSession().get(SessionConstant.MEMBER_SESSION);
        return member != null ? member.getId() : null;
    }

    public List<AddressInfoVO> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressInfoVO> addresses) {
        this.addresses = addresses;
    }

    public void setAddressInfoAO(AddressInfoAO addressInfoAO) {
        this.addressInfoAO = addressInfoAO;
    }

}
