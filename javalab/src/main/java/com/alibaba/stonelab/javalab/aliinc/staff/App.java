package com.alibaba.stonelab.javalab.aliinc.staff;

import java.util.List;

import com.alibaba.stonelab.javalab.common.http.HttpClient;
import com.alibaba.stonelab.javalab.common.http.SimpleSSLSocketFacotryContext;
import com.alibaba.stonelab.javalab.common.http.SocketFactoryContext;

/**
 * staff application
 */
public class App {

    private static String               ksfile       = "aliinc/jl.store";
    private static String               kspwd        = "123456";
    private static String               tksfile      = "aliinc/alibaba.store";
    private static String               tkspwd       = "123456";
    private static SocketFactoryContext ctx          = new SimpleSSLSocketFacotryContext(ksfile, kspwd, tksfile, tkspwd);
    private static StaffService         staffService = new StaffService();

    static {
        staffService.setHttpClient(new HttpClient(ctx));
    }

    public static void main(String[] args) throws Exception {
        args = new String[] { "金立" };
        if (args.length != 1) {
            System.out.println("args error.");
            System.exit(-1);
        }
        // search
        List<Staff> list = staffService.search(args[0]);
        for (Staff staff : list) {
            System.out.println(staff);
        }
    }

}
