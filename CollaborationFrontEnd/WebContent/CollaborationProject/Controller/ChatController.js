app.controller("ChatController", function($scope,$rootScope,$cookies,$location ,ChatService) 
{
	  $scope.messages = [];
	  $scope.message = "";
	  $scope.max = 140;
	  this.msg="";
	  this.guest="";
	  this.guestName="";
	  this.roomid= 0;
	  this.msgid=0;
	  this.groupid=0;

	  $scope.addMessage = function() {
		  console.log("Start Of Add Message")
		  console.log($scope.guestName)
		  console.log($rootScope.guest)
		  console.log($scope.messages)
		  console.log($rootScope.msgid);
	    ChatService.send($scope.message,$rootScope.guest,$rootScope.msgid);
	    $scope.message = "";	    
	  };
	  
	  ChatService.receive().then(null, null, function(message) {
		  console.log("ChatService.receive")
		  if(message.msgid == $rootScope.roomid)
		  {
			  console.log("Private Chat")
			  $scope.messages.push(message);
		  }
		  else if(message.msgid == $rootScope.groupid)
		  {
			  console.log("Group Chat")
			  $scope.messages.push(message);
		  }
	  });
	  
	  $scope.guestLogin = function(){	    	        	        
	        if ($("#nickname").val() !== '') 
	        {
	        	$cookies.put("realtime-chat-nickname", $("#nickname").val())
	        	this.guestName = $("#nickname").val();
	        	this.guest = $("#nickname").val();
	        	$scope.guestName = $("#nickname").val();
	        	$rootScope.guest = $("#nickname").val();
	        	this.groupid = 0;
		        $rootScope.groupid = this.groupid;
	        	this.msgid=$rootScope.groupid;
				$rootScope.msgid=$rootScope.groupid;
	        	console.log($rootScope.groupid)
	        	console.log($rootScope.guest)	        	
	        	$location.path("/chatOn");
	        }
	   }
	  this.privateChat = function(friend){
		  {
			  this.roomid = friend.chatid
			  $rootScope.roomid = this.roomid;
			  console.log(this.roomid);
			  console.log($rootScope.roomid);
			  this.msgid=$rootScope.roomid;
			  $rootScope.msgid=$rootScope.roomid;
			  $rootScope.guest = $rootScope.currentuser.id;
			  console.log($rootScope.guest);
		  }
	  }
});