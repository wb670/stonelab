package cn.zeroall.cow.dal.comment.hibernate;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import org.hibernate.criterion.Projections;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.zeroall.cow.biz.comment.CommentCode;
import cn.zeroall.cow.dal.product.po.CommentPO;

public class CommentHibernateDAO extends HibernateDaoSupport {

    public Integer add(CommentPO commentPO) {
        return (Integer) getHibernateTemplate().save(commentPO);
    }

    public CommentPO get(Integer id) {
        return (CommentPO) getHibernateTemplate().get(CommentPO.class, id);
    }

    public void update(CommentPO commentPO) {
        commentPO.setGmtModified(new Date());
        getHibernateTemplate().update(commentPO);
    }

    public void delete(CommentPO commentPO) {
        getHibernateTemplate().delete(commentPO);
    }

    public Integer getRowCount(int productId) {
        DetachedCriteria criteria = DetachedCriteria.forClass(CommentPO.class).add(
                Expression.eq("productId", productId)).add(Expression.eq("status",CommentCode.ENABLED.getMessage())).setProjection(Projections.rowCount());
        return (Integer) getHibernateTemplate().findByCriteria(criteria).get(0);
    }

    public List<CommentPO> findComments(final int start, final int limit, final int productId) {

        final String status=CommentCode.ENABLED.getMessage();
        List<CommentPO> list = super.getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                return session.createQuery(
                        "FROM  CommentPO t WHERE t.productId=? AND t.status=? ORDER BY t.gmtCreated DESC").setParameter(0,
                        productId).setString(1,status).setFirstResult(start).setMaxResults(limit).list();

            }
        });
        return list;

    }

    public List<CommentPO> findHotComments() {

        final String status=CommentCode.ENABLED.getMessage();
        List<CommentPO> list = super.getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                return session
                        .createQuery(
                                "FROM  CommentPO t WHERE t.rank>? AND t.status=?  ORDER BY t.referenceNum DESC,t.gmtCreated DESC")
                        .setParameter(0, 3).setString(1,status).setFirstResult(0).setMaxResults(6).list();

            }
        });

        return list;

    }

    public List<CommentPO> getByProductId(Integer productId) {
        DetachedCriteria criteria = DetachedCriteria.forClass(CommentPO.class).add(
                Expression.eq("productId", productId)).addOrder(Order.desc("id"));
        return getHibernateTemplate().findByCriteria(criteria);
    }

}
