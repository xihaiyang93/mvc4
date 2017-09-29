package com.tgb.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.tgb.model.User;
import com.tgb.service.PokerOperationService;
import com.tgb.service.PokerRecordService;
import com.tgb.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
		
	public static Map<Integer,Object> loginUserList;
	
	@RequestMapping("/login")
	@ResponseBody  
	public User login(@RequestParam("user_name") String user_name,@RequestParam("user_password") String user_password){
		User user = userService.login(user_name, user_password);
		
		if(loginUserList == null){
			loginUserList = new HashMap<Integer,Object>();
		}
		
		loginUserList.put(user.getId(), user);
		return user;
	}
	
	
	@RequestMapping("/getList")
	@ResponseBody  
	public List<User> getList(HttpServletRequest request){
		List<User> findAll = userService.findAll();
		return findAll;
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody  
	public HashMap<String,Object> addUser(@RequestBody User user,HttpServletRequest request){
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		
		int isExist = userService.isExist(user.getUser_name());
		
		if(isExist > 0){
			 map.put("success",false);
			 map.put("message", "您输入的用户名已被注册!");
		     return map;
		}
				
		user.setCreate_time(new Date());
		int id = userService.save(user);
		
	    if(id > 0){
	    	System.out.println("iesert success id:"+id);
	        map.put("success", true);
	        map.put("message", "注册成功!");
	        return map;
	    }
	    
	    map.put("success",false);
	    map.put("message", "注册失败!");
		return map;
	}

	@RequestMapping("/delete")
	public void delUser(int id,HttpServletRequest request,HttpServletResponse response){
		String result = "{\"result\":\"error\"}";
		
		if(userService.delete(id)){
			result = "{\"result\":\"success\"}";
		}
		
		response.setContentType("application/json");
		
		try {
			PrintWriter out = response.getWriter();
			out.write(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
}
