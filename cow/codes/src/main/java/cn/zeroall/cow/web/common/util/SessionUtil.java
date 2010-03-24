package cn.zeroall.cow.web.common.util;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;

import cn.zeroall.cow.service.member.model.Member;
import cn.zeroall.cow.service.order.model.OrderProduct;
import cn.zeroall.cow.web.common.constants.SessionConstant;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> May 2, 2009
 */
public class SessionUtil {

    public Member getMemberSession(HttpSession session) {
        return (Member) session.getAttribute(SessionConstant.MEMBER_SESSION);
    }

    public List<OrderProduct> getCartSession(HttpSession session) {
        return (List<OrderProduct>) session.getAttribute(SessionConstant.CART_SESSION);
    }

    public Integer getCartCount(HttpSession session) {
        List<OrderProduct> orderProducts = getCartSession(session);
        if (CollectionUtils.isEmpty(orderProducts)) {
            return 0;
        }
        Integer total = 0;
        for (OrderProduct orderProduct : orderProducts) {
            total += orderProduct.getAmount();
        }
        return total;
    }

}
