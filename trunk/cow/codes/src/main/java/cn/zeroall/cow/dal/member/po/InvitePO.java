package cn.zeroall.cow.dal.member.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "invite", catalog = "cow")
public class InvitePO implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private Integer memberId;
    private Short inviteType;
    private String email;
    private String guestBook;
    private String inviteName;
    private String relation = "";
    private String inviteIp;
    private Boolean inviteSuccess=false;
    private Date gmtCreated;
    private Date gmtModified;

    // Constructors

    public InvitePO() {
    }

    /** full constructor */
    public InvitePO(Integer id, Integer memberId, Short inviteType, String email, String guestBook,
            String inviteName, String relation, String inviteIp, Boolean inviteSuccess) {
        this.id = id;
        this.memberId = memberId;
        this.inviteType = inviteType;
        this.email = email;
        this.guestBook = guestBook;
        this.inviteName = inviteName;
        this.relation = relation;
        this.inviteIp = inviteIp;
        this.inviteSuccess = inviteSuccess;
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

    @Column(name = "memberId")
    public Integer getMemberId() {
        return this.memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    @Column(name = "inviteType", nullable = false)
    public Short getInviteType() {
        return this.inviteType;
    }

    public void setInviteType(Short inviteType) {
        this.inviteType = inviteType;
    }

    @Column(name = "email", length = 1000)
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "guestBook", length = 2000)
    public String getGuestBook() {
        return this.guestBook;
    }

    public void setGuestBook(String guestBook) {
        this.guestBook = guestBook;
    }

    @Column(name = "inviteName", length = 10)
    public String getInviteName() {
        return this.inviteName;
    }

    public void setInviteName(String inviteName) {
        this.inviteName = inviteName;
    }

    @Column(name = "relation", length = 30)
    public String getRelation() {
        return this.relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    @Column(name = "inviteIp", length = 30)
    public String getInviteIp() {
        return this.inviteIp;
    }

    public void setInviteIp(String inviteIp) {
        this.inviteIp = inviteIp;
    }

    @Column(name = "inviteSuccess")
    public Boolean getInviteSuccess() {
        return inviteSuccess;
    }

    public void setInviteSuccess(Boolean inviteSuccess) {
        this.inviteSuccess = inviteSuccess;
    }
    
    @Column(name = "gmt_created", length = 19)
    public Date getGmtCreated() {
        return this.gmtCreated;
    }

    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    @Column(name = "gmt_modified", length = 19)
    public Date getGmtModified() {
        return this.gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }


}