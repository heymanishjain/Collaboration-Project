package com.virus.collaborationBE;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.virus.collaborationBE.dao.CommentsDAO;
import com.virus.collaborationBE.dao.ForumDAO;
import com.virus.collaborationBE.model.Comments;
import com.virus.collaborationBE.model.Forum;

public class ForumTestCase {

	@Autowired
	static AnnotationConfigApplicationContext context;
	
	@Autowired
	static ForumDAO forumDAO;
	
	@Autowired
	static Forum forum;
	
	@BeforeClass
	public static void init()
	{
		context = new AnnotationConfigApplicationContext();
		context.scan("com.virus");
		context.refresh();
		forum = (Forum) context.getBean("forum");
		forumDAO = (ForumDAO) context.getBean("forumDAO");
	}
	@Test
	public void createForumTestCase()
	{
		long d = System.currentTimeMillis();
		Date today = new Date(d);
		forum.setId(01);
		forum.setTopic("What is java");
		forum.setUserid("mahi");
		forum.setDateadded(today);
		boolean flag = forumDAO.saveForum(forum);
		assertEquals("createForumTestCase",true, flag);
	}

}
