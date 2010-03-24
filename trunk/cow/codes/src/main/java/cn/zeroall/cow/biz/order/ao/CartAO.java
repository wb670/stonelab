package cn.zeroall.cow.biz.order.ao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import cn.zeroall.cow.biz.BaseAO;
import cn.zeroall.cow.biz.order.vo.CartProductVO;
import cn.zeroall.cow.biz.order.vo.CartProductsVO;
import cn.zeroall.cow.common.util.List2ObjUtil;
import cn.zeroall.cow.dal.product.po.ProductInfoPO;
import cn.zeroall.cow.dal.size.hibernate.SizeHibernateDAO;
import cn.zeroall.cow.service.order.model.OrderProduct;
import cn.zeroall.cow.service.product.ProductService;
import cn.zeroall.cow.service.product.model.Product;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Aug 20, 2009
 */
public class CartAO implements BaseAO {

    private ProductService productService;
    
    private SizeHibernateDAO sizeHibernateDAO;

    public CartProductsVO getCartProducts(List<OrderProduct> orderProducts) {
        if (CollectionUtils.isEmpty(orderProducts)) {
            CartProductsVO cartProductsVO = new CartProductsVO();
            cartProductsVO.setCartProducts(new ArrayList<CartProductVO>());
            return cartProductsVO;
        }
        Map<Integer, Product> productMap = getProductMap(orderProducts);
        List<CartProductVO> cartProducts = new ArrayList<CartProductVO>(orderProducts.size());
        for (OrderProduct orderProduct : orderProducts) {
            CartProductVO cartProductVO = new CartProductVO(orderProduct, productMap.get(orderProduct.getProductId()));
            cartProducts.add(cartProductVO);
        }
        CartProductsVO cartProductsVO = new CartProductsVO();
        cartProductsVO.setCartProducts(cartProducts);
        return cartProductsVO;
    }

    private Map<Integer, Product> getProductMap(List<OrderProduct> orderProducts) {
        Integer[] productIds = List2ObjUtil.list2IntegerArray(orderProducts, "getProductId");
        List<Product> products = productService.getProductSummarysByIds(productIds);
        return (Map<Integer, Product>) List2ObjUtil.list2Map(products, "getId");
    }

    
    public String getNameByDescription(String size)
    {
        List descriptionList=sizeHibernateDAO.findSizes(size);
        
        return descriptionList.get(0)!=null?(String)descriptionList.get(0):null;
    }
    
    
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public void setSizeHibernateDAO(SizeHibernateDAO sizeHibernateDAO) {
        this.sizeHibernateDAO = sizeHibernateDAO;
    }

}
