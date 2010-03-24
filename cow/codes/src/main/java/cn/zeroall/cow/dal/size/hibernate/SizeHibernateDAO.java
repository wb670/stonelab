package cn.zeroall.cow.dal.size.hibernate;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class SizeHibernateDAO extends HibernateDaoSupport {

    public List findDescriptions(String[] sizeName) {
        
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < sizeName.length; i++) {
            if (i == 0)
                str.append("?");
            else {
                str.append(",");
                str.append("?");
            }
        }
        return getHibernateTemplate().find("SELECT t.description FROM SizePO t WHERE t.name IN("+str+")",
                sizeName);
    }
    
    public List findSizes(String description) {
        return getHibernateTemplate().find("SELECT t.name FROM SizePO t WHERE t.description=?",
                description);
    }

}
