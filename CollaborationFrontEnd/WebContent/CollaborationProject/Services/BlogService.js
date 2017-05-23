app.service('BlogService', ['$http', '$q','$rootScope', function($http, $q,$rootScope){
	
	console.log("Starting Of BlogService.js!")
	
	var BASE_URL='http://localhost:8008/CollaborationRS/'
		
    return {
             
            createBlog: function(blog){
            	console.log("createBlog Function Being Called")
                    return $http.post(BASE_URL+'Blogsave/', blog)
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while creating blog please try again');
                                        return $q.reject(errResponse);
                                    }
                            );
            		},
            		updateBlog: function(blog){
                    	console.log("updateBlog Function Being Called")
                            return $http.put(BASE_URL+'Blogupdate/', blog)
                                    .then(
                                            function(response){
                                                return response.data;
                                            }, 
                                            function(errResponse){
                                                console.error('Error while updating blog please try again');
                                                return $q.reject(errResponse);
                                            }
                                    );
                    		},
            		deleteBlog: function(blog){
                    	console.log("deleteBlog Function Being Called")
                            return $http.delete(BASE_URL+'Blogdelete/'+blog)
                                    .then(
                                            function(response){
                                                return response.data;
                                            }, 
                                            function(errResponse){
                                                console.error('Error while deleting blog please try again');
                                                return $q.reject(errResponse);
                                            }
                                    );
                    		},
            		fetchAllBlogs: function(){
                    	console.log("fetchAllBlogs Function Being Called")
                            return $http.get(BASE_URL+'BlogAllList')
                                    .then(
                                            function(response){
                                                return response.data;
                                            }, 
                                            function(errResponse){
                                                console.error('Error while fetching all blogs please try again');
                                                return $q.reject(errResponse);
                                            }
                                    );
                    		},
                    fetchAllPendingBlogs: function(){
                          console.log("fetchAllPendingBlogs Function Being Called")
                             return $http.get(BASE_URL+'BlogAllPendingList')
                                     .then(
                                             function(response){
                                                        return response.data;
                                                    }, 
                                                    function(errResponse){
                                                        console.error('Error while fetching all pending blogs please try again');
                                                        return $q.reject(errResponse);
                                                    }
                                            );
                            		},
            		fetchBlogById: function(blog){
                    	console.log("fetchBlogById Function Being Called")
                            return $http.post(BASE_URL+'BlogById/'+blog)
                                    .then(
                                            function(response){
                                                return response.data;
                                            }, 
                                            function(errResponse){
                                                console.error('Error while fetching blogs by id please try again');
                                                return $q.reject(errResponse);
                                            }
                                    );
                    		},
                    fetchCommentsByBlog: function(blogid){
                        console.log("fetchCommentsByBlog Function Being Called")
                            return $http.get(BASE_URL+'CommentsListByBlog/'+blogid)
                                .then(
                                        function(response){
                                        	return response.data;
                                        }, 
                                        function(errResponse){
                                            console.error('Error while fetching comments by blogid please try again');
                                            return $q.reject(errResponse);
                                        }
                                  );
                           },
                    approveBlog: function(blog){
                    	console.log("approveBlog Function Being Called")
                    		return $http.put(BASE_URL+'BlogApproveupdate/'+blog)
                    			.then(
                    					function(response){
                    						return response.data;
                    					},
                    					function(errResponse){
                    						console.log("Error While Approving Blog please try again");
                    						return $q.reject(errResponse)
                    					}
                    			);
                    },
                    rejectBlog: function(blog){
                    	console.log("rejectBlog Function Being Called")
                    		return $http.put(BASE_URL+'BlogRejectupdate/'+blog)
                    			.then(
                    					function(response){
                    						return response.data;
                    					},
                    					function(errResponse){
                    						console.log("Error While Rejecting Blog please try again");
                    						return $q.reject(errResponse)
                    					}
                    			);
                    }
			}
}]);