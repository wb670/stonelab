package cn.zeroall.cow.biz.alliance.ao;

import java.util.Date;

import net.sf.cglib.beans.BeanCopier;

import org.springframework.util.Assert;

import cn.zeroall.cow.biz.BaseAO;
import cn.zeroall.cow.biz.BizException;
import cn.zeroall.cow.biz.alliance.AlliedMemberCode;
import cn.zeroall.cow.common.util.Md5Util;
import cn.zeroall.cow.dal.alliance.dao.AlliedMemberDAO;
import cn.zeroall.cow.dal.alliance.po.AlliedMemberPO;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Sep 24, 2009
 */
public class AlliedMemberAO implements BaseAO {

    private static final BeanCopier COPIER = BeanCopier.create(AlliedMemberPO.class,
            AlliedMemberPO.class, false);

    private AlliedMemberDAO alliedMemberDAO;

    private AlliedMemberLoginAO alliedLoginAO;// 联盟会员登陆的ao类

    private AlliedMemberPO cloneAlliedMember(AlliedMemberPO alliedMember) {
        AlliedMemberPO clone = new AlliedMemberPO();
        COPIER.copy(alliedMember, clone, null);
        return clone;
    }

    public Integer add(AlliedMemberPO alliedMember) throws BizException {
        Assert.notNull(alliedMember, "aliAlliedMember is null");
        // 已经存在
        AlliedMemberPO existed = alliedMemberDAO.findByLoginId(alliedMember.getLoginId());
        if (existed != null) {
            throw new BizException(AlliedMemberCode.DUPLICATE_LOGIN_ID);
        }
        alliedMember.setSeed(String.valueOf(System.currentTimeMillis()));
        alliedMember.setPassword(Md5Util
                .encrypt(alliedMember.getPassword(), alliedMember.getSeed()));
        return alliedMemberDAO.create(alliedMember);
    }

    // 修改联盟会员信息
    public void update(AlliedMemberPO alliedMember) throws BizException {

        if (alliedMember == null) {
            throw new IllegalArgumentException("alliedMember is null");
        }
        AlliedMemberPO memberPO = alliedMemberDAO.findByLoginId(alliedMember.getLoginId());
        AlliedMemberPO alliedCloneMember = this.cloneAlliedMember(alliedMember);// 附件
        Date now = new Date();
        alliedCloneMember.setId(memberPO.getId());
        alliedCloneMember.setLoginId(alliedCloneMember.getLoginId());
        alliedCloneMember.setPassword(memberPO.getPassword());
        alliedCloneMember.setSeed(memberPO.getSeed());
        alliedCloneMember.setGmtCreated(memberPO.getGmtCreated());
        alliedCloneMember.setGmtModified(now);
        alliedMemberDAO.update(alliedCloneMember);
    }

    // 修改联盟会员信息密码
    public void updatePwd(String loginId, String password, String newPassword) throws BizException {
        boolean isLogin = alliedLoginAO.isLogin(loginId, password);
        if (!isLogin) {
            throw new BizException(AlliedMemberCode.MEMBER_PWD_INCORRECT);
        }
        AlliedMemberPO memberPO = alliedMemberDAO.findByLoginId(loginId);
        if (memberPO == null) {
            return;// do nothing
        }
        String encrypted = Md5Util.encrypt(newPassword, memberPO.getSeed());

        memberPO.setPassword(encrypted);
        alliedMemberDAO.updatePassword(memberPO);
    }

    public AlliedMemberPO getAlliedMember(String loginId) {
        return alliedMemberDAO.findByLoginId(loginId);
    }

    public void setAlliedMemberDAO(AlliedMemberDAO alliedMemberDAO) {
        this.alliedMemberDAO = alliedMemberDAO;
    }

    public void setAlliedLoginAO(AlliedMemberLoginAO alliedLoginAO) {
        this.alliedLoginAO = alliedLoginAO;
    }

}
