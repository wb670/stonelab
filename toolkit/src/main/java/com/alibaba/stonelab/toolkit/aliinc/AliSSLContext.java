package com.alibaba.stonelab.toolkit.aliinc;

public class AliSSLContext {

    private static final String                 KS_PWD   = "123456";
    private static final String                 TKS_PWD  = "123456";
    private static final String                 KS_FILE  = "aliinc/jl.store";
    private static final String                 TKS_FILE = "aliinc/alibaba.store";

    private static EasySSLProtocolSocketFactory easySSLProtocolSocketFactory;

    public static EasySSLProtocolSocketFactory getEasySSLProtocolSocketFactory() {
        if (easySSLProtocolSocketFactory == null) {
            easySSLProtocolSocketFactory = new EasySSLProtocolSocketFactory(KS_FILE, KS_PWD, TKS_FILE, TKS_PWD);
        }
        return easySSLProtocolSocketFactory;
    }

}
