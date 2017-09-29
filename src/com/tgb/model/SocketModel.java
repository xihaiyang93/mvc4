package com.tgb.model;

public class SocketModel {
    
	/**
	 * 发送用户
	 */
	public String form_user;
	
	/**
	 * 接收用户
	 */
	public String to_user;
    
	/**
	 * 发送位置1.默认群发 2.单发
	 */
	public int to_location;
	 
	public int room_no;
	
	/**
	 * 执行操作
	 */
	public String do_action;
	
	/**
	 * 发送文本类型1.文本 2.图片 3.文件
	 */
	public int send_type;
    
	/**
	 * 发送信息
	 */
	public Object send_message;

	/**
	 * 在线人数
	 */
	public int onlineCount;
	
	public int multiple;
	
	public int money;
	
	public int totalMoney;
	
	
	public SocketModel(){
		super();
	}
	
	public String getForm_user() {
		return form_user;
	}

	public void setForm_user(String form_user) {
		this.form_user = form_user;
	}

	public String getTo_user() {
		return to_user;
	}

	public void setTo_user(String to_user) {
		this.to_user = to_user;
	}

	public int getTo_location() {
		return to_location;
	}

	public void setTo_location(int to_location) {
		this.to_location = to_location;
	}

	public String getDo_action() {
		return do_action;
	}

	public void setDo_action(String do_action) {
		this.do_action = do_action;
	}

	public int getSend_type() {
		return send_type;
	}

	public void setSend_type(int send_type) {
		this.send_type = send_type;
	}

	public Object getSend_message() {
		return send_message;
	}

	public void setSend_message(Object send_message) {
		this.send_message = send_message;
	}
	
	public int getOnlineCount() {
		return onlineCount;
	}

	public void setOnlineCount(int onlineCount) {
		this.onlineCount = onlineCount;
	}

	public int getRoom_no() {
		return room_no;
	}

	public void setRoom_no(int room_no) {
		this.room_no = room_no;
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

	public int getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(int totalMoney) {
		this.totalMoney = totalMoney;
	}
    
	public SocketModel(String form_user, String to_user, int to_location, int room_no, String do_action, int send_type,
			Object send_message, int onlineCount, int multiple, int money, int totalMoney) {
		super();
		this.form_user = form_user;
		this.to_user = to_user;
		this.to_location = to_location;
		this.room_no = room_no;
		this.do_action = do_action;
		this.send_type = send_type;
		this.send_message = send_message;
		this.onlineCount = onlineCount;
		this.multiple = multiple;
		this.money = money;
		this.totalMoney = totalMoney;
	}
}
