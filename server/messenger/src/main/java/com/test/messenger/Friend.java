package com.test.messenger;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.test.messenger.DAO.FriendDAO;
import com.test.messenger.DTO.FriendDTO;

@WebServlet("/friend")
public class Friend extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Friend() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		JSONObject obj = new JSONObject();
		PrintWriter out = response.getWriter();
		String id = request.getParameter("id");

		if (id == null || id.isEmpty()) {
			obj.put("result", "Required String paramter [id]");
			obj.put("code", "400");
		} else {
			try {
				obj.put("result", "Success");
				obj.put("code", "200");
				List<FriendDTO> userdata = FriendDAO.getFriendList(id);
				JSONArray arr = new JSONArray();
				if (userdata != null) {
					for (FriendDTO data : userdata) {
						arr.add(data.getJsonUser());
					}
					obj.put("userdata", arr);
				} else {
					obj.put("result", "no have or something is wrong");
					obj.put("code", "400");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				obj.put("result", "Error");
				obj.put("code", "400");
			}
		}

		out.println(obj.toJSONString());
		out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		JSONObject obj = new JSONObject();

		PrintWriter out = response.getWriter();
		String user = request.getParameter("user");
		String friend = request.getParameter("friend");
		String type = request.getParameter("type");
		if(user.compareTo(friend) == 0 || type == null || type.isEmpty() || user == null || user.isEmpty() || friend == null || friend.isEmpty()) {
			obj.put("result", "validation check is fail");
			obj.put("code", "400");
		} else {
			try {
				if (type.toLowerCase().compareTo("delete") == 0) {
					if (FriendDAO.deleteFriend(user, friend)) {
						obj.put("result", "Success");
						obj.put("code", "200");
					} else {
						obj.put("result", "error");
						obj.put("code", "400");
					}

				} else {
					if (FriendDAO.addFriend(user, friend)) {
						obj.put("result", "Success");
						obj.put("code", "200");
					} else {
						obj.put("result", "error");
						obj.put("code", "400");
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
				obj.put("result", "Error");
				obj.put("code", "400");
			}
		}

		out.println(obj.toJSONString());
		out.close();
	}

}
