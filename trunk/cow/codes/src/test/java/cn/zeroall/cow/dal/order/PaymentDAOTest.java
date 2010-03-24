package cn.zeroall.cow.dal.order;

import java.util.Date;
import java.util.List;

import cn.zeroall.cow.BaseTestCase;
import cn.zeroall.cow.dal.order.dao.PaymentDAO;
import cn.zeroall.cow.dal.order.po.PaymentPO;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> May 19, 2009
 */
public class PaymentDAOTest extends BaseTestCase {

    private PaymentDAO paymentDAO = (PaymentDAO) getBean("paymentDAO");

    public void testAdd() {
        PaymentPO paymentPO = new PaymentPO();
        paymentPO.setName("邮局汇款");
        paymentPO.setDescription("*邮局帐号：1234567891234567894 收款人：独孤一侠");
        paymentPO.setGmtCreated(new Date());
        paymentPO.setGmtModified(new Date());
        Integer id = paymentDAO.add(paymentPO);
        System.out.println(id);
    }

    public void testFindAll() {
        List<PaymentPO> list = paymentDAO.findAll();
        for (PaymentPO paymentPO : list) {
            System.out.println(paymentPO.getId());
        }

    }

}
