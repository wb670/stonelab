package cn.zeroall.cow.service.order.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import cn.zeroall.cow.dal.order.dao.DeliveryDAO;
import cn.zeroall.cow.dal.order.po.DeliveryPO;
import cn.zeroall.cow.service.order.DeliveryService;
import cn.zeroall.cow.service.order.adapter.DeliveryAdapter;
import cn.zeroall.cow.service.order.model.Delivery;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> May 19, 2009
 */
public class DefaultDeliveryService implements DeliveryService {

    private List<Delivery> cached = null;
    private boolean isLoaded = false;

    private DeliveryDAO deliveryDAO;

    @Override
    public List<Delivery> findAll() {
        if (!isLoaded) {
            cached = toDeliveryList(deliveryDAO.findAll());
            isLoaded = true;
        }
        return cached;
    }

    private List<Delivery> toDeliveryList(List<DeliveryPO> list) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        List<Delivery> deliverys = new ArrayList<Delivery>(list.size());
        for (DeliveryPO deliveryPO : list) {
            deliverys.add(new DeliveryAdapter().adpater(deliveryPO));
        }
        return deliverys;
    }

    public void setDeliveryDAO(DeliveryDAO deliveryDAO) {
        this.deliveryDAO = deliveryDAO;
    }

}
