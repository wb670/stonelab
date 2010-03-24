package com.alibaba.weblab.statistics.web.page.statistics;

import org.apache.click.Page;
import org.apache.click.control.Form;
import org.apache.click.control.Submit;
import org.apache.click.control.TextField;
import org.apache.click.util.Bindable;

import com.alibaba.weblab.statistics.service.PartyService;
import com.alibaba.weblab.statistics.service.domain.Party;

/**
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Dec 27, 2009
 */
public class PartyPage extends Page {

    private static final long serialVersionUID = 7346041595876765653L;

    private PartyService      partyService;

    @Bindable
    public String             title            = "活动页面";
    @Bindable
    public Form               form             = new Form();

    @Override
    public void onInit() {
        form.add(new TextField("jobNumber", "工号：", true));
        form.add(new TextField("name", "姓名：", true));
        form.add(new Submit("submit", "确认"));
        form.setJavaScriptValidation(true);
        form.setListener(this, "add");
    }

    public boolean add() {
        form.validate();
        if (!form.isValid()) {
            return false;
        }

        Party party = new Party();
        form.copyTo(party);
        partyService.addParty(party);

        addModel("message", "恭喜你，报名成功！");

        return true;
    }

    public void setPartyService(PartyService partyService) {
        this.partyService = partyService;
    }

    @Override
    public String getTemplate() {
        return "/statistics/default.htm";
    }

}
