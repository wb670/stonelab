package cn.zeroall.cow.dal.order.dao;

import java.util.List;

import cn.zeroall.cow.dal.BaseDAO;
import cn.zeroall.cow.dal.order.po.PaymentPO;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> May 19, 2009
 */
public interface PaymentDAO extends BaseDAO {

    public Integer add(PaymentPO paymentPO);

    public List<PaymentPO> findAll();

}
