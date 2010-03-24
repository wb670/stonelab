package cn.zeroall.cow.dal.image.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import cn.zeroall.cow.dal.image.dao.ImgGroupDAO;
import cn.zeroall.cow.dal.image.po.ImgGroupPO;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Mar 5, 2009
 */
public class ImgGroupIbatisDAO extends SqlMapClientDaoSupport implements ImgGroupDAO {

    @Override
    public ImgGroupPO findById(Integer id) {
        return (ImgGroupPO) getSqlMapClientTemplate().queryForObject("ImgGroup.find-by-id", id);
    }

    @Override
    public List<ImgGroupPO> findByIds(Integer[] ids) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("ids", ids);
        return (List<ImgGroupPO>) getSqlMapClientTemplate().queryForList("ImgGroup.find-by-ids", param);

    }

}
