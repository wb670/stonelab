package cn.zeroall.cow.dal.order.dao;

import java.util.List;

import cn.zeroall.cow.dal.BaseDAO;
import cn.zeroall.cow.dal.order.po.OrderProductPO;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> May 19, 2009
 */
public interface OrderProductDAO extends BaseDAO {

    public Integer add(OrderProductPO orderProductPO);

    public List<OrderProductPO> findByOrderId(Integer orderId);

}
