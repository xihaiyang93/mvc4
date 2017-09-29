package com.tgb.service;

import java.util.List;

import com.tgb.model.User;


public interface UserService {
	
	int save(User user);
	
	boolean update(User user);
	
	boolean delete(int id);
	
	User findById(int id);
	
	List<User> findAll();
	
	int isExist(String user_name); 
	    
	User login(String user_name,String user_password);
} 
