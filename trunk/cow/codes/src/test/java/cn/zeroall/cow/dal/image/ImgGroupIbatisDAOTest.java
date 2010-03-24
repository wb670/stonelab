package cn.zeroall.cow.dal.image;

import cn.zeroall.cow.BaseTestCase;
import cn.zeroall.cow.dal.image.dao.ImgGroupDAO;
import cn.zeroall.cow.dal.image.po.ImgGroupPO;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Mar 5, 2009
 */
public class ImgGroupIbatisDAOTest extends BaseTestCase {

    private static final Integer ID = 1;

    private ImgGroupDAO imgGroupDAO = (ImgGroupDAO) getBean("imgGroupDAO");

    public void testFindById() {
        ImgGroupPO imgGroupPO = imgGroupDAO.findById(ID);
        if (imgGroupPO != null) {
            System.out.println(imgGroupPO.getName());
        }
    }

}
