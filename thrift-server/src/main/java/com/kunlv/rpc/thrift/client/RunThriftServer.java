package com.kunlv.rpc.thrift.client;

import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class RunThriftServer {

    private static RPCThriftServer rpcThriftServer;

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(RunThriftServer.class, args);
        try {
            rpcThriftServer = context.getBean(RPCThriftServer.class);
            rpcThriftServer.start();
        } catch (BeansException e) {
            e.printStackTrace();
        }
    }
}
