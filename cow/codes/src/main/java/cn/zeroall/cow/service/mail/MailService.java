package cn.zeroall.cow.service.mail;

import cn.zeroall.cow.service.BaseService;
import cn.zeroall.cow.service.mail.exception.MailException;
import cn.zeroall.cow.service.mail.model.MailDefinition;
import cn.zeroall.cow.service.mail.model.MailParam;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Jul 1, 2009
 */
public interface MailService extends BaseService {

    public void send(String key, MailParam param) throws MailException;
    
    public void send(MailDefinition def) throws MailException;

}
