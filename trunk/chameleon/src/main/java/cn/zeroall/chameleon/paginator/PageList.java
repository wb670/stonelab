package cn.zeroall.chameleon.paginator;

import java.util.ArrayList;
import java.util.List;

/**
 * 带分页器的List
 * 
 * @author Maurice.J Jan 27, 2009
 */
public class PageList<E> extends ArrayList<E> {

    private static final long serialVersionUID = 7631637042492001167L;

    private Paginator paginator;// 分页器

    /**
     * 构造 PageList
     * 
     * @param list list
     * @param paginator 分页器
     */
    public PageList(List<E> list, Paginator paginator) {
	if (paginator == null) {
	    throw new IllegalArgumentException("paginator is null.");
	}
	this.addAll(list);
	this.paginator = paginator;
    }

    /**
     * 得到分页器
     * 
     * @return 分页器
     */
    public Paginator getPaginator() {
	return paginator;
    }

}
