package cn.zeroall.cow.web.order.action;

import cn.zeroall.cow.biz.order.ao.PayDeliveryAO;
import cn.zeroall.cow.biz.order.vo.PayDeliveryVO;
import cn.zeroall.cow.web.BaseAction;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Jun 10, 2009
 */
public class PayDeliveryAction extends BaseAction {

    private static final long serialVersionUID = 4948857786038197924L;

    private PayDeliveryVO payDeliveryVO;

    private PayDeliveryAO payDeliveryAO;

    public String list() throws Exception {
        payDeliveryVO = payDeliveryAO.getPayDeliveryVO();
        return SUCCESS;
    }

    public PayDeliveryVO getPayDeliveryVO() {
        return payDeliveryVO;
    }

    public void setPayDeliveryVO(PayDeliveryVO payDeliveryVO) {
        this.payDeliveryVO = payDeliveryVO;
    }

    public void setPayDeliveryAO(PayDeliveryAO payDeliveryAO) {
        this.payDeliveryAO = payDeliveryAO;
    }

}
