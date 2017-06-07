app.service('JobService', ['$http', '$q','$rootScope', function($http, $q,$rootScope){
	
	console.log("Starting Of JobService.js!")
	
	var BASE_URL='http://localhost:8008/CollaborationRS/'
		
    return {
             
            postJob: function(job){
            	console.log("postJob Function Being Called")
                    return $http.post(BASE_URL+'Jobpost/', job)
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while posting job please try again');
                                        return $q.reject(errResponse);
                                    }
                            );
            },
            fetchAllAvailableJobs: function(){
            	console.log("fetchAllAvailableJobs Function Being Called")
                    return $http.get(BASE_URL+'JobAvailableList')
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while fetching all available jobs please try again');
                                        return $q.reject(errResponse);
                                    }
                            );
            },
            fetchJobById: function(job){
            	console.log("fetchJobById Function Being Called")
                    return $http.post(BASE_URL+'JobById/'+job)
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while fetching job by id please try again');
                                        return $q.reject(errResponse);
                                    }
                            );
            },
            updateJob: function(job){
            	console.log("updateJob Function Being Called")
                    return $http.put(BASE_URL+'Jobupdate/', job)
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while updating job please try again');
                                        return $q.reject(errResponse);
                                    }
                            );
            },
            updateStatus: function(job){
            	console.log("updateJobStatus Function Being Called")
                    return $http.put(BASE_URL+'Jobupdatestatus/', job)
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while updating job Status please try again');
                                        return $q.reject(errResponse);
                                    }
                            );
            },
            deleteJob: function(job){
            	console.log("deleteJob Function Being Called")
                    return $http.delete(BASE_URL+'Jobdelete/'+job)
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while deleting job please try again');
                                        return $q.reject(errResponse);
                                    }
                            );
            }
	  }
}]);