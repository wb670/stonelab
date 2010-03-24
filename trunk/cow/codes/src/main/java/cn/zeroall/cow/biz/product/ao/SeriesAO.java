package cn.zeroall.cow.biz.product.ao;

import java.util.ArrayList;
import java.util.List;

import cn.zeroall.chameleon.paginator.PageList;
import cn.zeroall.chameleon.paginator.Paginator;
import cn.zeroall.cow.biz.BaseAO;
import cn.zeroall.cow.biz.product.bo.ProductBO;
import cn.zeroall.cow.biz.product.bo.SeriesBO;
import cn.zeroall.cow.biz.product.enums.SeriesType;
import cn.zeroall.cow.biz.product.vo.ProductListVO;
import cn.zeroall.cow.service.product.model.Product;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Jun 30, 2009
 */
public class SeriesAO implements BaseAO {

    private ProductBO productBO;
    private SeriesBO seriesBO;

    public ProductListVO getProducts(SeriesType type, Integer page, Integer pageSize) {
        Integer[] categoryIds = seriesBO.getCategoryIds(type);
        Integer total = productBO.count(categoryIds);
        if (total == 0) {
            return new ProductListVO(type, new PageList<Product>(new ArrayList<Product>(0), new Paginator(page,
                    pageSize, 0)));
        }
        Paginator paginator = new Paginator(page, pageSize, total);
        List<Product> products = productBO.getProducts(categoryIds, paginator.getBeginIndex() - 1, pageSize);
        PageList<Product> pageList = new PageList<Product>(products, paginator);
        return new ProductListVO(type, pageList);
    }

    public void setProductBO(ProductBO productBO) {
        this.productBO = productBO;
    }

    public void setSeriesBO(SeriesBO seriesBO) {
        this.seriesBO = seriesBO;
    }

}
