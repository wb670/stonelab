package com.alibaba.helloworld;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Servlet Filter implementation class Filter
 */
public class Filter implements javax.servlet.Filter {

    private static final String DEFAULT_ENCODIG = "GBK";

    private String              encoding;

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        request.setCharacterEncoding(encoding);
        response.setCharacterEncoding(encoding);

        response.getWriter().write("start\n");
        chain.doFilter(request, response);
        response.getWriter().write("\nend");
    }

    public void init(FilterConfig config) throws ServletException {
        encoding = config.getInitParameter("encoding");
        if (encoding == null) {
            encoding = DEFAULT_ENCODIG;
        }
    }

    public void destroy() {
    }

}
