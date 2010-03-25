package com.alibaba.helloworld.example.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Servlet Filter implementation class EncodingFilter
 */
public class EncodingFilter implements Filter {

    private static final String DEFAULT_ENCODING = "GBK";           // ƒ¨»œ±‡¬Î÷µ
    private static final String TAG_ENCODING     = "encoding";      //web.xml ≈‰÷√tag

    private String              encoding         = DEFAULT_ENCODING; //±‡¬Î

    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException {
        if (fConfig.getInitParameter(TAG_ENCODING) != null) {
            encoding = fConfig.getInitParameter(TAG_ENCODING);
        }
    }

    /**
     * @see Filter#destroy()
     */
    public void destroy() {
        // do nothing...
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        request.setCharacterEncoding(encoding);
        response.setCharacterEncoding(encoding);
        chain.doFilter(request, response);
    }

}
