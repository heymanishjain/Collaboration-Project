app.service('FriendService', ['$http', '$q','$rootScope', function($http, $q,$rootScope){
	
	console.log("Starting Of FriendService.js!")
	
	var BASE_URL='http://localhost:8008/CollaborationRS/'
		
    return {
			
            fetchAllFriends: function(){
            	console.log("fetchAllFriends Function Being Called")
                    return $http.get(BASE_URL+'ApprovedFriendsByUserId')
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
            	fetchAllPendingFriends: function(){
                	console.log("fetchAllPendingFriends Function Being Called")
                        return $http.get(BASE_URL+'PendingFriendsByUserId')
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
                 },
                 approveFriend: function(friend){
                  	console.log("approveFriend Function Being Called")
                          return $http.put(BASE_URL+'FriendupdateApproveStatus/', friend)
                                  .then(
                                          function(response){
                                              return response.data;
                                          }, 
                                          function(errResponse){
                                              console.error('Error while approving friend please try again');
                                              return $q.reject(errResponse);
                                          }
                                  );
                  },
                  rejectFriend: function(friend){
                    	console.log("rejectFriend Function Being Called")
                            return $http.put(BASE_URL+'FriendupdateRejectStatus/', friend)
                                    .then(
                                            function(response){
                                                return response.data;
                                            }, 
                                            function(errResponse){
                                                console.error('Error while rejecting friend please try again');
                                                return $q.reject(errResponse);
                                            }
                                    );
                    },
                 deleteFriend: function(friend){
                 	console.log("deleteFriend Function Being Called")
                         return $http.delete(BASE_URL+'Frienddelete/'+friend)
                                 .then(
                                         function(response){
                                             return response.data;
                                         }, 
                                         function(errResponse){
                                             console.error('Error while deleting friend please try again');
                                             return $q.reject(errResponse);
                                         }
                                 );
                 },
            }
}]);