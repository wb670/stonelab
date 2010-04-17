package com.alibaba.javalab.staff;

public class AliSSLContext {

    private static final String                 KS_PWD   = "123456";
    private static final String                 TKS_PWD  = "123456";
    private static final String                 KS_FILE  = "staff/jl.store";
    private static final String                 TKS_FILE = "staff/alibaba.store";

    private static EasySSLProtocolSocketFactory easySSLProtocolSocketFactory;

    public static EasySSLProtocolSocketFactory getEasySSLProtocolSocketFactory() {
        if (easySSLProtocolSocketFactory == null) {
            easySSLProtocolSocketFactory = new EasySSLProtocolSocketFactory(KS_FILE, KS_PWD, TKS_FILE, TKS_PWD);
        }
        return easySSLProtocolSocketFactory;
    }

}
