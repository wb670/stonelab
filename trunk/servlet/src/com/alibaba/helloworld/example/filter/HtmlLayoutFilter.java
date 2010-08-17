package com.alibaba.helloworld.example.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Servlet Filter implementation class HtmlLayoutFilter Sitemesh
 */
public class HtmlLayoutFilter implements Filter {

    private static final String LAYOUT_PREFIX = "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html;charset=UTF-8\"><title>HelloWorld Example</title><body><hr />";
    private static final String LAYOUT_SUFFIX = "<hr /></body></html>";

    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException {
    }

    /**
     * @see Filter#destroy()
     */
    public void destroy() {
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        response.getWriter().write(LAYOUT_PREFIX);
        chain.doFilter(request, response);
        response.getWriter().write(LAYOUT_SUFFIX);
    }

}
