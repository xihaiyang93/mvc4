package com.tgb.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tgb.model.User;

public class SpringTest {

	 public static void main(String[] args) {
		 /*ApplicationContext ctx = new ClassPathXmlApplicationContext("config/spring-common.xml");
		 Object userMapper = ctx.getBean("userMapper");
		 System.out.println(userMapper);*/
		 
		 List<User> studentList = new ArrayList();

		 User s1 = new User ();

	     s1.setUser_name("bdc");

	     s1.setAge(20);

	     studentList.add(s1);

	     s1 = new User ();

	     s1.setUser_name("abc");

	     s1.setAge(10);

	     studentList.add(s1);

	     Collections.reverse(studentList);  //按照age降序 23,22  
	     
	     for(int i=0;i<studentList.size();i++)
	     {
	         System.out.println(studentList.get(i).getUser_name());
	     }
	 }
}
