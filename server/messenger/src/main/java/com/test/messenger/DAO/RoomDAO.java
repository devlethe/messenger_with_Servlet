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
import com.test.messenger.DTO.UserDTO;
import com.test.messenger.database.DBManager;

public class RoomDAO {
	public static String getRoom(String user, String friend) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String result = null;

		if(user.compareToIgnoreCase(friend) < 0) {
			String tmp = friend;
			friend = user;
			user = tmp;
		}
		
		try {
			conn = DBManager.getConnection();
			
			String sql = "select idx from message_room where room_from = ? and room_to = ?" ;
			ps = conn.prepareStatement(sql);
			ps.setString(1, user);
			ps.setString(2, friend);

			rs = ps.executeQuery();

			if (rs.next()) {
				result = rs.getString("idx");
			}
		} finally {
			DBManager.dbClose(rs, ps, conn);
		}
		return result;
	}
	
	public static Boolean addRoom(String user, String friend) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Boolean result = true;

		if(user.compareToIgnoreCase(friend) < 0) {
			String tmp = friend;
			friend = user;
			user = tmp;
		}
		
		try {
			conn = DBManager.getConnection();
			
			String sql = "MERGE INTO message_room USING DUAL ON (room_from=? and room_to=?)"
					+ "WHEN NOT MATCHED THEN "
					+ "INSERT (idx ,room_from, room_to) VALUES (message_room_pk.NEXTVAL,?,?)" ;
			ps = conn.prepareStatement(sql);
			ps.setString(1, user);
			ps.setString(2, friend);
			ps.setString(3, user);
			ps.setString(4, friend);

			rs = ps.executeQuery();
			//??? 여긴 에러같은거 없나?
		} finally {
			DBManager.dbClose(rs, ps, conn);
		}
		return result;
	}
	
}
