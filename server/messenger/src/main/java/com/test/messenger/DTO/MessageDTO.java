package com.test.messenger.DTO;

import org.json.simple.JSONObject;

public class MessageDTO {
	private String msg_from;
	private String msg_to;
	private String msg_content;
	private Object msg_image;
	private String msg_room;

	public MessageDTO() {
		super();
	}

	public MessageDTO(String msg_from, String msg_to, String msg_content, String msg_room) {
		super();
		this.msg_from = msg_from;
		this.msg_to = msg_to;
		this.msg_content = msg_content;
		this.msg_room = msg_room;
	}

	public String getFrom() {
		return msg_from;
	}

	public void setFrom(String msg_from) {
		this.msg_from = msg_from;
	}

	public String getTo() {
		return msg_to;
	}

	public void setTo(String msg_to) {
		this.msg_to = msg_to;
	}

	public String getContent() {
		return msg_content;
	}

	public void setContent(String msg_content) {
		this.msg_content = msg_content;
	}

	public Object getImage() {
		return msg_image;
	}

	public void setImage(Object msg_image) {
		this.msg_image = msg_image;
	}

	public MessageDTO(String msg_from, String msg_to, String msg_content, Object msg_image) {
		super();
		this.msg_from = msg_from;
		this.msg_to = msg_to;
		this.msg_content = msg_content;
		this.msg_image = msg_image;
	}

	public String getMsgRoom() {
		return msg_room;
	}

	public void setMsgRoom(String msg_room) {
		this.msg_room = msg_room;
	}

	public JSONObject getJSON() {
		JSONObject obj = new JSONObject();
		obj.put("from", this.msg_from);
		obj.put("to", this.msg_to);
		obj.put("msg", this.msg_content);
		return obj;
	}

}
