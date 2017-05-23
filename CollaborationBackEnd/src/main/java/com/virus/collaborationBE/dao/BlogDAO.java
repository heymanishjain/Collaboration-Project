package com.virus.collaborationBE.dao;

import java.util.List;

import com.virus.collaborationBE.model.Blog;

public interface BlogDAO {

		public List<Blog> getAllUsersBlog();//To Show Complete List Of Blog To Admin
		
		public List<Blog> getApprovedBlogList();//To Show Only List of Blogs That Are Approved By Admin
		
		public List<Blog> getPendingBlogList();//To Show Only List of Blogs That Are Pending To Be Approved By Admin
		
		public List<Blog> getRejectedBlogList();//To Show Only List of Blogs That Are Rejected By Admin
		
		public List<Blog> getBlogByUserId(String userid);//To Get The Blogs Of Particular User

		public Blog getBlogById(int id);//To Get A Particular Blog By Its Blog ID
		
		public boolean saveBlog(Blog blog);
		
		public boolean updateBlog(Blog blog);
		
		public boolean deletBlog(Blog blog);
}
