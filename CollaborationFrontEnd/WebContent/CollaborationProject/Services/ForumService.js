app.service('ForumService', ['$http', '$q','$rootScope', function($http, $q,$rootScope){
	
	console.log("Starting Of ForumService.js!")
	
	var BASE_URL='http://localhost:8008/CollaborationRS/'
		
    return {  
            createForum: function(forum){
            	console.log("createForum Function Being Called")
                    return $http.post(BASE_URL+'Forumsave/', forum)
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while creating Forum please try again');
                                        return $q.reject(errResponse);
                                    }
                            );
            		},
	fetchAllForums: function(){
    	console.log("fetchAllForums Function Being Called")
            return $http.get(BASE_URL+'ForumAllList')
                    .then(
                            function(response){
                                return response.data;
                            }, 
                            function(errResponse){
                                console.error('Error while fetching all forums please try again');
                                return $q.reject(errResponse);
                            }
                    );
    		},
    		deleteForum: function(forum){
            	console.log("deleteForum Function Being Called")
                    return $http.delete(BASE_URL+'Forumdelete/'+forum)
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while deleting forum please try again');
                                        return $q.reject(errResponse);
                                    }
                            );
            },
    		fetchForumById: function(forum){
            	console.log("fetchForumById Function Being Called")
                    return $http.get(BASE_URL+'ForumById/'+forum)
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while fetching forum by id please try again');
                                        return $q.reject(errResponse);
                                    }
                            );
            		},
    		updateForum: function(forum){
            	console.log("updateForum Function Being Called")
                    return $http.put(BASE_URL+'Forumupdate/', forum)
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while updating forum please try again');
                                        return $q.reject(errResponse);
                                    }
                            );
            		},
		}
}]);