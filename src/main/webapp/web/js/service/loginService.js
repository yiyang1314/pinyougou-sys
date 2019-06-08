app.service('loginService',function($http){
	this.loginweb=function(username,password){
		return $http.get('../login/user.do?username='+username+"&password="+password);
	}
	this.showName=function(){
		return $http.get('../login/name.do');
	}
	
	//校验验证码
	this.checkCode=function(phone,smscode){
		return $http.get('../login/checkCode.do?phone='+phone+"&smscode="+smscode);
	} 
});