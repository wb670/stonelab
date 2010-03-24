package cn.zeroall.cow.biz.alliance.ao;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;

import org.apache.commons.io.IOUtils;

import cn.zeroall.cow.biz.BaseAO;
import cn.zeroall.cow.dal.alliance.dao.AlliedLinkDAO;
import cn.zeroall.cow.dal.alliance.po.AlliedLinkPO;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Sep 29, 2009
 */
public class ProxyAO implements BaseAO {

    private AlliedLinkDAO alliedLinkDAO;

    private String baseImagePath;

    public ByteArrayOutputStream getProxyImage(Integer id) throws Exception {
        AlliedLinkPO link = alliedLinkDAO.findById(id);
        if (link == null) {
            return null;
        }
        FileInputStream in = new FileInputStream(baseImagePath + "/" + link.getImageName());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        IOUtils.copy(in, out);
        return out;
    }

    public AlliedLinkPO getProxyLink(Integer id) {
        AlliedLinkPO link = alliedLinkDAO.findById(id);
        return link;
    }

    public String getBaseImagePath() {
        return baseImagePath;
    }

    public void setBaseImagePath(String baseImagePath) {
        this.baseImagePath = baseImagePath;
    }

    public void setAlliedLinkDAO(AlliedLinkDAO alliedLinkDAO) {
        this.alliedLinkDAO = alliedLinkDAO;
    }

}
