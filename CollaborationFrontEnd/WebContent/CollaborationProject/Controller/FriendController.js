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
		this.fetchAllFriends = function() {
			console.log("fetchAllFriends!")
			FriendService.fetchAllFriends()
			.then(
					function(d) 
					{
						this.friends=d;
						$rootScope.friends=d;
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
		this.addFriend = function(friend) {
			console.log("addFriend!")
			FriendService.addFriend(friend)
			.then(
					function(d) 
					{
						if(d.errorCode==200)
						{
							alert("Friend Added Successfully!!!")
							$location.path("/friends")
						}
						else if(d.errorCode==404)
						{
							alert("Error Adding Friend Please Try Again!!!")
							$location.path("/friends")
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
														
} ]);