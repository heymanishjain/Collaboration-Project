app.controller("ChatController", function($scope,$rootScope,$cookies,$location ,ChatService) 
{
	  $scope.messages = [];
	  $scope.message = "";
	  $scope.max = 140;
	  $rootScope.guest="";
	  $scope.guestName="";

	  $scope.addMessage = function() {
		  console.log("Start Of Add Message")
	    ChatService.send($scope.message);
	    $scope.message = "";	    
	  };
	  
	  ChatService.receive().then(null, null, function(message) {
		  console.log("ChatService.receive")
	    $scope.messages.push(message);
	  });
	  
	  $scope.guestLogin = function(){
	        if ($cookies.get("realtime-chat-nickname")) 
	        {
	        	console.log("if")
	        	$location.path("/chatOn");
	        } 
	        else 
	        {
	        	if ($("#nickname").val() !== '') 
	        	{
	        		console.log("else")
	        		$cookies.put("realtime-chat-nickname", $("#nickname").val())
	        		$location.path("/chatOn");
	        	}
	        }
	   }
});