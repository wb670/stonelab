package cn.zeroall.cow.web.order.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import cn.zeroall.chameleon.paginator.PageList;
import cn.zeroall.cow.biz.BizException;
import cn.zeroall.cow.biz.order.ao.TradeOrderAO;
import cn.zeroall.cow.biz.order.vo.TradeOrderDetailVO;
import cn.zeroall.cow.biz.order.vo.TradeOrderVO;
import cn.zeroall.cow.service.member.model.Member;
import cn.zeroall.cow.service.order.model.OrderProduct;
import cn.zeroall.cow.service.order.model.TradeOrder;
import cn.zeroall.cow.web.BaseAction;
import cn.zeroall.cow.web.common.constants.SessionConstant;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Aug 20, 2009
 */
public class OrderAction extends BaseAction {

    private static final long serialVersionUID = 4051696689843232262L;

    private static final String DEFAULT_PAGE_SIZE = "10";

    private TradeOrderAO tradeOrderAO;
    
    private String aike;
    

    protected Integer getMemberId() {
        Member member = (Member) getSession().get(SessionConstant.MEMBER_SESSION);
        return member != null ? member.getId() : null;
    }

    protected List<OrderProduct> getCartSession() {
        List<OrderProduct> orderProducts = (List<OrderProduct>) getSession().get(SessionConstant.CART_SESSION);
        if (orderProducts == null) {
            orderProducts = new ArrayList<OrderProduct>(1);
        }
        return orderProducts;
    }

    protected void saveCartSession(List<OrderProduct> orderProducts) {
        getSession().put(SessionConstant.CART_SESSION, orderProducts);
    }

    /********************************************
     ** 下订单页面
     ********************************************/
    public String addOrder() {
        return SUCCESS;
    }

    /********************************************
     ** 下订单操作
     ********************************************/
    private TradeOrderVO tradeOrderVO;

    public String doAddOrder() {
        tradeOrderVO.setMemberId(getMemberId());
        tradeOrderVO.setSource(getOrderSource());
        TradeOrder order;
        try {
            order = tradeOrderAO.addTradeOrder(tradeOrderVO, getCartSession(),aike,getMemberId());
        } catch (BizException e) {
            addActionError(e.getMessage());
            return ERROR;
        }
        catch (IllegalArgumentException e) {
            addActionError(e.getMessage());
            return ERROR;
        }
        tradeOrderVO.setId(String.valueOf(order.getId()));
        tradeOrderVO.setTotalPrice(String.valueOf(order.getTotalPrice()));
        saveCartSession(null);
        return SUCCESS;
    }

    private String getOrderSource() {
        return (String) getSession().get(SessionConstant.ALLIED_MEMBER_SOURCE);
    }

    /********************************************
     ** 取消订单操作
     ********************************************/
    private String orderId;

    public String doCancelOrder() throws Exception {
        tradeOrderAO.cancelOrder(Integer.valueOf(orderId));
        return SUCCESS;
    }

    /********************************************
     ** list页面
     ********************************************/
    private String page;
    private PageList<TradeOrder> pageList;

    public String list() throws Exception {
        pageList = tradeOrderAO.getTradeOrders(getMemberId(), getPage(), DEFAULT_PAGE_SIZE);
        return SUCCESS;
    }

    /********************************************
     ** detail页面
     ********************************************/
    private TradeOrderDetailVO detailVO;

    public String detail() throws Exception {
        detailVO = tradeOrderAO.getTradeOrderDetail(Integer.valueOf(orderId));
        return SUCCESS;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPage() {
        if (StringUtils.isBlank(page)) {
            return "1";
        }
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public PageList<TradeOrder> getPageList() {
        return pageList;
    }

    public void setPageList(PageList<TradeOrder> pageList) {
        this.pageList = pageList;
    }

    public void setTradeOrderVO(TradeOrderVO tradeOrderVO) {
        this.tradeOrderVO = tradeOrderVO;
    }

    public TradeOrderVO getTradeOrderVO() {
        return tradeOrderVO;
    }

    public TradeOrderDetailVO getDetailVO() {
        return detailVO;
    }

    public void setDetailVO(TradeOrderDetailVO detailVO) {
        this.detailVO = detailVO;
    }

    public void setTradeOrderAO(TradeOrderAO tradeOrderAO) {
        this.tradeOrderAO = tradeOrderAO;
    }

    public String getAike() {
        return aike;
    }

    public void setAike(String aike) {
        this.aike = aike;
    }

}
