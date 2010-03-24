package cn.zeroall.chameleon.vtree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 目录节点对象
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Feb 8, 2009
 */
public class Node implements Serializable, Comparable<Node> {

    private static final long serialVersionUID = 8085266615416399579L;

    private Integer id; // id号
    private Integer parentId;// 父亲id号
    private String name;// 目录名
    private String description;// 目录描述
    private String type;// 目录类型

    private Node parent;// 父亲
    private List<Node> children;// 儿子

    /**
     * 添加子节点,并且建立与当前节点的父子关系
     * 
     * @param child 儿子节点
     */
    public void addChild(Node child) {
        if (child == null) {
            return;
        }
        // 设置当前节点为child的父节点
        child.setParent(this);
        // 当前节点增加child为儿子节点
        if (getChildren() == null) {
            setChildren(new ArrayList<Node>());
        }
        getChildren().add(child);
    }

    /**
     * 删除子节点,并且建立与当前节点的父子关系
     * 
     * @param child 儿子节点
     */
    public void removeChild(Node child) {
        if (child == null) {
            return;
        }
        // 将child节点的父节点清空
        child.setParent(null);
        if (getChildren() == null) {
            return;
        }
        // 当前节点删除child这个儿子节点
        getChildren().remove(child);
    }

    /**
     * 得到全路径
     * 
     * @param sep 分隔符号
     * @return
     */
    public String getPathName(String sep) {
        String pathName = getName();
        if (getParent() != null) {
            pathName = getParent().getPathName(sep) + sep + pathName;
        }
        return pathName;
    }

    /**
     * 判断是否root节点
     * 
     * @return
     */
    public boolean isRootNode() {
        return getParentId() == -1;
    }

    /**
     * 判断是否是叶子节点
     * 
     * @return
     */
    public boolean isEndNode() {
        return getChildren() == null || getChildren().isEmpty();
    }

    /**
     * 对当前节点的儿子节点进行排序
     */
    public void sortChildren() {
        if (isEndNode()) {
            return;
        }
        Collections.sort(getChildren());
    }

    /**
     * 对当前节点的所有儿子节点进行排序
     */
    public void sortAllChidren() {
        if (isEndNode()) {
            return;
        }
        List<Node> children = getChildren();
        Collections.sort(children);
        for (Node child : children) {
            child.sortAllChidren();
        }
    }

    /**
     * 得到当前节点的所有子孙叶子
     * 
     * @return
     */
    public List<Node> getDescendantEndNodes() {
        List<Node> all = new ArrayList<Node>();
        getDescendantEndNodes(all, this);
        return all;
    }

    private void getDescendantEndNodes(List<Node> c, Node node) {
        if (node == null) {
            return;
        }
        List<Node> list = node.getChildren();
        if (list != null) {
            for (Node node2 : list) {
                if (node2.isEndNode()) {
                    c.add(node2);
                } else {
                    getDescendantEndNodes(c, node2);
                }
            }
        }
    }

    /**
     * 将虚拟目录转换成JSONObject对象
     * <p>
     * 本身转换包含id、name两个属性，子节点转换为children属性的数组
     * </p>
     * 
     * @return
     */
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
            jsonObject.put("name", name);
            if (!isEndNode()) {
                JSONArray jsonArray = new JSONArray();
                for (Node child : getChildren()) {
                    jsonArray.put(child.toJson());
                }
                jsonObject.put("children", jsonArray);
            }
        } catch (JSONException e) {
            // ignore
        }
        return jsonObject;
    }

    @Override
    public int compareTo(Node o) {
        return this.getId().compareTo(o.getId());
    }

    // ------------getters & setter------------------
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

}
