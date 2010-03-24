package cn.zeroall.cow.dal.image.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import cn.zeroall.cow.dal.image.dao.ImgDAO;
import cn.zeroall.cow.dal.image.po.ImgPO;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Mar 5, 2009
 */
public class ImgIbatisDAO extends SqlMapClientDaoSupport implements ImgDAO {

    @Override
    public ImgPO findById(Integer id) {
        return (ImgPO) getSqlMapClientTemplate().queryForObject("Img.find-by-id", id);
    }

    @Override
    public List<ImgPO> findByIds(Integer[] ids) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("ids", ids);
        return (List<ImgPO>) getSqlMapClientTemplate().queryForList("Img.find-by-ids", param);
    }

    @Override
    public List<ImgPO> findByGroupIds(Integer[] groupIds) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("ids", groupIds);
        return (List<ImgPO>) getSqlMapClientTemplate().queryForList("Img.find-by-group-ids", param);
    }

}
