package com.alibaba.javalab.staff;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.security.KeyStore;

import javax.net.SocketFactory;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.HttpClientError;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;

public class EasySSLProtocolSocketFactory implements ProtocolSocketFactory {

    private SSLContext sslcontext = null;

    private String     ksfile;
    private String     tksfile;
    private String     kspwd;
    private String     tkspwd;

    public EasySSLProtocolSocketFactory(String ks, String kspwd, String tks, String tkspwd){
        this.ksfile = ks;
        this.kspwd = kspwd;
        this.tksfile = tks;
        this.tkspwd = tkspwd;
    }

    public Socket createSocket(String host, int port, InetAddress clientHost, int clientPort) throws IOException,
                                                                                             UnknownHostException {
        return getSSLContext().getSocketFactory().createSocket(host, port, clientHost, clientPort);
    }

    public Socket createSocket(final String host, final int port, final InetAddress localAddress, final int localPort,
                               final HttpConnectionParams params) throws IOException, UnknownHostException,
                                                                 ConnectTimeoutException {
        if (params == null) {
            throw new IllegalArgumentException("Parameters may not be null");
        }
        int timeout = params.getConnectionTimeout();
        SocketFactory socketfactory = getSSLContext().getSocketFactory();
        if (timeout == 0) {
            return socketfactory.createSocket(host, port, localAddress, localPort);
        } else {
            Socket socket = socketfactory.createSocket();
            SocketAddress localaddr = new InetSocketAddress(localAddress, localPort);
            SocketAddress remoteaddr = new InetSocketAddress(host, port);
            socket.bind(localaddr);
            socket.connect(remoteaddr, timeout);
            return socket;
        }
    }

    public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
        return getSSLContext().getSocketFactory().createSocket(host, port);
    }

    public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException,
                                                                                       UnknownHostException {
        return getSSLContext().getSocketFactory().createSocket(socket, host, port, autoClose);
    }

    private SSLContext createEasySSLContext() {
        try {
            SSLContext context = SSLContext.getInstance("SSL");

            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");

            KeyStore ks = KeyStore.getInstance("JKS");
            KeyStore tks = KeyStore.getInstance("JKS");

            ks.load(new FileInputStream(ksfile), kspwd.toCharArray());
            tks.load(new FileInputStream(tksfile), tkspwd.toCharArray());

            kmf.init(ks, kspwd.toCharArray());
            tmf.init(tks);

            context.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
            return context;
        } catch (Exception e) {
            throw new HttpClientError(e.toString());
        }
    }

    private SSLContext getSSLContext() {
        if (this.sslcontext == null) {
            this.sslcontext = createEasySSLContext();
        }
        return this.sslcontext;
    }

}
