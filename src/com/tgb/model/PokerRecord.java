package com.tgb.model;

import java.util.Date;
import java.util.List;

public class PokerRecord {
	/**
	 * 编号
	 */
	public int id;

	/**
	 * 流水号
	 */
	public String serial_no;

	/**
	 * 房间号
	 */
	public int room_no;

	/**
	 * 用户编号
	 */
	public int user_id;

	/**
	 * 是否赢牌
	 */
	public int is_win;

	/**
	 * 获得金额
	 */
	public int money;

	/**
	 * 开始时间
	 */
	public Date beginTime;

	/**
	 * 结束时间
	 */
	public Date endTime;

	/**
	 * 发牌结果
	 */
	public List<Poker> pokerList;

	/**
	 * 参加用户
	 */
	public List<User> addUserList;
    
	
	public String pokers;
	
	/**
	 * 用户离开顺序
	 */
	public List<Object> leaveUserList;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getserial_no() {
		return serial_no;
	}

	public void setserial_no(String serial_no) {
		this.serial_no = serial_no;
	}

	public int getRoom_no() {
		return room_no;
	}

	public void setRoom_no(int room_no) {
		this.room_no = room_no;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getIs_win() {
		return is_win;
	}

	public void setIs_win(int is_win) {
		this.is_win = is_win;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public List<Poker> getPokerList() {
		return pokerList;
	}

	public void setPokerList(List<Poker> pokerList) {
		this.pokerList = pokerList;
	}

	public List<User> getAddUserList() {
		return addUserList;
	}

	public void setAddUserList(List<User> addUserList) {
		this.addUserList = addUserList;
	}

	public List<Object> getLeaveUserList() {
		return leaveUserList;
	}

	public void setLeaveUserList(List<Object> leaveUserList) {
		this.leaveUserList = leaveUserList;
	}

	public String getPokers() {
		return pokers;
	}

	public void setPokers(String pokers) {
		this.pokers = pokers;
	}

	public PokerRecord(){
		super();
	}

	public PokerRecord(int id, String serial_no, int room_no, int user_id, int is_win, int money, Date beginTime,
			Date endTime, List<Poker> pokerList, List<User> addUserList, List<Object> leaveUserList,String pokers) {
		super();
		this.id = id;
		this.serial_no = serial_no;
		this.room_no = room_no;
		this.user_id = user_id;
		this.is_win = is_win;
		this.money = money;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.pokerList = pokerList;
		this.addUserList = addUserList;
		this.leaveUserList = leaveUserList;
		this.pokers = pokers;
	}
}
