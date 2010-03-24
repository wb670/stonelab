package cn.zeroall.cow.dal.account.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "account_detail", catalog = "cow")
public class AccountDetailPO implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private Integer memberId;
    private Double amount;
    private String type;
    private String status;
    private Date validDate;
    private Date invalidDate;
    private Date gmtCreated;
    private Date gmtModified;

    public AccountDetailPO() {
    }

    /** full constructor */
    public AccountDetailPO(Integer id, Integer memberId, Double amount, String type, String status,
            Date validDate, Date invalidDate, Date gmtCreated, Date gmtModified) {
        this.id = id;
        this.memberId = memberId;
        this.amount = amount;
        this.type = type;
        this.status = status;
        this.validDate = validDate;
        this.invalidDate = invalidDate;
        this.gmtCreated = gmtCreated;
        this.gmtModified = gmtModified;
    }

    // Property accessors
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

    @Column(name = "amount", precision = 22, scale = 0)
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

    @Column(name = "status", nullable = false, length = 10)
    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "validDate", length = 19)
    public Date getValidDate() {
        return this.validDate;
    }

    public void setValidDate(Date validDate) {
        this.validDate = validDate;
    }

    @Column(name = "invalidDate", length = 19)
    public Date getInvalidDate() {
        return this.invalidDate;
    }

    public void setInvalidDate(Date invalidDate) {
        this.invalidDate = invalidDate;
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