package cn.zeroall.cow.dal.order;

import java.util.Date;
import java.util.List;

import cn.zeroall.cow.BaseTestCase;
import cn.zeroall.cow.dal.order.dao.OrderProductDAO;
import cn.zeroall.cow.dal.order.po.OrderProductPO;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> May 19, 2009
 */
public class OrderProductDAOTest extends BaseTestCase {

    private OrderProductDAO orderProductDAO = (OrderProductDAO) getBean("orderProductDAO");

    public void testAdd() {
        OrderProductPO orderProductPO = new OrderProductPO();
        orderProductPO.setOrderId(1);
        orderProductPO.setProductId(1);
        orderProductPO.setSize("M");
        orderProductPO.setColor("红色");
        orderProductPO.setAmount(2);
        orderProductPO.setGmtCreated(new Date());
        orderProductPO.setGmtModified(new Date());
        Integer id = orderProductDAO.add(orderProductPO);
        System.out.println(id);
    }

    public void testFindByOrderId() {
        List<OrderProductPO> list = orderProductDAO.findByOrderId(1);
        for (OrderProductPO orderProductPO : list) {
            System.out.println(orderProductPO.getId());
        }
    }

}
