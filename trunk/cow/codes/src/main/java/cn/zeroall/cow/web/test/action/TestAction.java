package cn.zeroall.cow.web.test.action;

import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import cn.zeroall.cow.biz.test.ao.TestAO;
import cn.zeroall.cow.dal.test.po.TestPO;
import cn.zeroall.cow.web.BaseAction;

/**
 * Test Action
 * 
 * @author Maurice.J Feb 2, 2009
 */
public class TestAction extends BaseAction {

    private static final long serialVersionUID = 8631590083658857903L;

    private static final Integer ID = 1;

    private TestPO testPO;

    private TestAO testAO;

    private HttpServletRequest httpServletRequest;

    @Override
    public String execute() throws Exception {
        Map<String, Object> map = httpServletRequest.getParameterMap();
        Set<Entry<String, Object>> set = map.entrySet();
        for (Entry<String, Object> entry : set) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
        testPO = testAO.findTest(ID);
        return SUCCESS;
    }

    public void setTestAO(TestAO testAO) {
        this.testAO = testAO;
    }

    public TestPO getTestPO() {
        return testPO;
    }

    public void setTestPO(TestPO testPO) {
        this.testPO = testPO;
    }

    public HttpServletRequest getHttpServletRequest() {
        return httpServletRequest;
    }

    public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

}
