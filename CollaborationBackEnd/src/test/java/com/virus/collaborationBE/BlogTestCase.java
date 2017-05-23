package com.virus.collaborationBE;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.virus.collaborationBE.dao.BlogDAO;
import com.virus.collaborationBE.model.Blog;

public class BlogTestCase {

	@Autowired
	static AnnotationConfigApplicationContext context;
	
	@Autowired
	static Blog blog;
	
	@Autowired
	static BlogDAO blogDAO;
	
	@BeforeClass
	public static void init()
	{
		context = new AnnotationConfigApplicationContext();
		context.scan("com.virus");
		context.refresh();
		
		blog = (Blog) context.getBean("blog");
		
		blogDAO = (BlogDAO) context.getBean("blogDAO");
	}

	@Test
	public void createBlogTest()
	{
		long d = System.currentTimeMillis();
		Date today = new Date(d);
		blog.setId(1);
		blog.setTitle("Samsung SmartPhones");
		blog.setDescription("There are many various types of smasung smartphones");
		blog.setStatus("Pending");
		blog.setUser_id("VirusMickey");
		blog.setDate_added(today);
		boolean flag = blogDAO.saveBlog(blog);
		assertEquals("createBlogTest", true, flag);
	}
	@Test
	public void updateBlogTest()
	{
		long d = System.currentTimeMillis();
		Date today = new Date(d);
		blog.setId(1);
		blog.setTitle("Samsung Smart Phones");
		blog.setDescription("There are many various types of smasung smartphones");
		blog.setStatus("Approved");
		blog.setUser_id("VirusMickey");
		blog.setDate_added(today);
		boolean flag = blogDAO.updateBlog(blog);
		assertEquals("updateBlogTest", true, flag);
	}
	//@Test
	public void deleteBlogTest()
	{
		blog = blogDAO.getBlogById(1);
		boolean flag = blogDAO.deletBlog(blog);
		assertEquals("deleteBlogTest", true, flag);
	}
}
