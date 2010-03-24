package cn.zeroall.cow.service.order.enums;

import java.util.Locale;

import cn.zeroall.cow.common.enums.EnumType;
import cn.zeroall.cow.common.util.ResourceBundleUtil;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> May 19, 2009
 */
public enum TradeOrderStatus implements EnumType {
    /** 已下单 */
    INITIALIZED,
    /** 已付款 */
    PAID,
    /** 已发货 */
    DELIVERED,
    /** 失败 */
    FAIL,
    /** 取消 */
    DELETED,
    /** 成功 */
    SUCCESS,
    /** 购物券余额不足，需要补足选中其它方式支付 */
    NO_CHECK_ACCOUNT;
    
    

    private ResourceBundleUtil resourceBundleUtil = new ResourceBundleUtil("enumtype.order");

    @Override
    public String getMessage() {
        return getMessage(Locale.getDefault());
    }

    @Override
    public String getMessage(Locale locale) {
        return resourceBundleUtil.getMessage("TradeOrderStatus." + this.toString(), locale);
    }

}
