//服务层
app.service('citiesService',function($http){
	    	
	//读取列表数据绑定到表单中
	this.findAll=function(){
		return $http.get('../cities/findAll.do');		
	}	
	
	//读取列表数据绑定到表单中
	this.findCities=function(provinceId){
		return $http.get('../cities/findCities.do?provinceid='+provinceId);		
	}	
	
});
