package com.baskok.thriftsample.server.http;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baskok.thriftsample.generated.HelloService;
import com.baskok.thriftsample.server.HelloServiceImpl;

public class HelloServiceServlet extends HttpServlet {

	private static final long serialVersionUID = 6275551284696999931L;

	@Override
	/**
	 * This will show the available service calls
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		PrintWriter writer = resp.getWriter();
		writer.write("<html><body>");
		String requestedMethod = null;
		String[] parts = req.getServletPath().split("/");
		if (parts.length > 0) {
			requestedMethod = parts[1];
		}
		if (requestedMethod == null) {
			showAllMethods(writer);
		} else if (allMethods().contains(requestedMethod)) {
			showMethodWithParameters(writer, requestedMethod);
		} else {
			writer.write(String.format("<emph style='color: red;'>ERROR</emph>: method with name '%s' does not exist", requestedMethod));
		}
		
		writer.write("</body></html>");
	}

	private void showMethodWithParameters(PrintWriter writer, String requestedMethod) {
		writer.write("TODO!");
	}

	private void showAllMethods(PrintWriter writer) {
		writer.write(String.format("<h2>Welcome to the '%s'</h2>", HelloService.class.getSimpleName()));
		writer.write("I'm offering the following calls:");
		writer.write("<ul>");
		for (String method : allMethods()) {
			writer.write(String.format("<li><a href='/%s'>%s</a></li>", method, method));
		}
		writer.write("</ul>");
	}

	/**
	 * @return all methods defined on service
	 */
	private Set<String> allMethods() {
		final AtomicReference<Set<String>> foo = new AtomicReference<Set<String>>();
		new HelloService.Processor(new HelloServiceImpl()) {{
			foo.set(this.processMap_.keySet());
		}};
		Set<String> allMethods = foo.get();
		return allMethods;
	}
}
