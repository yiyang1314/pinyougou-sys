//服务层
app.service('indexUserService',function($http){
	    	
	
	//显示当前用户名
	this.loginName=function(username,password){
		return $http.get('../../login/name.do');
	}
	
	//显示当前用户名
	this.logout=function(){
		return $http.get('../../login/seller_logout.do');
	}
});
