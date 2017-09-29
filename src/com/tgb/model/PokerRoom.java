package com.tgb.model;

import java.util.List;

public class PokerRoom {
	
	public 	int poker_record_id;
	
	/**
	 * 房间编号
	 */
	public int room_no;

	/**
	 * 房间状态
	 */
	public boolean room_state;

	/**
	 * 开始人数
	 */
	public int onlineCount;

	/**
	 * 是否准备
	 */
	public int is_ready;

	/**
	 * 用户
	 */
	public User user;

	/**
	 * 进入顺序
	 */
	public int sort;

	/**
	 * 是否继续1.继续 2.放弃
	 */
	public int is_continue;

	/**
	 * 房主
	 */
	public int room_owner;

	/**
	 * 0.观战 1.在线
	 */
	public int status;

	
	public int getPoker_record_id() {
		return poker_record_id;
	}

	public void setPoker_record_id(int poker_record_id) {
		this.poker_record_id = poker_record_id;
	}

	public int getRoom_no() {
		return room_no;
	}

	public void setRoom_no(int room_no) {
		this.room_no = room_no;
	}

	public boolean isRoom_state() {
		return room_state;
	}

	public void setRoom_state(boolean room_state) {
		this.room_state = room_state;
	}

	public int getOnlineCount() {
		return onlineCount;
	}

	public void setOnlineCount(int onlineCount) {
		this.onlineCount = onlineCount;
	}

	public int getIs_ready() {
		return is_ready;
	}

	public void setIs_ready(int is_ready) {
		this.is_ready = is_ready;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public int getIs_continue() {
		return is_continue;
	}

	public void setIs_continue(int is_continue) {
		this.is_continue = is_continue;
	}

	public int getRoom_owner() {
		return room_owner;
	}

	public void setRoom_owner(int room_owner) {
		this.room_owner = room_owner;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public PokerRoom() {
		super();
	}

	public PokerRoom(int room_no, boolean room_state, int onlineCount, int is_ready, User user, int sort,
			int is_continue, int room_owner, int status, List<PokerRoom> roomList,int poker_record_id) {
		super();
		this.room_no = room_no;
		this.room_state = room_state;
		this.onlineCount = onlineCount;
		this.is_ready = is_ready;
		this.user = user;
		this.sort = sort;
		this.is_continue = is_continue;
		this.room_owner = room_owner;
		this.status = status;
		this.poker_record_id = poker_record_id;
	}
}
