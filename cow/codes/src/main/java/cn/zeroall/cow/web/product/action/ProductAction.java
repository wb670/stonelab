package cn.zeroall.cow.web.product.action;

import java.util.List;

import cn.zeroall.cow.biz.BizException;
import cn.zeroall.cow.biz.comment.ao.CommentAO;
import cn.zeroall.cow.biz.product.ao.ProductAO;
import cn.zeroall.cow.biz.product.vo.ProductDetailVO;
import cn.zeroall.cow.biz.product.vo.ProductListVO;
import cn.zeroall.cow.dal.product.po.CommentPO;
import cn.zeroall.cow.service.image.model.Image;
import cn.zeroall.cow.web.BaseAction;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Aug 13, 2009
 */
public class ProductAction extends BaseAction {

    private static final long serialVersionUID = 7159832146624231259L;

    private static final Integer PAGE_SIZE = 12;

    private ProductAO productAO;
    
    private CommentAO commentAO;

    /********************************************
     ** list页面
     ********************************************/
    // param
    private String categoryId;
    private String page;
    // result
    private ProductListVO productListVO;
    
    List<CommentPO> commentList;

    public String list() {
        productListVO = productAO.getProducts(Integer.valueOf(categoryId), Integer.valueOf(getPage()), PAGE_SIZE);
        hotCommentList = commentAO.getHotComments();
        return SUCCESS;
    }

    /********************************************
     ** detail页面
     ********************************************/
    // param
    private String id;
    // result
    private ProductDetailVO detailVO;

    public String detail() {
        detailVO = productAO.getProductDetail(Integer.valueOf(id));
        Integer pageInt = Integer.valueOf(getPage());
        try {
            commentList = commentAO.findComment(pageInt, PAGE_SIZE, detailVO.getProduct().getId());
            hotCommentList = commentAO.getHotComments();
        } catch (BizException e) {
            addActionError(e.getBizCode().getMessage());
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

   
    

    /********************************************
     ** image list页面
     ********************************************/
    // param
    private Integer imgGroupId;
    // result
    private List<Image> images;

    public String imageList() {
        images = productAO.getImages(imgGroupId);
        return SUCCESS;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getPage() {
        if (page == null) {
            return "1";
        }
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public ProductListVO getProductListVO() {
        return productListVO;
    }

    public void setProductListVO(ProductListVO productListVO) {
        this.productListVO = productListVO;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDetailVO(ProductDetailVO detailVO) {
        this.detailVO = detailVO;
    }

    public ProductDetailVO getDetailVO() {
        return detailVO;
    }

    public Integer getImgGroupId() {
        return imgGroupId;
    }

    public void setImgGroupId(Integer imgGroupId) {
        this.imgGroupId = imgGroupId;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public void setProductAO(ProductAO productAO) {
        this.productAO = productAO;
    }

    public List<CommentPO> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<CommentPO> commentList) {
        this.commentList = commentList;
    }

    public void setCommentAO(CommentAO commentAO) {
        this.commentAO = commentAO;
    }

}
