/*
 * Copyright 1999-2011 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.javalab.common.http;

import java.io.InputStream;
import java.security.KeyStore;

import javax.net.SocketFactory;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import com.alibaba.stonelab.javalab.common.util.IoUtil;

/**
 * simple ssl socket factory context
 * 
 * @author <a href="mailto:li.jinl@alibaba-inc.com">Stone.J</a> 2011-1-19
 */
public class SimpleSSLSocketFacotryContext implements SocketFactoryContext {

    private static final String PROTOCOL_SSL      = "SSL";
    private static final String ALGORITHM_SUNX509 = "SunX509";
    private static final String STORE_TYPE        = "JKS";

    private String              ksfile;
    private String              kspwd;
    private String              tksfile;
    private String              tkspwd;

    private SSLSocketFactory    sslSocketFactory;

    public SimpleSSLSocketFacotryContext(String ksfile, String kspwd, String tksfile, String tkspwd){
        this.ksfile = ksfile;
        this.kspwd = kspwd;
        this.tksfile = tksfile;
        this.tkspwd = tkspwd;
        // init
        init();
    }

    @Override
    public SocketFactory createSocketFactory() {
        return sslSocketFactory;
    }

    private synchronized void init() {
        try {
            SSLContext ctx = SSLContext.getInstance(PROTOCOL_SSL);

            KeyManagerFactory kmf = KeyManagerFactory.getInstance(ALGORITHM_SUNX509);
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(ALGORITHM_SUNX509);

            KeyStore ks = KeyStore.getInstance(STORE_TYPE);
            KeyStore tks = KeyStore.getInstance(STORE_TYPE);

            InputStream ksin = getClassLoader().getResourceAsStream(ksfile);
            InputStream tksin = getClassLoader().getResourceAsStream(tksfile);
            ks.load(ksin, kspwd.toCharArray());
            tks.load(tksin, tkspwd.toCharArray());

            kmf.init(ks, kspwd.toCharArray());
            tmf.init(tks);

            ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

            ioclose(ksin);
            ioclose(tksin);

            sslSocketFactory = ctx.getSocketFactory();
        } catch (Exception e) {
            throw new SimpleSSLException("init ssl context fail.", e);
        }
    }

    private ClassLoader getClassLoader() {
        ClassLoader cl = getClass().getClassLoader();
        if (cl != null) {
            return cl;
        }
        return Thread.currentThread().getContextClassLoader();
    }

    private void ioclose(InputStream io) {
        IoUtil.close(io);
    }

    public static class SimpleSSLException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        public SimpleSSLException(String msg, Throwable e){
            super(msg, e);
        }

    }

}
