package cn.zeroall.cow.service.product.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import cn.zeroall.cow.dal.product.dao.ProductInfoDAO;
import cn.zeroall.cow.dal.product.po.ProductInfoPO;
import cn.zeroall.cow.dal.size.hibernate.SizeHibernateDAO;
import cn.zeroall.cow.service.product.ProductCategoryService;
import cn.zeroall.cow.service.product.ProductService;
import cn.zeroall.cow.service.product.adapter.ProductAdapter;
import cn.zeroall.cow.service.product.model.Product;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Mar 7, 2009
 */
public class DefaultProductService implements ProductService {

    private ProductInfoDAO productInfoDAO;

    private ProductCategoryService productCategoryService;

    private SizeHibernateDAO sizeHibernateDAO;

    public List<Product> getProductSummarysByIds(Integer[] ids) {
        if (ArrayUtils.isEmpty(ids)) {
            return new ArrayList<Product>(0);
        }
        List<ProductInfoPO> productInfoPOList = productInfoDAO.findByIds(ids);
        // 转换成product model
        List<Product> products = new ArrayList<Product>(productInfoPOList.size());
        for (ProductInfoPO productInfoPO : productInfoPOList) {
            products.add(new ProductAdapter(productInfoPO).getProduct());
        }
        return products;
    }

    @Override
    public Integer countProductsByCategoryId(Integer categoryId) {
        Integer[] descendantIds = productCategoryService.getDescendantIds(categoryId);
        if (ArrayUtils.isEmpty(descendantIds)) {
            return 0;
        }
        return productInfoDAO.countByCategoryIds(descendantIds);
    }

    @Override
    public List<Product> findProductSummarysByCategoryId(Integer categoryId, Integer start,
            Integer limit) {
        // 得到productInfoPO list
        Integer[] descendantIds = productCategoryService.getDescendantIds(categoryId);
        if (ArrayUtils.isEmpty(descendantIds)) {
            return new ArrayList<Product>(0);
        }
        List<ProductInfoPO> productInfoPOList = productInfoDAO.findByCategoryIds(descendantIds,
                start, limit);
        return toProducts(productInfoPOList);

    }

    private String getDescriptions(ProductInfoPO productInfo) {
        List descriptionList = sizeHibernateDAO.findDescriptions(StringUtils.split(productInfo
                .getSizes(), ","));
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < descriptionList.size(); i++) {
            if (i == 0)
                str.append(descriptionList.get(i));
            else {
                str.append(",");
                str.append(descriptionList.get(i));
            }

        }
        return str.toString();
    }

    @Override
    public Product findProduct(Integer id) {
        // 得到产品信息

        ProductInfoPO productInfo = productInfoDAO.findById(id);

        productInfo.setSizeDescriptions(this.getDescriptions(productInfo));
        Product product = new ProductAdapter(productInfo).getProduct();
        if (product == null) {
            return null;
        }
        return product;
    }

    @Override
    public Integer countProductsByPriceRange(Integer min, Integer max) {
        if (min == null || max == null) {
            return 0;
        }
        return productInfoDAO.countByPriceRange(min, max);
    }

    @Override
    public List<Product> findProductsByPriceRange(Integer min, Integer max, Integer start,
            Integer limit) {
        if (min == null || max == null) {
            return new ArrayList<Product>(0);
        }
        List<ProductInfoPO> productInfoPOList = productInfoDAO.findByPriceRange(min, max, start,
                limit);
        return toProducts(productInfoPOList);
    }

    private List<Product> toProducts(List<ProductInfoPO> list) {
        List<Product> products = new ArrayList<Product>(list.size());
        for (ProductInfoPO productInfoPO : list) {
            products.add(new ProductAdapter(productInfoPO).getProduct());
        }
        return products;
    }

    public void setProductInfoDAO(ProductInfoDAO productInfoDAO) {
        this.productInfoDAO = productInfoDAO;
    }

    public void setProductCategoryService(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    public SizeHibernateDAO getSizeHibernateDAO() {
        return sizeHibernateDAO;
    }

    public void setSizeHibernateDAO(SizeHibernateDAO sizeHibernateDAO) {
        this.sizeHibernateDAO = sizeHibernateDAO;
    }

}
