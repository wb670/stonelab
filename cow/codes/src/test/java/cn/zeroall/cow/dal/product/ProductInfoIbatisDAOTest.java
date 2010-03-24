package cn.zeroall.cow.dal.product;

import java.util.Date;
import java.util.List;

import cn.zeroall.cow.BaseTestCase;
import cn.zeroall.cow.dal.product.dao.ProductInfoDAO;
import cn.zeroall.cow.dal.product.po.ProductInfoPO;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Mar 4, 2009
 */
public class ProductInfoIbatisDAOTest extends BaseTestCase {

    private static final Integer ID = 1;
    private static final Integer CATEGORY_ID = 1;

    private ProductInfoDAO productInfoDAO = (ProductInfoDAO) getBean("productInfoDAO");

    public void testFindById() {
        ProductInfoPO productInfoPO = productInfoDAO.findById(ID);
        System.out.println(productInfoPO.getName());
    }

    public void testFindByIds() {
        List<ProductInfoPO> list = productInfoDAO.findByIds(new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8,
                9, 10 });
        for (ProductInfoPO productInfoPO : list) {
            System.out.println(productInfoPO.getName());
        }
    }

    public void testCountByCategoryId() {
        Integer num = productInfoDAO.countByCategoryIds(new Integer[] { CATEGORY_ID });
        System.out.println(num);
    }

    public void testFindByCategoryId() {
        List<ProductInfoPO> list = productInfoDAO.findByCategoryIds(new Integer[] { CATEGORY_ID },
                0, 13);
        System.out.println(list.size());
    }

    public void testAddProduct() {
        ProductInfoPO pInfo = new ProductInfoPO();
        pInfo.setName("test");
        pInfo.setTypeId(1);
        pInfo.setMarketPrice(100D);
        pInfo.setMyPrice(100D);
        pInfo.setCoverImgName("test.jpg");
        pInfo.setAttributes("a=b,c=d");
        pInfo.setDescription("testDescription");
        pInfo.setSizes("M,L");
        pInfo.setView(1);
        pInfo.setGmtCreated(new Date());
        pInfo.setGmtModified(new Date());
        Integer tID = productInfoDAO.addProduct(pInfo);
        System.out.println(tID);
    }

    public void testCountByPriceRange() {
        Integer total = productInfoDAO.countByPriceRange(1, 120);
        System.out.println(total);
    }

}
