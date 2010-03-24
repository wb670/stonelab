package cn.zeroall.cow.web.test.action;

import java.util.Locale;
import java.util.Map;
import java.util.Random;

import cn.zeroall.chameleon.vtree.Tree;
import cn.zeroall.cow.biz.BizException;
import cn.zeroall.cow.biz.test.ao.TestAO;
import cn.zeroall.cow.dal.product.po.ProductCategoryPO;
import cn.zeroall.cow.web.BaseAction;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Feb 15, 2009
 */
public class QueryCategoryAction extends BaseAction {

    private static final long serialVersionUID = 5654649045809546302L;

    private Tree<ProductCategoryPO> category;

    private TestAO testAO;

    @Override
    public String execute() throws Exception {
        try {
            category = testAO.getProductCategory();
        } catch (BizException e) {
            addActionError(e.getBizCode().getMessage(Locale.getDefault()));
        }
        Map<String, Object> session = getSession();
        session.put("cart", new Random().nextInt(1000));
        setSession(session);
        return SUCCESS;
    }

    public Tree<ProductCategoryPO> getCategory() {
        return category;
    }

    public void setCategory(Tree<ProductCategoryPO> category) {
        this.category = category;
    }

    public void setTestAO(TestAO testAO) {
        this.testAO = testAO;
    }

}
