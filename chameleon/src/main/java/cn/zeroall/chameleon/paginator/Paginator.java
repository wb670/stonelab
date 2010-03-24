package cn.zeroall.chameleon.paginator;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 分页器
 * 
 * @author Maurice.J Jan 27, 2009
 */
public class Paginator implements Serializable {

    private static final long serialVersionUID = 4588561553971217873L;

    private static final int DEFAULT_ITEMS_PER_PAGE = 20; // 默认每页条目显示数量(20)
    private static final int DEFAULT_SLIDER_SIZE = 7; // 默认滑动栏大小(7)

    private int page; // 当前页码
    private int itemsTotal;// 条目总量
    private int itemsPerPage;// 每页显示条目的数量

    /**
     * 构建 Paginator
     * 
     * @param itemsTotal 总量
     */
    public Paginator(int itemsTotal) {
        this(0, DEFAULT_ITEMS_PER_PAGE, itemsTotal);
    }

    /**
     * 构建 Paginator
     * 
     * @param page 当前页码数
     * @param itemsTotal 总量
     */
    public Paginator(int page, int itemsTotal) {
        this(page, DEFAULT_ITEMS_PER_PAGE, itemsTotal);
    }

    /**
     * 构建 Paginator
     * 
     * @param page 当前页码数
     * @param itemsPerPage 每页显示数量
     * @param itemsTotal 总数
     */
    public Paginator(int page, int itemsPerPage, int itemsTotal) {
        // 参数校验
        if (page < 0) {
            throw new IllegalArgumentException("page must be greater than or equal with zero.");
        }
        if (itemsPerPage <= 0) {
            throw new IllegalArgumentException("itemsPerPage must be greater than zero.");
        }
        if (itemsTotal < 0) {
            throw new IllegalArgumentException(
                    "itemsTotal must be greater than or equal with zero.");
        }
        this.itemsTotal = itemsTotal;
        this.itemsPerPage = itemsPerPage;
        this.page = calcPage(page);
    }

    /**
     * 得到页数总量
     * 
     * @return 页数总量
     */
    public int getPages() {
        return (int) Math.ceil((double) itemsTotal / itemsPerPage);
    }

    /**
     * 得到上一页页码数
     * 
     * @return 上一页页码数
     */
    public int getPrePage() {
        return calcPage(page - 1);
    }

    /**
     * 得到下一页页码数
     * 
     * @return 下一页页码数
     */
    public int getNextPage() {
        return calcPage(page + 1);
    }

    /**
     * 得到当前页显示的数量
     * 
     * @return 当前页显示数量
     */
    public int getLength() {
        if (page > 0) {
            return Math.min(itemsPerPage * page, itemsTotal) - (itemsPerPage * (page - 1));
        } else {
            return 0;
        }
    }

    /**
     * 得到滑动栏
     * 
     * @return 滑动栏显示情况
     */
    public int[] getSlider() {
        return getSlider(DEFAULT_SLIDER_SIZE);
    }

    /**
     * 得到滑动栏
     * 
     * <pre>
     *   _________________________________________________________________
     *   &lt;&lt;    &lt;    1    2    3    4    [5]    6    7    8    9    &gt;    &gt;&gt;
     *   &circ;     &circ;                         &circ;                         &circ;     &circ; 
     * 第一页  上一页                     当前页                     下一页  最后页
     * </pre>
     * 
     * @param width 滑动栏显示数量
     * @return 滑动栏显示情况
     */
    public int[] getSlider(int width) {
        int pages = getPages();

        if ((pages < 1) || (width < 1)) {
            return new int[0];
        } else {
            if (width > pages) {
                width = pages;
            }

            int[] slider = new int[width];
            int first = page - ((width - 1) / 2);

            if (first < 1) {
                first = 1;
            }

            if (((first + width) - 1) > pages) {
                first = pages - width + 1;
            }

            for (int i = 0; i < width; i++) {
                slider[i] = first + i;
            }

            return slider;
        }
    }

    /**
     * 得到当前页开始条目的Index编号
     * 
     * @return 当前页开始条目的Index编号
     */
    public int getBeginIndex() {
        if (page > 0) {
            return (itemsPerPage * (page - 1)) + 1;
        } else {
            return 0;
        }
    }

    /**
     * 得到当前页结束条目的Index编号
     * 
     * @return 当前页结束条目的Index编号
     */
    public int getEndIndex() {
        if (page > 0) {
            return Math.min(itemsPerPage * page, itemsTotal);
        } else {
            return 0;
        }
    }

    /**
     * 得到当前页码数
     * 
     * @return 当前页码数
     */
    public int getPage() {
        return page;
    }

    /**
     * 设置当前页码数
     * 
     * @param page 当前页码数
     */
    public void setPage(int page) {
        if (page < 0) {
            throw new IllegalArgumentException("page must be greater than or equal with zero.");
        }
        this.page = calcPage(page);
    }

    /**
     * 得到条目总数
     * 
     * @return 条目总数
     */
    public int getItemsTotal() {
        return itemsTotal;
    }

    /**
     * 得到每页显示条目数量
     * 
     * @return 每页显示条目数量
     */
    public int getItemsPerPage() {
        return itemsPerPage;
    }

    /**
     * 计算当前页码数
     * 
     * @param page 当前页码数，必须大于等于0
     * @return 当前页码数
     */
    private int calcPage(int page) {
        int pages = getPages();
        if (pages > 0) {
            if (page >= 1) {
                return (page < pages) ? page : pages;
            } else {
                return 1;
            }
        }
        return 0;
    }

    /**
     * toString
     */
    public String toString() {
        StringBuilder sb = new StringBuilder("Paginator:").append("\n");
        sb.append("itemsPerPage=").append(this.getItemsPerPage()).append("\n");
        sb.append("itemsTotal=").append(this.getItemsTotal()).append("\n");
        sb.append("page=").append(this.getPage()).append("\n");
        sb.append("prePage=").append(this.getPrePage()).append("\n");
        sb.append("nextPage=").append(this.getNextPage()).append("\n");
        sb.append("beginIndex=").append(this.getBeginIndex()).append("\n");
        sb.append("endIndex=").append(this.getEndIndex()).append("\n");
        sb.append("length=").append(this.getLength()).append("\n");
        sb.append("slider=").append(Arrays.toString(this.getSlider())).append("\n");
        return sb.toString();
    }

}
