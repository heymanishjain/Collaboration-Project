app.controller('UserController',['$scope','UserService','$location','$cookies','$rootScope','$cookieStore','$http',
	function($scope, UserService, $location, $cookies, $rootScope,$cookieStore, $http)
	{
		console.log("Starting Of UserController!")
		this.user = {
						id : '',
						name : '',
						password : '',
						isonline:'',
						confirmpassword : '',
						mobile : '',
						address : '',
						mail : '',
						role : '',
						errorCode : '',
						errorMessage : ''
					};
		this.currentuser = {
				id : '',
				name : '',
				password : '',
				isonline:'',
				confirmpassword : '',
				mobile : '',
				address : '',
				mail : '',
				role : '',
				errorCode : '',
				errorMessage : ''
			};
		this.users=[];
		this.createUser = function(user) {
		console.log("createUser!")
		UserService.createUser(user)
		.then(
				function(d) 
				{
					if(d.errorCode==410)
					{
						alert("Password & Confirm Password Not Matching")
						$location.path("/register")
					}
					if(d.errorCode==200)
					{
						alert("Thank You User Registered Successfully!!!")
						$location.path("/")
					}
					else if(d.errorCode==404)
					{
						alert("Error Registering User Please Try Again!!!")
						$location.path("/register")
					}
				},
				function(errResponse) 
				{
						console.error('Error while creating User.');
			});
		};
		this.updateUser = function(currentuser) {
			console.log("updateUser!")
			UserService.updateUser(currentuser)
			.then(
					function(d) 
					{
						if(d.errorCode==200)
						{
							alert("Thank You User Updated Successfully!!!")
							$location.path("/")
						}
						else if(d.errorCode==404)
						{
							alert("No Such User Exists!!!")
							$location.path("/")
						}
						else if(d.errorCode==400)
						{
							alert("Please Log In First To Edit Details")
							$location.path("/login")
						}
					},
					function(errResponse) 
					{
							console.error('Error while updating User.');
				});
			};
		this.validateUser = function(user) {
			console.log("validateUser!")
			UserService.validateUser(user)
			.then(
					function(d)
					{
						this.user = d;
						if(this.user.errorCode == 200)
						{
							if(this.user.role == "Admin")
							{
								alert("Youre Admin")
								$location.path("/admin")
							}
							else
							{
								$location.path("/")
							}
							$rootScope.currentuser = d;
							console.log(this.user)
							$cookieStore.put('currentuser',this.user)
							alert("Logged In Successfully!!!!")
						}
						else
						{
							this.user.id = "";
							this.user.password = "";
							alert("Invalid Credentials Please Try Again")
							$location.path("/login")
						}
					},
					function(errResponse)
					{
						alert("Invalid Credentials Please Try Again")
						$location.path("/login")
						console.error('User Not Logged In Invalid Credentials')
					}
			);
		};
		this.fetchAllUsers = function() {
			console.log("fetchAllUsers!")
			UserService.fetchAllUsers()
			.then(
					function(d) 
					{
						this.users=d;
						$rootScope.users=d;
						console.log(this.users)
						console.log($rootScope.friends)
						$location.path("/fetchUsers")
					},
					function(errResponse) 
					{
							console.error('Error while fetching User.');
				});
			};
		this.fetch = function() {
			{
				console.log('Fetching All User');
				this.fetchAllUsers();
			}
		};
		this.submit = function() {
									{
										console.log('Creating New User', this.user);
										this.createUser(this.user);
									}
								};
		this.update = function() {
									{
										console.log('Updating New User', $rootScope.currentuser);
										console.log($rootScope.currentuser)
										this.updateUser($rootScope.currentuser);
									}
								};
		this.login = function() {
									{
										console.log('Validating User Login', this.user);
										this.validateUser(this.user);
									}
								};
		this.logout = function() {
									{
										console.log('User Logout Function');
										console.log("logout function called")
										$rootScope.currentuser = {};
										$cookieStore.remove('currentuser');
										UserService.userLogout()
										.then(
												function(d)
												{
													$location.path("/");
												}
										)
									}
								};														
} ]);