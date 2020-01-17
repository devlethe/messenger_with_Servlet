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

import com.test.messenger.DAO.MessageDAO;
import com.test.messenger.DAO.UserDAO;
import com.test.messenger.DTO.MessageDTO;
import com.test.messenger.DTO.UserDTO;


@WebServlet("/message")
public class Message extends HttpServlet {
	private static final long serialVersionUID = 1L;


    public Message() {
        super();

    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		JSONObject obj = new JSONObject();

		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		String userid = (String) session.getAttribute("id");
		String person = (String) session.getAttribute("person");
		

		if (userid == null || userid.isEmpty() || person == null || person.isEmpty()) {
			obj.put("result", "Required String paramter [userid, person]");
			obj.put("code", "400");
		} else {
			try {
				List<MessageDTO> msglist = MessageDAO.getChatList(userid, person);
				if (msglist != null) {
					obj.put("result", "Success");
					obj.put("code", "200");
					JSONArray arr = new JSONArray();
					for (MessageDTO i : msglist) {
						arr.add(i.getJSON());
					}
					obj.put("msg", arr);
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
