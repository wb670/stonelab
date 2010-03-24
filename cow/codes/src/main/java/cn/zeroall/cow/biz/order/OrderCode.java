package cn.zeroall.cow.biz.order;

import java.util.Locale;

import cn.zeroall.cow.biz.BizCode;
import cn.zeroall.cow.common.util.ResourceBundleUtil;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Jun 4, 2009
 */
public enum OrderCode implements BizCode {
    CART_EMPTY, REGION_BUILD_ERROR, TRADE_ORDER_NOT_EXIST, TRADE_ORDER_NEED_NOT_PAY, PAY_ERROR, MEMBER_ID_NOT_MATCHED;

    private ResourceBundleUtil resourceBundleUtil = new ResourceBundleUtil("bizcode.order");

    @Override
    public String getMessage() {
        return getMessage(Locale.getDefault());
    }

    @Override
    public String getMessage(Locale locale) {
        return resourceBundleUtil.getMessage(this.toString(), locale);
    }

}
