package cn.zeroall.chameleon.vtree;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * 目录结构如下:
 * a(1) &gt;&gt; b1(2) &gt;&gt; c1(3) &gt;&gt; d1(4) 
 *      &gt;&gt; b2(5) &gt;&gt; c2(6) 
 *      &gt;&gt; b3(7)
 * </pre>
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Feb 8, 2009
 */
public class MockNodeStore implements NodeStore {

    @Override
    public List<Node> findByType(String type) {
        Node a = mockNode(1, -1, "a");
        Node b1 = mockNode(2, 1, "b1");
        Node c1 = mockNode(3, 2, "c1");
        Node d1 = mockNode(4, 3, "d1");

        Node b2 = mockNode(5, 1, "b2");
        Node c2 = mockNode(6, 5, "c2");

        Node b3 = mockNode(7, 1, "b3");

        List<Node> list = new ArrayList<Node>();
        list.add(a);
        list.add(b1);
        list.add(c1);
        list.add(d1);
        list.add(b2);
        list.add(c2);
        list.add(b3);
        return list;
    }

    @Override
    public void add(Node node) {
        return;
    }

    @Override
    public void delete(Node node) {
        return;
    }

    @Override
    public void update(Node node) {
        return;
    }

    private Node mockNode(Integer id, Integer parentId, String name) {
        Node node = new Node();
        node.setId(id);
        node.setParentId(parentId);
        node.setName(name);
        return node;
    }

}
