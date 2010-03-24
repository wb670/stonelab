package cn.zeroall.cow.service.product.impl;

import java.util.List;

import org.apache.commons.lang.ArrayUtils;

import cn.zeroall.cow.dal.product.dao.ProductOperatorDAO;
import cn.zeroall.cow.dal.product.po.ProductOperatorPO;
import cn.zeroall.cow.service.product.ProductOperatorService;
import cn.zeroall.cow.service.product.ProductService;
import cn.zeroall.cow.service.product.enums.ProductOperatorType;
import cn.zeroall.cow.service.product.model.Product;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Mar 8, 2009
 */
public class DefaultProductOperatorService implements ProductOperatorService {

    private ProductOperatorDAO productOperatorDAO;

    private ProductService productService;

    @Override
    public List<Product> findProductsByOperatorType(ProductOperatorType type) {
        // 得到运营记录
        List<ProductOperatorPO> productOperatorPOList = productOperatorDAO.findByType(type.toString());
        Integer[] productIds = getProductIds(productOperatorPOList);
        if (ArrayUtils.isEmpty(productIds)) {
            return null;
        }
        // 组装成product model 信息
        return productService.getProductSummarysByIds(productIds);
    }

    private Integer[] getProductIds(List<ProductOperatorPO> productOperatorPOList) {
        Integer[] ids = new Integer[productOperatorPOList.size()];
        for (int i = 0; i < productOperatorPOList.size(); i++) {
            ids[i] = productOperatorPOList.get(i).getProductId();
        }
        return ids;
    }

    public void setProductOperatorDAO(ProductOperatorDAO productOperatorDAO) {
        this.productOperatorDAO = productOperatorDAO;
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

}
