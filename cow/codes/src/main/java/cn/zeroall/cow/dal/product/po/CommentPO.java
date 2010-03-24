package cn.zeroall.cow.dal.product.po;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "comment", catalog = "cow")
public class CommentPO implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private Integer productId;
    private Integer memberId;
    private Integer referenceNum=0;// 被引用的次数
    private Boolean hotComment = false;// 热点评论
    private String name;
    private String status;
    private String title;
    private String body;
    private Integer rank;
    private Date gmtCreated;
    private Date gmtModified;


    /** default constructor */
    public CommentPO() {
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

    @Column(name = "product_id", nullable = false)
    public Integer getProductId() {
        return this.productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    @Column(name = "member_id", nullable = false)
    public Integer getMemberId() {
        return this.memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    @Column(name = "name", length = 10)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "status", length = 30)
    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "title", length = 50)
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "body", length = 300)
    public String getBody() {
        return this.body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Column(name = "rank")
    public Integer getRank() {
        return this.rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
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

    @Column(name = "reference_num", nullable = true)
    public Integer getReferenceNum() {
        return referenceNum;
    }

    public void setReferenceNum(Integer referenceNum) {
        this.referenceNum = referenceNum;
    }

    @Column(name = "hot_comment", nullable = true)
    public Boolean getHotComment() {
        return hotComment;
    }

    public void setHotComment(Boolean hotComment) {
        this.hotComment = hotComment;
    }

}