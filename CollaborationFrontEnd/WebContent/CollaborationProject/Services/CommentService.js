app.service('CommentService', ['$http', '$q','$rootScope', function($http, $q,$rootScope){
	
	console.log("Starting Of CommentService.js!")
	
	var BASE_URL='http://localhost:8008/CollaborationRS/'
		
    return {  
            createComment: function(comment){
            	console.log("createComment Function Being Called")
                    return $http.post(BASE_URL+'Commentsave/', comment)
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while creating comment please try again');
                                        return $q.reject(errResponse);
                                    }
                            );
            },
            fetchAllComments: function(){
            	console.log("fetchAllComments Function Being Called")
                    return $http.get(BASE_URL+'CommentsList')
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while fetching all comments please try again');
                                        return $q.reject(errResponse);
                                    }
                            );
            },
            fetchAllCommentsByBlog: function(blogid){
            	console.log("fetchAllCommentsByBlog Function Being Called")
                    return $http.get(BASE_URL+'CommentsListByBlog/'+blogid)
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while fetching all comments by blogid please try again');
                                        return $q.reject(errResponse);
                                    }
                            );
            },
            fetchAllCommentsByForum: function(forumid){
            	console.log("fetchAllCommentsByForum Function Being Called")
                    return $http.get(BASE_URL+'CommentsListByForum/'+forumid)
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while fetching all comments by forumid please try again');
                                        return $q.reject(errResponse);
                                    }
                            );
            },
            deleteComment: function(commentid){
            	console.log("deleteComment Function Being Called")
                    return $http.delete(BASE_URL+'Commentdelete/'+commentid)
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while deleting comment please try again');
                                        return $q.reject(errResponse);
                                    }
                            );
            },
		}
}]);