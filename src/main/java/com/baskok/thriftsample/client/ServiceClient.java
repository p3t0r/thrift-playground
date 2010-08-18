package com.baskok.thriftsample.client;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TSocket;

import com.baskok.thriftsample.generated.HelloService.Client;
import com.baskok.thriftsample.generated.User;

public class ServiceClient {

	private Client client;

	public ServiceClient() throws Exception {
		// transport can be http, socket, memory buffer, non blocking
		TSocket transport = new TSocket("localhost", 1234);
		// protocol can be binary, compact, JSON
		TBinaryProtocol protocol = new TBinaryProtocol(transport);
		client = new Client(protocol);
		transport.open();
	}
	
	public void callService() throws Exception {
		client.say_hello(new User("bas"));
	}

	public static void main(String[] args) throws Exception {
		new ServiceClient().callService();
	}
}
