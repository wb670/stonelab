package cn.zeroall.cow.service.order.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import cn.zeroall.cow.dal.order.dao.PaymentDAO;
import cn.zeroall.cow.dal.order.po.PaymentPO;
import cn.zeroall.cow.service.order.PaymentService;
import cn.zeroall.cow.service.order.adapter.PaymentAdapter;
import cn.zeroall.cow.service.order.model.Payment;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> May 19, 2009
 */
public class DefaultPaymentService implements PaymentService {

    private List<Payment> cached = null;
    private boolean isLoaded = false;

    private PaymentDAO paymentDAO;

    @Override
    public List<Payment> findAll() {
        if (!isLoaded) {
            cached = toPaymentList(paymentDAO.findAll());
            isLoaded = true;
        }
        return cached;
    }

    private List<Payment> toPaymentList(List<PaymentPO> list) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        List<Payment> payments = new ArrayList<Payment>(list.size());
        for (PaymentPO paymentPO : list) {
            payments.add(new PaymentAdapter().adpater(paymentPO));
        }
        return payments;
    }

    public void setPaymentDAO(PaymentDAO paymentDAO) {
        this.paymentDAO = paymentDAO;
    }

}
