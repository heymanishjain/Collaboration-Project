app.controller('BlogController',['$scope','BlogService','CommentService','$location','$rootScope','$cookieStore','$http',
	function($scope, BlogService, CommentService, $location, $rootScope,$cookieStore, $http)
	{
		console.log("Starting Of BlogController!")
		this.blog = {
						id : '',
						title : '',
						description : '',
						status : '',
						user_id : '',
						comments : '',
						date_added : '',
					};
		this.blogs=[];
		this.comments=[];
		this.createBlog = function(blog) {
		console.log("createBlog!")
		BlogService.createBlog(blog)
		.then(
				function(d) 
				{
					this.blog=d;
					if(this.blog.errorCode==200)
					{
						alert("Thank You Blog Created Successfully!!!")
					}
					else if(this.blog.errorCode==400)
					{
						alert("User Not Logged In Please Log In First To Create Blog")
					}
					else if(this.blog.errorCode==404)
					{
						alert("Error Creating Blog Please Try Again")
					}
					$location.path("/")
				},
				function(errResponse) 
				{
						console.error('Error while creating Blog.');
			});
		};
		this.updateBlog = function(blog) {
			console.log("updateBlog!")
			this.blog.id=$rootScope.blog.id
			BlogService.updateBlog(blog)
			.then(
					function(d) 
					{
						this.blog=d;
						if(this.blog.errorCode==200)
						{
							alert("Thank You Blog Updated Successfully!!!")
							$location.path("/")
						}
						else if(this.blog.errorCode==400)
						{
							alert("User Not Logged In Please Log In First To Update Blog")
							$location.path("/login")
						}
						else if(this.blog.errorCode==404)
						{
							alert("Error Updating Blog Please Try Again")
							$location.path("/editBlog")
						}
						else if(this.blog.errorCode==500)
						{
							alert("This Blog Is Not Created By You So You Cannot Update This Blog")
							$location.path("/")
						}
						
					},
					function(errResponse) 
					{
							console.error('Error while updating Blog.');
				});
			};
		this.fetchBlogById = function(blog) {
			console.log("fetchBlogById!")
			BlogService.fetchBlogById(blog)
			.then(
					function(d) 
					{
						this.blog=d;
						$rootScope.blog=d;
						console.log(this.blog)
						alert("Thank You Blog Fetched Successfully!!!")
						$location.path("/displayBlogById")
					},
					function(errResponse) 
					{
							console.error('Error while fetching Blog.');
				});
			};
		this.fetchAllBlogs = function() {
			console.log("fetchAllBlogs!")
			BlogService.fetchAllBlogs()
			.then(
					function(d) 
					{
						this.blogs=d;
						if(this.blogs.length==0)
						{
							alert("There Are No Blogs To Display")
						}
						$rootScope.blogs=d;
						console.log(this.blogs)
						CommentService.fetchAllComments()
							.then(
									function(d){
										this.comments=d;
										$rootScope.comments=d;
									}
							);
						alert("Thank You Blogs Fetched Successfully!!!")
						$location.path('/displayBlog')
					},
					function(errResponse) 
					{
							console.error('Error while Fetching Blogs.');
				});
			};
			this.fetchAllPendingBlogs = function() {
				console.log("fetchAllPendingBlogs!")
				BlogService.fetchAllPendingBlogs()
				.then(
						function(d)
						{
							this.blogs=d;
							if(this.blogs.length==0)
							{
								alert("There Are No Blogs To Display")
							}
							$rootScope.blogs=d;
							console.log(this.blogs)
							alert("Thank You Blogs Fetched Successfully!!!")
							$location.path('/adminManageBlogs')
						},
						function(errResponse) 
						{
								console.error('Error while Fetching Blogs.');
					});
				};
			this.display = function() {
				{
					console.log('Display All Blog');
					this.fetchAllBlogs();
				}
			};
			this.displayPendingBlogs = function() {
				{
					console.log('Display All Pending Blogs');
					this.fetchAllPendingBlogs();
				}
			};
			this.deleteBlog = function(blog) {
				console.log("deleteBlog!")
				BlogService.deleteBlog(blog)
				.then(
						function(d) 
						{
							this.blog=d;
							if(this.blog.errorCode==200)
							{
								alert("Thank You Blog Deleted Successfully!!!")
							}
							else if(this.blog.errorCode==400)
							{
								alert("User Not Logged In Please Log In First To Delete Blog")
							}
							else if(this.blog.errorCode==404)
							{
								alert("No Such Blog Exists")
							}
							else if(this.blog.errorCode==500)
							{
								alert("This Blog Is Not Created By You So You Cannot Delete This Blog")
							}
							
						},
						function(errResponse)
						{
								console.error('Error while deleting Blog.');
					});
				};
		this.submit = function() {
			{
				console.log('Creating New Blog', this.blog);
				this.createBlog(this.blog);
			}
		};
		this.update = function(blog) {
				this.blog.id=blog;
				console.log('Update Blog', this.blog.id);
				BlogService.fetchBlogById(blog)
				.then(
					function(d) 
					{
						this.blog=d;
						$rootScope.blog=d;
						console.log(this.blog)
						$location.path('/editBlog')
					},
					function(errResponse) 
					{
							console.error('Error while Fetching Blogs.');
				});
			}
		this.approve = function(blog) {
			this.blog.id=blog;
			console.log('Update Blog Status To Approve', this.blog.id);
			BlogService.approveBlog(blog)
			.then(
				function(d) 
				{
					this.blog=d;
					if(this.blog.errorCode==200)
					{
						alert("Thank You Blog Status Updated To Approved Successfully!!!")
						$location.path('/admin')
					}
					else if(this.blog.errorCode==400)
					{
						alert("User Not Logged In Please Log In First To Change Blog Status")
						$location.path('/login')
					}
					else if(this.blog.errorCode==404)
					{
						alert("No Such Blog Exists")
						$location.path('/admin')
					}
					else if(this.blog.errorCode==500)
					{
						alert("This Blog's Status Cannot Be Changed By You Because These Rights Are Given Only To Admin")
						$location.path('/')
					}
					$rootScope.blog=d;
					console.log(this.blog)
				},
				function(errResponse) 
				{
						console.error('Error while Approving Blog.');
			});
		}
		this.reject = function(blog) {
			this.blog.id=blog;
			console.log('Update Blog Status To Reject', this.blog.id);
			BlogService.rejectBlog(blog)
			.then(
				function(d) 
				{
					this.blog=d;
					if(this.blog.errorCode==200)
					{
						alert("Thank You Blog Status Updated To Rejected Successfully!!!")
						$location.path('/admin')
					}
					else if(this.blog.errorCode==400)
					{
						alert("User Not Logged In Please Log In First To Change Blog Status")
						$location.path('/login')
					}
					else if(this.blog.errorCode==404)
					{
						alert("No Such Blog Exists")
						$location.path('/admin')
					}
					else if(this.blog.errorCode==500)
					{
						alert("This Blog's Status Cannot Be Changed By You Because These Rights Are Given Only To Admin")
						$location.path('/')
					}
					$rootScope.blog=d;
					console.log(this.blog)
				},
				function(errResponse) 
				{
						console.error('Error while Rejecting Blog.');
			});
		}
		this.edit = function(){
			console.log('Updating Blog', this.blog);
			this.updateBlog(this.blog)
		}
		this.displaybyid = function(blog) {
			{
				console.log('Display FetchBlogById',this.blog.id);
				this.fetchBlogById(this.blog.id);
			}
		};
		this.remove = function(blog) {
			{
				this.blog.id=blog;
				console.log('Deleting Blog', this.blog.id);
				this.deleteBlog(this.blog.id);
			}
		};
} ]);