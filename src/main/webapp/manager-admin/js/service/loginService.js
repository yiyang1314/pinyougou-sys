app.service('loginService',function($http){
	
	this.loginName=function(username,password){
		//读取列表数据绑定到表单中
		return $http.get('../login/user.do?username='+username+"&password="+password);		
		
	}
	this.getInfo=function(){
		//读取列表数据绑定到表单中
		return $http.get('../../login/admin.do');		
		
	}
	
	
	//发送验证码
	this.sendCode=function(phone){
		return $http.get('../user/sendCode.do?phone='+phone);
	}  
	

	//校验验证码
	this.checkCode=function(phone,smscode){
		return $http.get('../login/checkCode.do?phone='+phone+"&smscode="+smscode);
	} 
	
	//显示当前用户名
	this.logout=function(){
		return $http.get('../../login/admin_logout.do');
	}
});