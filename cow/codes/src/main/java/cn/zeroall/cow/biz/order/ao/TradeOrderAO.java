package cn.zeroall.cow.biz.order.ao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import cn.zeroall.chameleon.paginator.PageList;
import cn.zeroall.chameleon.paginator.Paginator;
import cn.zeroall.chameleon.vtree.Tree;
import cn.zeroall.chameleon.vtree.TreeException;
import cn.zeroall.cow.biz.BaseAO;
import cn.zeroall.cow.biz.BizException;
import cn.zeroall.cow.biz.order.OrderCode;
import cn.zeroall.cow.biz.order.bo.TradeOrderBO;
import cn.zeroall.cow.biz.order.vo.TradeOrderDetailVO;
import cn.zeroall.cow.biz.order.vo.TradeOrderVO;
import cn.zeroall.cow.dal.alliance.dao.AlliedMemberDAO;
import cn.zeroall.cow.dal.alliance.dao.AlliedOrderDAO;
import cn.zeroall.cow.dal.alliance.po.AlliedMemberPO;
import cn.zeroall.cow.dal.alliance.po.AlliedOrderPO;
import cn.zeroall.cow.dal.common.po.RegionPO;
import cn.zeroall.cow.dal.size.hibernate.SizeHibernateDAO;
import cn.zeroall.cow.service.common.RegionService;
import cn.zeroall.cow.service.member.model.Region;
import cn.zeroall.cow.service.order.DeliveryService;
import cn.zeroall.cow.service.order.OrderProductService;
import cn.zeroall.cow.service.order.PaymentService;
import cn.zeroall.cow.service.order.TradeOrderService;
import cn.zeroall.cow.service.order.enums.TradeOrderStatus;
import cn.zeroall.cow.service.order.model.Delivery;
import cn.zeroall.cow.service.order.model.OrderProduct;
import cn.zeroall.cow.service.order.model.Payment;
import cn.zeroall.cow.service.order.model.TradeOrder;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Aug 20, 2009
 */
public class TradeOrderAO implements BaseAO {

    private TradeOrderBO tradeOrderBO;
    private RegionService regionService;
    private TradeOrderService tradeOrderService;
    private OrderProductService orderProductService;
    private PaymentService paymentService;
    private DeliveryService deliveryService;
    private SizeHibernateDAO sizeHibernateDAO;
    private AlliedOrderDAO alliedOrderDAO;
    private AlliedMemberDAO alliedMemberDAO;

    public TradeOrder addTradeOrder(TradeOrderVO tradeOrderVO, List<OrderProduct> orderProducts,
            String aike, Integer memberId) throws BizException {
        if (CollectionUtils.isEmpty(orderProducts)) {
            throw new BizException(OrderCode.CART_EMPTY);
        }
        if (memberId == null) {
            throw new IllegalArgumentException("memberId is null or orderProducts is empty.");
        }
        // 添加订单
        TradeOrder tradeOrder = null;
        try {
            Tree<RegionPO> root = regionService.getRegion();
            tradeOrder = tradeOrderBO.add(getTradeOrder(tradeOrderVO, root), orderProducts, aike,
                    memberId);
        } catch (TreeException e) {
            throw new BizException(OrderCode.REGION_BUILD_ERROR);
        }
        // 添加地址库
        tradeOrderBO.addAddressInfo(tradeOrder);
        // 添加联盟会员订单
        if (StringUtils.isNotBlank(tradeOrderVO.getSource())) {
            String loginId = tradeOrderVO.getSource();
            AlliedMemberPO alliedMemberPO = alliedMemberDAO.findByLoginId(loginId);
            if (alliedMemberPO != null) {
                AlliedOrderPO alliedOrderPO = new AlliedOrderPO();
                alliedOrderPO.setAlliedMemberId(alliedMemberPO.getId());
                alliedOrderPO.setTradeOrderId(tradeOrder.getId());
                alliedOrderDAO.create(alliedOrderPO);
            }
        }
        return tradeOrder;
    }

    public void cancelOrder(Integer orderId) throws BizException {
        TradeOrder tradeOrder = tradeOrderService.get(orderId);
        if (tradeOrder == null) {
            throw new BizException(OrderCode.TRADE_ORDER_NOT_EXIST);
        }
        tradeOrder.setStatus(TradeOrderStatus.DELETED);
        tradeOrderService.update(tradeOrder);
    }

    public PageList<TradeOrder> getTradeOrders(Integer memberId, String page, String pageSize) {
        Integer total = tradeOrderService.getCountByMemberId(memberId);
        Paginator paginator = new Paginator(Integer.valueOf(page), Integer.valueOf(pageSize), total);
        if (total == null || total.intValue() == 0) {
            return new PageList<TradeOrder>(new ArrayList<TradeOrder>(0), paginator);
        }
        List<TradeOrder> tradeOrders = tradeOrderService.getByMemberId(memberId, paginator
                .getBeginIndex() - 1, paginator.getItemsPerPage());
        return new PageList<TradeOrder>(tradeOrders, paginator);
    }

