package com.baskok.thriftsample.client;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TSocket;

import com.baskok.thriftsample.generated.HelloService;
import com.baskok.thriftsample.generated.PhoneNumber;
import com.baskok.thriftsample.generated.User;

public class ServiceClient {

	private HelloService.Client client;

	/**
	 * Creates a service client. Note that the transport can be HTTP, socket,
	 * memory buffer, non blocking, etc. Also the protocol can be binary,
	 * compact, JSON, etc.
	 */
	public ServiceClient() throws Exception {
		TSocket transport = new TSocket("localhost", 1234);
		TBinaryProtocol protocol = new TBinaryProtocol(transport);
		client = new HelloService.Client(protocol);
		transport.open();
	}
	
	public void callService() throws Exception {
		User user = client.getUser("foo@bar.com");
		System.out.println(user);
		for (PhoneNumber number : user.getPhoneNumbers()) {
			System.out.println(number.getLocation());
		}
	}

	public static void main(String[] args) throws Exception {
		new ServiceClient().callService();
	}
}
