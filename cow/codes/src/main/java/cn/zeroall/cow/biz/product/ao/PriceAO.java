package cn.zeroall.cow.biz.product.ao;

import java.util.ArrayList;
import java.util.List;

import cn.zeroall.chameleon.paginator.PageList;
import cn.zeroall.chameleon.paginator.Paginator;
import cn.zeroall.cow.biz.BaseAO;
import cn.zeroall.cow.biz.product.vo.PriceRangeVO;
import cn.zeroall.cow.service.product.ProductService;
import cn.zeroall.cow.service.product.model.Product;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Mar 23, 2009
 */
public class PriceAO implements BaseAO {

    private ProductService productService;

    public PriceRangeVO getProducts(Integer min, Integer max, Integer page, Integer pageSize) {
        if (min == null || max == null) {
            return null;
        }
        Integer total = productService.countProductsByPriceRange(min, max);
        Paginator paginator = new Paginator(page, pageSize, total);
        if (total == null || total == 0) {
            return new PriceRangeVO(min, max, new PageList<Product>(new ArrayList<Product>(), paginator));
        }
        List<Product> products = productService.findProductsByPriceRange(min, max, paginator.getBeginIndex() - 1,
                pageSize);
        return new PriceRangeVO(min, max, new PageList<Product>(products, paginator));
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

}
