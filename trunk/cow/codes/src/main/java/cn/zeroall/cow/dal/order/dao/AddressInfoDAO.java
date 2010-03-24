package cn.zeroall.cow.dal.order.dao;

import java.util.List;

import cn.zeroall.cow.dal.BaseDAO;
import cn.zeroall.cow.dal.order.po.AddressInfoPO;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Sep 13, 2009
 */
public interface AddressInfoDAO extends BaseDAO {

    public void add(AddressInfoPO addressInfoPO);

    public void delete(AddressInfoPO addressInfoPO);

    public void update(AddressInfoPO addressInfoPO);

    public List<AddressInfoPO> findByMemberId(Integer memberId);

}
