package com.tgb.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tgb.model.User;

public interface UserMapper {

	int save(User user);
	
	boolean update(User user);
	
	boolean delete(int id);
	
	User findById(int id);
	
	List<User> findAll();
	
    int isExist(@Param("user_name")String user_name); 
    
    User login(@Param("user_name")String user_name,@Param("user_password") String user_password);
}
