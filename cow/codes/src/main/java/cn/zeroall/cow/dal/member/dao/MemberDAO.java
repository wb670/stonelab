package cn.zeroall.cow.dal.member.dao;

import cn.zeroall.cow.dal.BaseDAO;
import cn.zeroall.cow.dal.member.po.MemberPO;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Apr 30, 2009
 */
public interface MemberDAO extends BaseDAO {

    public Integer create(MemberPO memberPO);
    
    public MemberPO findById(Integer id);

    public MemberPO findByLoginId(String loginId);

    public void updateById(MemberPO memberPO);

    public void updatePasswordById(Integer id, String password);

}
