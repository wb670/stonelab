package cn.zeroall.cow.biz.alliance.ao;

import org.apache.commons.lang.StringUtils;

import cn.zeroall.cow.biz.BaseAO;
import cn.zeroall.cow.common.util.Md5Util;
import cn.zeroall.cow.dal.alliance.dao.AlliedMemberDAO;
import cn.zeroall.cow.dal.alliance.po.AlliedMemberPO;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> May 2, 2009
 */
public class AlliedMemberLoginAO implements BaseAO {

    private AlliedMemberDAO alliedMemberDAO;

    public boolean isLogin(String loginId, String password) {

        if (StringUtils.isBlank(loginId)) {
            //throw new IllegalArgumentException("loginId is blank.");
        }
        AlliedMemberPO alliendmemberPO = alliedMemberDAO.findByLoginId(StringUtils.trim(loginId));
        if (alliendmemberPO == null) {
            return false;
        }
        String encrypted = Md5Util.encrypt(password, alliendmemberPO.getSeed());
        return StringUtils.equals(encrypted, alliendmemberPO.getPassword());

    }

    public AlliedMemberPO getLoginedMember(String loginId) {
        return alliedMemberDAO.findByLoginId(loginId);
    }

    public void setAlliedMemberDAO(AlliedMemberDAO alliedMemberDAO) {
        this.alliedMemberDAO = alliedMemberDAO;
    }

}
