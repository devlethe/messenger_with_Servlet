package com.test.messenger.DTO;

import org.json.simple.JSONObject;

public class UserDTO {
	private String user_id;
	private String user_password;
	private String device_id;
	private String user_name;
	private String user_nickname;
	private Object user_image;
	private String user_status_msg;

	public UserDTO(String user_id) {
		super();
		this.user_id = user_id;
	}

	public UserDTO(String user_id, String user_password, String user_name, String user_nickname, Object user_image,
			String user_status_msg) {
		super();
		this.user_id = user_id;
		this.user_password = user_password;
		this.user_name = user_name;
		this.user_nickname = user_nickname;
		this.user_image = user_image;
		this.user_status_msg = user_status_msg;
		System.out.println(this.user_id + "/" + this.user_password + "/" + this.user_name + "/" + this.user_nickname
				+ "/" + this.user_status_msg);
	}

	public UserDTO() {
		super();
	}

	public String getId() {
		return user_id;
	}

	// 유효성 검사 수정하기
	public boolean validation() {
		if (this.user_id == null) {
			return false;
		}
		if (this.user_password == null) {
			return false;
		}
		if (this.user_name == null) {
			return false;
		}
		if (this.user_nickname == null) {
			return false;
		}

		return true;
	}

	public void setId(String user_id) {
		this.user_id = user_id;
	}

	public String getPw() {
		return user_password;
	}

	public void setPw(String user_password) {
		this.user_password = user_password;
	}

	public String getDeviceId() {
		return device_id;
	}

	public void setDeviceId(String device_id) {
		this.device_id = device_id;
	}

	public String getName() {
		return user_name;
	}

	public void setName(String user_name) {
		this.user_name = user_name;
	}

	public String getNickname() {
		return user_nickname;
	}

	public void setNickname(String user_nickname) {
		this.user_nickname = user_nickname;
	}

	public Object getProfileImage() {
		return user_image;
	}

	public void setProfileImage(Object user_image) {
		this.user_image = user_image;
	}

	public String getStatusMsg() {
		return user_status_msg;
	}

	public void setStatusMsg(String user_status_msg) {
		this.user_status_msg = user_status_msg;
	}

	public JSONObject getJSONUser() {
		JSONObject json = new JSONObject();
		json.put("user", this.user_id);
		json.put("name", this.user_name);
		json.put("nickname", this.user_nickname);
		json.put("status", this.user_status_msg);
		// json.put("image", this.user_image);
		return json;
	}
}
