app.controller("contentController",function($scope,contentService){
	$scope.contentList = [];
	$scope.keywords="";
	$scope.loginName="";
	// 根据分类ID查询广告的方法:
	$scope.findByCategoryId = function(categoryId){
		console.log(categoryId);
		contentService.findByCategoryId(categoryId).success(function(response){
			console.log(response);
			$scope.contentList[categoryId] = response;
			console.log($scope.contentList[categoryId]);
		});
	}
	$scope.search=function(){
		//http://localhost:8888/SSM-pinyougou/web
		location.href='search.html#?keywords='+$scope.keywords;
	}
	$scope.findByKeywords=function(keywords){
		$scope.keywords=keywords;
		$scope.search();
	}
	//显示当前用户名
	$scope.showName=function(){
		contentService.showName().success(function(response){
				console.log(response);	
				$scope.loginName=response.loginName;
		});
		
	}
	
	//显示当前用户名
	$scope.findKeywords=function(keywords){
		location.href='search.html#?keywords='+keywords;
	}
	
});