package cn.zeroall.cow.biz.product.vo;

import cn.zeroall.chameleon.paginator.PageList;
import cn.zeroall.cow.biz.BaseVO;
import cn.zeroall.cow.service.product.model.Product;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Mar 23, 2009
 */
public class PriceRangeVO implements BaseVO {

    private static final long serialVersionUID = -73336494638207810L;

    private Integer min;
    private Integer max;

    private PageList<Product> productPageList;

    public PriceRangeVO(Integer min, Integer max, PageList<Product> productPageList) {
        this.min = min;
        this.max = max;
        this.productPageList = productPageList;
    }

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public PageList<Product> getProductPageList() {
        return productPageList;
    }

    public void setProductPageList(PageList<Product> productPageList) {
        this.productPageList = productPageList;
    }

}
