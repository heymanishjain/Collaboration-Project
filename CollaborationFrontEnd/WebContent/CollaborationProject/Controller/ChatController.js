app.controller("ChatController", function($scope,$rootScope,$cookies,$location ,ChatService) 
{
	  $scope.messages = [];
	  $scope.message = "";
	  $scope.max = 140;
	  this.guest="";
	  this.guestName="";

	  $scope.addMessage = function() {
		  console.log("Start Of Add Message")
		  console.log($scope.guestName)
		  console.log($rootScope.guest)
		  console.log($scope.messages)
	    ChatService.send($scope.message,$rootScope.guest);
	    $scope.message = "";	    
	  };
	  
	  ChatService.receive().then(null, null, function(message) {
		  console.log("ChatService.receive")
	    $scope.messages.push(message);
	  });
	  
	  $scope.guestLogin = function(){	    	        	        
	        if ($("#nickname").val() !== '') 
	        {
	        	$cookies.put("realtime-chat-nickname", $("#nickname").val())
	        	this.guestName = $("#nickname").val();
	        	this.guest = $("#nickname").val();
	        	$scope.guestName = $("#nickname").val();
	        	$rootScope.guest = $("#nickname").val();
	        	console.log(this.guest)
	        	console.log(this.guestName)
	        	console.log($rootScope.guest)
	        	console.log($scope.guestName)
	        	$location.path("/chatOn");
	        }
	   }
});