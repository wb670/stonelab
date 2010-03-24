package cn.zeroall.cow.web.product.action;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import cn.zeroall.cow.biz.comment.ao.CommentAO;
import cn.zeroall.cow.biz.product.ao.SeriesAO;
import cn.zeroall.cow.biz.product.enums.SeriesType;
import cn.zeroall.cow.biz.product.vo.ProductListVO;
import cn.zeroall.cow.dal.product.po.CommentPO;
import cn.zeroall.cow.web.BaseAction;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Jun 30, 2009
 */
public class SeriesAction extends BaseAction {

    private static final long serialVersionUID = 5879684720979723400L;

    private static final int PAGE_SIZE = 12;

    private String type;
    private String page;
    private ProductListVO productListVO;

    private SeriesAO seriesAO;

    public String list() {
        SeriesType seriesType = Enum.valueOf(SeriesType.class, type);
        Integer pageInt = Integer.valueOf(getPage());
        productListVO = seriesAO.getProducts(seriesType, pageInt, PAGE_SIZE);
        hotCommentList = commentAO.getHotComments();
        return SUCCESS;
    }
    
    
    
    /***************************************************************************
     * * hot评论
     **************************************************************************/
    List<CommentPO> hotCommentList;
    private CommentAO commentAO;

    public void setCommentAO(CommentAO commentAO) {
        this.commentAO = commentAO;
    }

    public List<CommentPO> getHotCommentList() {
        return hotCommentList;
    }

    public void setHotCommentList(List<CommentPO> hotCommentList) {
        this.hotCommentList = hotCommentList;
    }

      
    

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public ProductListVO getProductListVO() {
        return productListVO;
    }

    public void setProductListVO(ProductListVO productListVO) {
        this.productListVO = productListVO;
    }

    public void setSeriesAO(SeriesAO seriesAO) {
        this.seriesAO = seriesAO;
    }

}
