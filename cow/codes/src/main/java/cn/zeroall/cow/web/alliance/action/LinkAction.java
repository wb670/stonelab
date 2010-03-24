package cn.zeroall.cow.web.alliance.action;

import java.util.List;

import cn.zeroall.cow.biz.alliance.ao.LinkAO;
import cn.zeroall.cow.dal.alliance.po.AlliedLinkPO;
import cn.zeroall.cow.dal.alliance.po.AlliedMemberPO;
import cn.zeroall.cow.web.BaseAction;
import cn.zeroall.cow.web.common.constants.SessionConstant;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Sep 29, 2009
 */
public class LinkAction extends BaseAction {

    private static final long serialVersionUID = 2417172166435218527L;

    private LinkAO linkAO;

    private List<AlliedLinkPO> links;
    private AlliedMemberPO alliedSession;

    public String imageLinks() {
        links = linkAO.getImageLinks();
        alliedSession = (AlliedMemberPO) getSession().get(SessionConstant.ALLIED_MEMBER_SESSION);
        return SUCCESS;
    }

    public String literalLinks() {
        links = linkAO.getLiteralLinks();
        alliedSession = (AlliedMemberPO) getSession().get(SessionConstant.ALLIED_MEMBER_SESSION);
        return SUCCESS;
    }

    public List<AlliedLinkPO> getLinks() {
        return links;
    }

    public void setLinks(List<AlliedLinkPO> links) {
        this.links = links;
    }

    public AlliedMemberPO getAlliedSession() {
        return alliedSession;
    }

    public void setAlliedSession(AlliedMemberPO alliedSession) {
        this.alliedSession = alliedSession;
    }

    public void setLinkAO(LinkAO linkAO) {
        this.linkAO = linkAO;
    }

}
