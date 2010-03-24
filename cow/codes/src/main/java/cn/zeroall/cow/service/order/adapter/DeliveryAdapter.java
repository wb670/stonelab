package cn.zeroall.cow.service.order.adapter;

import net.sf.cglib.beans.BeanCopier;
import cn.zeroall.cow.dal.order.po.DeliveryPO;
import cn.zeroall.cow.service.order.model.Delivery;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> May 19, 2009
 */
public class DeliveryAdapter {

    private static final BeanCopier model2PO = BeanCopier.create(Delivery.class, DeliveryPO.class, false);
    private static final BeanCopier po2Model = BeanCopier.create(DeliveryPO.class, Delivery.class, false);

    public Delivery adpater(DeliveryPO deliveryPO) {
        if (deliveryPO == null) {
            return null;
        }
        Delivery delivery = new Delivery();
        po2Model.copy(deliveryPO, delivery, null);
        return delivery;
    }

    public DeliveryPO adapter(Delivery delivery) {
        if (delivery == null) {
            return null;
        }
        DeliveryPO deliveryPO = new DeliveryPO();
        model2PO.copy(delivery, deliveryPO, null);
        return deliveryPO;
    }

}
