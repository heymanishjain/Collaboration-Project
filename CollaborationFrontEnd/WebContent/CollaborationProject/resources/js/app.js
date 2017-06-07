var app = angular.module('myApp', ['ngRoute','ngCookies']);
/*angular.module("chatApp", [
                           "chatApp.controllers",
                           "chatApp.services"
                         ]);

                         angular.module("chatApp.controllers", []);
                         angular.module("chatApp.services", []);*/

                         
app.config(function($routeProvider) {
    $routeProvider
    .when('/', {
        templateUrl : 'CollaborationProject/Common/Home.html'
    })
    .when('/login', {
        templateUrl : 'CollaborationProject/User/Login.html',
        controller : 'UserController'
    })
    .when('/fetchUsers', {
        templateUrl : 'CollaborationProject/User/DisplayUsers.html',
        controller : 'UserController'
    })
    .when('/friends', {
        templateUrl : 'CollaborationProject/Friends/Friends.html',
        controller : 'FriendController'
    })
    .when('/friendRequest', {
        templateUrl : 'CollaborationProject/Friends/FriendRequest.html',
        controller : 'FriendController'
    })
    .when('/register', {
    	templateUrl : 'CollaborationProject/User/Register.html',
		controller : 'UserController'
	})
	.when('/myProfile', {
        templateUrl : 'CollaborationProject/User/MyProfile.html',
        controller : 'UserController'
    })
    .when('/editUser', {
        templateUrl : 'CollaborationProject/User/EditUser.html',
        controller : 'UserController'
    })
    .when('/admin', {
        templateUrl : 'CollaborationProject/Admin/Admin.html'
    })
    .when('/adminManageBlogs', {
        templateUrl : 'CollaborationProject/Admin/ManageBlogs.html',
        controller:'BlogController',
    })
	.when('/createBlog', {
    	templateUrl : 'CollaborationProject/Blog/CreateBlog.html',
		controller : 'BlogController'
	})
	.when('/editBlog', {
    	templateUrl : 'CollaborationProject/Blog/EditBlog.html',
		controller : 'BlogController'
	})
	.when('/displayBlog', {
    	templateUrl : 'CollaborationProject/Blog/DisplayBlog.html',
		controller : 'BlogController'
	})
	.when('/displayBlogById', {
    	templateUrl : 'CollaborationProject/Blog/DisplayBlogById.html',
		controller : 'BlogController'
	})
	.when('/createForum', {
    	templateUrl : 'CollaborationProject/Forum/AddForum.html',
		controller : 'ForumController'
	})
	.when('/editForum', {
    	templateUrl : 'CollaborationProject/Forum/EditForum.html',
		controller : 'ForumController'
	})
	.when('/displayForum', {
    	templateUrl : 'CollaborationProject/Forum/DisplayAllForums.html',
		controller : 'ForumController'
	})
	.when('/addComment', {
		templateUrl : 'CollaborationProject/Comments/AddComment.html',
		controller : 'CommentController',
	})
	.when('/chat', {
		templateUrl : 'CollaborationProject/Chat/ChatGuestLogin.html',
		controller : 'ChatController',
	})
	.when('/chatOn', {
		templateUrl : 'CollaborationProject/Chat/Chat.html',
		controller : 'ChatController',
	})
	.when('/displayJobs', {
        templateUrl : 'CollaborationProject/Job/Job.html',
        controller : 'JobController'
    })
    .when('/displayJobApplications', {
        templateUrl : 'CollaborationProject/Job/JobApplications.html',
        controller : 'JobApplicationController'
    })
    .when('/postAJob', {
        templateUrl : 'CollaborationProject/Job/PostJob.html',
        controller : 'JobController'
    })
    .when('/editJob', {
        templateUrl : 'CollaborationProject/Job/EditJob.html',
        controller : 'JobController'
    })
    .when('/applyJob', {
        templateUrl : 'CollaborationProject/Job/ApplyForJob.html',
        controller : 'JobController'
    })
	.otherwise({
		redirectTo : '/'
	});
});