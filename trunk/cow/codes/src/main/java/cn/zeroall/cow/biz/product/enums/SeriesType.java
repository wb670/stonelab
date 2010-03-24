package cn.zeroall.cow.biz.product.enums;

import java.util.Locale;

import cn.zeroall.cow.common.enums.EnumType;
import cn.zeroall.cow.common.util.ResourceBundleUtil;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Jun 30, 2009
 */
public enum SeriesType implements EnumType {

    /** 宝宝系列 */
    BABY,
    /** 妈妈系列 */
    MAMA;

    private ResourceBundleUtil resourceBundleUtil = new ResourceBundleUtil("enumtype.product");

    @Override
    public String getMessage() {
        return getMessage(Locale.getDefault());
    }

    @Override
    public String getMessage(Locale locale) {
        return resourceBundleUtil.getMessage("SeriesType." + this.toString(), locale);
    }

}
