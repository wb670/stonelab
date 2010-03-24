package cn.zeroall.cow.dal.member;

import cn.zeroall.cow.BaseTestCase;
import cn.zeroall.cow.dal.member.dao.MemberDAO;
import cn.zeroall.cow.dal.member.po.MemberPO;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Apr 30, 2009
 */
public class MemberIbatisDAOTest extends BaseTestCase {

    private MemberDAO memberDAO;

    @Override
    protected void setUp() throws Exception {
        memberDAO = (MemberDAO) getBean("memberDAO"); 
    }

    public void testCreate() {
        MemberPO memberPO = new MemberPO();
        memberPO.setLoginId(String.valueOf(System.currentTimeMillis()));
        memberPO.setSeed(String.valueOf(System.currentTimeMillis()));
        memberPO.setPassword("password");
        Integer id = memberDAO.create(memberPO);
        System.out.println(id);
    }

}
