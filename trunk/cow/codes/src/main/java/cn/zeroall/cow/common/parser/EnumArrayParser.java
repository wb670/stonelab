package cn.zeroall.cow.common.parser;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import cn.zeroall.cow.common.enums.EnumType;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Feb 28, 2009
 */
public class EnumArrayParser implements Parser<List<? extends EnumType>> {

    private String split;
    private Class<? extends Enum> enumType;

    public EnumArrayParser(Class<? extends Enum> enumType, String split) {
        if (enumType == null) {
            throw new IllegalArgumentException("enumType is null");
        }
        if (StringUtils.isBlank(split)) {
            throw new IllegalArgumentException("split is empty.");
        }
        this.enumType = enumType;
        this.split = split;
    }

    @Override
    public String obj2Str(List<? extends EnumType> list) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            EnumType value = list.get(i);
            if (value == null) {
                continue;
            }
            sb.append(value.toString());
            if (i != list.size() - 1) {
                sb.append(split);
            }
        }
        return sb.toString();
    }

    @Override
    public List str2Obj(String str) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        String[] values = StringUtils.split(str, split);
        List<String> list = new ArrayList<String>(values.length);
        for (String value : values) {
            list.add(value);
        }
        return list;
    }
    
//    @Override
//    public List<? extends EnumType> str2Obj(String str) {
//        if (StringUtils.isEmpty(str)) {
//            return null;
//        }
//        String[] values = StringUtils.split(str, split);
//        List<EnumType> list = new ArrayList<EnumType>(values.length);
//        for (String value : values) {
//            list.add((EnumType) Enum.valueOf(enumType, value));
//        }
//        return list;
//    }
}
