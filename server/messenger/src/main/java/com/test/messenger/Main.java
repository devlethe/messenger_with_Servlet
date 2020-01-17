package com.test.messenger;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Main
 */
@WebServlet("/main")
public class Main extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Main() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String id = null;
			Cookie[] cookies = request.getCookies();
			for (int i = 0; i < cookies.length; i++) {
				String name = cookies[i].getName();
				if (name.equals("id")) {
					id = URLDecoder.decode(cookies[i].getValue(), "utf-8");
				} else {
					response.sendRedirect("login.jsp");
				}
			}
		} catch (Exception e) {
			response.sendRedirect("login.jsp");
		}
	}

/*
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}*/

}
