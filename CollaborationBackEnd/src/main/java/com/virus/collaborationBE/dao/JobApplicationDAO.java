package com.virus.collaborationBE.dao;

import java.util.List;

import com.virus.collaborationBE.model.JobApplication;

public interface JobApplicationDAO {

	public List<JobApplication> fetchAllJobApplications();
	
	public List<JobApplication> fetchAllJobApplicationsByJobID(int jobid);
	
	public JobApplication fetchJobApplicationsByID(int id);
	
	public List<JobApplication> fetchAllJobApplicationsByUserID(String userid);
	
	public boolean saveJobApplication(JobApplication jobApplication);
	
	public boolean updateJobApplication(JobApplication jobApplication);
	
	public boolean deleteJobApplication(JobApplication jobApplication);
	
}
