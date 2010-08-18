package com.baskok.thriftsample.server.http;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class RunHttpServer {

	public static void main(String[] args) throws Exception {
		Server server = new Server(8080);
		Handler handler = new WebAppContext("./src/main/webapp", "/");
		server.setHandler(handler);
		server.start();
	}
	
}
