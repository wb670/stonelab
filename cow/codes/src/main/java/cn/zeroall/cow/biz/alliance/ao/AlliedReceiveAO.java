package cn.zeroall.cow.biz.alliance.ao;

import cn.zeroall.cow.biz.BaseAO;
import cn.zeroall.cow.biz.BizException;
import cn.zeroall.cow.dal.alliance.dao.AlliedReceiveDAO;
import cn.zeroall.cow.dal.alliance.po.ReceivePO;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Sep 24, 2009
 */
public class AlliedReceiveAO implements BaseAO {

    private AlliedReceiveDAO alliedReceiveDAO;

    public ReceivePO findByAlliedMemberId(Integer id) throws BizException {

        if (id == null) {
            throw new IllegalArgumentException("alliedMember is null");
        }
        return alliedReceiveDAO.findByAlliedMemberId(id);

    }

    public void add(ReceivePO alliedReceivePO) throws BizException {

        if (alliedReceivePO == null) {
            throw new IllegalArgumentException("alliedMember is null");
        }
        alliedReceiveDAO.create(alliedReceivePO);

    }

    // 修改联盟会员信息
    public void update(ReceivePO alliedReceivePO) throws BizException {

        if (alliedReceivePO == null) {
            throw new IllegalArgumentException("alliedMember is null");
        }

        alliedReceiveDAO.update(alliedReceivePO);
    }

    public void setAlliedReceiveDAO(AlliedReceiveDAO alliedReceiveDAO) {
        this.alliedReceiveDAO = alliedReceiveDAO;
    }

}
