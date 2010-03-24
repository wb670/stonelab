package cn.zeroall.cow.common.validators;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> May 2, 2009
 */
public class RePasswordValidator extends FieldValidatorSupport {

    private String passwordFiled;

    public String getPasswordFiled() {
        return passwordFiled;
    }

    public void setPasswordFiled(String passwordFiled) {
        this.passwordFiled = passwordFiled;
    }

    @Override
    public void validate(Object object) throws ValidationException {
        String fieldName = getFieldName();

        String password = (String) getFieldValue(passwordFiled, object);
        String repassword = (String) getFieldValue(fieldName, object);

        if (!StringUtils.equals(password, repassword)) {
            addFieldError(fieldName, object);
        }
    }

}
