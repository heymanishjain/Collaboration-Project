app.controller('EventController',['$scope','EventService','$location','$rootScope','$cookieStore','$http',
	function($scope, EventService, $location, $rootScope,$cookieStore, $http)
	{
		console.log("Starting Of EventController!")
		this.event = {
						id : '',
						title : '',
						description : '',
						eventdate : '',
						user_id:'',
						status:'',
					};
		this.eventsOpen=[];
		this.eventsClosed=[];		
		this.eventsOngoing=[];
		this.postEvent = function(event) {
		console.log("postEvent!")
		EventService.postEvent(event)
		.then(
				function(d) 
				{
					this.event=d;
					if(this.event.errorCode==200)
					{
						this.display();
						alert("Thank You Event Posted Successfully!!!")
						$location.path("/displayEvents")
					}
					else if(this.event.errorCode==400)
					{
						alert("User Not Logged In Please Log In First To Post A Event")
						$location.path("/login")
					}
					else if(this.event.errorCode==404)
					{
						alert("Error Posting Event Please Try Again")
						$location.path("/")
					}
				},
				function(errResponse) 
				{
						console.error('Error while Posting Event.');
			});
		};
		this.submit = function() {
			{
				console.log('Posting new Event', this.event);
				this.postEvent(this.event);
			}
		};
		this.fetchOpenEvents = function() {
			console.log("fetchOpenEvents!")
			EventService.fetchAllEvents()
			.then(
					function(d)
					{
						EventService.fetchAllEventsByOpenStatus()
						.then(
								function(d)
								{
									this.eventsOpen=d;
									if(this.eventsOpen.length==0)
									{
										console.log("No Open Events")
									}
									$rootScope.eventsOpen=d;
									console.log(this.events)
								}
						)
						$location.path('/displayEvents')
					},
					function(errResponse) 
					{
							console.error('Error while Fetching Available Events.');
				});
			};
		this.fetchCloseEvents = function() {
			console.log("fetchCloseEvents!")
			EventService.fetchAllEvents()
			.then(
					function(d)
					{
						EventService.fetchAllEventsByCloseStatus()
						.then(
								function(d)
								{
									this.eventsClosed=d;
									if(this.eventsClosed.length==0)
									{
										console.log("No Closed Events")
									}
									$rootScope.eventsClosed=d;
									console.log(this.events)
								}
						)
						$location.path('/displayEvents')
					},
					function(errResponse) 
					{
							console.error('Error while Fetching Available Events.');
				});
			};
		this.fetchOngoingEvents = function() {
			console.log("fetchOngoingEvents!")
			EventService.fetchAllEvents()
			.then(
					function(d)
					{
						EventService.fetchAllEventsByOngoingStatus()
						.then(
								function(d)
								{
									this.eventsOngoing=d;
									if(this.eventsOngoing.length==0)
									{
										console.log("No OnGoing Events")
									}
									$rootScope.eventsOngoing=d;
									console.log(this.events)
								}
						)
						alert("Thank You Events Fetched Successfully!!!")
						$location.path('/displayEvents')
					},
					function(errResponse) 
					{
							console.error('Error while Fetching Available Events.');
				});
			};
		this.display = function() {
			{
				console.log('Displaying Open Event');
				this.fetchOpenEvents();
				this.fetchCloseEvents();
				this.fetchOngoingEvents();
			}
		};
		
} ]);