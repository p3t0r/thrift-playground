package com.baskok.thriftsample.server;

import org.apache.thrift.TException;

import com.baskok.thriftsample.generated.HelloService;
import com.baskok.thriftsample.generated.User;

public class HelloServiceImpl implements HelloService.Iface {

	@Override
	public String say_hello(User user) throws TException {
		return "hello, world";
	}

}
