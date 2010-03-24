package cn.zeroall.cow.web.alliance.action;

import java.util.Date;

import cn.zeroall.cow.biz.alliance.ao.IncomeAO;
import cn.zeroall.cow.biz.alliance.vo.IncomeVO;
import cn.zeroall.cow.dal.alliance.po.AlliedMemberPO;
import cn.zeroall.cow.web.BaseAction;
import cn.zeroall.cow.web.common.constants.SessionConstant;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Sep 23, 2009
 */
public class IncomeAction extends BaseAction {

    private static final long serialVersionUID = -3204340154415344840L;
    private IncomeAO incomeAO;
    private IncomeVO incomeVO;
    private int year;
    private int month;
    AlliedMemberPO member = null;

    private AlliedMemberPO getAlliedMember() {
        member = (AlliedMemberPO) getSession().get(SessionConstant.ALLIED_MEMBER_SESSION);
        return member;
    }

    public String income() throws Exception {
        return SUCCESS;
    }

    /**
     * ************************************************** 根据日期查询
     * **************************************************
     */
    public String doIncome() throws Exception {
        Date date = new Date();
        date.setYear(year);
        date.setMonth(month);
        getAlliedMember();
        incomeVO = incomeAO.queryByOrderYearAndMonth(member.getId(), date);
        incomeVO.setLoginId(member.getLoginId());
        return SUCCESS;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public IncomeVO getIncomeVO() {
        return incomeVO;
    }

    public void setIncomeVO(IncomeVO incomeVO) {
        this.incomeVO = incomeVO;
    }

    public void setIncomeAO(IncomeAO incomeAO) {
        this.incomeAO = incomeAO;
    }

   
}
