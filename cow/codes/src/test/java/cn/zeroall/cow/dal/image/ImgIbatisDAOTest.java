package cn.zeroall.cow.dal.image;

import java.util.List;

import cn.zeroall.cow.BaseTestCase;
import cn.zeroall.cow.dal.image.dao.ImgDAO;
import cn.zeroall.cow.dal.image.po.ImgPO;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Mar 5, 2009
 */
public class ImgIbatisDAOTest extends BaseTestCase {

    private static final Integer ID = 1;

    private ImgDAO imgDAO = (ImgDAO) getBean("imgDAO");

    public void testFindById() {
        ImgPO imgPO = imgDAO.findById(ID);
        if (imgPO != null) {
            System.out.println(imgPO.getTitle() + ":" + imgPO.getGroupId());
        }
    }

    public void testFindByIds() {
        List<ImgPO> list = imgDAO.findByIds(new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 });
        if (list != null) {
            for (ImgPO imgPO : list) {
                System.out.println(imgPO.getTitle());
            }
        }
    }

}
