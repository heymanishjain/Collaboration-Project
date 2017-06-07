app.controller('JobController',['$scope','JobService','$location','$rootScope','$cookieStore','$http',
	function($scope, JobService, $location, $rootScope,$cookieStore, $http)
	{
		console.log("Starting Of JobController!")
		this.job = {
						id : '',
						title : '',
						description : '',
						salary : '',
						post : '',
						company_name : '',
						user_id:'',
						status:'',
					};
		this.jobs=[];
		this.postJob = function(job) {
		console.log("postJob!")
		JobService.postJob(job)
		.then(
				function(d) 
				{
					this.job=d;
					if(this.job.errorCode==200)
					{
						JobService.fetchAllAvailableJobs()
						.then(
								function(d)
								{
									this.jobs=d;
									$rootScope.jobs=d;
									console.log(this.jobs)
								}
						)
						alert("Thank You Job Posted Successfully!!!")
						$location.path("/displayJobs")
					}
					else if(this.job.errorCode==400)
					{
						alert("User Not Logged In Please Log In First To Post A Job")
						$location.path("/login")
					}
					else if(this.job.errorCode==404)
					{
						alert("Error Posting Job Please Try Again")
						$location.path("/")
					}
				},
				function(errResponse) 
				{
						console.error('Error while Posting Job.');
			});
		};
		this.submit = function() {
			{
				console.log('Posting new Job', this.job);
				this.postJob(this.job);
			}
		};
		this.fetchAllAvailableJobs = function() {
			console.log("fetchAllAvailableJobs!")
			JobService.fetchAllAvailableJobs()
			.then(
					function(d)
					{
						this.jobs=d;
						if(this.jobs.length==0)
						{
							alert("There Are No Jobss To Display")
						}
						$rootScope.jobs=d;
						console.log(this.jobs)
						alert("Thank You Jobs Fetched Successfully!!!")
						$location.path('/displayJobs')
					},
					function(errResponse) 
					{
							console.error('Error while Fetching Available Jobs.');
				});
			};
		this.displayAvailableJobs = function() {
			{
				console.log('Display All Available Jobs');
				this.fetchAllAvailableJobs();
			}
		};
		this.deleteJob = function(job) {
			console.log("deleteJob!")
			JobService.deleteJob(job)
			.then(
					function(d) 
					{
						this.job=d;
						if(this.job.errorCode==200)
						{
							JobService.fetchAllAvailableJobs()
							.then(
									function(d)
									{
										this.jobs=d;
										$rootScope.jobs=d;
										console.log(this.jobs)
									}
							)
							alert("Thank You Job Deleted Successfully!!!")
							$location.path("/displayJobs")
						}
						else if(this.job.errorCode==400)
						{
							alert("User Not Logged In Please Log In First To Delete Job")
							$location.path("/login")
						}
						else if(this.job.errorCode==404)
						{
							alert("No Such Job Exists")
							$location.path("/")
						}
						else if(this.job.errorCode==500)
						{
							alert("This Job Is Not Created By You So You Cannot Delete This Job")
							
							$location.path("/")
						}
						
					},
					function(errResponse)
					{
							console.error('Error while deleting Job.');
				});
			};
		this.remove = function(job) {
			{
				this.job.id=job;
				console.log('Deleting Job', this.job.id);
				this.deleteJob(this.job.id);
			}
		};
		this.update = function(job) {
			this.job.id=job;
			console.log('Update Blog', this.job.id);
			JobService.fetchJobById(job)
			.then(
				function(d) 
				{
					this.job=d;
					$rootScope.job=d;
					console.log(this.job)
					$location.path('/editJob')
				},
				function(errResponse) 
				{
						console.error('Error while Fetching Job.');
			});
		}
		this.updateJob = function(job) {
			console.log("updateJob!")
			this.job.id=$rootScope.job.id
			JobService.updateJob(job)
			.then(
					function(d) 
					{
						this.job=d;
						if(this.job.errorCode==200)
						{
							JobService.fetchAllAvailableJobs()
							.then(
									function(d)
									{
										this.jobs=d;
										$rootScope.jobs=d;
										console.log(this.jobs)
									}
							)
							alert("Thank You Job Updated Successfully!!!")
							$location.path("/displayJobs")
						}
						else if(this.job.errorCode==400)
						{
							alert("User Not Logged In Please Log In First To Update Job")
							$location.path("/login")
						}
						else if(this.job.errorCode==404)
						{
							alert("Error Updating Job Please Try Again")
							$location.path("/editJob")
						}
						else if(this.job.errorCode==500)
						{
							alert("This Job Is Not Created By You So You Cannot Update This Job")
							$location.path("/")
						}
						
					},
					function(errResponse) 
					{
							console.error('Error while updating Job.');
				});
			};
		this.edit = function(){
			console.log('Updating Posted Job', $rootScope.job);
			this.updateJob($rootScope.job)
		}
		this.updateStatus = function(job) {
			this.job.id=job;
			console.log('Update Blog', this.job.id);
			JobService.updateStatus(this.job)
			.then(
				function(d) 
				{
					JobService.fetchAllAvailableJobs()
					.then(
							function(d)
							{
								this.jobs=d;
								$rootScope.jobs=d;
								console.log(this.jobs)
							}
					)
					$location.path('/displayJobs')
				},
				function(errResponse) 
				{
						console.error('Error while Updating Job Status.');
			});
		}
} ]);