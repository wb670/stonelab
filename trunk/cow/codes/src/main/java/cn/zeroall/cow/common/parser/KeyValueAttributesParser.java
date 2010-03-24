package cn.zeroall.cow.common.parser;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import cn.zeroall.cow.service.common.model.KeyValueAttribute;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Feb 28, 2009
 */
public class KeyValueAttributesParser implements Parser<List<KeyValueAttribute>> {

    private String conjunction;
    private String split;

    public KeyValueAttributesParser(String conjunction, String split) {
        if (StringUtils.isBlank(split) || StringUtils.isBlank(conjunction)) {
            throw new IllegalArgumentException("conjunction or split is empty.");
        }
        this.conjunction = conjunction;
        this.split = split;
    }

    @Override
    public String obj2Str(List<KeyValueAttribute> attributes) {
        if (CollectionUtils.isEmpty(attributes)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < attributes.size(); i++) {
            KeyValueAttribute attr = attributes.get(i);
            sb.append(attr.getKey()).append(conjunction).append(attr.getValue());
            if (i != attributes.size() - 1) {
                sb.append(split);
            }
        }
        return sb.toString();
    }

    @Override
    public List<KeyValueAttribute> str2Obj(String attributesStr) {
        if (StringUtils.isBlank(attributesStr)) {
            return null;
        }
        String[] attributes = StringUtils.split(attributesStr, split);
        List<KeyValueAttribute> list = new ArrayList<KeyValueAttribute>(attributes.length);

        for (String keyValueStr : attributes) {
            String[] keyValue = StringUtils.split(keyValueStr, conjunction);
            if (keyValue == null || keyValue.length != 2) {
                continue;
            }
            list.add(new KeyValueAttribute(keyValue[0], keyValue[1]));
        }

        return list;
    }

}
