package cn.zeroall.cow.common.parser;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Feb 28, 2009
 */
public class StringArrayParser implements Parser<List<String>> {

    private String split;

    public StringArrayParser(String split) {
        if (StringUtils.isBlank(split)) {
            throw new IllegalArgumentException("split is empty.");
        }
        this.split = split;
    }

    @Override
    public String obj2Str(List<String> list) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            String value = list.get(i);
            if (value == null) {
                continue;
            }
            sb.append(value);
            if (i != list.size() - 1) {
                sb.append(split);
            }
        }
        return sb.toString();
    }

    @Override
    public List<String> str2Obj(String str) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        String[] values = StringUtils.split(str, split);
        return Arrays.asList(values);
    }

}
