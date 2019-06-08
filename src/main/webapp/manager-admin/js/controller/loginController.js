app.controller('loginController',function($scope,$location,loginService){
	$scope.msg='';//信息提示
	$scope.smscode="";//验证码
	$scope.isChecked=false;//校验状态
	$scope.isCheckSms=false;//校验状态
	$scope.phone="";
	$scope.loginName=function(){
		$scope.checkName();
		$scope.checkPWD();
		$scope.checkPhone();
		if($scope.msg!=''){
			alert("请重新填写信息！");
			return ;
		}
		if(!$scope.isCheckSms){
			return ;
		}
		loginService.loginName($scope.username,$scope.password).success(
				function(response){
					if(response.success){
						console.log(response);
						alert(response.message);
						location.href="admin/index.html";
					}else{
						alert(response.message);
						$scope.username="";
						$scope.password="";
						return ;
					}
			
				}
			);		
	}	
	
	
	$scope.checkName=function(){
		if($scope.username==""||$scope.username==null){
			$scope.msg="用户名不能为空";
		}else{
			$scope.msg="";
		}
	}
	$scope.checkPWD=function(){
		if($scope.password==""||$scope.password==null){
			$scope.msg="密码不能为空";		
		}else{
			$scope.msg="";
		}
	}
	$scope.checkPhone=function(){
		if($scope.phone==""||$scope.phone==null){
			$scope.msg="手机号不能为空";	
			return ;
		}else{
			$scope.msg="";
		}
		if($scope.phone.length!=11){
			$scope.msg="手机号长度必须是11位";
			return ;
		}else{
			$scope.msg="";
		}
		if(isNaN($scope.phone)||$scope.phone.charAt(0)!='1'){
			$scope.msg="手机号格式不合法";
			return ;
		}else{
			$scope.msg="";
		}
	}
	
	//发送验证码
	$scope.sendCode=function(){
		if($scope.phone==""||$scope.phone==null){
			$scope.msg="手机号不能为空！";
			return ;
		}
		loginService.sendCode($scope.phone).success(function(response){
			alert(response.message);
			console.log(response);
		});
	}
	
	//验证码校验
	$scope.checkCode=function(){
		if($scope.smscode==''||$scope.phone==""){
			alert("请重新填写信息！");
			$scope.msg="不能为空！";
			return ;
		}else{
			loginService.checkCode($scope.phone,$scope.smscode).success(function(response){
				console.log(response);
				$scope.isCheckSms=response.success;
				if(!response.success){
					$scope.sms=response;
					alert(response.message);
				}
				
			});	
		}
	}
});