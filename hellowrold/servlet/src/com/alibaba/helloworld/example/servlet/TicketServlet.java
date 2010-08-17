package com.alibaba.helloworld.example.servlet;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class TicketServlet
 */
public class TicketServlet extends HttpServlet {

    private static final long        serialVersionUID   = 1L;

    private static final String      VIEW               = "��Ʊ:{0};����:{1};���ƹ�������:{2}"; //���ģ��
    private static final String      SESSION_TICKET_KEY = "__TICKET__";              //��ƱSession Key

    private static final TicketStore store              = new TicketStore();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //����У��
        try {
            validate(request);
        } catch (IllegalArgumentException e) {
            response.getWriter().write(e.getMessage());
            return;
        }

        //����mapping
        Ticket param = bind(request);

        //�õ�ҵ������
        Ticket ticket = store.getTicket(param.getCode());
        //�õ���ƱSession
        Integer num = getTicketSession(request.getSession());

        //ҵ������У��
        try {
            validate(ticket, param, num);
        } catch (RuntimeException e) {
            response.getWriter().write(e.getMessage());
            return;
        }

        //����ҵ���߼�
        request.getSession().setAttribute(SESSION_TICKET_KEY, ++num);
        response.getWriter().write(getView(param, ticket, request.getSession()));
    }

    /*
     * ����У��
     */
    private void validate(HttpServletRequest request) throws IllegalArgumentException {
        String code = request.getParameter("code");
        String price = request.getParameter("price");
        if (code == null) {
            throw new IllegalArgumentException("��Ʊ���Ŵ���");
        }
        if (price == null) {
            throw new IllegalArgumentException("��Ǯ����");
        }
        //FIXME:����ʹ��commons-lang��StringUtils�ж����ֵķ���--���ǵ���Ҫ��������lib,��ʱʹ��Inter.valueOf�ж�
        try {
            Integer.valueOf(price);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("��Ǯ����");
        }
    }

    /*
     * ����bind
     */
    private Ticket bind(HttpServletRequest request) {
        String code = request.getParameter("code");
        String price = request.getParameter("price");
        Ticket param = new Ticket(null, code, null, Integer.valueOf(price));
        return param;
    }

    /*
     * �õ�����Ʊsession����
     */
    private Integer getTicketSession(HttpSession session) {
        Integer num = (Integer) session.getAttribute(SESSION_TICKET_KEY);
        return (num == null) ? 0 : num;
    }

    /*
     * ҵ������У��
     */
    private void validate(Ticket ticket, Ticket param, Integer num) throws RuntimeException {
        if (ticket == null) {
            throw new RuntimeException("��Ʊ������");
        }
        if (ticket.getPrice() > param.getPrice()) {
            throw new RuntimeException("�۸���");
        }
        if (num != null && num >= 3) {
            throw new RuntimeException("���ƹ���");
        }
    }

    /*
     * �õ����ģ��
     */
    private String getView(Ticket param, Ticket ticket, HttpSession session) {
        String name = ticket.getName();
        Integer remain = param.getPrice() - ticket.getPrice();
        Integer num = getTicketSession(session);
        return MessageFormat.format(VIEW, name, remain, num);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        doGet(request, response);
    }

    /**
     * ��Ʊ����
     * 
     * @author li.jinl
     */
    public static class TicketStore {

        private static Map<String, Ticket> store = new HashMap<String, Ticket>();

        static {
            store.put("SX", new Ticket(1, "SX", "����", 25));
        }

        public Ticket getTicket(String code) {
            return store.get(code);
        }

    }

    /**
     * ��Ʊ��Ϣ
     * 
     * @author li.jinl
     */
    public static class Ticket {

        private Integer id;
        private String  code;
        private String  name;
        private Integer price;

        public Ticket(Integer id, String code, String name, Integer price) {
            this.id = id;
            this.code = code;
            this.name = name;
            this.price = price;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }
    }
}
