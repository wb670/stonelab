package cn.zeroall.cow.common.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Apr 27, 2009
 */
public class List2ObjUtil {

    private static final Log log = LogFactory.getLog(List2ObjUtil.class);

    public static <T extends Object> Map<?, T> list2Map(List<T> list, String method4key) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        Class clazz = list.get(0).getClass();
        Map map = new HashMap();
        for (T obj : list) {
            try {
                Method method = clazz.getDeclaredMethod(method4key, new Class[] {});
                map.put(method.invoke(obj, new Object[] {}), obj);
            } catch (Exception e) {
                log.error("list to map error.", e);
            }
        }
        return map;
    }

    public static Integer[] list2IntegerArray(List<?> list, String method4Array) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        Class clazz = list.get(0).getClass();
        Integer[] ret = new Integer[list.size()];
        for (int i = 0; i < list.size(); i++) {
            try {
                ret[i] = (Integer) clazz.getDeclaredMethod(method4Array, new Class[] {}).invoke(list.get(i),
                        new Object[] {});
            } catch (Exception e) {
                log.error("list to array error.", e);
            }
        }
        return ret;
    }
}
