package cn.zeroall.cow.biz.misc.ao;

import java.util.Random;

import cn.zeroall.cow.biz.BaseAO;
import cn.zeroall.cow.biz.misc.vo.CodeImageVO;
import cn.zeroall.cow.service.image.CodeImageService;
import cn.zeroall.cow.service.image.impl.SimpleCodeImageService;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> May 2, 2009
 */
public class CodeImageAO implements BaseAO {

    private static final String[] CONTENT_RANGE = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
    private static final int LENGHT = 6;

    private CodeImageService codeImageService;

    public CodeImageVO getCodeImage() {
        String content = getRandom(LENGHT);
        initCodeImageSerivce();
        return new CodeImageVO(content, codeImageService.genCodeImage(content));
    }

    private String getRandom(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(CONTENT_RANGE[random.nextInt(CONTENT_RANGE.length)]);
        }
        return sb.toString();
    }

    private void initCodeImageSerivce() {
        SimpleCodeImageService simpleCodeImageService = (SimpleCodeImageService) codeImageService;
        simpleCodeImageService.setBackgroundR(245);
        simpleCodeImageService.setBackgroundG(242);
        simpleCodeImageService.setBackgroundB(242);
        simpleCodeImageService.setForegroundR(0);
        simpleCodeImageService.setForegroundG(0);
        simpleCodeImageService.setForegroundB(0);
    }

    public void setCodeImageService(CodeImageService codeImageService) {
        this.codeImageService = codeImageService;
    }

}
