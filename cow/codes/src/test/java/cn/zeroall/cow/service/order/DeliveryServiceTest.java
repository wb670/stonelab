package cn.zeroall.cow.service.order;

import java.util.List;

import cn.zeroall.cow.BaseTestCase;
import cn.zeroall.cow.service.order.model.Delivery;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> May 19, 2009
 */
public class DeliveryServiceTest extends BaseTestCase {

    private DeliveryService deliveryService = (DeliveryService) getBean("deliveryService");

    public void testGetAll() {
        List<Delivery> deliverys = deliveryService.findAll();
        for (Delivery each : deliverys) {
            System.out.println(each.getId());
        }
    }

}
