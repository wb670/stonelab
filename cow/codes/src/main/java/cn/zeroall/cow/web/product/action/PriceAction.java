package cn.zeroall.cow.web.product.action;

import java.util.List;

import cn.zeroall.cow.biz.comment.ao.CommentAO;
import cn.zeroall.cow.biz.product.ao.PriceAO;
import cn.zeroall.cow.biz.product.vo.PriceRangeVO;
import cn.zeroall.cow.dal.product.po.CommentPO;
import cn.zeroall.cow.web.BaseAction;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Mar 23, 2009
 */
public class PriceAction extends BaseAction {

    private static final long serialVersionUID = -1169653700521604594L;

    private static final int PAGE_SIZE = 12;
    private static final int PRICE_RANGE = 100;
    private static final int MAX_PRICE = 100000;

    // param
    private String price;
    private String page;

    // result
    private PriceRangeVO priceRangeVO;

    // manager
    private PriceAO priceAO;

    public String list() throws Exception {
        Integer min = Integer.valueOf(getPrice());
        Integer max = getMax();
        Integer page = Integer.valueOf(getPage());
        Integer pageSize = PAGE_SIZE;
        priceRangeVO = priceAO.getProducts(min, max, page, pageSize);
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
    
    

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    private Integer getMax() {
        if (Integer.valueOf(price) >= 300) {
            return MAX_PRICE;
        }
        return Integer.valueOf(price) + PRICE_RANGE;
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

    public void setPriceRangeVO(PriceRangeVO priceRangeVO) {
        this.priceRangeVO = priceRangeVO;
    }

    public PriceRangeVO getPriceRangeVO() {
        return priceRangeVO;
    }

    public void setPriceAO(PriceAO priceAO) {
        this.priceAO = priceAO;
    }

}
