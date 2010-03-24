package cn.zeroall.cow.web.alliance.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Map;

import cn.zeroall.cow.biz.alliance.ao.ProxyAO;
import cn.zeroall.cow.dal.alliance.po.AlliedLinkPO;
import cn.zeroall.cow.web.BaseAction;
import cn.zeroall.cow.web.common.constants.SessionConstant;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Sep 29, 2009
 */
public class ProxyAction extends BaseAction {

    private static final long serialVersionUID = -2189984412943421048L;

    private ProxyAO proxyAO;

    private String linkId;
    private InputStream imageStream;

    public String imageProxy() throws Exception {
        ByteArrayOutputStream out = proxyAO.getProxyImage(Integer.valueOf(linkId));
        imageStream = new ByteArrayInputStream(out.toByteArray());
        return SUCCESS;
    }

    private String url;
    private String source;

    public String proxy() throws Exception {
        AlliedLinkPO link = proxyAO.getProxyLink(Integer.valueOf(linkId));
        if (link == null) {
            return "index";
        }
        Map<String, Object> session = getSession();
        session.put(SessionConstant.ALLIED_MEMBER_SOURCE, source);
        url = link.getLinkUrl();
        return SUCCESS;
    }

    public String getLinkId() {
        return linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    public InputStream getImageStream() {
        return imageStream;
    }

    public void setImageStream(InputStream imageStream) {
        this.imageStream = imageStream;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setProxyAO(ProxyAO proxyAO) {
        this.proxyAO = proxyAO;
    }

}
