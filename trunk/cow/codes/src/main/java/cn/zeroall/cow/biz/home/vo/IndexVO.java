package cn.zeroall.cow.biz.home.vo;

import java.util.List;

import cn.zeroall.cow.biz.BaseVO;
import cn.zeroall.cow.service.product.model.Product;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Mar 8, 2009
 */
public class IndexVO implements BaseVO {

    private static final long serialVersionUID = -677282700153292103L;

    private List<Product> hots;
    private List<Product> recommends;
    private List<Product> news;

    public List<Product> getHots() {
        return hots;
    }

    public void setHots(List<Product> hots) {
        this.hots = hots;
    }

    public List<Product> getRecommends() {
        return recommends;
    }

    public void setRecommends(List<Product> recommends) {
        this.recommends = recommends;
    }

    public List<Product> getNews() {
        return news;
    }

    public void setNews(List<Product> news) {
        this.news = news;
    }

}
