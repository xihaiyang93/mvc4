package com.tgb.model;

import java.util.Date;

public class PokerOperation {
	
	public int id;
	
	/**
	 * 流水号
	 */
    public String serial_no;
	
    /**
     * 房间编号
     */
	public int room_no;
	
	/**
	 * 用户编号
	 */
	public int user_id;
    
	/**
	 * 是否翻牌
	 */
    public int is_show;
    
    /**
     * 是否比较
     */
    public int is_compare;
    
    /**
     * 比较用户
     */
    public int compare_user_id;
      
    /**
     * 是否继续
     */
    public int is_continue;
    
    /**
     * 倍数
     */
    public int multiple;
    
    /**
     * 消耗金额
     */
    public int money;
    
    /**
     * 消耗总金额
     */
    public int total_money;
    
    /**
     * 当前次数
     */
    public int current_count;
    
    /**
     * 发生时间
     */
	public Date send_time;
     
	
	public int poker_record_id;
	
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

	public int getIs_show() {
		return is_show;
	}

	public void setIs_show(int is_show) {
		this.is_show = is_show;
	}

	public int getIs_compare() {
		return is_compare;
	}

	public void setIs_compare(int is_compare) {
		this.is_compare = is_compare;
	}

	public int getCompare_user_id() {
		return compare_user_id;
	}

	public void setCompare_user_id(int compare_user_id) {
		this.compare_user_id = compare_user_id;
	}

	public int getIs_continue() {
		return is_continue;
	}

	public void setIs_continue(int is_continue) {
		this.is_continue = is_continue;
	}

	public int getMultiple() {
		return multiple;
	}

	public void setMultiple(int multiple) {
		this.multiple = multiple;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public int getTotal_money() {
		return total_money;
	}

	public void setTotal_money(int total_money) {
		this.total_money = total_money;
	}

	public int getCurrent_count() {
		return current_count;
	}

	public void setCurrent_count(int current_count) {
		this.current_count = current_count;
	}

	public Date getSend_time() {
		return send_time;
	}

	public void setSend_time(Date send_time) {
		this.send_time = send_time;
	}
	
	public int getPoker_record_id() {
		return poker_record_id;
	}

	public void setPoker_record_id(int poker_record_id) {
		this.poker_record_id = poker_record_id;
	}
    
	public PokerOperation(){
		super();
	}

	public PokerOperation(int id, String serial_no, int room_no, int user_id, int is_show, int is_compare,
			int compare_user_id, int is_continue, int multiple, int money, int total_money, int current_count,
			Date send_time,int poker_record_id) {
		super();
		this.id = id;
		this.serial_no = serial_no;
		this.room_no = room_no;
		this.user_id = user_id;
		this.is_show = is_show;
		this.is_compare = is_compare;
		this.compare_user_id = compare_user_id;
		this.is_continue = is_continue;
		this.multiple = multiple;
		this.money = money;
		this.total_money = total_money;
		this.current_count = current_count;
		this.send_time = send_time;
		this.poker_record_id = poker_record_id;
	}
}
