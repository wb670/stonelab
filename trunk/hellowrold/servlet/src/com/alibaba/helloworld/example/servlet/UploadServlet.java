package com.alibaba.helloworld.example.servlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("=========================================");
        System.out.println("Method:GET; port:" + req.getRemotePort());
        Enumeration<?> enumeration = req.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String hd = enumeration.nextElement().toString();
            System.out.println(hd + ":" + req.getHeader(hd));
        }
        resp.getOutputStream().write("Welcome to UploadServlet".getBytes());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPut(req, resp);
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
