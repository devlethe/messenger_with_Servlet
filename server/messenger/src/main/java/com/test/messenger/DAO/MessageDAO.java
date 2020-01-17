package com.test.messenger.DAO;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.test.messenger.DTO.FriendDTO;
import com.test.messenger.DTO.MessageDTO;
import com.test.messenger.DTO.UserDTO;
import com.test.messenger.database.DBManager;

public class MessageDAO {

	public static List<MessageDTO> getChatList(String user, String friend) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<MessageDTO> msglist = new ArrayList<MessageDTO>();
		
		if(user.compareToIgnoreCase(friend) < 0) {
			String tmp = friend;
			friend = user;
			user = tmp;
		}
		
		try {
			conn = DBManager.getConnection();
			// 구분 분류하기
			String sql = "SELECT  * FROM  Message WHERE  msg_room = (SELECT  idx FROM  message_room WHERE  room_from=? and room_to=?) ORDER BY msg_timestamp ASC";
			ps = conn.prepareStatement(sql);
			ps.setString(1, user);
			ps.setString(2, friend);

			rs = ps.executeQuery();
			while (rs.next()) {
				String msg_content = rs.getString("msg_content");
				String msg_from = rs.getString("msg_from");
				String msg_room = rs.getString("msg_room");
				String msg_to = rs.getString("msg_to");
				MessageDTO mdto = new MessageDTO();
				mdto.setContent(msg_content);
				mdto.setFrom(msg_from);
				mdto.setMsgRoom(msg_room);
				mdto.setTo(msg_to);
				//mdto.setImage(msg_image);
				

				msglist.add(mdto);
			}
		} finally {
			DBManager.dbClose(rs, ps, conn);
		}
		return msglist;
		
	}
	
	public static Boolean addMessage(String user, String friend, String content) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Boolean result = true;
		String user1 = user;
		String friend1 = friend;
		if(user.compareToIgnoreCase(friend) < 0) {
			friend1 = user;
			user1 = friend;
		}
		try {
			conn = DBManager.getConnection();
			
			String sql = "insert into message(idx ,msg_from, msg_to, msg_content, msg_image, msg_room)"
					+ " values(message_pk.NEXTVAL,?,?,?,NULL, (SELECT  idx FROM  message_room WHERE  room_from=? and room_to=?))";
			ps = conn.prepareStatement(sql);
			ps.setString(1, user);
			ps.setString(2, friend);
			ps.setString(3, content);
			ps.setString(4, user1);
			ps.setString(5, friend1);

			rs = ps.executeQuery();
			//??? 여긴 에러같은거 없나?
		} finally {
			DBManager.dbClose(rs, ps, conn);
		}
		return result;
	}

	
}
