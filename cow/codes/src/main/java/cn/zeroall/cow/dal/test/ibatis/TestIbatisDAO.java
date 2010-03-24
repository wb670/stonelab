package cn.zeroall.cow.dal.test.ibatis;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import cn.zeroall.cow.dal.test.dao.TestDAO;
import cn.zeroall.cow.dal.test.po.TestPO;

/**
 * TestIbatisDAO
 * 
 * @author Maurice.J Feb 2, 2009
 */
public class TestIbatisDAO extends SqlMapClientDaoSupport implements TestDAO {

    @Override
    public TestPO findById(Integer id) {
        return (TestPO) getSqlMapClientTemplate().queryForObject("Test.find-by-id", id);
    }

    @Override
    public Integer add(TestPO testPO) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<TestPO> findByIds(Integer[] ids) {
        // TODO Auto-generated method stub
        return null;
    }

}
