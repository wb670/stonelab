package cn.zeroall.cow.service.product;

import java.util.List;

import cn.zeroall.cow.service.BaseService;
import cn.zeroall.cow.service.product.enums.ProductOperatorType;
import cn.zeroall.cow.service.product.model.Product;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Mar 8, 2009
 */
public interface ProductOperatorService extends BaseService {

    public List<Product> findProductsByOperatorType(ProductOperatorType type);

}
