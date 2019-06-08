app.service('searchService',function($http){
	
	
	this.search=function(searchMap){
		console.log(searchMap);
		return $http.post('../itemsearch/search.do',JSON.stringify(searchMap));
	}
	this.showName=function(){
		return $http.get('../login/name.do');
	}
});