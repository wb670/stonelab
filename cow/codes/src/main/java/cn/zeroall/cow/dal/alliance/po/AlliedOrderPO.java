package cn.zeroall.cow.dal.alliance.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.zeroall.cow.dal.BasePO;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Oct 9, 2009
 */
@Entity
@Table(name = "allied_order")
public class AlliedOrderPO implements BasePO {

    private static final long serialVersionUID = -2881418843911723848L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "allied_member_id")
    private Integer alliedMemberId;
    @Column(name = "trade_order_id")
    private Integer tradeOrderId;
    @Column(name = "gmt_created")
    private Date gmtCreated;
    @Column(name = "gmt_modified")
    private Date gmtModified;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAlliedMemberId() {
        return alliedMemberId;
    }

    public void setAlliedMemberId(Integer alliedMemberId) {
        this.alliedMemberId = alliedMemberId;
    }

    public Integer getTradeOrderId() {
        return tradeOrderId;
    }

    public void setTradeOrderId(Integer tradeOrderId) {
        this.tradeOrderId = tradeOrderId;
    }

    public Date getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

}
