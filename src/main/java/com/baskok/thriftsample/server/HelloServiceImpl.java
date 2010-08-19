package com.baskok.thriftsample.server;

import java.util.HashMap;
import java.util.Map;

import org.apache.thrift.TException;

import com.baskok.thriftsample.generated.HelloService;
import com.baskok.thriftsample.generated.Location;
import com.baskok.thriftsample.generated.PhoneNumber;
import com.baskok.thriftsample.generated.UnauthorizedException;
import com.baskok.thriftsample.generated.User;

public class HelloServiceImpl implements HelloService.Iface {

	@Override
	public boolean ping() throws TException {
		return true;
	}

	@Override
	public Map<String, String> getStatus() throws TException {
		return new HashMap<String, String>(){{
			put("uptime", "since 1970");
			put("mavenVersion", "1.0");
			put("gitSha1", "9c1f1656ea100f39192f9c3babf50adcc62a4b53");
		}};
	}

	@Override
	public String hello(String userName) throws TException {
		return String.format("hello, %s", userName);
	}

	@Override
	public User getUser(String email) throws TException, UnauthorizedException {
		User user = new User("bk", "bk@kb.co", true);
		user.addToPhoneNumbers(new PhoneNumber("+312099988877"));
		PhoneNumber phoneNumber = new PhoneNumber("+31012345678");
		phoneNumber.setLocation(Location.MOBILE);
		user.addToPhoneNumbers(phoneNumber);
		//throw new UnauthorizedException("This is the server, telling you you're unauthorized to go here");
		return user;
	}

}
