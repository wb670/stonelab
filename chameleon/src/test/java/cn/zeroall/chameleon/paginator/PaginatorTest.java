package cn.zeroall.chameleon.paginator;

import java.util.Arrays;

import junit.framework.TestCase;

/**
 * 分页器单元测试
 * 
 * @author Maurice.J Jan 27, 2009
 */
public class PaginatorTest extends TestCase {

    private int[] DIVIDE_EXACTLY_PARAM = { 1, 10, 100 };
    private int[] DIVIDE_INEXACTLY_PARAM = { 12, 10, 102 };
    private int[] PAGE_EXCEPTION_PARAM = { 0, 10, 102 };
    private int[] TOTAL_EXCEPTION_PARAM = { 3, 10, 0 };

    private Paginator exactly;
    private Paginator inexactly;
    private Paginator pageException;
    private Paginator totalException;

    @Override
    protected void setUp() throws Exception {
	exactly = new Paginator(DIVIDE_EXACTLY_PARAM[0], DIVIDE_EXACTLY_PARAM[1],
		DIVIDE_EXACTLY_PARAM[2]);
	inexactly = new Paginator(DIVIDE_INEXACTLY_PARAM[0], DIVIDE_INEXACTLY_PARAM[1],
		DIVIDE_INEXACTLY_PARAM[2]);
	pageException = new Paginator(PAGE_EXCEPTION_PARAM[0], PAGE_EXCEPTION_PARAM[1],
		PAGE_EXCEPTION_PARAM[2]);
	totalException = new Paginator(TOTAL_EXCEPTION_PARAM[0], TOTAL_EXCEPTION_PARAM[1],
		TOTAL_EXCEPTION_PARAM[2]);
    }

    /**
     * 测试"得到当前页码数"
     */
    public void testGetPage() {
	assertEquals(1, exactly.getPage());
	assertEquals(11, inexactly.getPage());
	assertEquals(1, pageException.getPage());
	assertEquals(0, totalException.getPage());
    }

    /**
     * 测试"得到总数"
     */
    public void testGetItemsTotal() {
	assertEquals(100, exactly.getItemsTotal());
	assertEquals(102, inexactly.getItemsTotal());
	assertEquals(102, pageException.getItemsTotal());
	assertEquals(0, totalException.getItemsTotal());
    }

    /**
     * 测试"得到每页显示数量"
     */
    public void testGetItemsPerPage() {
	assertEquals(10, exactly.getItemsPerPage());
	assertEquals(10, inexactly.getItemsPerPage());
	assertEquals(10, pageException.getItemsPerPage());
	assertEquals(10, totalException.getItemsPerPage());
    }

    /**
     * 测试"得到页码数量"
     */
    public void testGetPages() {
	assertEquals(10, exactly.getPages());
	assertEquals(11, inexactly.getPages());
	assertEquals(11, pageException.getPages());
	assertEquals(0, totalException.getPages());
    }

    /**
     * 测试"得到前一页"
     */
    public void testGetPrePage() {
	assertEquals(1, exactly.getPrePage());
	assertEquals(10, inexactly.getPrePage());
	assertEquals(1, pageException.getPrePage());
	assertEquals(0, totalException.getPrePage());
    }

    /**
     * 测试"得到下一页"
     */
    public void testGetNextPage() {
	assertEquals(2, exactly.getNextPage());
	assertEquals(11, inexactly.getNextPage());
	assertEquals(2, pageException.getNextPage());
	assertEquals(0, totalException.getNextPage());
    }

    /**
     * 测试"得到当前页开始显示的index"
     */
    public void testGetBeginIndex() {
	assertEquals(1, exactly.getBeginIndex());
	assertEquals(101, inexactly.getBeginIndex());
	assertEquals(1, pageException.getBeginIndex());
	assertEquals(0, totalException.getBeginIndex());
    }

    /**
     * 测试"得到当前页最后显示的index"
     */
    public void testGetEndIndex() {
	assertEquals(10, exactly.getEndIndex());
	assertEquals(102, inexactly.getEndIndex());
	assertEquals(10, pageException.getEndIndex());
	assertEquals(0, totalException.getEndIndex());
    }

    /**
     * 测试"得到当前页显示的条数"
     */
    public void testGetLength() {
	assertEquals(10, exactly.getLength());
	assertEquals(2, inexactly.getLength());
	assertEquals(10, pageException.getLength());
	assertEquals(0, totalException.getLength());
    }

    /**
     * 测试"得到滑动栏"
     */
    public void testGetSlider() {
	assertEquals("[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]", array2Str(exactly.getSlider(11)));
	assertEquals("[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]", array2Str(exactly.getSlider(10)));
	assertEquals("[1, 2, 3, 4, 5, 6, 7]", array2Str(exactly.getSlider(7)));
	assertEquals("[1, 2, 3]", array2Str(exactly.getSlider(3)));
	assertEquals("[1, 2]", array2Str(exactly.getSlider(2)));
	assertEquals("[]", array2Str(exactly.getSlider(0)));

	assertEquals("[]", array2Str(totalException.getSlider()));
	System.out.println(exactly);
    }

    /**
     * 将int型数组转变成字符串形式
     * 
     * @param array
     * @return
     */
    private String array2Str(int[] array) {
	return Arrays.toString(array);
    }
}
