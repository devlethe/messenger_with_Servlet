package com.test.messenger.DTO;

import org.json.simple.JSONObject;

public class FriendDTO {
	private String user_id;
	private String user_friend;
	private String user_type;
	private UserDTO user_data;

	public FriendDTO() {
		super();
	}

	public FriendDTO(String user_id) {
		super();
		this.user_id = user_id;
	}

	public String getId() {
		return user_id;
	}

	public void setId(String user_id) {
		this.user_id = user_id;
	}

	public JSONObject getJsonUser() {
		JSONObject json = new JSONObject();
		json.put("user", this.user_id);
		json.put("f_user", this.user_friend);
		json.put("f_name", this.user_data.getName());
		json.put("f_nickname", this.user_data.getNickname());
		json.put("f_status", this.user_data.getStatusMsg());
	//	json.put("f_profile", this.user_data.getProfileImage());
		return json;
	}

	public void setFriend(String user_friend) {
		this.user_friend = user_friend;
	}

	public String getFriend() {
		return user_friend;
	}

	public String getType() {
		return user_type;
	}

	public void setType(String user_type) {
		this.user_type = user_type;
	}

	public UserDTO getUserData() {
		return user_data;
	}

	public void setUserData(UserDTO user_data) {
		this.user_data = user_data;
	}

}
