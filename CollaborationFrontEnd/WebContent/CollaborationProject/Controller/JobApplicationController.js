app.controller('JobApplicationController',['$scope','JobApplicationService','$location','$rootScope','$cookieStore','$http',
	function($scope, JobApplicationService, $location, $rootScope,$cookieStore, $http)
	{
		console.log("Starting Of JobApplicationController!")
		this.jobApplication = {
						id : '',
						firstname : '',
						lastname : '',
						experience : '',
						qualification : '',
						email : '',
						userid:'',
						jobid:'',
						contact:'',
						marks10th:'',
						marks12th:'',
						ugmarks:'',
					};
		this.jobid='';
		this.jobApplications=[];
		this.applyJob = function(jobApplication) {
		console.log("applyJob!")
		this.jobApplication.jobid=$rootScope.jobid;
		console.log(this.jobApplication.jobid)
		JobApplicationService.applyJob(jobApplication)
		.then(
				function(d) 
				{
					this.jobApplication=d;
					if(this.jobApplication.errorCode==200)
					{
						alert("Thank You Job Applied Successfully!!!")
						$location.path("/")
					}
					else if(this.jobApplication.errorCode==400)
					{
						alert("User Not Logged In Please Log In First To Apply For A Job")
						$location.path("/login")
					}
					else if(this.jobApplication.errorCode==404)
					{
						alert("Error Applying Job Please Try Again")
						$location.path("/")
					}
					$rootScope.jobid='';
				},
				function(errResponse) 
				{
						console.error('Error while Applying Job.');
			});
		};
		this.submit = function() {
			{
				console.log('Applying for Job', this.jobApplication);
				this.applyJob(this.jobApplication);
			}
		};
		this.apply = function(jobid){
			{
				this.jobApplication.jobid = jobid;
				$rootScope.jobid = this.jobApplication.jobid;
				console.log(this.jobApplication.jobid)
			}
		};
		this.fetchAllJobApplicationsByJobId = function(jobApplication) {
			console.log("fetchAllJobApplicationsByJobId!")
			JobApplicationService.fetchJobApplicationsByJobId(jobApplication)
			.then(
					function(d)
					{
						this.jobApplications=d;
						console.log(this.jobApplications)
						if(this.jobApplications.length==0)
						{
							alert("There Are No Jobs Applications To Display")
						}
						$rootScope.jobApplications=d;
						console.log(this.jobApplications)
						alert("Thank You JobApplications Fetched Successfully!!!")
						$location.path('/displayJobApplications')
					},
					function(errResponse) 
					{
							console.error('Error while Fetching JobApplications.');
				});
			};
		this.displayByJobId = function(jobApplication) {
			{
				console.log('Display All Job Applications By Job Id');
				this.fetchAllJobApplicationsByJobId(jobApplication);
			}
		};
} ]);