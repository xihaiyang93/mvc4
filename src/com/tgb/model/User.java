package com.tgb.model;

import java.util.Date;

import com.tgb.custom.CustomObjectMapper;

/**
 * 用户表
 * 
 * @author Administrator
 *
 */
public class User  {

	private int id;
	private int age;
	private String real_name;
	private String user_name;
	private String user_password;
	private int money;
	private String mobile;

	private Date create_time;

	public User() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getReal_name() {
		return real_name;
	}

	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_password() {
		return user_password;
	}

	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public User(int id, int age, String real_name, String user_name, String user_password, int money, String mobile,
			Date create_time) {
		super();
		this.id = id;
		this.age = age;
		this.real_name = real_name;
		this.user_name = user_name;
		this.user_password = user_password;
		this.money = money;
		this.mobile = mobile;
		this.create_time = create_time;
	}

}
