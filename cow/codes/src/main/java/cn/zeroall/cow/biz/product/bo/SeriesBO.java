package cn.zeroall.cow.biz.product.bo;

import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;

import cn.zeroall.cow.biz.BaseBO;
import cn.zeroall.cow.biz.product.enums.SeriesType;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Jun 30, 2009
 */
public class SeriesBO implements BaseBO {

    private String split;
    private ResourceBundle resource;

    /**
     * 根据系列名，得到该系列下所有类目
     * 
     * @param type
     * @return
     */
    public Integer[] getCategoryIds(SeriesType type) {
        if (type == null) {
            throw new IllegalArgumentException("type is null.");
        }
        String value = resource.getString(type.toString());
        if (StringUtils.isBlank(value)) {
            return new Integer[0];
        }
        String[] splited = StringUtils.split(value, split);
        Integer[] categoryIds = new Integer[splited.length];
        for (int i = 0; i < splited.length; i++) {
            categoryIds[i] = Integer.valueOf(splited[i]);
        }
        return categoryIds;
    }

    public void setConfig(String config) {
        resource = ResourceBundle.getBundle(config);
    }

    public void setSplit(String split) {
        this.split = split;
    }

}
