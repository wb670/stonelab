package cn.zeroall.cow.biz.comment;

import java.util.Locale;

import cn.zeroall.cow.common.enums.EnumType;
import cn.zeroall.cow.common.util.ResourceBundleUtil;

public enum CommentCode implements EnumType {

    /** 保存成功*/
    ENABLED,
    /** 已删除 */
    DELETED;
  

    private ResourceBundleUtil resourceBundleUtil = new ResourceBundleUtil("enumtype.comment");

    @Override
    public String getMessage() {
        return getMessage(Locale.getDefault());
    }

    @Override
    public String getMessage(Locale locale) {
        return resourceBundleUtil.getMessage("CommentCode." + this.toString(), locale);
    }

}
