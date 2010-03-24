package cn.zeroall.cow.dal.alliance.dao;

import java.util.Date;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import cn.zeroall.cow.dal.BaseDAO;
import cn.zeroall.cow.dal.alliance.po.AlliedMemberPO;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Sep 23, 2009
 */
public class AlliedMemberDAO extends HibernateDaoSupport implements BaseDAO {

    public AlliedMemberPO findByLoginId(String loginId) {
        DetachedCriteria criteria = DetachedCriteria.forClass(AlliedMemberPO.class).add(
                Expression.eq("loginId", loginId));
        List<AlliedMemberPO> list = getHibernateTemplate().findByCriteria(criteria);
        return list.isEmpty() ? null : list.get(0);
    }

    public Integer create(AlliedMemberPO aliAlliedMemberPO) {
        if (aliAlliedMemberPO == null) {
            throw new IllegalArgumentException("aliAlliedMemberPO is null");
        }
        Date now = new Date();
        aliAlliedMemberPO.setGmtCreated(now);
        aliAlliedMemberPO.setGmtModified(now);
        return (Integer) getHibernateTemplate().save(aliAlliedMemberPO);
    }

    // 修改联盟会员信息
    public void update(AlliedMemberPO aliAlliedMemberPO) {
        if (aliAlliedMemberPO == null) {
            throw new IllegalArgumentException("aliAlliedMemberPO is null");
        }
        Date now = new Date();
        aliAlliedMemberPO.setGmtModified(now);
        getHibernateTemplate().update(aliAlliedMemberPO);
    }

    // 修改联盟会员信息密码
    public void updatePassword(AlliedMemberPO aliAlliedMemberPO) {

        if (aliAlliedMemberPO == null) {
            throw new IllegalArgumentException("aliAlliedMemberPO is null");
        }
        getHibernateTemplate().update(aliAlliedMemberPO);
    }

}
