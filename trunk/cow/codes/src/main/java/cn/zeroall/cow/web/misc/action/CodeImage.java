package cn.zeroall.cow.web.misc.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Map;

import javax.imageio.ImageIO;

import cn.zeroall.cow.biz.misc.ao.CodeImageAO;
import cn.zeroall.cow.biz.misc.vo.CodeImageVO;
import cn.zeroall.cow.web.BaseAction;
import cn.zeroall.cow.web.common.constants.SessionConstant;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> May 2, 2009
 */
public class CodeImage extends BaseAction {

    private static final long serialVersionUID = -997121793053861903L;

    private InputStream imageStream;

    private CodeImageAO codeImageAO;

    @Override
    public String execute() throws Exception {
        CodeImageVO codeImageVO = codeImageAO.getCodeImage();
        Map<String, Object> session = getSession();
        session.put(SessionConstant.CODE_IMAGE_SESSION, codeImageVO.getContent());

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ImageIO.write(codeImageVO.getBufferedImage(), "jpg", output);
        imageStream = new ByteArrayInputStream(output.toByteArray());

        return SUCCESS;
    }

    public InputStream getImageStream() {
        return imageStream;
    }

    public void setImageStream(InputStream imageStream) {
        this.imageStream = imageStream;
    }

    public void setCodeImageAO(CodeImageAO codeImageAO) {
        this.codeImageAO = codeImageAO;
    }

}
