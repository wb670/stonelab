package cn.zeroall.cow.common.validators;

import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;

public class SwitchFieldValidator extends FieldValidatorSupport {

    private String msn;

    @Override
    public void validate(Object object) throws ValidationException {
        String fieldName = getFieldName();

        String msnValue = (String) getFieldValue(msn, object);
        String fieldValue = (String) getFieldValue(fieldName, object);

        if ("".equals(msnValue) && "".equals(fieldValue)) {
            addFieldError(fieldName, object);
        }
    }

    public String getMsn() {
        return msn;
    }

    public void setMsn(String msn) {
        this.msn = msn;
    }

}
