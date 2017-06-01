app.service('UserService', ['$http', '$q','$rootScope', function($http, $q,$rootScope){
	
	console.log("Starting Of UserService.js!")
	
	var BASE_URL='http://localhost:8008/CollaborationRS/'
		
    return {
             
            createUser: function(user){
            	console.log("createUser Function Being Called")
                    return $http.post(BASE_URL+'Usersave/', user)
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while creating user please try again');
                                        return $q.reject(errResponse);
                                    }
                            );
            },
            updateUser: function(currentuser){
            	console.log("updateUser Function Being Called")
                    return $http.put(BASE_URL+'Userupdate/', currentuser)
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while updating user please try again');
                                        return $q.reject(errResponse);
                                    }
                            );
            },
            fetchAllUsers: function(){
            	console.log("fetchAllUsers Function Being Called")
                    return $http.get(BASE_URL+'UserAllList')
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while fetching users please try again');
                                        return $q.reject(errResponse);
                                    }
                            );
            },
            validateUser: function(user){
            	console.log("validateUser Function Being Called")
            	return $http.post(BASE_URL+'UserLoginValidate', user)
            		.then(
            				function(response){
            					console.log("Function Response")
            					return response.data;
            				},
            				function(errResponse){
                                console.error('Error while validating user please try again');
                                return $q.reject(errResponse);
                            }
            		)
            },
            userLogout: function(user){
            	console.log("userLogout Function Being Called")
                    return $http.get(BASE_URL+'Userlogout')
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while logging out user please try again');
                                        return $q.reject(errResponse);
                                    }
                            );
            }
    };
}]);