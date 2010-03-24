package cn.zeroall.cow.service.common.model;

import java.io.Serializable;

/**
 * key,value attibute
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Feb 28, 2009
 */
public class KeyValueAttribute implements Serializable {

    private static final long serialVersionUID = -6151963056514813313L;

    private String key;
    private String value;

    public KeyValueAttribute(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
