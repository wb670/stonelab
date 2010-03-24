package cn.zeroall.cow.service.product.impl;

import cn.zeroall.chameleon.vtree.Tree;
import cn.zeroall.chameleon.vtree.TreeException;
import cn.zeroall.cow.dal.product.dao.ProductCategoryDAO;
import cn.zeroall.cow.dal.product.po.ProductCategoryPO;
import cn.zeroall.cow.service.product.ProductCategoryService;

public class DefaultProductCategoryService implements ProductCategoryService {

    private ProductCategoryDAO productCategoryDAO;

    private Tree<ProductCategoryPO> cached;

    public void init() throws TreeException {
        build();
    }

    public Tree<ProductCategoryPO> getProductCategory() {
        return cached;
    }

    @Override
    public Integer[] getDescendantIds(Integer categoryId) {
        if (categoryId == null) {
            return null;
        }
        ProductCategoryPO productCategoryPO = cached.getNodeById(categoryId);
        if (productCategoryPO == null) {
            return null;
        }
        if (productCategoryPO.hasDescendants()) {
            return productCategoryPO.getDescendantIds();
        }
        return new Integer[] { categoryId };
    }

    private void build() throws TreeException {
        Tree<ProductCategoryPO> tree = new Tree<ProductCategoryPO>();
        tree.setNodeStore(productCategoryDAO);
        tree.build();
        cached = tree;
    }

    public void setProductCategoryDAO(ProductCategoryDAO productCategoryDAO) {
        this.productCategoryDAO = productCategoryDAO;
    }

}