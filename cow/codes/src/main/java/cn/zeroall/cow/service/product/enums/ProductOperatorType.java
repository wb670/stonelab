package cn.zeroall.cow.service.product.enums;

import java.util.Locale;

import cn.zeroall.cow.common.enums.EnumType;
import cn.zeroall.cow.common.util.ResourceBundleUtil;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Mar 8, 2009
 */
public enum ProductOperatorType implements EnumType {
    NEW, RECOMMEND, HOT;

    private ResourceBundleUtil resourceBundleUtil = new ResourceBundleUtil("enumtype.product");

    @Override
    public String getMessage() {
        return getMessage(Locale.getDefault());
    }

    @Override
    public String getMessage(Locale locale) {
        return resourceBundleUtil.getMessage("ProductOperatorType" + this.toString(), locale);
    }

}
