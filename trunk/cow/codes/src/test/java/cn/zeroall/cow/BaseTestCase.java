package cn.zeroall.cow;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Feb 28, 2009
 */
public abstract class BaseTestCase extends TestCase {

    private static final ApplicationContext context = new ClassPathXmlApplicationContext(
            "spring.xml");

    public Object getBean(String bean) {
        return context.getBean(bean);
    }

}
