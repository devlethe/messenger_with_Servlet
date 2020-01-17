package com.test.messenger;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.test.messenger.DAO.MessageDAO;
import com.test.messenger.DAO.RoomDAO;
import com.test.messenger.DAO.UserDAO;
import com.test.messenger.DTO.MessageDTO;
import com.test.messenger.DTO.UserDTO;
import com.test.messenger.fcm.Fcm;

@ServerEndpoint(value = "/webchat", configurator = HttpSessionConfig.class)
public class webchat extends HttpServlet {
	private static Map<String, Session> users = Collections.synchronizedMap(new HashMap<String, Session>());

	public HttpSession http;

	@OnMessage
	public void onMsg(String message, Session session) throws IOException {
		String userid = null;
		String person = null;
		JSONObject obj = new JSONObject();
		try {
			userid = session.getRequestParameterMap().get("id").get(0);
			person = session.getRequestParameterMap().get("person").get(0);
		}catch(Exception e) {
			
		}
		if(userid == null)
			userid = (String) ((HttpSession) (session.getUserProperties().get(HttpSession.class.getName())))
				.getAttribute("id");

		if(person == null)
			person = (String) ((HttpSession) (session.getUserProperties().get(HttpSession.class.getName())))
				.getAttribute("person");
		

		
		String tokenid = null;
		
		try{
			tokenid = UserDAO.getUserDeviceID(person);
		}catch(SQLException s) {
			
		}

		System.out.println(userid + " : " + message);

		synchronized (users) {
			try {
				MessageDAO.addMessage(userid, person, message);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
			if (users.containsKey(person)) {
				obj.put("from", userid);
				obj.put("msg", message);
				// users.get(person).getBasicRemote().sendText(userid + " : " + message);
				users.get(person).getBasicRemote().sendText(obj.toJSONString());
			} else {
				session.getBasicRemote().sendText("server respone" + message);
				if (tokenid != null && !tokenid.isEmpty()) {
					Fcm fcm = new Fcm();
					fcm.Send(tokenid, userid, message);
				}
			}
			}catch(Exception e) {
				session.getBasicRemote().sendText("server respone" + message);
				if (tokenid != null && !tokenid.isEmpty()) {
					Fcm fcm = new Fcm();
					fcm.Send(tokenid, userid, message);
				}
			}
			/*
			 * try { // message UserDTO userdata = UserDAO.getUserInfo(userid, false); }
			 * catch (SQLException e) { e.printStackTrace();
			 * session.getBasicRemote().sendText("error"); }
			 */

			/*
			 * Iterator<Session> it = users.keySet().iterator(); while(it.hasNext()){
			 * Session currentSession = it.next(); if(!currentSession.equals(session)){
			 * currentSession.getBasicRemote().sendText(userName + " : " + message); } }
			 */
		}

	}

	@OnOpen
	public void onOpen(Session session) {
		String userid = null;
		String person = null;
		try {
			userid = session.getRequestParameterMap().get("id").get(0);
			person = session.getRequestParameterMap().get("person").get(0);
		}catch(Exception e) {
			
		}
		if(userid == null)
			userid = (String) ((HttpSession) (session.getUserProperties().get(HttpSession.class.getName())))
				.getAttribute("id");

		if(person == null)
			person = (String) ((HttpSession) (session.getUserProperties().get(HttpSession.class.getName())))
				.getAttribute("person");
		
		
		System.out.println(userid + " connect to " + person);
		try {
			RoomDAO.addRoom(userid, person);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		users.put(userid, session);
				
		JSONObject obj = new JSONObject();
		try {
				List<MessageDTO> msglist = MessageDAO.getChatList(userid, person);
				if (msglist != null) {
					JSONArray arr = new JSONArray();
					for (MessageDTO i : msglist) {
						users.get(userid).getBasicRemote().sendText(i.getJSON().toJSONString());
					}
				} else {
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	/*
	 * public void sendNotice(String message){ String userName = "server";
	 * System.out.println(userName + " : " + message);
	 * 
	 * synchronized (users) { Iterator<Session> it = users.keySet().iterator();
	 * while(it.hasNext()){ Session currentSession = it.next(); try {
	 * currentSession.getBasicRemote().sendText(userName + " : " + message); } catch
	 * (IOException e) { e.printStackTrace(); } } } }
	 */
	@OnClose
	public void onClose(Session session) {
		try {
			String userName = (String) ((HttpSession) (session.getUserProperties().get(HttpSession.class.getName())))
				.getAttribute("id");
			users.remove(userName);
			System.out.println(userName + " disconnect");
		}catch(Exception e) {
			System.out.println("unknown disconnect");
		}
	}
}
