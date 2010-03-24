package cn.zeroall.cow.web.test.action;

import cn.zeroall.cow.biz.test.model.TestModel;
import cn.zeroall.cow.web.BaseAction;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Jun 24, 2009
 */
public class MultiMethodAction extends BaseAction {

    private static final long serialVersionUID = 3507365187704584816L;

    private TestModel test;

    public String query() {
        return SUCCESS;
    }

    public String update() {
        test.setName("update");
        test.setPassword("update");
        return SUCCESS;
    }

    public TestModel getTest() {
        return test;
    }

    public void setTest(TestModel test) {
        this.test = test;
    }

}
