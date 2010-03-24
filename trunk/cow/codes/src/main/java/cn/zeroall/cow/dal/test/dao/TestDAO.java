package cn.zeroall.cow.dal.test.dao;

import java.util.List;

import cn.zeroall.cow.dal.test.po.TestPO;

/**
 * TestDAO
 * 
 * @author Maurice.J Feb 2, 2009
 */
public interface TestDAO {

    public Integer add(TestPO testPO);

    public TestPO findById(Integer id);

    public List<TestPO> findByIds(Integer[] ids);

}
