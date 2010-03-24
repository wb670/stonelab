package cn.zeroall.cow.biz.comment.ao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zeroall.chameleon.paginator.PageList;
import cn.zeroall.chameleon.paginator.Paginator;
import cn.zeroall.cow.biz.BaseAO;
import cn.zeroall.cow.biz.BizException;
import cn.zeroall.cow.biz.comment.CommentCode;
import cn.zeroall.cow.dal.comment.hibernate.CommentHibernateDAO;
import cn.zeroall.cow.dal.product.po.CommentPO;

public class CommentAO implements BaseAO {

    private CommentHibernateDAO commentHibernateDAO;

    private CommentCache cache = new CommentCache();

    private long expireTime = 1200000;// 20分钟

    private long loadtime;

    public void init() {
        load();
    }

    /**
     * load HotComments
     */
    private void load() {
        // read from dao
        List<CommentPO> list = commentHibernateDAO.findHotComments();
        // set list
        cache.setList(list);
        // set map
        Map<Integer, CommentPO> map = new HashMap<Integer, CommentPO>();
        for (CommentPO commentPO : list) {
            map.put(commentPO.getId(), commentPO);
        }
        cache.setMap(map);
        // set isLoaded
        cache.setLoaded(true);
        
        //record load time
        loadtime = new Date().getTime();
    }

    public List<CommentPO> getHotComments() {
        if (!cache.isLoaded()) {
            load();
        }
        else if (new Date().getTime() > loadtime + expireTime) {
            this.load();
        }
        System.out.println(cache.getList());
        return cache.getList();
    }

    private static class CommentCache {

        private List<CommentPO> list;
        private Map<Integer, CommentPO> map;
        private boolean isLoaded = false;

        public List<CommentPO> getList() {
            return list;
        }

        public void setList(List<CommentPO> list) {
            this.list = list;
        }

        public Map<Integer, CommentPO> getMap() {
            return map;
        }

        public void setMap(Map<Integer, CommentPO> map) {
            this.map = map;
        }

        public boolean isLoaded() {
            return isLoaded;
        }

        public void setLoaded(boolean isLoaded) {
            this.isLoaded = isLoaded;
        }
    }
    
    

    public PageList<CommentPO> findComment(int page, int PAGE_SIZE, int productId)
            throws BizException {

        Integer total = commentHibernateDAO.getRowCount(productId);
        if (total == 0) {
            return  new PageList<CommentPO>(new ArrayList<CommentPO>(0), new Paginator(page, PAGE_SIZE, 0));
        }
        Paginator paginator = new Paginator(page, PAGE_SIZE, total);

        List<CommentPO> commentPOList = commentHibernateDAO.findComments(
                paginator.getBeginIndex() - 1, PAGE_SIZE, productId);
        return new PageList<CommentPO>(commentPOList, paginator);

    }

    public CommentPO getById(Integer id) throws BizException {
        return commentHibernateDAO.get(id);
    }

    public List<CommentPO> findByProductId(Integer productId) throws BizException {

        if (productId == null) {
            throw new IllegalArgumentException("productId is null");
        }
        return commentHibernateDAO.getByProductId(productId);

    }

    public Integer add(CommentPO commentPO, Integer referenceCommentId) throws BizException {

        if (commentPO == null) {
            throw new IllegalArgumentException("commentPO is null");
        }
        if (referenceCommentId != null) {
            CommentPO cPO = commentHibernateDAO.get(referenceCommentId);
            cPO.setReferenceNum(cPO.getReferenceNum() + 1);
            commentHibernateDAO.update(cPO);
        }

        Date now = new Date();
        commentPO.setGmtCreated(now);
        commentPO.setGmtModified(now);
        commentPO.setStatus(CommentCode.ENABLED.getMessage());
        Integer result = commentHibernateDAO.add(commentPO);

        this.load();// reload hotComments
        return result;

    }

    public void delete(CommentPO commentPO) throws BizException {

        if (commentPO == null) {
            throw new IllegalArgumentException("commentPO is null");
        }
        commentHibernateDAO.delete(commentPO);
    }

    public void update(CommentPO commentPO) throws BizException {

        if (commentPO == null) {
            throw new IllegalArgumentException("commentPO is null");
        }
        commentPO.setGmtModified(new Date());
        commentHibernateDAO.update(commentPO);
        this.load();// reload hotComments
    }

    public void setCommentHibernateDAO(CommentHibernateDAO commentHibernateDAO) {
        this.commentHibernateDAO = commentHibernateDAO;
    }

}
