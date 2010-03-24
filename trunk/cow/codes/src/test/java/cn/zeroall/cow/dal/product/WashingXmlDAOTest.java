package cn.zeroall.cow.dal.product;

import java.util.List;

import cn.zeroall.cow.BaseTestCase;
import cn.zeroall.cow.dal.product.dao.WashingDAO;
import cn.zeroall.cow.dal.product.po.WashingPO;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Mar 4, 2009
 */
public class WashingXmlDAOTest extends BaseTestCase {

    private WashingDAO washingDAO = (WashingDAO) getBean("washingDAO");

    public void testFindWashings() {
        List<WashingPO> list = washingDAO.findWashings();
        for (WashingPO washingPO : list) {
            System.out.println(washingPO.getId() + ":" + washingPO.getName() + ":"
                    + washingPO.getImgName());
        }
    }

}
