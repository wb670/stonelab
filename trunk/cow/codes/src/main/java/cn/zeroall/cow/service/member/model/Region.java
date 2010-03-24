package cn.zeroall.cow.service.member.model;

import cn.zeroall.cow.service.BaseModel;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Apr 30, 2009
 */
public class Region implements BaseModel {

    private static final long serialVersionUID = 3613310199469947572L;

    private Integer code;
    private String name;

    public Region(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Region(Integer code) {
        this(code, null);
    }

    public Region(String name) {
        this(null, name);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
