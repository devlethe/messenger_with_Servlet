package com.test.messenger.DAO;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.test.messenger.DTO.UserDTO;
import com.test.messenger.database.DBManager;

public class UserDAO {

	public static List<UserDTO> getUserList(String nickname) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<UserDTO> userlist = new ArrayList<UserDTO>();

		try {
			conn = DBManager.getConnection();
			// 구분 분류하기
			String sql = "select * from user_info where user_nickname=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, nickname);

			rs = ps.executeQuery();
			while (rs.next()) {
				UserDTO dto = new UserDTO();
				String user_id = rs.getString("user_id");
				String user_name = rs.getString("user_name");
				String user_nickname = rs.getString("user_nickname");
				// Object user_image = rs.getString("user_image");
				String user_status_msg = rs.getString("user_status_msg");

				dto.setId(user_id);
				// dto.setProfileImage(user_image);
				dto.setName(user_name);
				dto.setNickname(user_nickname);
				dto.setStatusMsg(user_status_msg);
				userlist.add(dto);
			}
		} finally {
			DBManager.dbClose(rs, ps, conn);
		}
		return userlist;

	}

	public static Boolean getUserID(String id) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Boolean result = false;

		try {
			conn = DBManager.getConnection();
			String sql = "select user_id from user_info where user_id=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);

			rs = ps.executeQuery();
			if (rs.next()) {
				result = false;
			} else {
				result = true;
			}
		} finally {
			DBManager.dbClose(rs, ps, conn);
		}
		return result;
	}

	public static Boolean setRegister(UserDTO user) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Boolean result = true;

		try {
			conn = DBManager.getConnection();

			String sql = "insert into user_info(idx ,user_id, user_password, user_name, user_nickname, user_image, user_status_msg)"
					+ " values(userid_pk.NEXTVAL,?,?,?,?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getId());
			ps.setString(2, user.getPw());
			ps.setString(3, user.getName());
			ps.setString(4, user.getNickname());
			ps.setBlob(5, (Blob) user.getProfileImage());
			ps.setString(6, user.getStatusMsg());

			rs = ps.executeQuery();
			// ??? 여긴 에러같은거 없나?
		} finally {
			DBManager.dbClose(rs, ps, conn);
		}
		return result;
	}

	public static UserDTO getUserInfo(String id, boolean UsePw) throws SQLException {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		UserDTO dto = null;

		try {
			conn = DBManager.getConnection();

			String sql = "select * from user_info where user_id=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);

			rs = ps.executeQuery();
			if (rs.next()) {
				dto = new UserDTO();
				String user_id = rs.getString("user_id");
				String device_id = rs.getString("device_id");
				String user_name = rs.getString("user_name");
				String user_nickname = rs.getString("user_nickname");
				// Object user_image = rs.getString("user_image");
				String user_status_msg = rs.getString("user_status_msg");

				dto.setId(user_id);
				dto.setDeviceId(device_id);
				// dto.setProfileImage(user_image);
				dto.setName(user_name);
				dto.setNickname(user_nickname);
				dto.setStatusMsg(user_status_msg);

				if (UsePw) {
					String user_password = rs.getString("user_password");
					dto.setPw(user_password);
				}
			}
		} finally {
			DBManager.dbClose(rs, ps, conn);
		}
		return dto;
	}

	public static UserDTO getUserInfo(String id, String pw) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		UserDTO dto = null;

		try {
			conn = DBManager.getConnection();

			String sql = "select * from user_info where user_id = ? and user_password = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, pw);

			rs = ps.executeQuery();
			if (rs.next()) {
				dto = new UserDTO();
				String user_id = rs.getString("user_id");
				String device_id = rs.getString("device_id");
				String user_name = rs.getString("user_name");
				String user_nickname = rs.getString("user_nickname");
				// Object user_image = rs.getString("user_image");
				String user_status_msg = rs.getString("user_status_msg");

				dto.setId(user_id);
				dto.setDeviceId(device_id);
				// dto.setProfileImage(user_image);
				dto.setName(user_name);
				dto.setNickname(user_nickname);
				dto.setStatusMsg(user_status_msg);

				String user_password = rs.getString("user_password");
				dto.setPw(user_password);
			}
		} finally {
			DBManager.dbClose(rs, ps, conn);
		}
		return dto;

	}

	public static Boolean setDeviceid(String id, String deviceid) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Boolean result = true;
		try {
			conn = DBManager.getConnection();
			String sql = "update user_info set device_id=? where user_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, deviceid);
			ps.setString(2, id);

			rs = ps.executeQuery();
			// ??? 여긴 에러같은거 없나?
		} finally {
			DBManager.dbClose(rs, ps, conn);
		}
		return result;
	}

	public static String getUserDeviceID(String person) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String deviceid = null;

		try {
			conn = DBManager.getConnection();

			String sql = "select device_id from user_info where user_id=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, person);

			rs = ps.executeQuery();
			if (rs.next()) {
				deviceid = rs.getString("device_id");
			}
		} finally {
			DBManager.dbClose(rs, ps, conn);
		}
		return deviceid;
	}
}
