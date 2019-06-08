app.controller('indexUserController',function($scope,sellerService,$location,indexUserService){
	
	
	//显示当前用户名
	$scope.showLoginName=function(){
		sellerService.getSellerOne().success(function(response){
			if(response.sellerId==null){
				alert("非法操作！");
				location.href="../shoplogin.html";
			}
			$scope.sellerEntity=response;
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
	/**
	 * 商家用户注销
	 */
	$scope.logout=function(){
		indexUserService.logout().success(function(response){
			if(response.success){
				alert(response.message);
				location.href="../shoplogin.html";
			}else{
				alert("系统错误，请稍后再试");
			}
			
		});
	}
	
});