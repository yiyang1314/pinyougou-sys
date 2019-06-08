//服务层
app.service('areasService',function($http){
	    	
	//读取列表数据绑定到表单中
	this.findAll=function(){
		return $http.get('../areas/findAll.do');		
	}
	
	//读取列表数据绑定到表单中
	this.findAreas=function(cityid){
		return $http.get('../areas/findAreas.do?cityid='+cityid);		
	}
});
