package cn.zeroall.cow.web.order.action.payment;

import java.text.MessageFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import beartool.MD5;
import cn.zeroall.cow.biz.BizException;
import cn.zeroall.cow.biz.order.ao.OrderPayAO;
import cn.zeroall.cow.service.order.model.TradeOrder;
import cn.zeroall.cow.web.BaseAction;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Aug 21, 2009
 */
public class ChinabankAction extends BaseAction {

    private static final long serialVersionUID = 7210075070962155710L;

    private static final Log log = LogFactory.getLog("pay");

    private static final String DEFAULT_KEY = "123456789123456789";
    private static final String DEFAULT_VMID = "21383077";
    private static final String PAY_TYPE = "chinabank";

    private static final MD5 MD5 = new MD5();

    private OrderPayAO orderPayAO;

    private String orderId;
    private String v_oid; // 订单号
    private String v_amount;// 金额
    private String v_moneytype;// 币种
    /********************************************
     ** send
     ********************************************/
    private String v_url; // url

    private String v_mid = DEFAULT_VMID; // 网银商户
    private String v_md5info;// 密文

    /********************************************
     ** 支付
     ********************************************/
    private String memberId;

    public String doPay() {
        v_oid = orderId;
        TradeOrder tradeOrder = null;
        try {
            tradeOrder = orderPayAO.getTradeOrder(Integer.valueOf(v_oid));
        } catch (BizException e) {
            addActionError(e.getBizCode().getMessage());
            return ERROR;
        }
        memberId = String.valueOf(tradeOrder.getMemberId());
        
      //网上银行要支付的金额=商品总价（包含邮费）- 使用爱可帐户支付的金额（已支付的,如果没有使用爱可支付，那默认为0）
        Double lastPrice=tradeOrder.getTotalPrice()-tradeOrder.getAccountPaymentPrice();
        
        v_amount = String.valueOf(lastPrice);

        String info = v_amount + v_moneytype + v_oid + v_mid + v_url + DEFAULT_KEY;
        v_md5info = MD5.getMD5ofStr(info);

        String logInfo = "PAY:type={0};memberId={1};orderId={2};amount={3};url={4}";
        log.info(MessageFormat.format(logInfo, PAY_TYPE, memberId, v_oid, v_amount, v_url));
        return SUCCESS;
    }

    /********************************************
     ** receive
     ********************************************/
    private String v_pmode;
    private String v_pstatus;
    private String v_pstring;
    private String v_md5str;

    public String doReceive() {
        String text = v_oid + v_pstatus + v_amount + v_moneytype + DEFAULT_KEY;
        String v_md5 = MD5.getMD5ofStr(text).toUpperCase();
        if (!v_md5.equals(v_md5str)) {
            return ERROR;
        }
        String logInfo = "RECEIVE:type={0};orderId={1};amount={2};status={3};message={4}";
        log.info(MessageFormat.format(logInfo, PAY_TYPE, v_oid, v_amount, v_pstatus, v_pstring));

        boolean isSuccess = "20".equals(v_pstatus);

        try {
            orderPayAO.pay(Integer.valueOf(v_oid), isSuccess);
        } catch (BizException e) {
            addActionError(e.getBizCode().getMessage());
            return ERROR;
        }
        return SUCCESS;
    }

    public String getV_pmode() {
        return v_pmode;
    }

    public void setV_pmode(String vPmode) {
        v_pmode = vPmode;
    }

    public String getV_pstatus() {
        return v_pstatus;
    }

    public void setV_pstatus(String vPstatus) {
        v_pstatus = vPstatus;
    }

    public String getV_pstring() {
        return v_pstring;
    }

    public void setV_pstring(String vPstring) {
        v_pstring = vPstring;
    }

    public String getV_md5str() {
        return v_md5str;
    }

    public void setV_md5str(String vMd5str) {
        v_md5str = vMd5str;
    }

    public String getV_mid() {
        return v_mid;
    }

    public void setV_mid(String vMid) {
        v_mid = vMid;
    }

    public String getV_url() {
        return v_url;
    }

    public void setV_url(String vUrl) {
        v_url = vUrl;
    }

    public String getV_oid() {
        return v_oid;
    }

    public void setV_oid(String vOid) {
        v_oid = vOid;
    }

    public String getV_amount() {
        return v_amount;
    }

    public void setV_amount(String vAmount) {
        v_amount = vAmount;
    }

    public String getV_moneytype() {
        return v_moneytype;
    }

    public void setV_moneytype(String vMoneytype) {
        v_moneytype = vMoneytype;
    }

    public String getV_md5info() {
        return v_md5info;
    }

    public void setV_md5info(String vMd5info) {
        v_md5info = vMd5info;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public void setOrderPayAO(OrderPayAO orderPayAO) {
        this.orderPayAO = orderPayAO;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

}
