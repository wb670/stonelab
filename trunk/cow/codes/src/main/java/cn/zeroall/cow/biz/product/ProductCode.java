package cn.zeroall.cow.biz.product;

import java.util.Locale;

import cn.zeroall.cow.biz.BizCode;
import cn.zeroall.cow.common.util.ResourceBundleUtil;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Feb 15, 2009
 */
public enum ProductCode implements BizCode {

    /** 类目信息build失败 */
    PRODUCT_TREE_BUILD_ERROR;

    private transient ResourceBundleUtil resourceBundleUtil = new ResourceBundleUtil("bizcode.product");

    @Override
    public String getMessage() {
        return getMessage(Locale.getDefault());
    }

    @Override
    public String getMessage(Locale locale) {
        return resourceBundleUtil.getMessage(this.toString(), locale);
    }

}
