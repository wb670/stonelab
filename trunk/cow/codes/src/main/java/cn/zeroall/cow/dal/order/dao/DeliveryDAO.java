package cn.zeroall.cow.dal.order.dao;

import java.util.List;

import cn.zeroall.cow.dal.BaseDAO;
import cn.zeroall.cow.dal.order.po.DeliveryPO;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> May 19, 2009
 */
public interface DeliveryDAO extends BaseDAO {

    public Integer add(DeliveryPO deleveryPO);

    public List<DeliveryPO> findAll();

}
