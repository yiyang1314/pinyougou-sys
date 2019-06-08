app.controller('userRegisterController',function($scope,loginService,userService){
	
	$scope.msg='';//信息提示
	$scope.msgpwd='';//信息提示
	$scope.msgpwd1='';//信息提示
	$scope.msgpwd2='';//信息提示
	$scope.msgPhone='';//信息提示
	$scope.smscode="";//验证码
	$scope.checkcodemsg="";
	
	$scope.isChecked=false;//校验状态
	$scope.sms={};//校验状态
	$scope.phone="";
	$scope.entity={};
	//查找用户
	$scope.findByName=function(){
		if($scope.entity.username==""||$scope.entity.username==null){
			$scope.msg="用户名不能为空";
			return ;
		}
		console.log($scope.entity);
		userService.findByName($scope.entity).success(function(response){
			console.log(response);
			if(response.total==0){
				$scope.msg="";
			}else{
				$scope.msg="用户已存在";
			}
		});
	}

	//密码校验
	$scope.checkcompareToPWD=function(){
		if($scope.entity.password==""||$scope.entity.password==null){
			$scope.msgpwd1="密码不能为空";
			return ;
		}else{
			$scope.msgpwd1="";
		}
		if($scope.password==""||$scope.entity.password==null){
			$scope.msgpwd2="密码不能为空";
			return ;
		}else{
			$scope.msgpwd2="";
		}
		if($scope.entity.password!=$scope.password){
			$scope.msgpwd="密码不一致";
			return ;
		}else{
			$scope.msgpwd="";
		}
		
	}
	
	//手机号不能为空
	$scope.checkPhone=function(){
		if($scope.entity.phone==""||$scope.entity.phone==null){
			$scope.msgPhone="手机号不能为空";	
			return ;
		}else{
			$scope.msgPhone="";
		}
		if($scope.entity.phone.length!=11){
			$scope.msgPhone="手机号长度必须是11位";
			return ;
		}else{
			$scope.msgPhone="";
		}
		if(isNaN($scope.entity.phone)||$scope.entity.phone.charAt(0)!='1'){
			$scope.msgPhone="手机号格式不合法";
			return ;
		}else{
			$scope.msgPhone="";
		}
	}
	

	//发送验证码
	$scope.sendCode=function(){
		if($scope.entity.phone==""||$scope.entity.phone==null){
			return ;
		}
		userService.sendCode($scope.entity.phone).success(function(response){
			console.log(response);
			if(!response.success){
				$scope.checkcodemsg=response.message;
			}
		});
	}
	
	//验证码校验
	$scope.checkCode=function(){
		if($scope.smscode==''||$scope.entity.phone==""){
			alert("不能为空");
			$scope.checkcodemsg="不能为空";
			return ;
		}else{
			loginService.checkCode($scope.entity.phone,$scope.smscode).success(function(response){
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
	
	
	//用户注册
	$scope.register=function(){

		if($scope.smscode==''||$scope.entity.phone==""||$scope.entity.password==""||$scope.entity.username==""){
			alert("请正确填写信息");
			return ;
		}
		$scope.isCheckRegEntity();
		if($scope.checkcodemsg!=""){
			alert($scope.checkcodemsg);
			return ;
		}
		if($scope.isCheckEntity){
			userService.add($scope.entity).success(
					function(response){
						console.log(response);
						if(response.success){
							alert("用户注册成功！");
							location.href="index.html";
						}else{
							alert(response.message);
						}
					}		
				);	
		}
		
		
	}
	
});