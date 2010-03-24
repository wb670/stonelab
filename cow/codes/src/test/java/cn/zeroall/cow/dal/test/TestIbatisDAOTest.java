package cn.zeroall.cow.dal.test;

import java.util.List;

import cn.zeroall.cow.BaseTestCase;
import cn.zeroall.cow.dal.test.dao.TestDAO;
import cn.zeroall.cow.dal.test.po.TestPO;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Mar 5, 2009
 */
public class TestIbatisDAOTest extends BaseTestCase {

    private static final Integer ID = 1;

    private TestDAO testDAO = (TestDAO) getBean("testDAO");

    public void testFindById() {
        TestPO testPO = testDAO.findById(ID);
        System.out.println(testPO.getName());
    }

    public void testFindByIds() {
        List<TestPO> list = testDAO.findByIds(new Integer[] { 1, 2, 3 });
        for (TestPO testPO : list) {
            System.out.println(testPO.getId());
        }
    }
}
