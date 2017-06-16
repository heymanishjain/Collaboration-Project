app.controller('CommentController',['$scope','CommentService','$location','$rootScope','$cookieStore','$http',
	function($scope, CommentService, $location, $rootScope,$cookieStore, $http)
	{
		console.log("Starting Of CommentController!")
		this.comment = {
						id : '',
						commentsmsg : '',
						blogid : '',
						forumid : '',
						userid : '',
						dateadded : '',
					};
		this.comments=[];
		this.blogid='';
		this.commentid='';
		this.forumid='';
		this.createComment = function(comment) {
		console.log("createComment!")
		comment.blogid=$rootScope.blogid
		comment.forumid=$rootScope.forumid
		CommentService.createComment(comment)
		.then(
				function(d) 
				{
					this.comment=d;
					if(this.comment.errorCode==200)
					{
						alert("Thank You Comment Created Successfully!!!")
						$location.path("/")
					}
					else if(this.comment.errorCode==400)
					{
						alert("User Not Logged In Please Log In First To Create Comment")
						$location.path("/login")
					}
					else if(this.comment.errorCode==404)
					{
						alert("Error Creating Comment Please Try Again")
						$location.path("/")
					}
					$rootScope.blogid='';
					$rootScope.forumid='';
				},
				function(errResponse) 
				{
						console.error('Error while creating Comment.');
			});
		};
		this.fetchCommentsByBlogId = function(blogid){
			console.log("fetchCommentsByBlogId!!!")
			CommentService.fetchAllCommentsByBlog(blogid)
			.then(
					function(d){
						this.comments=d;
						$rootScope.comments=d;
					}
			)
		}
		this.submit = function() {
			{
				console.log('Creating New comment', this.comment);
				this.createComment(this.comment);
			}
		};
		this.deleteComment = function(commentid) {
			console.log("deleteComment!")
			CommentService.deleteComment(commentid)
			.then(
					function(d) 
					{
						this.comment=d;
						if(this.comment.errorCode==200)
						{
							alert("Thank You Comment Deleted Successfully!!!")
							$location.path("/")
						}
						else if(this.comment.errorCode==400)
						{
							alert("User Not Logged In Please Log In First To Delete Comment")
							$location.path("/login")
						}
						else if(this.comment.errorCode==404)
						{
							alert("No Such Comment Exists")
							$location.path("/")
						}
						else if(this.comment.errorCode==405)
						{
							alert("Error Deleting Comment")
							$location.path("/")
						}
						else if(this.comment.errorCode==500)
						{
							alert("This Comment Is Not Created By You So You Cannot Delete This Comment")
							$location.path("/")
						}
						
					},
					function(errResponse)
					{
							console.error('Error while deleting Comment.');
				});
			};
		this.remove = function(commentid) {
			{
				console.log('Deleting comment', commentid);
				this.deleteComment(commentid);
			}
		};
		this.add = function(blogid) {
			{
				this.comment.blogid=blogid
				console.log('Adding New comment', this.comment.blogid);
				$rootScope.blogid=blogid
			}
		};
		this.addForum = function(forumid) {
			{
				this.comment.forumid=forumid
				console.log('Adding New comment', this.comment.forumid);
				$rootScope.forumid=forumid
			}
		};
		this.displayCommentsByBlogId = function(blogid){
			{
				this.blogid=blogid;
				console.log('Display Comment By Blog Id',this.blogid);
				this.fetchCommentsByBlogId(this.blogid);
			}
		};
		this.displayCommentsByForumId = function(forumid){
			{
				this.forumid=forumid;
				console.log('Display Comment By Forum Id',this.forumid);
				this.fetchCommentsByForumId(this.forumid);
			}
		};
} ]);