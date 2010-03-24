package cn.zeroall.cow.biz.alliance.vo;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Oct 10, 2009
 */
public class IncomeVO {

    private int month;// 报表月份
    private String loginId;// 用户名
    private int no_deduct_count;// 不可提成订单数
    private int deduct_count;// 可提成订单数
    private double totalPriceOut;// 出库金额合计
    private double totalPriceIn;// 入库金额合计
    private double deduct_price_count;// 可提成订单金额合计
    private double deduct_scale;// 提成比例
    private double shuiQianyongJin;// 税前佣金
    private double shuiJin;// 代扣税金
    private double yongJin;// 佣金
    private String status;// 状态

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public int getNo_deduct_count() {
        return no_deduct_count;
    }

    public void setNo_deduct_count(int no_deduct_count) {
        this.no_deduct_count = no_deduct_count;
    }

    public int getDeduct_count() {
        return deduct_count;
    }

    public void setDeduct_count(int deduct_count) {
        this.deduct_count = deduct_count;
    }

    public double getTotalPriceOut() {
        return totalPriceOut;
    }

    public void setTotalPriceOut(double totalPriceOut) {
        this.totalPriceOut = totalPriceOut;
    }

    public double getTotalPriceIn() {
        return totalPriceIn;
    }

    public void setTotalPriceIn(double totalPriceIn) {
        this.totalPriceIn = totalPriceIn;
    }

    public double getDeduct_price_count() {
        return deduct_price_count;
    }

    public void setDeduct_price_count(double deduct_price_count) {
        this.deduct_price_count = deduct_price_count;
    }

    public double getDeduct_scale() {
        return deduct_scale;
    }

    public void setDeduct_scale(double deduct_scale) {
        this.deduct_scale = deduct_scale;
    }

    public double getShuiQianyongJin() {
        return shuiQianyongJin;
    }

    public void setShuiQianyongJin(double shuiQianyongJin) {
        this.shuiQianyongJin = shuiQianyongJin;
    }

    public double getShuiJin() {
        return shuiJin;
    }

    public void setShuiJin(double shuiJin) {
        this.shuiJin = shuiJin;
    }

    public double getYongJin() {
        return yongJin;
    }

    public void setYongJin(double yongJin) {
        this.yongJin = yongJin;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
