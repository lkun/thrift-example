package com.kunlv.rpc.thrift.client.controller;

import com.kunlv.rpc.thrift.client.RPCThriftClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/thrift")
@Slf4j
public class RPCThriftDemoController {
    @Autowired
    private RPCThriftClient rpcThriftClient;

    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public String getData(@RequestParam(value = "userName") String userName) {
        try {
            rpcThriftClient.open();
            return rpcThriftClient.getRPCThriftService().getDemoData(userName);
        } catch (TTransportException e) {
            log.error("RPC调用失败", e);
            return "error";
        } catch (TException e) {
            log.error("转换异常:", e);
            return "error";
        } finally {
            rpcThriftClient.close();
        }
    }
}
