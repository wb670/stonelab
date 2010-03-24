package cn.zeroall.cow.biz.product.bo;

import java.util.ArrayList;
import java.util.List;

import cn.zeroall.cow.biz.BaseBO;
import cn.zeroall.cow.dal.product.dao.ProductInfoDAO;
import cn.zeroall.cow.dal.product.po.ProductInfoPO;
import cn.zeroall.cow.service.product.adapter.ProductAdapter;
import cn.zeroall.cow.service.product.model.Product;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Jun 30, 2009
 */
public class ProductBO implements BaseBO {

    private ProductInfoDAO productInfoDAO;

    public Integer count(Integer[] categoryIds) {
        return productInfoDAO.countByCategoryIds(categoryIds);
    }

    public List<Product> getProducts(Integer[] categoryIds, Integer start, Integer limit) {
        List<ProductInfoPO> list = productInfoDAO.findByCategoryIds(categoryIds, start, limit);
        List<Product> products = new ArrayList<Product>(list.size());
        for (ProductInfoPO info : list) {
            products.add(new ProductAdapter(info).getProduct());
        }
        return products;
    }

    public void setProductInfoDAO(ProductInfoDAO productInfoDAO) {
        this.productInfoDAO = productInfoDAO;
    }

}
