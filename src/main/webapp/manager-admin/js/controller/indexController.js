app.controller('indexController',function($scope,$location,loginService){
	$scope.indexUser={};
	
	//显示当前用户名
	$scope.showLoginName=function(){
		loginService.getInfo().success(
			function(response){
				if(response.username==null){
					alert("非法操作！");
					location.href="../login.html";
				}
				$scope.indexUser=response;
			}
		);		
	}
	/*
		{{indexUser.birthday}}: "1991-02-15 16:54:02"​
		{{indexUser.email}}: "mgr@263.com"​
		{{indexUser.headPic}}: null​
		{{indexUser.lastLoginTime}}: "2019-04-10 03:54:26"​
		{{indexUser.name}}: "微微"​
		{{indexUser.nickName}}: "微微一笑"​
		{{indexUser.password}}: "123456"​
		{{indexUser.phone}}: "13765896352"​
		{{indexUser.username}}: "manager"
	*/
	
	
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
	 * 管理员注销
	 */
	$scope.logout=function(){
		loginService.logout().success(function(response){
			if(response.success){
				alert(response.message);
				location.href="../login.html";
			}else{
				alert("系统错误，请稍后再试");
			}
		});
	}
	
});