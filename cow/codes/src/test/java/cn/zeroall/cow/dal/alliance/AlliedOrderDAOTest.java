package cn.zeroall.cow.dal.alliance;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;

import cn.zeroall.cow.BaseTestCase;
import cn.zeroall.cow.dal.alliance.dao.AlliedOrderDAO;
import cn.zeroall.cow.dal.alliance.po.AlliedOrderPO;
import cn.zeroall.cow.dal.order.po.TradeOrderPO;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Oct 10, 2009
 */
public class AlliedOrderDAOTest extends BaseTestCase {

    private AlliedOrderDAO alliedOrderDAO = (AlliedOrderDAO) getBean("alliedOrderDAO");

    public void testFindByOrderDate() {
        List<Object[]> list = alliedOrderDAO.findByOrderDate(6, DateUtils.addDays(new Date(), -2), DateUtils.addDays(
                new Date(), 1));
        for (Object[] objects : list) {
            AlliedOrderPO alliedOrderPO = (AlliedOrderPO) objects[0];
            TradeOrderPO tradeOrderPO = (TradeOrderPO) objects[1];
            System.out.println(alliedOrderPO.getId() + ":" + tradeOrderPO.getId());
        }
    }

}
