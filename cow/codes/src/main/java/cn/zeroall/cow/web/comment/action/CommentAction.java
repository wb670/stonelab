package cn.zeroall.cow.web.comment.action;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import cn.zeroall.chameleon.paginator.PageList;
import cn.zeroall.chameleon.paginator.Paginator;
import cn.zeroall.cow.biz.BizException;
import cn.zeroall.cow.biz.comment.ao.CommentAO;
import cn.zeroall.cow.dal.product.po.CommentPO;
import cn.zeroall.cow.service.member.model.Member;
import cn.zeroall.cow.web.BaseAction;
import cn.zeroall.cow.web.common.constants.SessionConstant;

/**
 * @category 评论
 */
public class CommentAction extends BaseAction {

    private static final long serialVersionUID = -3359372962548452541L;

    private CommentAO commentAO;

    private PageList<CommentPO> commentList;
    private Paginator paginator;
    private static final int PAGE_SIZE = 12;
    private String page;
    CommentPO commentPO = new CommentPO();
    private Integer id;
    private Integer productId;
    private Integer memberId;
    private Integer referenceNum = 0;// 被引用的次数
    private Integer referenceCommentId;// 被引用的留言ID号
    private Boolean hotComment = false;// 热点评论
    private String name;
    private String status;
    private String title;
    private String body;
    private Integer rank;

    private Member getMember() {
        return (Member) getSession().get(SessionConstant.MEMBER_SESSION);
    }

    /***************************************************************************
     * * 评论列表
     **************************************************************************/

    public String commentList() {

        try {
            Integer pageInt = Integer.valueOf(getPage());
            commentList = commentAO.findComment(pageInt, PAGE_SIZE, productId);
            paginator = commentList.getPaginator();
        } catch (BizException e) {
            addActionError(e.getBizCode().getMessage());
            return INPUT;
        }
        return SUCCESS;
    }

    /***************************************************************************
     * * hot评论
     **************************************************************************/
    List<CommentPO> hotCommentList;

    public List<CommentPO> getHotCommentList() {
        return hotCommentList;
    }

    public void setHotCommentList(List<CommentPO> hotCommentList) {
        this.hotCommentList = hotCommentList;
    }

    public String detail() {

        try {
            CommentPO po = commentAO.getById(id);
            this.body = po.getBody();
        } catch (BizException e) {
            addActionError(e.getBizCode().getMessage());
        }
        return SUCCESS;
    }

    /**
     * 使用jquery添加留言
     * 
     * @return
     */
    public String add() {
        Member member = this.getMember();
        try {
            if (member != null) {
                commentPO.setMemberId(member.getId());
                commentPO.setName(member.getName());
                commentPO.setBody(this.getBody());
                commentPO.setProductId(this.getProductId());
                commentPO.setRank(this.getRank());
                if (commentAO.add(commentPO, referenceCommentId) != null) {
                    Integer pageInt = Integer.valueOf(getPage());
                    commentList = commentAO.findComment(pageInt, PAGE_SIZE, productId);
                    paginator = commentList.getPaginator();
                }
            }

        } catch (BizException e) {
            addActionError(e.getBizCode().getMessage());
        }

        return SUCCESS;
    }

    public void setCommentAO(CommentAO commentAO) {
        this.commentAO = commentAO;
    }

    public CommentPO getCommentPO() {
        return commentPO;
    }

    public void setCommentPO(CommentPO commentPO) {
        this.commentPO = commentPO;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getReferenceNum() {
        return referenceNum;
    }

    public void setReferenceNum(Integer referenceNum) {
        this.referenceNum = referenceNum;
    }

    public Boolean getHotComment() {
        return hotComment;
    }

    public void setHotComment(Boolean hotComment) {
        this.hotComment = hotComment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getPage() {
        if (!StringUtils.isNumeric(page)) {
            return "1";
        }
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public PageList<CommentPO> getCommentList() {
        return commentList;
    }

    public void setCommentList(PageList<CommentPO> commentList) {
        this.commentList = commentList;
    }

    public Paginator getPaginator() {
        return paginator;
    }

    public void setPaginator(Paginator paginator) {
        this.paginator = paginator;
    }

    public Integer getReferenceCommentId() {
        return referenceCommentId;
    }

    public void setReferenceCommentId(Integer referenceCommentId) {
        this.referenceCommentId = referenceCommentId;
    }

}
