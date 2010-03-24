package cn.zeroall.cow.service.template;

import java.io.File;
import java.io.InputStream;
import java.util.Map;

import cn.zeroall.cow.service.BaseService;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Jul 2, 2009
 */
public interface TemplateService extends BaseService {

    /**
     * 
     * @param content 模板内容
     * @param model 数据对象
     * @return 模板和数据merge后内容
     */
    public String merge(String content, Map<String, Object> model);

    /**
     * 
     * @param file 模板文件
     * @param model 数据对象
     * @return 模板和数据merge后内容
     */
    public String merge(File file, Map<String, Object> model);

    /**
     * 
     * @param input 模板输入流
     * @param model 数据对象
     * @return 模板和数据merge后内容
     */
    public String merge(InputStream input, Map<String, Object> model);

}
