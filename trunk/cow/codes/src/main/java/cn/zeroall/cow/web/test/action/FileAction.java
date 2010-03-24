package cn.zeroall.cow.web.test.action;

import java.io.File;

import cn.zeroall.cow.web.BaseAction;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Mar 2, 2009
 */
public class FileAction extends BaseAction {

    private static final long serialVersionUID = 4330595595982434183L;

    private File[] image;
    private String[] imageContentType;
    private String[] imageFileName;

    @Override
    public String execute() throws Exception {
        for (int i = 0; i < image.length; i++) {
            System.out.println(image[i] + ":" + imageContentType[i] + ":" + imageFileName[i]);
        }
        return SUCCESS;
    }

    public File[] getImage() {
        return image;
    }

    public void setImage(File[] image) {
        this.image = image;
    }

    public String[] getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String[] imageContentType) {
        this.imageContentType = imageContentType;
    }

    public String[] getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String[] imageFileName) {
        this.imageFileName = imageFileName;
    }

}
