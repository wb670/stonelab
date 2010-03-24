package cn.zeroall.cow.service.template;

import java.util.HashMap;
import java.util.Map;

import cn.zeroall.cow.BaseTestCase;
import cn.zeroall.cow.service.template.impl.VelocityTemplateService;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Jul 2, 2009
 */
public class VelocityTemplateServiceTest extends BaseTestCase {

    private VelocityTemplateService velocityTemplateService;

    @Override
    protected void setUp() throws Exception {
        velocityTemplateService = new VelocityTemplateService();
        velocityTemplateService.afterPropertiesSet();
    }

    public void testMerge() {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("name", "stone2083");
        String result = velocityTemplateService.merge("welcome ${name}", model);
        System.out.println(result);
    }

}
