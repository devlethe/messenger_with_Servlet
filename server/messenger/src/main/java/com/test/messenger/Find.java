package com.test.messenger;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.test.messenger.DAO.UserDAO;
import com.test.messenger.DTO.UserDTO;

@WebServlet("/find")
public class Find extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HttpSession session;

	public Find() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		JSONObject obj = new JSONObject();

		session = request.getSession();
		PrintWriter out = response.getWriter();
		String nickname = request.getParameter("nickname");

		if (nickname == null || nickname.isEmpty()) {
			obj.put("result", "Required String paramter [nickname]");
			obj.put("code", "400");
		} else {
			System.out.println(nickname);
			try {
				List<UserDTO> userdata = UserDAO.getUserList(nickname);
				if (userdata != null) {
					obj.put("result", "Success");
					obj.put("code", "200");
					String myid = (String) session.getAttribute("id");
					if(myid == null) {
						myid = request.getParameter("id");
					}
					JSONArray arr = new JSONArray();
					for (UserDTO i : userdata) {
						if (myid.compareTo(i.getId()) != 0)
							arr.add(i.getJSONUser());
					}
					obj.put("userdata", arr);
				} else {
					obj.put("result", "Userid is already registered");
					obj.put("code", "400");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				obj.put("result", "Error");
				obj.put("code", "403");
			}
		}

		out.println(obj.toJSONString());
		out.close();
	}

	/*
	 * protected void doPost(HttpServletRequest request, HttpServletResponse
	 * response) throws ServletException, IOException { doGet(request, response); }
	 */

}
