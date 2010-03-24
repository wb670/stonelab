package cn.zeroall.chameleon.vtree;

import java.util.List;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Feb 8, 2009
 */
public interface NodeStore<T extends Node> {

    /**
     * 根据tree类型得到所有的节点信息
     * 
     * @param type
     * @return
     */
    public List<T> findByType(String type);

    /**
     * 添加新节点
     * 
     * @param node
     */
    public void add(T node);

    /**
     * 更新节点
     * 
     * @param node
     */
    public void update(T node);

    /**
     * 删除节点
     * 
     * @param node
     */
    public void delete(T node);

}
