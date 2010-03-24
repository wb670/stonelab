package cn.zeroall.cow.service.mail.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Jul 2, 2009
 */
public class MailParam {

    private List<String> toList = new ArrayList<String>();
    private Map<String, Object> model;

    public MailParam(Map<String, Object> model) {
        this.model = model;
    }

    public MailParam(String to, Map<String, Object> model) {
        addTo(to);
        this.model = model;
    }

    public MailParam(List<String> toList, Map<String, Object> model) {
        this.toList.addAll(toList);
        this.model = model;
    }

    public void addTo(String to) {
        toList.add(to);
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public void setModel(Map<String, Object> model) {
        this.model = model;
    }

    public List<String> getToList() {
        return toList;
    }

}
