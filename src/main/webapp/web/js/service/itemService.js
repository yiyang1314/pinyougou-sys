//服务层
app.service('itemService',function($http){
	    	
	//读取列表数据绑定到表单中
	this.findAll=function(){
		return $http.get('../items/findAll.do');		
	}
	//分页 
	this.findPage=function(page,rows){
		return $http.get('../items/findPage.do?page='+page+'&rows='+rows);
	}
	//查询实体
	this.findOne=function(id){
		return $http.get('../items/findOne.do?itemId='+id);
	}
	//增加 
	this.add=function(entity){
		return  $http.post('../items/add.do',entity );
	}
	//修改 
	this.update=function(entity){
		return  $http.post('../items/update.do',entity );
	}
	//删除
	this.dele=function(ids){
		return $http.get('../items/delete.do?ids='+ids);
	}
	//搜索
	this.search=function(page,rows,searchEntity){
		return $http.post('../items/search.do?page='+page+"&rows="+rows, searchEntity);
	}  
	
	
	//查询实体
	this.findGoodsOne=function(id){
		return $http.get('../goods/findOne.do?id='+id);
	}
	this.showName=function(){
		return $http.get('../login/name.do');
	}
});
