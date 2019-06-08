 //控制层 
app.controller('homeIndexController' ,function($scope,$controller   ,$location){	
	
	$controller('baseController',{$scope:$scope});//继承
	
	$scope.loadUsers=function(){
		$scope.entityUser= $location.search()['user'];
		console.log($scope.entityUser);
	}

    
});	
