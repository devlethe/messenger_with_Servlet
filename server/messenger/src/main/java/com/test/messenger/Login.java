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

@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HttpSession session;

	public Login() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect("login.jsp");
		/*
		 * response.sendRedirect("login.jsp?data=test"); System.out.println("test");
		 * String data = request.getParameter("data");
		 * 
		 * request.setAttribute("dataobject", data); //객체를 request객체에 담음 (data가 문자열이
		 * 아니어도 가능)
		 * 
		 * RequestDispatcher dispatcher =
		 * request.getRequestDispatcher("jsp005_call_jsp_from_servlet.jsp");
		 * 
		 * dispatcher.forward(request, response);
		 */
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		session = request.getSession();
		response.setContentType("text/html;charset=UTF-8");

		PrintWriter out = response.getWriter();
		String id = request.getParameter("id");
		String pw = request.getParameter("password");
		String deviceid = request.getParameter("deviceid");
		String mob = request.getParameter("mob");
		if (deviceid == null)
			deviceid = "";
		System.out.println(id + "/" + deviceid);
		String page = "login.jsp";
		String msg = "";
		String code = "400";
		if (id == null || id.isEmpty() || pw == null || pw.isEmpty()) {
			msg = "Required String paramter [id, pw]";
		} else {
			try {
				UserDTO userdata = UserDAO.getUserInfo(id, pw);
				if (userdata != null) {
					msg = "success";
					page = "main.jsp";
					session.setAttribute("id", id);
					session.setAttribute("deviceid", deviceid);
					code = "200";
					// session.setMaxInactiveInterval(10*60);
				} else {
					msg = "User or password incorrect";
				}
			} catch (SQLException e) {
				e.printStackTrace();
				msg = "Error";
			}
		}
		if (mob != null && !mob.isEmpty()) {

			if (deviceid != null && !deviceid.isEmpty()) {

				if (msg.compareTo("success") == 0) {
					try {
						UserDAO.setDeviceid(id, deviceid);
					} catch (SQLException e) {
					}
				}
			}
			JSONObject obj = new JSONObject();
			obj.put("code", code);
			out.println(obj.toJSONString());
		} else {
			out.println("<script type=\"text/javascript\">");
			if (msg.compareTo("success") != 0)
				out.println("alert('" + msg + "');");
			out.println("location='" + page + "';");
			out.println("</script>");
		}
		out.close();

		/*
		 * request.setAttribute("ids", id); request.setAttribute("pws", password);
		 * RequestDispatcher dispatcher = request.getRequestDispatcher("sendtest.jsp");
		 * dispatcher.forward(request, response);
		 */
	}

}
