package com.virus.collaborationBE.dao;

import java.util.List;

import com.virus.collaborationBE.model.Job;

public interface JobDAO {

	public List<Job> getAllJobs();
	
	public boolean saveJob(Job job);
	
	public boolean updateJob(Job job);
	
	public boolean deleteJob(Job job);
	
	public Job getJobById(int id);
	
	public List<Job> getJobsByUserid(String userid);
	
	public List<Job> getAvailableJobs();
}
