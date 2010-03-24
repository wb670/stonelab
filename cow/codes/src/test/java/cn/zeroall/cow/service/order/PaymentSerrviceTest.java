package cn.zeroall.cow.service.order;

import java.util.List;

import cn.zeroall.cow.BaseTestCase;
import cn.zeroall.cow.service.order.model.Payment;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> May 19, 2009
 */
public class PaymentSerrviceTest extends BaseTestCase {

    private PaymentService paymentService = (PaymentService) getBean("paymentService");

    public void testGetAll() {
        List<Payment> list = paymentService.findAll();
        for (Payment each : list) {
            System.out.println(each.getId());
        }
    }

}
