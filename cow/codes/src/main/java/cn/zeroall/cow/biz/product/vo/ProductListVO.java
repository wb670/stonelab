package cn.zeroall.cow.biz.product.vo;

import cn.zeroall.chameleon.paginator.PageList;
import cn.zeroall.cow.biz.BaseVO;
import cn.zeroall.cow.biz.product.enums.SeriesType;
import cn.zeroall.cow.dal.product.po.ProductCategoryPO;
import cn.zeroall.cow.service.product.model.Product;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Mar 23, 2009
 */
public class ProductListVO implements BaseVO {

    private static final long serialVersionUID = -1778036595724349915L;

    private ProductCategoryPO category;
    private SeriesType seriesType;
    private PageList<Product> productPageList;

    public ProductListVO(ProductCategoryPO category, PageList<Product> productPageList) {
        this.category = category;
        this.productPageList = productPageList;
    }

    public ProductListVO(SeriesType type, PageList<Product> productPageList) {
        this.seriesType = type;
        this.productPageList = productPageList;
    }

    public ProductCategoryPO getCategory() {
        return category;
    }

    public PageList<Product> getProductPageList() {
        return productPageList;
    }

    public void setCategory(ProductCategoryPO category) {
        this.category = category;
    }

    public void setProductPageList(PageList<Product> productPageList) {
        this.productPageList = productPageList;
    }

    public SeriesType getSeriesType() {
        return seriesType;
    }

    public void setSeriesType(SeriesType seriesType) {
        this.seriesType = seriesType;
    }

}
