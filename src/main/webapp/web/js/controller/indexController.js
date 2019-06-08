app.controller('indexController',function($scope,$location,uploadService,loginService,userService){
	
	
	$scope.sts=0;
	$scope.findSts=function(num){
		$scope.sts=num;
		alert($scope.sts);
	}
	
	$scope.searchEntity={};
	//显示当前用户名
	$scope.showName=function(){
		loginService.showName().success(function(response){
				console.log(response);	
				$scope.loginName=response.loginName;
				$scope.searchEntity.username=response.loginName;
				
		});
		
	}
	
	$scope.uploadFile = function(){
		// 调用uploadService的方法完成文件的上传
		uploadService.uploadFile().success(function(response){

			if(response.success){
				// 获得url .replace("../../","../")
				$scope.image_entity.url=response.message;
				alert($scope.image_entity.url);
			}else{
				alert(response.message);
			}
		});
	}

	//查看用户信息
	$scope.findOne=function(){
		console.log($scope.searchEntity);
		userService.searchOne().success(function(response){
				console.log(response);	
				$scope.userEntity=response.rows[0];
				console.log($scope.userEntity);	
		});
		
	}
	
	
	
	
});