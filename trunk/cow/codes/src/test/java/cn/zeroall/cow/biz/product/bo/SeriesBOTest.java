package cn.zeroall.cow.biz.product.bo;

import cn.zeroall.cow.BaseTestCase;
import cn.zeroall.cow.biz.product.enums.SeriesType;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Jun 30, 2009
 */
public class SeriesBOTest extends BaseTestCase {

    private SeriesBO seriesBO = (SeriesBO) getBean("seriesBO");

    public void testGetCategoryIds() {
        Integer[] babyIds = seriesBO.getCategoryIds(SeriesType.BABY);
        for (Integer id : babyIds) {
            System.out.print(id + ",");
        }
        System.out.println();
        Integer[] mamaIds = seriesBO.getCategoryIds(SeriesType.MAMA);
        for (Integer id : mamaIds) {
            System.out.print(id + ",");
        }
    }

}
