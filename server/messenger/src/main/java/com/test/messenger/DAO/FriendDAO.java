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

public class FriendDAO {

	public static List<FriendDTO> getFriendList(String id) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<FriendDTO> userlist = new ArrayList<FriendDTO>();

		try {
			conn = DBManager.getConnection();
			// 구분 분류하기
			String sql = "select f.user_id, r.user_name, f.user_type, f.user_friend, r.user_nickname, r.user_image, r.user_status_msg from friend f, user_info r where f.user_friend = r.user_id and f.user_id=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);

			rs = ps.executeQuery();
			while (rs.next()) {
				FriendDTO fdto = new FriendDTO();
				UserDTO dto = new UserDTO();
				String user_id = rs.getString("user_friend");
				String user_nickname = rs.getString("user_nickname");
				String user_name = rs.getString("user_name");
				//Object user_image = rs.getString("user_image");
				String user_status_msg = rs.getString("user_status_msg");
				dto.setId(user_id);
			//	dto.setProfileImage(user_image);
				dto.setNickname(user_nickname);
				dto.setName(user_name);
				dto.setStatusMsg(user_status_msg);
				fdto.setId(rs.getString("user_id"));
				fdto.setType(rs.getString("user_type"));
				fdto.setFriend(user_id);
				fdto.setUserData(dto);
				userlist.add(fdto);
			}
		} finally {
			DBManager.dbClose(rs, ps, conn);
		}
		return userlist;
		
	}
	
	public static Boolean addFriend(String user, String friend) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Boolean result = true;

		try {
			conn = DBManager.getConnection();
			
			String sql = "MERGE INTO friend USING DUAL ON (user_id=? and user_friend=?) WHEN NOT MATCHED THEN INSERT (idx ,user_id, user_friend, user_type) VALUES (friend_pk.NEXTVAL,?,?,1)" ;
					//"insert into friend(idx ,user_id, user_friend, user_type)"
					//+ " values(friend_pk.NEXTVAL,?,?,1)";
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
	
	public static Boolean deleteFriend(String user, String friend) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Boolean result = true;

		try {
			conn = DBManager.getConnection();
			
			String sql = "delete from friend where user_id=? and user_friend=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, user);
			ps.setString(2, friend);

			rs = ps.executeQuery();
			//??? 여긴 에러같은거 없나?
		} finally {
			DBManager.dbClose(rs, ps, conn);
		}
		return result;
	}
	
	
}
