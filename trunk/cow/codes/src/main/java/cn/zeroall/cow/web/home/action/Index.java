package cn.zeroall.cow.web.home.action;

import java.util.List;

import cn.zeroall.cow.biz.comment.ao.CommentAO;
import cn.zeroall.cow.biz.home.ao.HomeAO;
import cn.zeroall.cow.biz.home.vo.IndexVO;
import cn.zeroall.cow.dal.product.po.CommentPO;
import cn.zeroall.cow.web.BaseAction;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Mar 8, 2009
 */
public class Index extends BaseAction {

    private static final long serialVersionUID = 6515738367616475936L;

    private HomeAO homeAO;

    private IndexVO indexVO;

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
    
    
    @Override
    public String execute() throws Exception {
        indexVO = homeAO.query();
        hotCommentList = commentAO.getHotComments();
        return SUCCESS;
    }

    public IndexVO getIndexVO() {
        return indexVO;
    }

    public void setIndexVO(IndexVO indexVO) {
        this.indexVO = indexVO;
    }

    public void setHomeAO(HomeAO homeAO) {
        this.homeAO = homeAO;
    }

}
