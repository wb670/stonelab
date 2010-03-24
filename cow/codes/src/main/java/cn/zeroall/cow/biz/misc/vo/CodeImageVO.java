package cn.zeroall.cow.biz.misc.vo;

import java.awt.image.BufferedImage;

import cn.zeroall.cow.biz.BaseVO;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> May 2, 2009
 */
public class CodeImageVO implements BaseVO {

    private static final long serialVersionUID = 6864613669223121479L;

    private String content;
    private BufferedImage bufferedImage;

    public CodeImageVO(String content, BufferedImage bufferedImage) {
        this.content = content;
        this.bufferedImage = bufferedImage;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public void setBufferedImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

}
