package cn.zeroall.cow.biz;

import org.springframework.util.Assert;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Feb 15, 2009
 */
public class BizException extends Exception {

    private static final long serialVersionUID = 8058262072660660834L;

    private BizCode bizCode;

    public BizException(BizCode bizCode) {
        Assert.notNull(bizCode, "bizCode is null.");
        this.bizCode = bizCode;
    }

    public BizCode getBizCode() {
        return bizCode;
    }

    public void setBizCode(BizCode bizCode) {
        this.bizCode = bizCode;
    }

}
