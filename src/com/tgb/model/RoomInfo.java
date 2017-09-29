package com.tgb.model;

public class RoomInfo {
	public int room_no;
	public int onlineCount;
	public int state;

	public int getRoom_no() {
		return room_no;
	}

	public void setRoom_no(int room_no) {
		this.room_no = room_no;
	}

	public int getOnlineCount() {
		return onlineCount;
	}

	public void setOnlineCount(int onlineCount) {
		this.onlineCount = onlineCount;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public RoomInfo() {
		super();
	}

	public RoomInfo(int room_no, int onlineCount, int state) {
		super();
		this.room_no = room_no;
		this.onlineCount = onlineCount;
		this.state = state;
	}
}
