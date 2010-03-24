package cn.zeroall.cow.service.order;

import java.util.List;

import cn.zeroall.cow.service.BaseService;
import cn.zeroall.cow.service.order.model.OrderProduct;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> May 19, 2009
 */
public interface OrderProductService extends BaseService {

    public Integer add(OrderProduct orderProduct);

    public List<OrderProduct> findByOrderId(Integer orderId);

}
