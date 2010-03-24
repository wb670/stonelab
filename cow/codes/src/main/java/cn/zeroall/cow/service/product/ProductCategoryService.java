package cn.zeroall.cow.service.product;

import cn.zeroall.chameleon.vtree.Tree;
import cn.zeroall.cow.dal.product.po.ProductCategoryPO;
import cn.zeroall.cow.service.BaseService;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Mar 5, 2009
 */
public interface ProductCategoryService extends BaseService {

    public Tree<ProductCategoryPO> getProductCategory();

    public Integer[] getDescendantIds(Integer categoryId);

}