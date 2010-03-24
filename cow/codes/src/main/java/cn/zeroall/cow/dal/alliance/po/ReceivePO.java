package cn.zeroall.cow.dal.alliance.po;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import cn.zeroall.cow.dal.BasePO;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Sep 29, 2009
 */
@Entity
@Table(name = "receive")
public class ReceivePO implements java.io.Serializable, BasePO {

    // Fields

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "accountType")
    private String accountType;
    private String companyName;
    private String represent;
    private String permit;
    private String bank;
    private String bankDetail;
    private String account;
    private String name;
    @OneToOne(optional = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)   
    @JoinColumn(name = "allied_member_id")
    private AlliedMemberPO alliedMemberPO;
    

    // Constructors

    /** default constructor */
    public ReceivePO() {
    }

    /** full constructor */
    public ReceivePO(Integer id, String accountType, String conpanyName, String represent,
            String permit, String bank, String bankDetail, String account, String name) {
        this.id = id;
        this.accountType = accountType;
        this.companyName = conpanyName;
        this.represent = represent;
        this.permit = permit;
        this.bank = bank;
        this.bankDetail = bankDetail;
        this.account = account;
        this.name = name;
    }

    // Property accessors

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountType() {
        return this.accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getRepresent() {
        return this.represent;
    }

    public void setRepresent(String represent) {
        this.represent = represent;
    }

    public String getPermit() {
        return this.permit;
    }

    public void setPermit(String permit) {
        this.permit = permit;
    }

    public String getBank() {
        return this.bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBankDetail() {
        return this.bankDetail;
    }

    public void setBankDetail(String bankDetail) {
        this.bankDetail = bankDetail;
    }

    public String getAccount() {
        return this.account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AlliedMemberPO getAlliedMemberPO() {
        return alliedMemberPO;
    }

    public void setAlliedMemberPO(AlliedMemberPO alliedMemberPO) {
        this.alliedMemberPO = alliedMemberPO;
    }

}