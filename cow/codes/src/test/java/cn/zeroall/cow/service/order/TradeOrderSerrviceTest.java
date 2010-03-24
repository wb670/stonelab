package cn.zeroall.cow.service.order;

import java.util.Date;

import cn.zeroall.cow.BaseTestCase;
import cn.zeroall.cow.service.order.enums.TradeOrderStatus;
import cn.zeroall.cow.service.order.model.TradeOrder;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> May 19, 2009
 */
public class TradeOrderSerrviceTest extends BaseTestCase {

    private TradeOrderService tradeOrderService = (TradeOrderService) getBean("tradeOrderService");

    public void testAdd() {
        TradeOrder tradeOrder = new TradeOrder();
        tradeOrder.setStatus(TradeOrderStatus.INITIALIZED);
        tradeOrder.setMemberId(1);
        tradeOrder.setPayId(1);
        tradeOrder.setDeliveryId(1);
        tradeOrder.setMemo("memo");
        tradeOrder.setName("stone");
        tradeOrder.setTelphone("123");
        tradeOrder.setMobile("123");
        tradeOrder.setCountry("CN");
        tradeOrder.setProvince("P");
        tradeOrder.setCity("c");
        tradeOrder.setArea("a");
        tradeOrder.setAddress("address");
        tradeOrder.setZipcode("2");
        tradeOrder.setGmtCreated(new Date());
        tradeOrder.setGmtModified(new Date());
        System.out.println(tradeOrderService.add(tradeOrder));
    }

}
