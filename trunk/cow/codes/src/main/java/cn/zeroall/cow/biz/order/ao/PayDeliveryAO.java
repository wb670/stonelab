package cn.zeroall.cow.biz.order.ao;

import cn.zeroall.cow.biz.BaseAO;
import cn.zeroall.cow.biz.order.vo.PayDeliveryVO;
import cn.zeroall.cow.service.order.DeliveryService;
import cn.zeroall.cow.service.order.PaymentService;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Jun 10, 2009
 */
public class PayDeliveryAO implements BaseAO {

    private PaymentService paymentService;
    private DeliveryService deliveryService;

    public PayDeliveryVO getPayDeliveryVO() {
        return new PayDeliveryVO(paymentService.findAll(), deliveryService.findAll());
    }

    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public void setDeliveryService(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

}
