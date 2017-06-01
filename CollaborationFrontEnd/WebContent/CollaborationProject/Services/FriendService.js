app.service('FriendService', ['$http', '$q','$rootScope', function($http, $q,$rootScope){
	
	console.log("Starting Of FriendService.js!")
	
	var BASE_URL='http://localhost:8008/CollaborationRS/'
		
    return {
			
            fetchAllFriends: function(){
            	console.log("fetchAllFriends Function Being Called")
                    return $http.get(BASE_URL+'FriendsByUserId')
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while fetching friends please try again');
                                        return $q.reject(errResponse);
                                    }
                            );
            	},
            	addFriend: function(friend){
                 	console.log("addFriend Function Being Called")
                         return $http.post(BASE_URL+'Friendsave/', friend)
                                 .then(
                                         function(response){
                                             return response.data;
                                         }, 
                                         function(errResponse){
                                             console.error('Error while adding friend please try again');
                                             return $q.reject(errResponse);
                                         }
                                 );
                 }
            }
}]);