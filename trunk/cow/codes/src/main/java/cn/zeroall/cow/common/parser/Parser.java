package cn.zeroall.cow.common.parser;

import java.util.List;

/**
 * 字段解析器
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Feb 28, 2009
 */
public interface Parser<T> {

    public String obj2Str(T obj);

    public T str2Obj(String str);
    
//    public List<String> str2Obj(String str);
    
}
