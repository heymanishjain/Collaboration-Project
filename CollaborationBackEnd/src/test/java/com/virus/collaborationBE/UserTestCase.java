package com.virus.collaborationBE;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.virus.collaborationBE.model.*;
import com.virus.collaborationBE.dao.UserDAO;

public class UserTestCase {

	@Autowired
	static AnnotationConfigApplicationContext context;
	
	@Autowired
	static User user;
	
	@Autowired
	static UserDAO userDAO;
	
	@BeforeClass
	public static void init()
	{
		context = new AnnotationConfigApplicationContext();
		context.scan("com.virus");
		context.refresh();
		user = (User) context.getBean("user");
		userDAO = (UserDAO) context.getBean("userDAO");
	}
	@Test
	public void createUserTestCase()
	{
		user.setId("Mickey");
		user.setName("Manish Jain");
		user.setAddress("3,RK street");
		user.setMail("msmanishq@gmail.com");
		user.setMobile("9999999999");
		user.setPassword("mickey");
		user.setConfirmpassword("mickey");
		user.setRole("admin");
		boolean flag = userDAO.saveUser(user);
		assertEquals("createUserTestCase", true, flag);
	}
	@Test
	public void updateUserTestCase()
	{
		user.setId("VirusMickey");
		user.setName("Manish Jain");
		user.setAddress("3,RK street");
		user.setMail("msmanishq15@gmail.com");
		user.setMobile("9999999999");
		user.setPassword("mickey");
		user.setConfirmpassword("mickey");
		user.setRole("admin");
		boolean flag = userDAO.updateUser(user);
		assertEquals("updateUserTestCase", true, flag);
	}
	@Test
	public void validateUserTestCase()
	{
		boolean flag = userDAO.validateUser("1", "mickey")!=null;
		assertEquals("validateUserTestCase", true, flag);
	}
	//@Test
	public void deleteUserTestCase()
	{
		userDAO.getUserById("1");
		boolean flag = userDAO.deletUser(user);
		assertEquals("deleteUserTestCase", true, flag);
	}
	
}
