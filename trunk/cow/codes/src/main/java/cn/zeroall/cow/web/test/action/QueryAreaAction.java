package cn.zeroall.cow.web.test.action;

import java.util.Locale;

import cn.zeroall.chameleon.vtree.Tree;
import cn.zeroall.cow.biz.BizException;
import cn.zeroall.cow.biz.test.ao.TestAO;
import cn.zeroall.cow.dal.common.po.RegionPO;
import cn.zeroall.cow.web.BaseAction;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Feb 15, 2009
 */
public class QueryAreaAction extends BaseAction {

    private static final long serialVersionUID = 5654649045809546302L;

    private Tree<RegionPO> area;

    private TestAO testAO;

    @Override
    public String execute() throws Exception {
        try {
            area = testAO.getArea();
        } catch (BizException e) {
            addActionError(e.getBizCode().getMessage(Locale.getDefault()));
        }
        return SUCCESS;
    }

    public Tree<RegionPO> getArea() {
        return area;
    }

    public void setArea(Tree<RegionPO> area) {
        this.area = area;
    }

    public void setTestAO(TestAO testAO) {
        this.testAO = testAO;
    }

}
