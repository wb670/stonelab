package cn.zeroall.cow.web.member.action;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import cn.zeroall.cow.biz.BizException;
import cn.zeroall.cow.biz.member.MemberCode;
import cn.zeroall.cow.biz.member.ao.InviteAO;
import cn.zeroall.cow.dal.member.po.InvitePO;
import cn.zeroall.cow.service.mail.impl.DefaultMailService;
import cn.zeroall.cow.service.mail.model.MailDefinition;
import cn.zeroall.cow.service.member.model.Member;
import cn.zeroall.cow.service.template.impl.VelocityTemplateService;
import cn.zeroall.cow.web.BaseAction;
import cn.zeroall.cow.web.common.constants.SessionConstant;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> May 2, 2009
 */
public class InviteAction extends BaseAction {

    private static final long serialVersionUID = -3359372962548452541L;

    private static final String QQMSNINVITE = "qqMsnInvite";
    private static final String EMAILINVITE = "emailInvite";
    
    
    /**
     * *******************************************
     * email
     * *****************************************
     */
    private static final String PROTOCOL = "smtp";
    private static final String SMTP_SERVER = "smtp.gmail.com";
    private static final String SMTP_PORT = "465";
    private static final String USER_NAME = "jones2083";
    private static final String PASSWORD = "hello123";
    private static final String MAIL_CONF = "classpath:biz/mail.xml";
    

    private InviteAO inviteAO;

    private short inviteType;

    private InvitePO invitePO;

    private Integer inviteId;

    private List<InvitePO> invitePOList;
    
    private DefaultMailService defaultMailService=new DefaultMailService();

//    private DefaultMailService defaultMailService;
    
    private Integer getMemberId() {
        Member member = (Member) getSession().get(SessionConstant.MEMBER_SESSION);
        return member != null ? Integer.valueOf(member.getId()) : null;
    }

    private String changeEnter(String changeStr) {
        // 可以根据不同的操作系统获得相应的换行符
        String lineSeparator = System.getProperty("line.separator");
        changeStr = changeStr.replaceAll(lineSeparator, "<br>");
        return changeStr;
    }

    //电子邮件验证
    private boolean checkEmail(String email) {
        String lineSeparator = System.getProperty("line.separator");
        String[] emailStr = email.split(lineSeparator);
        String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = null;
        for (String str : emailStr) {
            matcher = regex.matcher(str.trim());
            if (!matcher.matches()) {
                return false;
            }
        }
        return true;
    }

    private String deChangeEnter(String changeStr) {
        // 可以根据不同的操作系统获得相应的换行符
        String lineSeparator = System.getProperty("line.separator");
        changeStr = changeStr.replaceAll("<br>", lineSeparator);
        return changeStr;
    }

    public String invite() {
        return SUCCESS;
    }
    
    public String activeInvite() {
        return SUCCESS;
    }

    public String inviteType() {
        if (inviteType == 0)
            return QQMSNINVITE;
        else
            return EMAILINVITE;
    }

    public String doEmail() {

        try {
            Integer memberId = this.getMemberId();
            if (memberId == null) {
                addActionError(MemberCode.MEMBER_NOT_FOUND.getMessage());
                return INPUT;
            } else {
                String email = invitePO.getEmail();
                if (!checkEmail(email)) {
                    addActionError(MemberCode.EMAIL_FORMAT_ERROR.getMessage()); 
                    return INPUT;
                }
                invitePO.setEmail(changeEnter(email));
                invitePO.setMemberId(memberId);
                invitePO.setInviteType((short) 1);
                inviteAO.add(invitePO);
                try
                {
                    this.sendEmail(invitePO);
                } catch (Exception e) {
                    addActionError(e.getMessage());
                }
                invitePO.setEmail(deChangeEnter(invitePO.getEmail()));
                // 添加邮件成功
                this.addActionMessage(MemberCode.EMAIL_ADD_OK.getMessage());
            }
        } catch (BizException e) {
            addActionError(e.getMessage());
            return INPUT;
        }

        return SUCCESS;
    }
    
    private void sendEmail(InvitePO invitePO) throws Exception
    {
        String []toList=invitePO.getEmail().split("<br>");
        defaultMailService.setProtocol(PROTOCOL);
        defaultMailService.setHost(SMTP_SERVER);
        defaultMailService.setPort(SMTP_PORT);
        defaultMailService.setUser(USER_NAME);
        defaultMailService.setPassword(PASSWORD);
        defaultMailService.setAuth("true");
        defaultMailService.setSsl("true");
        defaultMailService.setMailConf(MAIL_CONF);
        defaultMailService.setTemplateService(new VelocityTemplateService());
        defaultMailService.afterPropertiesSet();
        
        StringBuilder str=new StringBuilder();
        
        MailDefinition def=new MailDefinition();
        for(String to : toList)
        {
            def.addTo(to);
        }
        def.setFrom("li.jinl@alibaba-inc.com");
        def.setSubject("欢迎注册爱可会员");
        str.append("<b>您的");
        str.append(invitePO.getRelation());
        str.append(invitePO.getInviteName());
        str.append("对您说：</b>");
        str.append("<br>");
        str.append(invitePO.getGuestBook());
        str.append(",请点击<a href='http://www.aike.hk/aike/member/register");
        str.append("?inviteId=");
        str.append(invitePO.getId());
        str.append("'>这里</a>注册");
        def.setContent(str.toString());
        
        defaultMailService.send(def);
    }
    

    public String reSendEmail() {

        try {
            invitePOList = inviteAO.findByInviteIdEmailInfo(inviteId);
            if (invitePOList != null) {
                invitePO = invitePOList.get(0);
                invitePO.setEmail(deChangeEnter(invitePO.getEmail()));
            }

        } catch (IllegalArgumentException e) {
            addActionError(e.getMessage());
            return INPUT;
        }
        return SUCCESS;
    }

    public String emailList() {
        Integer memberId = this.getMemberId();
        if (memberId == null) {
            addActionError(MemberCode.MEMBER_NOT_FOUND.getMessage());
            return SUCCESS;
        } else {
            try {
                invitePOList = inviteAO.findByMemberIdEmailInfo(memberId);
            } catch (IllegalArgumentException e) {
                addActionError(e.getMessage());
                return INPUT;
            }
        }
        return SUCCESS;
    }

    public String list() {
        return SUCCESS;
    }

    public short getInviteType() {
        return inviteType;
    }

    public void setInviteType(short inviteType) {
        this.inviteType = inviteType;
    }

    public void setInviteAO(InviteAO inviteAO) {
        this.inviteAO = inviteAO;
    }

    public InvitePO getInvitePO() {
        return invitePO;
    }

    public void setInvitePO(InvitePO invitePO) {
        this.invitePO = invitePO;
    }

    public List<InvitePO> getInvitePOList() {
        return invitePOList;
    }

    public void setInvitePOList(List<InvitePO> invitePOList) {
        this.invitePOList = invitePOList;
    }

    public Integer getInviteId() {
        return inviteId;
    }

    public void setInviteId(Integer inviteId) {
        this.inviteId = inviteId;
    }

//    public void setDefaultMailService(DefaultMailService defaultMailService) {
//        this.defaultMailService = defaultMailService;
//    }

}
