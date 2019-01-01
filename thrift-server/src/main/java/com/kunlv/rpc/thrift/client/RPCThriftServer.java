package com.kunlv.rpc.thrift.client;

import com.kunlv.rpc.thrift.client.impl.RPCDemoServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;
import org.apache.thrift.transport.TTransportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RPCThriftServer {
    @Value("${thrift.port}")
    private int port;
    @Value("${thrift.minWorkerThreads}")
    private int minThreads;
    @Value("${thrift.maxWorkerThreads}")
    private int maxThreads;
    private TBinaryProtocol.Factory protocolFactory;
    private TTransportFactory transportFactory;

    @Autowired
    private RPCDemoServiceImpl rpcDemoService;

    public void init() {
        protocolFactory = new TBinaryProtocol.Factory();
        transportFactory = new TTransportFactory();
    }

    public void start() {
        RPCDemoService.Processor processor = new RPCDemoService.Processor(rpcDemoService);
        init();
        try {
            TServerTransport transport = new TServerSocket(port);
            TThreadPoolServer.Args args = new TThreadPoolServer.Args(transport);
            args.processor(processor);
            args.protocolFactory(protocolFactory);
            args.transportFactory(transportFactory);
            args.minWorkerThreads(minThreads);
            args.maxWorkerThreads(maxThreads);
            TServer server = new TThreadPoolServer(args);
            log.info("thrift服务启动成功，端口={}", port);
            server.serve();
        } catch (TTransportException e) {
            log.error("thrift服务启动失败", e);
        }
    }
}
