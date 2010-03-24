package cn.zeroall.cow.biz.home.ao;

import java.util.List;

import cn.zeroall.cow.biz.BaseAO;
import cn.zeroall.cow.biz.home.vo.IndexVO;
import cn.zeroall.cow.service.product.ProductOperatorService;
import cn.zeroall.cow.service.product.enums.ProductOperatorType;
import cn.zeroall.cow.service.product.model.Product;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Mar 8, 2009
 */
public class HomeAO implements BaseAO {

    private ProductOperatorService productOperatorService;

    public IndexVO query() {
        IndexVO indexVO = new IndexVO();
        indexVO.setHots(getHotProducts());
        indexVO.setNews(getNewProduts());
        indexVO.setRecommends(getRecommendedProducts());
        return indexVO;
    }

    private List<Product> getNewProduts() {
        return productOperatorService.findProductsByOperatorType(ProductOperatorType.NEW);
    }

    private List<Product> getRecommendedProducts() {
        return productOperatorService.findProductsByOperatorType(ProductOperatorType.RECOMMEND);
    }

    private List<Product> getHotProducts() {
        return productOperatorService.findProductsByOperatorType(ProductOperatorType.HOT);
    }

    public void setProductOperatorService(ProductOperatorService productOperatorService) {
        this.productOperatorService = productOperatorService;
    }

}
