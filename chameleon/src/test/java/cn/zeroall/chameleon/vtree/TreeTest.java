package cn.zeroall.chameleon.vtree;

import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Feb 8, 2009
 */
public class TreeTest extends TestCase {

    private static final String TREE_JSON_STR = "{\"id\":1,\"name\":\"a\",\"children\":[{\"id\":2,\"name\":\"b1\",\"children\":[{\"id\":3,\"name\":\"c1\",\"children\":[{\"id\":4,\"name\":\"d1\"}]}]},{\"id\":5,\"name\":\"b2\",\"children\":[{\"id\":6,\"name\":\"c2\"}]},{\"id\":7,\"name\":\"b3\"}]}";

    private Tree tree;

    @Override
    protected void setUp() throws Exception {
        tree = new Tree();
        tree.setNodeStore(new MockNodeStore());
    }

    public void testBuild() {
        try {
            tree.build();
        } catch (TreeException e) {
            Assert.fail("build exception:" + e.getMessage());
        }
        assertEquals(TREE_JSON_STR, tree.getRoot().toJson().toString());
        System.out.println(tree.getRoot().toJson());

        List<Node> list = tree.getNodeById(5).getDescendantEndNodes();
        for (Node node : list) {
            System.out.println(node.getId());
        }
    }

}
