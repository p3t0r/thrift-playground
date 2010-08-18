package com.baskok.thriftsample.server.http;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.thrift.TBase;
import org.apache.thrift.TFieldIdEnum;

import com.baskok.thriftsample.generated.HelloService;
import com.baskok.thriftsample.server.HelloServiceImpl;

public class HelloServiceServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	/**
	 * This will show the available service calls
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
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
				writer.write(String
						.format("<emph style='color: red;'>ERROR</emph>: method with name '%s' does not exist",
								requestedMethod));
			}

			writer.write("</body></html>");
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	private void showAllMethods(PrintWriter writer) {
		writer.write(String.format("<h2>Welcome to the '%s'</h2>",
				HelloService.class.getSimpleName()));
		writer.write("I'm offering the following calls:");
		writer.write("<ul>");
		for (String method : allMethods()) {
			writer.write(String.format("<li><a href='/%s'>%s</a></li>", method,
					method));
		}
		writer.write("</ul>");
	}

	/**
	 * @return all methods defined on service
	 */
	private Set<String> allMethods() {
		final AtomicReference<Set<String>> foo = new AtomicReference<Set<String>>();
		new HelloService.Processor(new HelloServiceImpl()) {
			{
				foo.set(this.processMap_.keySet());
			}
		};
		Set<String> allMethods = foo.get();
		return allMethods;
	}

	private void showMethodWithParameters(PrintWriter writer,
			String requestedMethod) throws Exception {
		writer.write("<form>");
		Class<?>[] classes = HelloService.class.getDeclaredClasses();
		for (Class<?> clazz : classes) {
			if ((requestedMethod + "_args").equals(clazz.getSimpleName())) {
				writer.write(String.format(
						"method '%s' has the following arguments:",
						requestedMethod));
				printTBaseClass(writer, clazz, null);
			}
		}
		writer.write("<input type='submit'>");
		writer.write("</form>");
	}

	private void printTBaseClass(PrintWriter writer, Class<?> tBaseClass, String parentParameter) throws Exception {
		writer.write("<ul>");
		Class<?> fields = tBaseClass.getDeclaredClasses()[0];
		TFieldIdEnum[] parameterNames = (TFieldIdEnum[]) fields
				.getEnumConstants();
		for (int i = 0; i < parameterNames.length; i++) {
			String parameterFieldName = parameterNames[i].getFieldName();
			String getterName = "get"
					+ parameterFieldName.substring(0, 1).toUpperCase()
					+ parameterFieldName.substring(1);
			Class<?> parameterClass = tBaseClass.getDeclaredMethod(getterName,
					new Class[0]).getReturnType();
			writer.write(String.format("<li>%s %s", parameterClass.getSimpleName(), parameterFieldName));
			String nextParentParameter = ((parentParameter != null) ? (parentParameter + ".") : ("")) + parameterFieldName;
			if (!TBase.class.isAssignableFrom(parameterClass)) {
				writer.write(String.format("<input name='%s'></li>", nextParentParameter));
			} else {
				writer.write("</li>");
				printTBaseClass(writer, parameterClass, nextParentParameter);
			}
		}
		writer.write("</ul>");
	}

}
