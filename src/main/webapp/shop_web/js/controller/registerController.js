app.controller('registerController',function($scope,loginService,sellerService){
	
	$scope.msg='';//信息提示
	$scope.msgpwd='';//信息提示
	$scope.msgpwd1='';//信息提示
	$scope.msgpwd2='';//信息提示
	$scope.msgPhone='';//信息提示
	$scope.smscode="";//验证码
	$scope.checkcodemsg="";
	$scope.isRegChecked=false;//注册状态
	$scope.isChecked=false;//校验状态
	$scope.sms={};//校验状态
	$scope.phone="";
	$scope.entity={};
	//查找用户
	$scope.findByName=function(){
		if($scope.entity.sellerId==""||$scope.entity.sellerId==null){
			$scope.msg="用户名不能为空";
			return ;
		}
		console.log($scope.entity);
		sellerService.findRegisterOne($scope.entity.sellerId).success(function(response){
			
			if(!response.success){
				$scope.msg="";
				$scope.isRegChecked=true;
			}else{
				$scope.msg=response.message;
				$scope.isRegChecked=false;
			}
			
			
		});
	}

	//密码校验
	$scope.checkcompareToPWD=function(){
		if($scope.entity.password==""||$scope.entity.password==null){
			$scope.msgpwd2="密码不能为空";
			return ;
		}else{
			$scope.msgpwd2="";
		}
		if($scope.pwd==""||$scope.pwd==null){
			$scope.msgpwd1="密码不能为空";
			return ;
		}else{
			$scope.msgpwd1="";
		}
		if($scope.entity.password!=$scope.pwd){
			$scope.msgpwd="密码不一致";
			return ;
		}else{
			$scope.msgpwd="";
		}
		
		
	}
	
	//手机号不能为空
	$scope.checkPhone=function(){
		if($scope.entity.linkmanMobile==""||$scope.entity.linkmanMobile==null){
			$scope.msgPhone="手机号不能为空";	
			return ;
		}else{
			$scope.msgPhone="";
		}
		if($scope.entity.linkmanMobile.length!=11){
			$scope.msgPhone="手机号长度必须是11位";
			return ;
		}else{
			$scope.msgPhone="";
		}
		if(isNaN($scope.entity.linkmanMobile)||$scope.entity.linkmanMobile.charAt(0)!='1'){
			$scope.msgPhone="手机号格式不合法";
			return ;
		}else{
			$scope.msgPhone="";
		}
	}
	

	//发送验证码
	$scope.sendCode=function(){
		if($scope.entity.linkmanMobile==""||$scope.entity.linkmanMobile==null){
			return ;
		}
		loginService.sendCode($scope.entity.linkmanMobile).success(function(response){
			console.log(response);
			if(!response.success){
				$scope.checkcodemsg=response.message;
			}
		});
	}
	
	//验证码校验
	$scope.checkCode=function(){
		if($scope.smscode==''||$scope.entity.linkmanMobile==""){
			$scope.checkcodemsg="检验失败！";
			return ;
		}else{
			loginService.checkCode($scope.entity.linkmanMobile,$scope.smscode).success(function(response){
				$scope.sms=response;
				console.log(response);
				if(!response.success){
					$scope.checkcodemsg=response.message;
					console.log($scope.checkcodemsg);
				}else{
					$scope.checkcodemsg="";
				}
			});	
		}
	}
	
	
	$scope.isCheckEntity=false;
	$scope.isCheckRegEntity=function(){
		$scope.isCheckEntity=$scope.msgpwd2==""&&$scope.msg==""&&$scope.msgPhone=="";
	}
	
	
	//商家注册
	$scope.add=function(){
		if(!$scope.isRegChecked){
			alert("登录名已存在！");
			return ;
		}
		if($scope.smscode==''||$scope.entity.linkmanMobile==""||$scope.entity.password==""||$scope.entity.sellerId==""){
			alert("请正确填写信息");
			return ;
		}
		$scope.isCheckRegEntity();
		if($scope.checkcodemsg==""&&$scope.isCheckEntity){
			sellerService.add($scope.entity).success(
					function(response){
						console.log(response);
						if(response.success){
							alert("注册成功！");
							location.href="shoplogin.html";
						}else{
							alert(response.message);
						}
					}		
				);	
		}
		
		
	}
	
});