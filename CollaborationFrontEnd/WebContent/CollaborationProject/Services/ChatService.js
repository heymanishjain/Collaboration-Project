app.service("ChatService", function($q, $timeout,$cookies,$rootScope) 
{
	console.log('chat service')
    var service = {}
	var listener = $q.defer()
	var socket = 
    {
      client: null,
      stomp: null
    }
    
    var messageIds = [];
    
    service.RECONNECT_TIMEOUT = 30000;
    service.SOCKET_URL = "http://localhost:8008/CollaborationRS/chat";
    service.CHAT_TOPIC = "/topic/message";
    service.CHAT_BROKER = "/app/chat";
    
    service.receive = function() 
    {
    	console.log('receive')
    	console.log('listener.promise: '+listener.promise)
      return listener.promise;
    };
    
    service.send = function(message,userid) 
    {
    	console.log('send')
    	console.log('MESSAGE '+message)
    	console.log('UserID'+userid)
      var id = Math.floor(Math.random() * 1000000);
      socket.stomp.send(service.CHAT_BROKER, 
      {
        priority: 9
      }
      ,
      JSON.stringify(
      {
        message: message,
        id: id,
        userid:userid
      }));
      console.log("message: "+message)
      console.log("id: "+ id)
      console.log("userid: "+ userid)
      messageIds.push(id);
      messageIds.push(message)
      messageIds.push(userid)
    };
    
    var reconnect = function() 
    {
    	console.log("reconnect")
      $timeout(function() 
      {
        initialize();
      }
      ,
      this.RECONNECT_TIMEOUT);
    };
    
    var getMessage = function(data) 
    {
    	console.log("getMessage")
    	console.log("Data: "+data)
      var message = JSON.parse(data)
      var out = {};
      out.message = message.message;
      out.time = new Date(message.time);
      out.userid = message.userid;
      
      console.log("data: "+data)
      console.log("message: "+message.message)
      console.log("time: "+message.time)
      console.log("userid: "+message.userid)
      return out;
    };
    
    var startListener = function() 
    {
    	console.log("Start Listner")
      socket.stomp.subscribe(service.CHAT_TOPIC, function(data) 
      {
        listener.notify(getMessage(data.body));
      });
    };
    
    var initialize = function() 
    {
    	console.log("Initialize")
      socket.client = new SockJS(service.SOCKET_URL);
      socket.stomp = Stomp.over(socket.client);
      socket.stomp.connect({}, startListener);
      socket.stomp.onclose = reconnect;
    };
    
    initialize();
    return service;
});