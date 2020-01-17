package com.test.messenger;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.test.messenger.DAO.UserDAO;
import com.test.messenger.DTO.UserDTO;

@WebServlet("/register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Register() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect("register.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");

		PrintWriter out = response.getWriter();
		String userid = request.getParameter("id");
		String password = request.getParameter("password");
		String nickname = request.getParameter("nickname");
		String name = request.getParameter("name");
		String status = request.getParameter("status");
		String moblie = request.getParameter("mob");
		UserDTO user = new UserDTO(userid, password, name, nickname,  null, status);
		String msg = "";
		String code = "400";
		String page = "register.jsp";
		if (user.validation() == false) {
			msg = "validation check is fail";
		} else {
			try {
				if (UserDAO.setRegister(user)) {
					msg = "success";
					page = "login.jsp";
					code = "200";
				} else {
					msg = "Userid is already registered";
				}
			} catch (SQLException e) {
				e.printStackTrace();
				msg = "Error";
			}
		}
		if (moblie != null && !moblie.isEmpty()) {
			JSONObject obj = new JSONObject();
			obj.put("code", code);
			out.println(obj.toJSONString());
		} else {
			out.println("<script type=\"text/javascript\">");
			out.println("alert('" + msg + "');");
			out.println("location='" + page + "';");
			out.println("</script>");
		}
		
		out.close();

	}

}
