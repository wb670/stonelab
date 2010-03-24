package cn.zeroall.cow.service.order.adapter;

import net.sf.cglib.beans.BeanCopier;
import cn.zeroall.cow.dal.order.po.PaymentPO;
import cn.zeroall.cow.service.order.model.Payment;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> May 19, 2009
 */
public class PaymentAdapter {

    private static final BeanCopier model2PO = BeanCopier.create(Payment.class, PaymentPO.class, false);
    private static final BeanCopier po2Model = BeanCopier.create(PaymentPO.class, Payment.class, false);

    public Payment adpater(PaymentPO paymentPO) {
        if (paymentPO == null) {
            return null;
        }
        Payment payment = new Payment();
        po2Model.copy(paymentPO, payment, null);
        return payment;
    }

    public PaymentPO adapter(Payment payment) {
        if (payment == null) {
            return null;
        }
        PaymentPO paymentPO = new PaymentPO();
        model2PO.copy(payment, paymentPO, null);
        return paymentPO;
    }

}
