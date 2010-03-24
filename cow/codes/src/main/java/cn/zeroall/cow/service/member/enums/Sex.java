package cn.zeroall.cow.service.member.enums;

import java.util.Locale;

import cn.zeroall.cow.common.enums.EnumType;
import cn.zeroall.cow.common.util.ResourceBundleUtil;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Apr 30, 2009
 */
public enum Sex implements EnumType {

    M, F;

    private static Sex[] all = { F, M };

    private ResourceBundleUtil resourceBundleUtil = new ResourceBundleUtil("enumtype.member");

    @Override
    public String getMessage() {
        return getMessage(Locale.getDefault());
    }

    @Override
    public String getMessage(Locale locale) {
        return resourceBundleUtil.getMessage("Sex." + this.toString(), locale);
    }

    public static Sex[] listAll() {
        return all;
    }
}
