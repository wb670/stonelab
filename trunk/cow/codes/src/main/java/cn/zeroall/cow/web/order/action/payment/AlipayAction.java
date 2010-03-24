package cn.zeroall.cow.web.order.action.payment;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import cn.zeroall.cow.biz.BizException;
import cn.zeroall.cow.biz.order.ao.OrderPayAO;
import cn.zeroall.cow.service.order.model.TradeOrder;
import cn.zeroall.cow.web.BaseAction;

import com.alipay.util.CheckURL;
import com.alipay.util.Payment;
import com.alipay.util.SignatureHelper_return;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Sep 13, 2009
 */
public class AlipayAction extends BaseAction {

    private static final long serialVersionUID = 4377148040696165651L;

    private static final Log log = LogFactory.getLog("pay");

    private static final String PAY_TYPE = "alipay";

    private static final String DEFAULT_SIGN_TYPE = "MD5";
    private static final String DEFAULT_SERVICE = "trade_create_by_buyer";
    private static final String DEFAULT_PARTNER = "2088201361914300";
    private static final String DEFAULT_SELLER_EMAIL = "aikehk@163.com";
    private static final String DEFAULT_DISCOUNT = "0";
    private static final String DEFAULT_PAYMENT_TYPE = "1";
    private static final String DEFAULT_LOGISTICS_TYPE = "EXPRESS";
    private static final String DEFAULT_LOGISTICS_FEE = "0";
    private static final String DEFAULT_LOGISTICS_PAYMENT = "SELLER_PAY";
    private static final String DEFAULT_QUANTITY = "1";
    private static final String DEFAULT_KEY = "s6d72h67zcqi9x2cz6vz19mpe8zqugy2";
    private static final String DEFAULT_PAYGATEWAY = "https://www.alipay.com/cooperate/gateway.do?";
    private static final String DEFAULT_INPUT_CHARSET = "utf-8";
    private static final String DEFAULT_SUBJECT = "爱可服饰(订单id：{0})";

    private String orderId;
    private String subject;// 商品名称
    private String body;// 商品名称
    private String out_trade_no; // 商户网站订单
    private String price;// 订单总价
    private String memberId;// 会员id
    private String show_url;// 订单信息

    private String paygateway = DEFAULT_PAYGATEWAY;
    private String partner = DEFAULT_PARTNER;// 支付宝合作伙伴id
    private String seller_email = DEFAULT_SELLER_EMAIL; // 卖家支付宝帐户
    private String key = DEFAULT_KEY;
    private String itemUrl;
    private String return_url;;

    private String logistics_type = DEFAULT_LOGISTICS_TYPE;
    private String logistics_fee = DEFAULT_LOGISTICS_FEE;
    private String logistics_payment = DEFAULT_LOGISTICS_PAYMENT;
    private String payment_type = DEFAULT_PAYMENT_TYPE;
    private String service = DEFAULT_SERVICE;
    private String sign_type = DEFAULT_SIGN_TYPE;
    private String quantity = DEFAULT_QUANTITY;
    private String discount = DEFAULT_DISCOUNT;

    private OrderPayAO orderPayAO;

    /********************************************
     ** doPay
     ********************************************/

    public String doPay() {
        out_trade_no = orderId;
        TradeOrder tradeOrder = null;
        try {
            tradeOrder = orderPayAO.getTradeOrder(Integer.valueOf(out_trade_no));
        } catch (BizException e) {
            addActionError(e.getBizCode().getMessage());
            return ERROR;
        }
        memberId = String.valueOf(tradeOrder.getMemberId());
        
        //支付宝要支付的金额=商品总价（包含邮费）- 使用爱可帐户支付的金额（已支付的,如果没有使用爱可支付,那默认为0）
        Double lastPrice=tradeOrder.getTotalPrice()-tradeOrder.getAccountPaymentPrice();
        price = String.valueOf(lastPrice);
        subject = MessageFormat.format(DEFAULT_SUBJECT, out_trade_no);
        body = subject;
        show_url = show_url + "?orderId=" + out_trade_no;

        itemUrl = Payment.CreateUrl(paygateway, service, sign_type, out_trade_no, DEFAULT_INPUT_CHARSET, partner, key,
                seller_email, body, subject, price, quantity, show_url, payment_type, discount, logistics_type,
                logistics_fee, logistics_payment, return_url);
        log.info(itemUrl);

        String logInfo = "PAY:type={0},memberId={1};orderId={2};amount={3};url={4}";
        log.info(MessageFormat.format(logInfo, PAY_TYPE, memberId, out_trade_no, price, return_url));
        return SUCCESS;
    }

    /********************************************
     ** doReceive
     ********************************************/
    private String is_success;
    private String trade_status;

    public String doReceive() {
        HttpServletRequest request = ServletActionContext.getRequest();

        String alipayNotifyURL = "http://notify.alipay.com/trade/notify_query.do?" + "partner=" + DEFAULT_PARTNER
                + "&notify_id=" + request.getParameter("notify_id");
        String responseTxt = CheckURL.check(alipayNotifyURL);

        Map params = new HashMap();

        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }

        String mysign = SignatureHelper_return.sign(params, DEFAULT_KEY);

        String logInfo = "RECEIVE:type={0};orderId={1};amount={2};status={3};tradeStatus={4}";
        log.info(MessageFormat.format(logInfo, PAY_TYPE, out_trade_no, price, is_success, trade_status));

        // 不成功
        if (!(mysign.equals(request.getParameter("sign")) && responseTxt.equals("true"))) {
            return ERROR;
        }
        // 成功
        try {
            orderPayAO.pay(Integer.valueOf(out_trade_no), true);
        } catch (BizException e) {
            addActionError(e.getBizCode().getMessage());
            return ERROR;
        }
        return SUCCESS;

    }

    public void setOrderPayAO(OrderPayAO orderPayAO) {
        this.orderPayAO = orderPayAO;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getLogistics_type() {
        return logistics_type;
    }

    public void setLogistics_type(String logisticsType) {
        logistics_type = logisticsType;
    }

    public String getLogistics_fee() {
        return logistics_fee;
    }

    public void setLogistics_fee(String logisticsFee) {
        logistics_fee = logisticsFee;
    }

    public String getLogistics_payment() {
        return logistics_payment;
    }

    public void setLogistics_payment(String logisticsPayment) {
        logistics_payment = logisticsPayment;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String outTradeNo) {
        out_trade_no = outTradeNo;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String paymentType) {
        payment_type = paymentType;
    }

    public String getSeller_email() {
        return seller_email;
    }

    public void setSeller_email(String sellerEmail) {
        seller_email = sellerEmail;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getItemUrl() {
        return itemUrl;
    }

    public void setItemUrl(String itemUrl) {
        this.itemUrl = itemUrl;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String signType) {
        sign_type = signType;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getShow_url() {
        return show_url;
    }

    public void setShow_url(String showUrl) {
        show_url = showUrl;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getReturn_url() {
        return return_url;
    }

    public void setReturn_url(String returnUrl) {
        return_url = returnUrl;
    }

    public String getPaygateway() {
        return paygateway;
    }

    public void setPaygateway(String paygateway) {
        this.paygateway = paygateway;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getIs_success() {
        return is_success;
    }

    public void setIs_success(String isSuccess) {
        is_success = isSuccess;
    }

    public String getTrade_status() {
        return trade_status;
    }

    public void setTrade_status(String tradeStatus) {
        trade_status = tradeStatus;
    }

}
