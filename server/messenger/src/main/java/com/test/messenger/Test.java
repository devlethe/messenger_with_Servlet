package com.test.messenger;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/test")
public class Test extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
			out.println("<!DOCTYPE html>");
			out.println("<html><head></head><body>");
			out.println("<h1>안녕 세상!</h1>");
			out.println("</body></html>");
		} finally {
			out.close();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		//request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.print("<html><head><title>post test</title></head>");
		out.print("<body>");
		out.print("<h1>POST 테스트</h1>");

		String test1 = request.getParameter("test1");
		String test2 = request.getParameter("test2");

		// String[] interest = request.getParameterValues("interest");

		out.println(request.getParameter("test1"));
		out.println("<h3>" + test1 + "</h3>");
		out.println("<h3>" + test2 + "</h3>");

		/*
		 * for(int i=0; i< interest.length;i++) { if(i+1 == interest.length) {
		 * out.println(interest[i]); }else { out.println(interest[i]+","); } }
		 */

		out.close();
	}
}