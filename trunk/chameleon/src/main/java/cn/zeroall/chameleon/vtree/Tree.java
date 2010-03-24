package cn.zeroall.chameleon.vtree;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Feb 8, 2009
 */
public class Tree<T extends Node> {

    private String type;
    private T root; // root节点
    private Map<Integer, T> nodeHolder = new HashMap<Integer, T>();// 节点持有器

    private NodeStore<T> nodeStore;

    /**
     * 将平面的node list构建成树
     * 
     * @throws TreeException
     */
    public void build() throws TreeException {
        List<T> nodes = nodeStore.findByType(type);
        // 如果nodes为空,则不做任何处理
        if (nodes == null || nodes.isEmpty()) {
            return;
        }
        // 设置root和node持有器
        for (T node : nodes) {
            if (node.isRootNode()) {
                this.root = node;
            }
            nodeHolder.put(node.getId(), node);
        }

        // 如果root为空,则build失败了
        if (root == null) {
            throw new TreeException("no root node found.");
        }

        // 建立节点之前关系
        for (T node : nodes) {
            if (node.isRootNode()) {
                continue;
            }
            Node parent = getNodeById(node.getParentId());
            if (parent == null) {
                throw new TreeException("no parent node found.current node id is:" + node.getId());
            }
            parent.addChild(node);
        }

        // 排序
        root.sortAllChidren();
    }

    /**
     * 得到root节点
     * 
     * @return
     */
    public T getRoot() {
        return root;
    }

    /**
     * 根据id得到对应节点
     * 
     * @param id
     * @return
     */
    public T getNodeById(Integer id) {
        return nodeHolder.get(id);
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setNodeStore(NodeStore nodeStore) {
        this.nodeStore = nodeStore;
    }

}
