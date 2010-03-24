package cn.zeroall.cow.dal.order;

import java.util.Date;
import java.util.List;

import cn.zeroall.cow.BaseTestCase;
import cn.zeroall.cow.dal.order.dao.DeliveryDAO;
import cn.zeroall.cow.dal.order.po.DeliveryPO;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> May 19, 2009
 */
public class DeliveryDAOTest extends BaseTestCase {

    private DeliveryDAO deliveryDAO = (DeliveryDAO) getBean("deliveryDAO");

    public void testAdd() {
        DeliveryPO deliveryPO = new DeliveryPO();
        deliveryPO.setName("平邮");
        deliveryPO.setGmtCreated(new Date());
        deliveryPO.setGmtModified(new Date());
        Integer id = deliveryDAO.add(deliveryPO);
        System.out.println(id);
    }

    public void testFindAll() {
        List<DeliveryPO> list = deliveryDAO.findAll();
        for (DeliveryPO deleveryPO : list) {
            System.out.println(deleveryPO.getId());
        }
    }

}
