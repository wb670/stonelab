package cn.zeroall.cow.biz.order.ao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.cglib.beans.BeanCopier;
import cn.zeroall.chameleon.vtree.Tree;
import cn.zeroall.chameleon.vtree.TreeException;
import cn.zeroall.cow.biz.BaseAO;
import cn.zeroall.cow.biz.order.vo.AddressInfoVO;
import cn.zeroall.cow.dal.common.po.RegionPO;
import cn.zeroall.cow.dal.order.dao.AddressInfoDAO;
import cn.zeroall.cow.dal.order.po.AddressInfoPO;
import cn.zeroall.cow.service.common.RegionService;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Sep 13, 2009
 */
public class AddressInfoAO implements BaseAO {

    private static final BeanCopier copier = BeanCopier.create(AddressInfoPO.class, AddressInfoVO.class, false);

    private static final Log log = LogFactory.getLog(AddressInfoAO.class);

    private AddressInfoDAO addressInfoDAO;
    private RegionService regionService;

    public List<AddressInfoVO> getAddresses(Integer memberId) {
        Tree<RegionPO> tree = null;
        try {
            tree = regionService.getRegion();
        } catch (TreeException e) {
            log.error("tree build error.", e);
        }

        List<AddressInfoPO> list = addressInfoDAO.findByMemberId(memberId);
        List<AddressInfoVO> addresses = new ArrayList<AddressInfoVO>(list.size());
        for (AddressInfoPO addressInfoPO : list) {
            AddressInfoVO vo = new AddressInfoVO(tree);
            copier.copy(addressInfoPO, vo, null);
            addresses.add(vo);
        }
        return addresses;
    }

    public void setAddressInfoDAO(AddressInfoDAO addressInfoDAO) {
        this.addressInfoDAO = addressInfoDAO;
    }

    public void setRegionService(RegionService regionService) {
        this.regionService = regionService;
    }

}
