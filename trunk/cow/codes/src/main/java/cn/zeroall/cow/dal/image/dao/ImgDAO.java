package cn.zeroall.cow.dal.image.dao;

import java.util.List;

import cn.zeroall.cow.dal.BaseDAO;
import cn.zeroall.cow.dal.image.po.ImgPO;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Mar 5, 2009
 */
public interface ImgDAO extends BaseDAO {

    public ImgPO findById(Integer id);

    public List<ImgPO> findByIds(Integer[] ids);

    public List<ImgPO> findByGroupIds(Integer[] groupIds);

}
