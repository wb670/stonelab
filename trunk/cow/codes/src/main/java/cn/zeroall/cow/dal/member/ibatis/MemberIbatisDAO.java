package cn.zeroall.cow.dal.member.ibatis;

import java.util.HashMap;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import cn.zeroall.cow.dal.member.dao.MemberDAO;
import cn.zeroall.cow.dal.member.po.MemberPO;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Apr 30, 2009
 */
public class MemberIbatisDAO extends SqlMapClientDaoSupport implements MemberDAO {

    @Override
    public Integer create(MemberPO memberPO) {
        return (Integer) getSqlMapClientTemplate().insert("Member.insert", memberPO);
    }

    @Override
    public MemberPO findById(Integer id) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("id", id);
        return (MemberPO) getSqlMapClientTemplate().queryForObject("Member.find-by-id", param);
    }

    @Override
    public MemberPO findByLoginId(String loginId) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("loginId", loginId);
        return (MemberPO) getSqlMapClientTemplate().queryForObject("Member.find-by-login-id", param);
    }

    @Override
    public void updateById(MemberPO memberPO) {
        getSqlMapClientTemplate().update("Member.update-by-id", memberPO);
    }

    @Override
    public void updatePasswordById(Integer id, String password) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("id", id);
        param.put("password", password);
        getSqlMapClientTemplate().update("Member.update-password-by-id", param);
    }

}
