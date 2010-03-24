package cn.zeroall.cow.biz.test.ao;

import cn.zeroall.chameleon.vtree.Tree;
import cn.zeroall.chameleon.vtree.TreeException;
import cn.zeroall.cow.biz.BaseAO;
import cn.zeroall.cow.biz.BizException;
import cn.zeroall.cow.biz.product.ProductCode;
import cn.zeroall.cow.dal.common.po.RegionPO;
import cn.zeroall.cow.dal.product.po.ProductCategoryPO;
import cn.zeroall.cow.dal.test.dao.TestDAO;
import cn.zeroall.cow.dal.test.po.TestPO;
import cn.zeroall.cow.service.common.impl.DefaultRegionService;
import cn.zeroall.cow.service.product.impl.DefaultProductCategoryService;

/**
 * TestAO
 * 
 * @author Maurice.J Feb 2, 2009
 */
public class TestAO implements BaseAO {

    private TestDAO testDAO;
    private DefaultProductCategoryService productCategoryService;
    private DefaultRegionService areaService;

    public TestPO findTest(Integer id) {
        return testDAO.findById(id);
    }

    public Tree<ProductCategoryPO> getProductCategory() throws BizException {
        return productCategoryService.getProductCategory();
    }

    public Tree<RegionPO> getArea() throws BizException {
        try {
            return areaService.getRegion();
        } catch (TreeException e) {
            throw new BizException(ProductCode.PRODUCT_TREE_BUILD_ERROR);
        }
    }

    public void setTestDAO(TestDAO testDAO) {
        this.testDAO = testDAO;
    }

    public void setProductCategoryService(DefaultProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    public void setAreaService(DefaultRegionService areaService) {
        this.areaService = areaService;
    }

}
