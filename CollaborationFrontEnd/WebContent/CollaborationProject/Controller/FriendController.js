app.controller('FriendController',['$scope','FriendService','$location','$cookies','$rootScope','$cookieStore','$http',
	function($scope, FriendService, $location, $cookies, $rootScope,$cookieStore, $http)
	{
		console.log("Starting Of FriendController!")
		this.friend = {
			id:'',
			userid:'',
			friendid:'',
		};
		this.friendid='';
		this.friends=[];
		this.length='';
		this.fetchAllFriends = function() {
			console.log("fetchAllFriends!")
			FriendService.fetchAllFriends()
			.then(
					function(d) 
					{
						this.friends=d;
						$rootScope.friends=d;
						this.length=friends.length;
						$rootScope.length=friends.length;
						console.log("Length"+$rootScope.length)
						console.log(this.friends)
						$location.path("/friends")
					},
					function(errResponse) 
					{
							console.error('Error while fetching friends.');
				});
			};
		this.fetch = function() {
			{
				console.log('Fetching All Friends');
				this.fetchAllFriends();
			}
		};
		this.fetchAllPendingFriends = function() {
			console.log("fetchAllPendingFriends!")
			FriendService.fetchAllPendingFriends()
			.then(
					function(d) 
					{
						this.friends=d;
						$rootScope.friends=d;
						this.length=friends.length;
						$rootScope.length=friends.length;
						console.log("Length"+$rootScope.length)
						console.log(this.friends)
						$location.path("/friendRequest")
					},
					function(errResponse) 
					{
							console.error('Error while fetching friends.');
				});
			};
		this.fetchPendingFriends = function() {
			{
				console.log('Fetching All Pending Friends');
				this.fetchAllPendingFriends();
			}
		};
		this.addFriend = function(friend) {
			console.log("addFriend!")
			FriendService.addFriend(friend)
			.then(
					function(d) 
					{
						if(d.errorCode==200)
						{
							alert("Friend Request Sent!!!")
							$location.path("/")
						}
						else if(d.errorCode==404)
						{
							alert("Error Adding Friend Please Try Again!!!")
							$location.path("/")
						}
					},
					function(errResponse) 
					{
							console.error('Error while adding friend.');
				});
			};
		this.submit = function(friendid) {
			{
				this.friend.friendid = friendid;
				console.log('Adding New Friend', this.friend);
				this.addFriend(this.friend);
			}
		};
		this.deleteFriend = function(friend) {
			console.log("deleteFriend!")
			FriendService.deleteFriend(friend)
			.then(
					function(d) 
					{
						this.friend=d;
						if(this.friend.errorCode==200)
						{
							alert("Thank You Friend Deleted Successfully!!!")
							$location.path("/")
						}
						else if(this.friend.errorCode==400)
						{
							alert("User Not Logged In Please Log In First To Delete Friend")
							$location.path("/login")
						}
						else if(this.friend.errorCode==404)
						{
							alert("No Such Friend Exists")
							$location.path("/")
						}
					},
					function(errResponse)
					{
							console.error('Error while deleting Friend.');
				});
			};
			this.remove = function(friend) {
				{
					this.friend.id=friend;
					console.log('Deleting Friend', this.friend.id);
					this.deleteFriend(this.friend.id);
				}
			};
			this.approveFriend = function(friend) {
				console.log("approveFriend!")
				FriendService.approveFriend(friend)
				.then(
						function(d) 
						{
							if(d.errorCode==200)
							{
								alert("Friend Request Approved!!!")
								$location.path("/")
							}
							else if(d.errorCode==400)
							{
								alert("User Not Logged In Please Log In")
								$location.path("/login")
							}
							else if(d.errorCode==404)
							{
								alert("Error Adding Friend Please Try Again!!!")
								$location.path("/")
							}
						},
						function(errResponse) 
						{
								console.error('Error while adding friend.');
					});
				};
			this.approve = function(friend) {
				{
					this.friend.id=friend;
					console.log('Approving Friend', this.friend);
					this.approveFriend(this.friend);
				}
			};
			this.rejectFriend = function(friend) {
				console.log("rejectFriend!")
				FriendService.rejectFriend(friend)
				.then(
						function(d) 
						{
							if(d.errorCode==200)
							{
								alert("Friend Rejected Successfully!!!")
								$location.path("/")
							}
							else if(d.errorCode==400)
							{
								alert("User Not Logged In Please Log In")
								$location.path("/login")
							}
							else if(d.errorCode==404)
							{
								alert("Error Rejecting Friend Please Try Again!!!")
								$location.path("/")
							}
						},
						function(errResponse) 
						{
								console.error('Error while rejecting friend.');
					});
				};
			this.reject = function(friend) {
				{
					this.friend.id=friend;
					console.log('Approving Friend', this.friend);
					this.rejectFriend(this.friend);
				}
			};
														
} ]);