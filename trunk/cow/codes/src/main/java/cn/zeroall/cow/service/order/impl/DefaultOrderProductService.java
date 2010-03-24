package cn.zeroall.cow.service.order.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import cn.zeroall.cow.dal.order.dao.OrderProductDAO;
import cn.zeroall.cow.dal.order.po.OrderProductPO;
import cn.zeroall.cow.service.order.OrderProductService;
import cn.zeroall.cow.service.order.adapter.OrderProductAdapter;
import cn.zeroall.cow.service.order.model.OrderProduct;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> May 19, 2009
 */
public class DefaultOrderProductService implements OrderProductService {

    private static final OrderProductAdapter adapter = new OrderProductAdapter();

    private OrderProductDAO orderProductDAO;

    @Override
    public Integer add(OrderProduct orderProduct) {
        Date now = new Date();
        orderProduct.setGmtCreated(now);
        orderProduct.setGmtModified(now);
        return orderProductDAO.add(adapter.adapter(orderProduct));
    }

    @Override
    public List<OrderProduct> findByOrderId(Integer orderId) {
        return toOrderProducts(orderProductDAO.findByOrderId(orderId));
    }

    private List<OrderProduct> toOrderProducts(List<OrderProductPO> list) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        List<OrderProduct> orderProducts = new ArrayList<OrderProduct>(list.size());
        for (OrderProductPO orderProductPO : list) {
            orderProducts.add(adapter.adapter(orderProductPO));
        }
        return orderProducts;
    }

    public void setOrderProductDAO(OrderProductDAO orderProductDAO) {
        this.orderProductDAO = orderProductDAO;
    }

}
