package com.test.messenger;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.test.messenger.DAO.UserDAO;
import com.test.messenger.DTO.UserDTO;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

@WebServlet("/logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HttpSession session;

	public Logout() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect("logout.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		session = request.getSession();
		response.setContentType("text/html;charset=UTF-8");

		PrintWriter out = response.getWriter();
		String id = request.getParameter("id");
		String msg = "";
		String code = "400";
		if (id == null || id.isEmpty()) {
			msg = "Required String paramter [id]";
		} else {
			try {
				if (UserDAO.setDeviceid(id, "")) {
					msg = "success";
					code = "200";
					session.invalidate();
				}
			} catch (SQLException e) {
				e.printStackTrace();
				msg = "Error";
			}
		}
		JSONObject obj = new JSONObject();
		obj.put("code", code);
		obj.put("msg", msg);
		out.println(obj.toJSONString());
		out.close();
	}

}