    public TradeOrderDetailVO getTradeOrderDetail(Integer orderId) throws BizException {
        TradeOrderDetailVO detail = new TradeOrderDetailVO();

        TradeOrder tradeOrder = tradeOrderService.get(orderId);
        if (tradeOrder == null) {
            throw new BizException(OrderCode.TRADE_ORDER_NOT_EXIST);
        }
        detail.setTradeOrder(tradeOrder);

        List<OrderProduct> orderProducts = orderProductService.findByOrderId(orderId);

        for (OrderProduct orderProduct : orderProducts) {
            List list = sizeHibernateDAO.findDescriptions(new String[] { orderProduct.getSize() });
            for (int i = 0; i < list.size(); i++) {
                orderProduct.setSize((String) list.get(i));
            }

        }
        detail.setOrderProducts(orderProducts);

        List<Payment> payments = paymentService.findAll();
        for (Payment payment : payments) {
            if (tradeOrder.getPayId().intValue() == payment.getId().intValue()) {
                detail.setPayment(payment);
                break;
            }
        }

        List<Delivery> deliveries = deliveryService.findAll();
        for (Delivery delivery : deliveries) {
            if (tradeOrder.getDeliveryId().intValue() == delivery.getId().intValue()) {
                delivery.setPrice(tradeOrder.getDeliveryPrice());
                detail.setDelivery(delivery);
                break;
            }
        }

        return detail;
    }

    /**
     * @return 截至当前时间，您共有 N 张订单完成交易
     */
    public Integer countByMemberIdNowTime(Integer memberId) {

        return tradeOrderService.countByMemberIdNowTime(memberId);
    }

    /**
     * @return 截至当前时间，累计消费 N 元。
     */
    public Float sumTotalPriceByMemberId(Integer memberId) {
        Float total = tradeOrderService.sumTotalPriceByMemberId(memberId);
        return total == null ? Float.valueOf(0) : total;
    }

    private TradeOrder getTradeOrder(TradeOrderVO tradeOrderVO, Tree<RegionPO> root) {
        TradeOrder tradeOrder = new TradeOrder();
        tradeOrder.setName(tradeOrderVO.getName());
        tradeOrder.setTelphone(tradeOrderVO.getTelphone());
        tradeOrder.setMobile(tradeOrderVO.getMobile());
        tradeOrder.setCountry(tradeOrderVO.getCountry());
        tradeOrder.setProvince(getRegionName(root, tradeOrderVO.getProvince()));
        tradeOrder.setCity(getRegionName(root, tradeOrderVO.getCity()));
        tradeOrder.setArea(getRegionName(root, tradeOrderVO.getArea()));
        tradeOrder.setAddress(tradeOrderVO.getAddress());
        tradeOrder.setZipcode(tradeOrderVO.getZipcode());
        tradeOrder.setMemo(tradeOrderVO.getMemo());
        tradeOrder.setDeliveryId(Integer.valueOf(tradeOrderVO.getDeliveryId()));
        tradeOrder.setPayId(Integer.valueOf(tradeOrderVO.getPayId()));
        tradeOrder.setStatus(TradeOrderStatus.INITIALIZED);
        tradeOrder.setMemberId(tradeOrderVO.getMemberId());
        tradeOrder.setProvinceRegion(getRegion(root, tradeOrderVO.getProvince()));
        tradeOrder.setCityRegion(getRegion(root, tradeOrderVO.getCity()));
        tradeOrder.setAreaRegion(getRegion(root, tradeOrderVO.getArea()));
        return tradeOrder;
    }

    private Region getRegion(Tree<RegionPO> root, String code) {
        if (StringUtils.isBlank(code) || !StringUtils.isNumeric(code)) {
            return null;
        }
        RegionPO regionPO = root.getNodeById(Integer.valueOf(code));
        return regionPO != null ? new Region(regionPO.getId(), regionPO.getName()) : null;
    }

    private String getRegionName(Tree<RegionPO> root, String code) {
        if (StringUtils.isBlank(code) || !StringUtils.isNumeric(code)) {
            return null;
        }
        RegionPO regionPO = root.getNodeById(Integer.valueOf(code));
        return regionPO != null ? regionPO.getName() : null;
    }

    public void setTradeOrderBO(TradeOrderBO tradeOrderBO) {
        this.tradeOrderBO = tradeOrderBO;
    }

    public void setRegionService(RegionService regionService) {
        this.regionService = regionService;
    }

    public void setTradeOrderService(TradeOrderService tradeOrderService) {
        this.tradeOrderService = tradeOrderService;
    }

    public void setOrderProductService(OrderProductService orderProductService) {
        this.orderProductService = orderProductService;
    }

    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public void setDeliveryService(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    public void setAlliedOrderDAO(AlliedOrderDAO alliedOrderDAO) {
        this.alliedOrderDAO = alliedOrderDAO;
    }

    public void setAlliedMemberDAO(AlliedMemberDAO alliedMemberDAO) {
        this.alliedMemberDAO = alliedMemberDAO;
    }

    public void setSizeHibernateDAO(SizeHibernateDAO sizeHibernateDAO) {
        this.sizeHibernateDAO = sizeHibernateDAO;
    }

}
