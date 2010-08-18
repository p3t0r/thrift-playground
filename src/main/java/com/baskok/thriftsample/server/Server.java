package com.baskok.thriftsample.server;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TBinaryProtocol.Factory;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;

import com.baskok.thriftsample.generated.HelloService;

public class Server {

	public static void main(String[] args) throws Exception {
		TServerSocket serverTransport = new TServerSocket(1234);
		HelloService.Processor processor = new HelloService.Processor(new HelloServiceImpl());
		Factory protocolFactory = new TBinaryProtocol.Factory(true, true);
		TThreadPoolServer server = new TThreadPoolServer(processor, serverTransport, protocolFactory);
		server.serve();
	}
}
