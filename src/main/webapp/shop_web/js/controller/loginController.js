app.controller('loginController',function($scope,$location,loginService){
	
	$scope.msg='';//信息提示
	$scope.smscode="";//验证码
	$scope.isChecked=false;
	$scope.isCheckCode=false;
	$scope.sms={};//校验状态
	$scope.phone="";
	
	//用户名校验
	$scope.checkName=function(){
		if($scope.username==""||$scope.username==null){
			$scope.msg="用户名不能为空";
			return ;
		}else{
			$scope.msg="";
		}
	}
	//密码校验
	$scope.checkPWD=function(){
		if($scope.password==""||$scope.password==null){
			$scope.msg="密码不能为空";	
			return;
		}else{
			$scope.msg="";
		}
	}
	//手机号不能为空
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
		$scope.isChecked=true;
	}
		
	$scope.isSendCodeChecked=false;
	//发送验证码
	$scope.sendCode=function(){
		if(!$scope.isChecked){
			$scope.isSendCodeChecked=false;
			alert("请填写合法的手机号码！");
			return ;
		}
		loginService.sendCode($scope.phone).success(function(response){
			console.log(response);
			$scope.isSendCodeChecked=response.success;
		});
	}
	
	//验证码校验
	$scope.checkCode=function(){
		if($scope.smscode==''){
			alert("验证码不能为空！");
			$scope.isCheckCode=false;
			return ;
		}else{
			loginService.checkCode($scope.phone,$scope.smscode).success(function(response){
				$scope.sms=response;
				console.log(response);
				$scope.isCheckCode=response.success;
				if(!response.success){
					$scope.msg=response.message;
				}else{
					$scope.msg="";
				}
			});	
		}
	}
	

	
	$scope.login1=function(){
		$scope.checkName();
		if($scope.msg!=""){
			return ;
		}
		$scope.checkPWD();
		if($scope.msg!=""){
			return ;
		}
		$scope.checkPhone();
		if($scope.msg!=""){
			return ;
		}
//		if($scope.isSendCodeChecked){
//			alert("请获取验证码！");
//			return ;
//		}
		if(!$scope.isCheckCode){
			alert("验证码错误！");
			return ;
		}
		loginService.login($scope.username,$scope.password).success(
				function(response){
					if(response.success){
						alert(response.message);
						location.href="admin/index.html";
					}else{
						alert(response.message);
					}
			
				}
			);	
	}
	
});