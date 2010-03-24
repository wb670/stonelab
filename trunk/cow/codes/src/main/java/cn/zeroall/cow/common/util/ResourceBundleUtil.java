package cn.zeroall.cow.common.util;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Feb 15, 2009
 */
public class ResourceBundleUtil {

    private String properties;
    private Map<Locale, ResourceBundle> resources = new HashMap<Locale, ResourceBundle>();

    public ResourceBundleUtil(String properties) {
        this.properties = properties;
    }

    public String getMessage(String key) {
        return getMessage(key, null);
    }

    public String getMessage(String key, Locale locale) {
        if (locale == null) {
            locale = Locale.getDefault();
        }
        if (resources.get(locale) == null) {
            resources.put(locale, ResourceBundle.getBundle(properties, locale));
        }
        return resources.get(locale).getString(key);
    }

}
