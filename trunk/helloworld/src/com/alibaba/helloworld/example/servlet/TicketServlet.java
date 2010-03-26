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

    private static final String      VIEW               = "车票:{0};找零:{1};共计购买数量:{2}"; //输出模板
    private static final String      SESSION_TICKET_KEY = "__TICKET__";              //车票Session Key

    private static final TicketStore store              = new TicketStore();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //参数校验
        try {
            validate(request);
        } catch (IllegalArgumentException e) {
            response.getWriter().write(e.getMessage());
            return;
        }

        //参数mapping
        Ticket param = bind(request);

        //得到业务数据
        Ticket ticket = store.getTicket(param.getCode());
        //得到车票Session
        Integer num = getTicketSession(request.getSession());

        //业务数据校验
        try {
            validate(ticket, param, num);
        } catch (RuntimeException e) {
            response.getWriter().write(e.getMessage());
            return;
        }

        //处理业务逻辑
        request.getSession().setAttribute(SESSION_TICKET_KEY, ++num);
        response.getWriter().write(getView(param, ticket, request.getSession()));
    }

    /*
     * 参数校验
     */
    private void validate(HttpServletRequest request) throws IllegalArgumentException {
        String code = request.getParameter("code");
        String price = request.getParameter("price");
        if (code == null) {
            throw new IllegalArgumentException("车票代号错误");
        }
        if (price == null) {
            throw new IllegalArgumentException("价钱错误");
        }
        //FIXME:可以使用commons-lang下StringUtils判断数字的方法--考虑到需要引用其他lib,暂时使用Inter.valueOf判断
        try {
            Integer.valueOf(price);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("价钱错误");
        }
    }

    /*
     * 参数bind
     */
    private Ticket bind(HttpServletRequest request) {
        String code = request.getParameter("code");
        String price = request.getParameter("price");
        Ticket param = new Ticket(null, code, null, Integer.valueOf(price));
        return param;
    }

    /*
     * 得到购买车票session数量
     */
    private Integer getTicketSession(HttpSession session) {
        Integer num = (Integer) session.getAttribute(SESSION_TICKET_KEY);
        return (num == null) ? 0 : num;
    }

    /*
     * 业务数据校验
     */
    private void validate(Ticket ticket, Ticket param, Integer num) throws RuntimeException {
        if (ticket == null) {
            throw new RuntimeException("车票不存在");
        }
        if (ticket.getPrice() > param.getPrice()) {
            throw new RuntimeException("价格不足");
        }
        if (num != null && num >= 3) {
            throw new RuntimeException("限制购买");
        }
    }

    /*
     * 得到输出模板
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
     * 车票中心
     * 
     * @author li.jinl
     */
    public static class TicketStore {

        private static Map<String, Ticket> store = new HashMap<String, Ticket>();

        static {
            store.put("SX", new Ticket(1, "SX", "绍兴", 25));
        }

        public Ticket getTicket(String code) {
            return store.get(code);
        }

    }

    /**
     * 车票信息
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
