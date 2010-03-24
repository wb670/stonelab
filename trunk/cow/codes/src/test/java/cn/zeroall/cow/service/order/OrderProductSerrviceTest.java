package cn.zeroall.cow.service.order;

import java.util.Date;

import cn.zeroall.cow.BaseTestCase;
import cn.zeroall.cow.service.order.model.OrderProduct;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> May 19, 2009
 */
public class OrderProductSerrviceTest extends BaseTestCase {

    private OrderProductService orderProductService = (OrderProductService) getBean("orderProductService");

    public void testAdd() {
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setAmount(2);
        orderProduct.setColor("color");
        orderProduct.setGmtCreated(new Date());
        orderProduct.setGmtModified(new Date());
        orderProduct.setOrderId(1);
        orderProduct.setProductId(1);
        orderProduct.setSize("M");
        orderProductService.add(orderProduct);
    }

}
