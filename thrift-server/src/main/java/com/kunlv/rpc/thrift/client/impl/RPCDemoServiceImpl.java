package com.kunlv.rpc.thrift.client.impl;

import com.kunlv.rpc.thrift.client.RPCDemoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RPCDemoServiceImpl implements RPCDemoService.Iface {
    @Override
    public String getDemoData(String userName) throws org.apache.thrift.TException {
        try {
            return "hello " + userName;
        } catch (Exception e) {
            log.error("异常：", e);
            return "error";
        }
    }
}
