package cn.zeroall.cow.common.validators;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import cn.zeroall.cow.web.common.constants.SessionConstant;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> May 2, 2009
 */
public class CheckCodeValidator extends FieldValidatorSupport {

    @Override
    public void validate(Object object) throws ValidationException {
        String fieldName = getFieldName();
        String value = (String) getFieldValue(fieldName, object);

        Map<String, Object> session = ActionContext.getContext().getSession();
        String sessionValue = (String) session.get(SessionConstant.CODE_IMAGE_SESSION);

        if (!StringUtils.equals(value, sessionValue)) {
            addFieldError(fieldName, object);
        }

    }

}
