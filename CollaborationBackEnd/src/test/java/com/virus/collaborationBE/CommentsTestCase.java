package com.virus.collaborationBE;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.virus.collaborationBE.dao.CommentsDAO;
import com.virus.collaborationBE.model.Comments;

public class CommentsTestCase {

	@Autowired
	static AnnotationConfigApplicationContext context;
	
	@Autowired
	static Comments comments;
	
	@Autowired
	static CommentsDAO commentsDAO;
	
	@BeforeClass
	public static void init()
	{
		context = new AnnotationConfigApplicationContext();
		context.scan("com.virus");
		context.refresh();
		comments = (Comments) context.getBean("comments");
		commentsDAO = (CommentsDAO) context.getBean("commentsDAO");
	}
	@Test
	public void createCommentTestCase()
	{
		comments.setId(1);
		comments.setCommentsmsg("Nice Post");
		comments.setBlogid(1);
		comments.setUserid("1");
		long d = System.currentTimeMillis();
		Date today = new Date(d);
		comments.setDateadded(today);
		boolean flag = commentsDAO.saveComment(comments);
		assertEquals("createCommentTestCase", true, flag);
	}
}
