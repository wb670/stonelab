package cn.zeroall.cow.service.product.enums;

import java.util.Locale;

import cn.zeroall.cow.common.enums.EnumType;
import cn.zeroall.cow.common.util.ResourceBundleUtil;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Mar 7, 2009
 */
public enum ProductSize implements EnumType {

    /** 婴儿尺寸 */
    L1, L2, L3, L4, L5,

    /** 孕妇尺寸 */
    S, M, L, XL, XXL, AVG;

    private static final ProductSize[] babySize = { L1, L2, L3, L4, L5 };
    private static final ProductSize[] adultSize = { S, M, L, XL, XXL, AVG };
    private static final ProductSize[] all = { L1, L2, L3, L4, L5, S, M, L, XL, XXL, AVG };

    private ResourceBundleUtil resourceBundleUtil = new ResourceBundleUtil("enumtype.product");

    @Override
    public String getMessage() {
        return getMessage(Locale.getDefault());
    }

    @Override
    public String getMessage(Locale locale) {
        return resourceBundleUtil.getMessage("ProductSize." + this.toString(), locale);
    }

    public static ProductSize[] listBabySize() {
        return babySize;
    }

    public static ProductSize[] listAdultSize() {
        return adultSize;
    }

    public static ProductSize[] listAll() {
        return all;
    }

}
