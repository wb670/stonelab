package cn.zeroall.cow.service.mail.exception;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Jul 2, 2009
 */
public class MailException extends RuntimeException {

    private static final long serialVersionUID = -9047686829885320896L;

    public MailException(String message) {
        super(message);
    }

    public MailException(String message, Throwable e) {
        super(message, e);
    }

}
