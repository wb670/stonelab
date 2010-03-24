package cn.zeroall.cow.service.product;

import java.util.List;

import cn.zeroall.cow.BaseTestCase;
import cn.zeroall.cow.dal.product.po.WashingPO;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Mar 5, 2009
 */
public class WashingServiceTest extends BaseTestCase {

    private WashingService washingService = (WashingService) getBean("washingService");

    public void testGetWashings() {
        List<WashingPO> list = washingService.getWashings();
        for (WashingPO washingPO : list) {
            System.out.println(washingPO.getName());
        }
    }

    public void testGetWashingById() {
        WashingPO washingPO = washingService.getWashingById(1);
        System.out.println(washingPO.getName());
    }

}
