package cn.zeroall.cow.biz.product.ao;

import java.util.ArrayList;
import java.util.List;

import cn.zeroall.chameleon.paginator.PageList;
import cn.zeroall.chameleon.paginator.Paginator;
import cn.zeroall.cow.biz.BaseAO;
import cn.zeroall.cow.biz.product.vo.ProductDetailVO;
import cn.zeroall.cow.biz.product.vo.ProductListVO;
import cn.zeroall.cow.dal.product.dao.ProductColorDAO;
import cn.zeroall.cow.dal.product.dao.ProductWashingDAO;
import cn.zeroall.cow.dal.product.po.ProductCategoryPO;
import cn.zeroall.cow.dal.product.po.ProductWashingPO;
import cn.zeroall.cow.service.image.ImageService;
import cn.zeroall.cow.service.image.model.Image;
import cn.zeroall.cow.service.product.ProductCategoryService;
import cn.zeroall.cow.service.product.ProductService;
import cn.zeroall.cow.service.product.WashingService;
import cn.zeroall.cow.service.product.model.Product;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Aug 13, 2009
 */
public class ProductAO implements BaseAO {

    private ProductService productService;
    private ProductCategoryService productCategoryService;
    private WashingService washingService;
    private ImageService imageService;

    private ProductColorDAO productColorDAO;
    private ProductWashingDAO productWashingDAO;

    public ProductListVO getProducts(Integer categoryId, Integer page, Integer pageSize) {
        ProductCategoryPO category = productCategoryService.getProductCategory().getNodeById(categoryId);
        if (category == null) {
            return null;
        }
        Integer total = productService.countProductsByCategoryId(categoryId);
        if (total == 0) {
            return new ProductListVO(category, new PageList<Product>(new ArrayList<Product>(0), new Paginator(page,
                    pageSize, 0)));
        }
        Paginator paginator = new Paginator(page, pageSize, total);
        List<Product> products = productService.findProductSummarysByCategoryId(categoryId,
                paginator.getBeginIndex() - 1, pageSize);
        PageList<Product> pageList = new PageList<Product>(products, paginator);
        return new ProductListVO(category, pageList);
    }

    public ProductDetailVO getProductDetail(Integer id) {
        ProductDetailVO detailVO = new ProductDetailVO();
        detailVO.setProduct(productService.findProduct(id));
        detailVO.setProductColors(productColorDAO.findByProductId(id));

        List<ProductWashingPO> list = productWashingDAO.findByProductId(id);
        Integer[] ids = new Integer[list.size()];
        for (int i = 0; i < list.size(); i++) {
            ids[i] = list.get(i).getId();
        }
        detailVO.setWashings(washingService.getWashingByIds(ids));
        return detailVO;
    }

    public List<Image> getImages(Integer imgGroupId) {
        return imageService.getImagesByGroupIds(new Integer[] { imgGroupId });
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public void setProductCategoryService(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    public void setWashingService(WashingService washingService) {
        this.washingService = washingService;
    }

    public void setProductColorDAO(ProductColorDAO productColorDAO) {
        this.productColorDAO = productColorDAO;
    }

    public void setProductWashingDAO(ProductWashingDAO productWashingDAO) {
        this.productWashingDAO = productWashingDAO;
    }

    public void setImageService(ImageService imageService) {
        this.imageService = imageService;
    }

}
