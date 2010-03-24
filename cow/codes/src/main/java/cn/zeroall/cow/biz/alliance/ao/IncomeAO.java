package cn.zeroall.cow.biz.alliance.ao;

import java.util.Date;
import java.util.List;
import cn.zeroall.cow.biz.BaseAO;
import cn.zeroall.cow.biz.alliance.vo.IncomeVO;
import cn.zeroall.cow.dal.alliance.dao.AlliedOrderDAO;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Oct 10, 2009
 */
public class IncomeAO implements BaseAO {

    private AlliedOrderDAO alliedOrderDAO;

    public IncomeVO queryByOrderYearAndMonth(Integer alliedMemberId, Date date) {

        IncomeVO incomeVO = new IncomeVO();
        List<List> list = alliedOrderDAO.findByOrderYearAndMonth(alliedMemberId, date);

        int no_deduct_count = ((Long)list.get(0).get(0)).intValue();
        int deduct_count =  ((Long)list.get(1).get(0)).intValue();
        // 可提成订单金额
        double deduct_count_price=0;
        deduct_count_price=list.get(2).get(0)==null?0:((Double)list.get(2).get(0)).doubleValue();
       
        // 出库金额合计
        double totalPriceOut=0;
        totalPriceOut=list.get(3).get(0)==null?0:((Double)list.get(3).get(0)).doubleValue();   
           
        incomeVO.setMonth(date.getMonth());
        incomeVO.setNo_deduct_count(no_deduct_count);
        incomeVO.setDeduct_count(deduct_count);
        incomeVO.setTotalPriceOut(deduct_count_price);
        incomeVO.setShuiQianyongJin(totalPriceOut*0.15);
        incomeVO.setShuiJin(deduct_count_price*0.17);
        
        

        return incomeVO;
    }

    public void setAlliedOrderDAO(AlliedOrderDAO alliedOrderDAO) {
        this.alliedOrderDAO = alliedOrderDAO;
    }

}
