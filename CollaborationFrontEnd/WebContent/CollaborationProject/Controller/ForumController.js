app.controller('ForumController',['$scope','ForumService','CommentService','$location','$rootScope','$cookieStore','$http',
	function($scope, ForumService, CommentService, $location, $rootScope,$cookieStore, $http)
	{
		console.log("Starting Of ForumController!")
		this.forum = {
						id : '',
						topic : '',
						userid : '',
						date_added : '',
					};
		this.forums=[];
		this.comments=[];
		this.createForum = function(forum) {
		console.log("createForum!")
		ForumService.createForum(forum)
		.then(
				function(d) 
				{
					this.forum=d;
					if(this.forum.errorCode==200)
					{
						alert("Thank You forum Created Successfully!!!")
						$location.path("/")
					}
					else if(this.forum.errorCode==400)
					{
						alert("User Not Logged In Please Log In First To Create Forum")
						$location.path("/login")
					}
					else if(this.forum.errorCode==404)
					{
						alert("Error Creating Forum Please Try Again")
						$location.path("/")
					}
				},
				function(errResponse) 
				{
						console.error('Error while creating Forum.');
			});
		};
		this.fetchAllForums = function() {
			console.log("fetchAllForums!")
			ForumService.fetchAllForums()
			.then(
					function(d) 
					{
						this.forums=d;
						if(this.forums.length==0)
						{
							alert("There Are No Forums To Display")
						}
						$rootScope.forums=d;
						console.log(this.forums)
						CommentService.fetchAllComments()
							.then(
									function(d){
										this.comments=d;
										$rootScope.comments=d;
									}
							);
						alert("Thank You Forums Fetched Successfully!!!")
						$location.path('/displayForum')
					},
					function(errResponse) 
					{
							console.error('Error while Fetching Forums.');
				});
			};
			this.deleteForum = function(forum) {
				console.log("deleteForum!")
				ForumService.deleteForum(forum)
				.then(
						function(d) 
						{
							this.forum=d;
							if(this.forum.errorCode==200)
							{
								alert("Thank You Forum Deleted Successfully!!!")
							}
							else if(this.forum.errorCode==400)
							{
								alert("User Not Logged In Please Log In First To Delete Forum")
							}
							else if(this.forum.errorCode==404)
							{
								alert("No Such Forum Exists")
							}
							else if(this.forum.errorCode==500)
							{
								alert("This Forum Is Not Created By You So You Cannot Delete This Forum")
							}
							
						},
						function(errResponse)
						{
								console.error('Error while deleting Forum.');
					});
				};
			this.updateForum= function(forum) {
				console.log("updateForum!")
				this.forum.id=$rootScope.forum.id
				ForumService.updateForum(forum)
				.then(
						function(d) 
						{
							this.forum=d;
							if(this.forum.errorCode==200)
							{
								alert("Thank You Forum Updated Successfully!!!")
								$location.path("/")
							}
							else if(this.forum.errorCode==400)
							{
								alert("User Not Logged In Please Log In First To Update Forum")
								$location.path("/login")
							}
							else if(this.forum.errorCode==404)
							{
								alert("Error Updating Forum Please Try Again")
								$location.path("/editForum")
							}
							else if(this.forum.errorCode==500)
							{
								alert("This Forum Is Not Created By You So You Cannot Update This Forum")
								$location.path("/")
							}
							
						},
						function(errResponse) 
						{
								console.error('Error while updating forum.');
					});
				};
		this.submit = function() {
			{
				console.log('Creating New Forum', this.forum);
				this.createForum(this.forum);
			}
		};
		this.update = function(forum) {
			this.forum.id=forum;
			console.log('Update Forum', this.forum.id);
			ForumService.fetchForumById(forum)
			.then(
				function(d) 
				{
					this.forum=d;
					$rootScope.forum=d;
					console.log(this.forum)
					$location.path('/editForum')
				},
				function(errResponse) 
				{
						console.error('Error while Fetching Froum.');
			});
		}
		this.edit = function(){
			console.log('Updating Forum', this.forum);
			this.updateForum(this.forum)
		}
		this.display = function() {
			{
				console.log('Display All Forums');
				this.fetchAllForums();
			}
		};
		this.remove = function(forum) {
			{
				this.forum.id=forum;
				console.log('Deleting Forum', this.forum.id);
				this.deleteForum(this.forum.id);
			}
		};
} ]);