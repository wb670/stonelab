package cn.zeroall.cow.biz.alliance.ao;

import java.util.List;

import cn.zeroall.cow.biz.BaseAO;
import cn.zeroall.cow.dal.alliance.dao.AlliedLinkDAO;
import cn.zeroall.cow.dal.alliance.po.AlliedLinkPO;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Sep 29, 2009
 */
public class LinkAO implements BaseAO {

    private AlliedLinkDAO alliedLinkDAO;

    public List<AlliedLinkPO> getImageLinks() {
        return alliedLinkDAO.findByType(AlliedLinkPO.TYPE_IMAGE);
    }

    public List<AlliedLinkPO> getLiteralLinks() {
        return alliedLinkDAO.findByType(AlliedLinkPO.TYPE_LITERAL);
    }

    public void setAlliedLinkDAO(AlliedLinkDAO alliedLinkDAO) {
        this.alliedLinkDAO = alliedLinkDAO;
    }

}
