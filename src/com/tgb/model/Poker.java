package com.tgb.model;

/**
 * @author Administrator
 *
 */
public class Poker {

	/**
	 * 纸牌编号
	 */
	public int id;

	/**
	 * 纸牌名称
	 */
	public String name;

	/**
	 * 纸牌数值
	 */
	public int number;

	/**
	 * 纸牌花色
	 */
	public int type;
	
	/**
	 * 牌型描述
	 */
	public String describe;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}
	
	public Poker(){
		super();
	}

	public Poker(int id, String name, int type,int number,String describe) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.number = number;
		this.describe = describe;
	}
}
