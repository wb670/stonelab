package cn.zeroall.cow.dal.image.dao;

import java.util.List;

import cn.zeroall.cow.dal.BaseDAO;
import cn.zeroall.cow.dal.image.po.ImgGroupPO;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Mar 5, 2009
 */
public interface ImgGroupDAO extends BaseDAO {

    public ImgGroupPO findById(Integer id);

    public List<ImgGroupPO> findByIds(Integer[] ids);

}
