package com.alibaba.helloworld.example.servlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UploadServlet
 */
public class UploadServlet extends HttpServlet {

    private static final long   serialVersionUID = 1L;

    private static final String BASE             = "/Users/stone/Tmp/tmp/";
    private static final String CROSSDOMAIN      = "<?xml version=\"1.0\"?><cross-domain-policy><allow-access-from domain=\"*\" to-ports=\"*\" /></cross-domain-policy>";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Method:GET; port:" + req.getRemotePort());
        resp.getOutputStream().write(CROSSDOMAIN.getBytes());
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int total = req.getContentLength();
        String name = String.valueOf(System.nanoTime());
        System.out.println("Method:POST; name:" + name + "; total:" + total);

        InputStream in = req.getInputStream();
        OutputStream out = new FileOutputStream(BASE + name);
        for (int i = 0; i < total; i++) {
            int r = in.read();
            out.write(r);
        }
        in.close();
        out.close();
    }
}
