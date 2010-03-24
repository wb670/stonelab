package cn.zeroall.cow.web.alliance.action;

import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;
import cn.zeroall.cow.biz.alliance.ao.PerformanceAO;
import cn.zeroall.cow.biz.alliance.vo.PerformanceVO;
import cn.zeroall.cow.dal.alliance.po.AlliedMemberPO;
import cn.zeroall.cow.web.BaseAction;
import cn.zeroall.cow.web.common.constants.SessionConstant;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Sep 23, 2009
 */
public class PerformanceAction extends BaseAction {

    private static final long serialVersionUID = -3204340154415344840L;
    private PerformanceAO performanceAO;
    private List<PerformanceVO> performanceVOList;
    private Date startTime;
    private Date endTime;
    private Integer orderNum;
    private String strStartTime;
    private String strEndTime;

    private Integer getAlliedMemberId() {
        AlliedMemberPO member = (AlliedMemberPO) getSession().get(
                SessionConstant.ALLIED_MEMBER_SESSION);
        return member != null ? member.getId() : null;
    }

    /**
     * ************************************************** 根据日期查询
     * **************************************************
     */
    public String performance() throws Exception {
        return SUCCESS;
    }

    private void dealSimpleDate() {
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        strStartTime = s.format(startTime);
        strEndTime = s.format(endTime);
    }

    public String doPerformance() throws Exception {
        performanceVOList = performanceAO.queryByOrderDate(getAlliedMemberId(), startTime, endTime);
        this.dealSimpleDate();
        return SUCCESS;
    }

    public String doOrderNum() throws Exception {
        performanceVOList = performanceAO.queryByOrderId(getAlliedMemberId(),orderNum);
        return SUCCESS;
    }

    public void setPerformanceAO(PerformanceAO performanceAO) {
        this.performanceAO = performanceAO;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public List<PerformanceVO> getPerformanceVOList() {
        return performanceVOList;
    }

    public void setPerformanceVOList(List<PerformanceVO> performanceVOList) {
        this.performanceVOList = performanceVOList;
    }

    public String getStrStartTime() {
        return strStartTime;
    }

    public void setStrStartTime(String strStartTime) {
        this.strStartTime = strStartTime;
    }

    public String getStrEndTime() {
        return strEndTime;
    }

    public void setStrEndTime(String strEndTime) {
        this.strEndTime = strEndTime;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

}
