package cn.zeroall.cow.dal.order;

import java.util.Date;
import java.util.List;

import cn.zeroall.cow.BaseTestCase;
import cn.zeroall.cow.dal.order.dao.TradeOrderDAO;
import cn.zeroall.cow.dal.order.po.TradeOrderPO;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> May 19, 2009
 */
public class TradeOrderDAOTest extends BaseTestCase {

    private TradeOrderDAO tradeOrderDAO = (TradeOrderDAO) getBean("tradeOrderDAO");

    public void offtestAdd() {
        TradeOrderPO orderPO = new TradeOrderPO();
        orderPO.setStatus("INIT");
        orderPO.setMemberId(1);
        orderPO.setPayId(1);
        orderPO.setDeliveryId(1);
        orderPO.setMemo("memo");
        orderPO.setName("stone");
        orderPO.setTelphone("123");
        orderPO.setMobile("123");
        orderPO.setCountry("CN");
        orderPO.setProvince("P");
        orderPO.setCity("c");
        orderPO.setArea("a");
        orderPO.setAddress("address");
        orderPO.setZipcode("2");
        orderPO.setGmtCreated(new Date());
        orderPO.setGmtModified(new Date());
        Integer id = tradeOrderDAO.add(orderPO);
        System.out.println(id);
    }

    public void testGet() {
        TradeOrderPO orderPO = tradeOrderDAO.get(1);
        System.out.println(orderPO.getId());
    }

    public void testCountByMemberId() {
        System.out.println(tradeOrderDAO.countByMemberId(48));
    }

    public void testFindByMemberId() {
        List<TradeOrderPO> list = tradeOrderDAO.findByMemberId(48, 0, 10);
        for (TradeOrderPO tradeOrderPO : list) {
            System.out.println(tradeOrderPO.getId());
        }
    }

}
