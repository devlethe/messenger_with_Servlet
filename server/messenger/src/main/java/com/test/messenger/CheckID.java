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

@WebServlet("/checkID")
public class CheckID extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CheckID() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");

        JSONObject obj = new JSONObject();
        
		PrintWriter out = response.getWriter();
		String userid = request.getParameter("id");

		if (userid == null || userid.isEmpty()) {
			obj.put("result", "Required String paramter [id]");
			obj.put("code", "400");
		} else {
			try {
				if(UserDAO.getUserID(userid)) {
					obj.put("result", "Success");
					obj.put("code", "200");
					obj.put("id", userid);
				}else {
					obj.put("result", "Userid is already registered");
					obj.put("code", "400");
					obj.put("id", userid);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				obj.put("result", "Error");
				obj.put("code", "400");
				obj.put("id", userid);
			}
		}

		out.println(obj.toJSONString());
		out.close();
	}

	/*
	 * protected void doPost(HttpServletRequest request, HttpServletResponse
	 * response) throws ServletException, IOException { // TODO Auto-generated
	 * method stub doGet(request, response); }
	 */

}
