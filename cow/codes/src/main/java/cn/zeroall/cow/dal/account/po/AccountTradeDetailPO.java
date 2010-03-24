package cn.zeroall.cow.dal.account.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "account_trade_detail", catalog = "cow")
public class AccountTradeDetailPO implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private Integer memberId;
    private Double amount;
    private String type;
    private String memo;
    private Date gmtCreated;
    private Date gmtModified;

    // Constructors

    /** default constructor */
    public AccountTradeDetailPO() {
    }

    /** full constructor */
    public AccountTradeDetailPO(Integer id, Integer memberId, Double amount, String type,
            String memo, Date gmtCreated, Date gmtModified) {
        this.id = id;
        this.memberId = memberId;
        this.amount = amount;
        this.type = type;
        this.memo = memo;
        this.gmtCreated = gmtCreated;
        this.gmtModified = gmtModified;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "member_id")
    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    @Column(name = "amount", nullable = false, precision = 22, scale = 0)
    public Double getAmount() {
        return this.amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Column(name = "type", nullable = false, length = 10)
    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "memo", length = 300)
    public String getMemo() {
        return this.memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Column(name = "gmtCreated", length = 19)
    public Date getGmtCreated() {
        return this.gmtCreated;
    }

    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    @Column(name = "gmtModified", length = 19)
    public Date getGmtModified() {
        return this.gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

}