package com.test.messenger.DTO;

public class MessageRoomDTO {
	private String room_from;
	private String room_to;
	private String idx;

	public MessageRoomDTO() {
		super();
	}

	public MessageRoomDTO(String room_from, String room_to) {
		super();
		this.room_from = room_from;
		this.room_to = room_to;
	}

	public String getFrom() {
		return room_from;
	}

	public void setFrom(String room_from) {
		this.room_from = room_from;
	}

	public String getTo() {
		return room_to;
	}

	public void setTo(String room_to) {
		this.room_to = room_to;
	}

	public String getIdx() {
		return idx;
	}

	public void setIdx(String idx) {
		this.idx = idx;
	}

}
