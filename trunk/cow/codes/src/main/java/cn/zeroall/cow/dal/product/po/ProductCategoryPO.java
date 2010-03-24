package cn.zeroall.cow.dal.product.po;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;

import cn.zeroall.chameleon.vtree.Node;
import cn.zeroall.cow.dal.BasePO;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Feb 15, 2009
 */
public class ProductCategoryPO extends Node implements BasePO, Serializable {

    private static final long serialVersionUID = -8839929464158924627L;

    private Integer[] descendantIds;

    public Integer[] getDescendantIds() {
        if (descendantIds == null) {
            descendantInit();
        }
        return descendantIds;
    }

    public boolean hasDescendants() {
        if (descendantIds == null) {
            descendantInit();
        }
        return !ArrayUtils.isEmpty(descendantIds);
    }

    private void descendantInit() {
        List<Node> list = getDescendantEndNodes();
        descendantIds = new Integer[list.size()];
        for (int i = 0; i < list.size(); i++) {
            Node node = list.get(i);
            descendantIds[i] = node.getId();
        }
    }

}
