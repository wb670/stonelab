package cn.zeroall.cow.service.order.adapter;

import net.sf.cglib.beans.BeanCopier;
import cn.zeroall.cow.dal.order.po.OrderProductPO;
import cn.zeroall.cow.service.order.model.OrderProduct;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> May 19, 2009
 */
public class OrderProductAdapter {

    private static final BeanCopier model2PO = BeanCopier.create(OrderProduct.class, OrderProductPO.class, false);
    private static final BeanCopier po2Model = BeanCopier.create(OrderProductPO.class, OrderProduct.class, false);

    public OrderProductPO adapter(OrderProduct orderProduct) {
        if (orderProduct == null) {
            return null;
        }
        OrderProductPO orderProductPO = new OrderProductPO();
        model2PO.copy(orderProduct, orderProductPO, null);
        return orderProductPO;
    }

    public OrderProduct adapter(OrderProductPO orderProductPO) {
        if (orderProductPO == null) {
            return null;
        }
        OrderProduct orderProduct = new OrderProduct();
        po2Model.copy(orderProductPO, orderProduct, null);
        return orderProduct;
    }

}
