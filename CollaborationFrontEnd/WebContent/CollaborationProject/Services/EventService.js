app.service('EventService', ['$http', '$q','$rootScope', function($http, $q,$rootScope){
	
	console.log("Starting Of EventService.js!")
	
	var BASE_URL='http://localhost:8008/CollaborationRS/'
		
    return {
             
            postEvent: function(event){
            	console.log("postEvent Function Being Called")
                    return $http.post(BASE_URL+'Eventpost/', job)
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while posting event please try again');
                                        return $q.reject(errResponse);
                                    }
                            );
            },
            fetchAllEvents: function(){
            	console.log("fetchAllEvents Function Being Called")
                    return $http.get(BASE_URL+'EventAllList')
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while fetching all Events please try again');
                                        return $q.reject(errResponse);
                                    }
                            );
            },
            fetchAllEventsByOpenStatus: function(){
            	console.log("fetchAllEventsByOpenStatus Function Being Called")
                    return $http.get(BASE_URL+'EventListByStatusOpen')
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while fetching all Events By Status Open please try again');
                                        return $q.reject(errResponse);
                                    }
                            );
            },
            fetchAllEventsByCloseStatus: function(){
            	console.log("fetchAllEventsByCloseStatus Function Being Called")
                    return $http.get(BASE_URL+'EventListByStatusClose')
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while fetching all Events By Status Close please try again');
                                        return $q.reject(errResponse);
                                    }
                            );
            },
            fetchAllEventsByOngoingStatus: function(){
            	console.log("fetchAllEventsByOngoingStatus Function Being Called")
                    return $http.get(BASE_URL+'EventListByStatusOngoing')
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while fetching all Events By Status Ongoing please try again');
                                        return $q.reject(errResponse);
                                    }
                            );
            }
            
	  }
}]);