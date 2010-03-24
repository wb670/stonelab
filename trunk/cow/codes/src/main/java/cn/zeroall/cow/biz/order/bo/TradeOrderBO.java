package cn.zeroall.cow.biz.order.bo;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import cn.zeroall.cow.biz.BaseBO;
import cn.zeroall.cow.dal.account.hibernate.AccountDetailHibernateDAO;
import cn.zeroall.cow.dal.order.dao.AddressInfoDAO;
import cn.zeroall.cow.dal.order.po.AddressInfoPO;
import cn.zeroall.cow.service.member.model.Region;
import cn.zeroall.cow.service.order.DeliveryService;
import cn.zeroall.cow.service.order.OrderProductService;
import cn.zeroall.cow.service.order.TradeOrderService;
import cn.zeroall.cow.service.order.enums.TradeOrderStatus;
import cn.zeroall.cow.service.order.model.Delivery;
import cn.zeroall.cow.service.order.model.OrderProduct;
import cn.zeroall.cow.service.order.model.TradeOrder;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Jun 4, 2009
 */
public class TradeOrderBO implements BaseBO {

    private static final int COUNT = 3;

    private TradeOrderService tradeOrderService;
    private OrderProductService orderProductService;
    private DeliveryService deliveryService;
    private AddressInfoDAO addressInfoDAO;
    private AccountDetailHibernateDAO accountDetailHibernateDAO;

    public TradeOrder add(TradeOrder tradeOrder, List<OrderProduct> orderProducts, String aike,
            Integer memberId) {
        if (tradeOrder == null || CollectionUtils.isEmpty(orderProducts)) {
            throw new IllegalArgumentException("tradeOrder is null or orderProducts is empty.");
        }
        if (memberId == null ) {
            throw new IllegalArgumentException("memberId is null or orderProducts is empty.");
        }
        
        Double product_price = getTotalPrice(orderProducts);
        Double delivery_price = getDeliveryPrice(tradeOrder.getDeliveryId());
        tradeOrder.setProductPrice(product_price);
        tradeOrder.setDeliveryPrice(delivery_price);
        tradeOrder.setTotalPrice(product_price + delivery_price);

        // 使用爱可支付
        if (aike != null && "t".equals(aike)) {
            double balanceAccount = accountDetailHibernateDAO.updateAmount(product_price
                    + delivery_price, memberId);
            if (balanceAccount == 0) {
                tradeOrder.setStatus(TradeOrderStatus.PAID);
                tradeOrder.setAccountPaymentPrice(product_price + delivery_price);
            } else {
                tradeOrder.setStatus(TradeOrderStatus.NO_CHECK_ACCOUNT);
                tradeOrder.setAccountPaymentPrice(product_price + delivery_price-balanceAccount);
                tradeOrder.setPaymentPrice(balanceAccount);
            }

        }

        Integer id = tradeOrderService.add(tradeOrder);
        for (OrderProduct orderProduct : orderProducts) {
            orderProduct.setOrderId(id);
            orderProductService.add(orderProduct);
        }
        tradeOrder.setId(id);
        tradeOrderService.update(tradeOrder);

        return tradeOrder;
    }

    public void addAddressInfo(TradeOrder tradeOrder) {
        if (tradeOrder == null) {
            throw new IllegalArgumentException("tradeOrder is null.");
        }
        List<AddressInfoPO> addresses = addressInfoDAO.findByMemberId(tradeOrder.getMemberId());
        AddressInfoPO current = toAddressInfo(tradeOrder);
        AddressInfoPO existed = getExisted(addresses, current);
        // 如果已经存在，则更新时间
        if (existed != null) {
            existed.setGmtModified(new Date());
            addressInfoDAO.update(existed);
            return;
        }
        // 不存在，则插入
        if (addresses.size() >= COUNT) {
            AddressInfoPO deleted = addresses.get(COUNT - 1);
            addressInfoDAO.delete(deleted);
        }
        addressInfoDAO.add(current);
    }

    private Double getTotalPrice(List<OrderProduct> orderProducts) {
        Double total = 0.0;
        for (OrderProduct orderProduct : orderProducts) {
            total += orderProduct.getAmount() * orderProduct.getPrice();
        }
        return total;
    }

    private Double getDeliveryPrice(Integer id) {
        List<Delivery> deliveries = deliveryService.findAll();
        for (Delivery delivery : deliveries) {
            if (delivery.getId().intValue() == id.intValue()) {
                return delivery.getPrice();
            }
        }
        return 0D;
    }

    private AddressInfoPO getExisted(List<AddressInfoPO> list, AddressInfoPO current) {
        for (AddressInfoPO each : list) {
            if (isEqual(current, each)) {
                return each;
            }
        }
        return null;
    }

    private boolean isEqual(AddressInfoPO self, AddressInfoPO to) {
        return (StringUtils.equals(self.getName(), to.getName())
                && StringUtils.equals(self.getCountry(), to.getCountry())
                && isNumberEqual(self.getProvince(), to.getProvince())
                && isNumberEqual(self.getCity(), to.getCity())
                && isNumberEqual(self.getArea(), to.getArea())
                && StringUtils.equals(self.getAddress(), to.getAddress())
                && StringUtils.equals(self.getZipcode(), to.getZipcode())
                && StringUtils.equals(self.getTelphone(), to.getTelphone()) && StringUtils.equals(
                self.getMobile(), to.getMobile()));
    }

    private boolean isNumberEqual(Integer self, Integer to) {
        if (self == to) {
            return true;
        }
        if (self == null && to == null) {
            return true;
        }
        return self.intValue() == to.intValue();
    }

    private AddressInfoPO toAddressInfo(TradeOrder tradeOrder) {
        Date now = new Date();
        AddressInfoPO addressInfoPO = new AddressInfoPO();
        addressInfoPO.setMemberId(tradeOrder.getMemberId());
        addressInfoPO.setName(tradeOrder.getName());
        addressInfoPO.setCountry(tradeOrder.getCountry());
        addressInfoPO.setProvince(getRegionCode(tradeOrder.getProvinceRegion()));
        addressInfoPO.setCity(getRegionCode(tradeOrder.getCityRegion()));
        addressInfoPO.setArea(getRegionCode(tradeOrder.getAreaRegion()));
        addressInfoPO.setAddress(tradeOrder.getAddress());
        addressInfoPO.setZipcode(tradeOrder.getZipcode());
        addressInfoPO.setTelphone(tradeOrder.getTelphone());
        addressInfoPO.setMobile(tradeOrder.getMobile());
        addressInfoPO.setGmtCreated(now);
        addressInfoPO.setGmtModified(now);
        return addressInfoPO;
    }

    private Integer getRegionCode(Region region) {
        return region != null ? region.getCode() : null;
    }

    public void setTradeOrderService(TradeOrderService tradeOrderService) {
        this.tradeOrderService = tradeOrderService;
    }

    public void setOrderProductService(OrderProductService orderProductService) {
        this.orderProductService = orderProductService;
    }

    public void setDeliveryService(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    public void setAddressInfoDAO(AddressInfoDAO addressInfoDAO) {
        this.addressInfoDAO = addressInfoDAO;
    }

    public void setAccountDetailHibernateDAO(AccountDetailHibernateDAO accountDetailHibernateDAO) {
        this.accountDetailHibernateDAO = accountDetailHibernateDAO;
    }

}
