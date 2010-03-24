package cn.zeroall.cow.biz.member;

import java.util.Locale;

import cn.zeroall.cow.biz.BizCode;
import cn.zeroall.cow.common.util.ResourceBundleUtil;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Feb 15, 2009
 */
public enum MemberCode implements BizCode {

    /** 重复的loginId */
    DUPLICATE_LOGIN_ID,
    /** 登录失败 */
    LOGIN_ERROR,
    /** member不存在 */
    MEMBER_NOT_FOUND,
    /** member密码不正确 */
    MEMBER_PWD_INCORRECT,
    /** 密码修改成功 */
    MEMBER_PWD_MODIFY_OK,
    /** 会员信息修改成功 */
    MEMBER_MODIFY_OK,
    /** 电子邮件添加成功 */
    EMAIL_ADD_OK,
    /** 电子邮件格式不对 */
    EMAIL_FORMAT_ERROR;
    
    

    private transient ResourceBundleUtil resourceBundleUtil = new ResourceBundleUtil("bizcode.member");

    @Override
    public String getMessage() {
        return getMessage(Locale.getDefault());
    }

    @Override
    public String getMessage(Locale locale) {
        return resourceBundleUtil.getMessage(this.toString(), locale);
    }

}
