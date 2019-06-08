//服务层
app.service('loginService',function($http){
	    	
	//读取列表数据绑定到表单中
	this.login=function(username,password){
		return $http.get('../login/seller.do?username='+username+"&password="+password);		
	}
	//发送验证码
	this.sendCode=function(phone){
		return $http.get('../user/sendCode.do?phone='+phone);
	}
	//校验验证码
	this.checkCode=function(phone,smscode){
		return $http.get('../login/checkCode.do?phone='+phone+"&smscode="+smscode);
	}  
});
