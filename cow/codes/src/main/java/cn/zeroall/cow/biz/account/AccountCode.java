package cn.zeroall.cow.biz.account;

import java.util.Locale;

import cn.zeroall.cow.common.enums.EnumType;
import cn.zeroall.cow.common.util.ResourceBundleUtil;

public enum AccountCode implements EnumType {

    /** 金额表类型（长期） */
    ACCOUNT_DETAIL_LONG_TYPE,
    /** 金额表类型（短期） */
    ACCOUNT_DETAIL_SHORT_TYPE,
    /** 交易表类型（收入） */
    ACCOUNT_TREDE__DETAIL_INPUT_TYPE,
    /** 交易表类型（支出） */
    ACCOUNT_TREDE__DETAIL_OUTPUT_TYPE,
    /** 状态（有效） */
    ACCOUNT_DETAIL_IN_EFFECT_STATUS,
    /** 状态（无效） */
    ACCOUNT_DETAIL_DISABLE_STATUS;

    private ResourceBundleUtil resourceBundleUtil = new ResourceBundleUtil("enumtype.account");

    @Override
    public String getMessage() {
        return getMessage(Locale.getDefault());
    }

    @Override
    public String getMessage(Locale locale) {
        return resourceBundleUtil.getMessage("AccountCode." + this.toString(), locale);
    }

}
