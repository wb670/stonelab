package cn.zeroall.cow.biz.order.vo;

import java.util.List;

import cn.zeroall.cow.biz.BaseVO;
import cn.zeroall.cow.service.order.model.Delivery;
import cn.zeroall.cow.service.order.model.Payment;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Jun 10, 2009
 */
public class PayDeliveryVO implements BaseVO {

    private static final long serialVersionUID = -8917974043597470920L;

    private List<Payment> payments;
    private List<Delivery> deliverys;

    public PayDeliveryVO(List<Payment> payments, List<Delivery> deliverys) {
        this.deliverys = deliverys;
        this.payments = payments;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public List<Delivery> getDeliverys() {
        return deliverys;
    }

    public void setDeliverys(List<Delivery> deliverys) {
        this.deliverys = deliverys;
    }

}
