app.controller('searchController',function($scope,searchService,$location){
	
	//定义搜索对象栏的结构
	$scope.searchMap={'keywords':'','category':'','brand':'','spec':{},'price':'','pageIndex':1,'pageSize':5,'sort':'ASC','sortFeild':'price'};
	
	//定义面包屑对象
	$scope.searchNavMap={'category':'','brand':'','spec':''}
	$scope.supperPath='D:/20152203/tts9/workbases/.metadata/.plugins/org.eclipse.wst.server.core/tmp1/wtpwebapps/SSM-pinyougou/';
	
	$scope.loadKeywords=function(){
		$scope.searchMap.keywords= $location.search()['keywords'];
		$scope.search();
	}
	
	
	
	//搜索
	$scope.search=function(){
		$scope.searchMap.pageIndex=parseInt($scope.searchMap.pageIndex);
		searchService.search($scope.searchMap).success(
			function(response){
				console.log(response);
				$scope.resultMap=response;	
				
				createPageLabel();
				$scope.searchMap.pageIndex=1;
			}
		);		
	}

	createPageLabel=function(){
		$scope.pageObj=[];
		var firstPage=1;
		var lastPage=$scope.resultMap.totalPages;
		
		$scope.firstDott=true;
		$scope.lastDott=true;
		
		if($scope.resultMap.totalPages>5){
			if($scope.searchMap.pageIndex<3){
				lastPage=5;
				$scope.firstDott=false;
			}else if($scope.searchMap.pageIndex >$scope.resultMap.totalPages-2){
				firstPage=$scope.resultMap.totalPages-4;
				$scope.lastDott=false;
			}else{
				firstPage=$scope.searchMap.pageIndex-2;
				lastPage=$scope.searchMap.pageIndex+2;
			}
		}else{
			$scope.firstDott=false;
			$scope.lastDott=false;
		}
		for(var i=firstPage;i<=lastPage;i++){
			$scope.pageObj.push(i);
		}
		console.log(firstPage);
		console.log(lastPage);
		console.log($scope.pageObj);

	}
	
	
	//点击页码查询
	$scope.queryFindPage=function(index){
		if(index<1 || index>$scope.resultMap.totalPages){
			return;
		}
		$scope.searchMap.pageIndex=index;
		$scope.search();
	}
	
	//判断是否为第一页
	$scope.isTopPage=function(){
		return $scope.searchMap.pageIndex==1?true:false;
	}
	//判断是否为最后一页
	$scope.isEndPage=function(){
		return $scope.searchMap.pageIndex==$scope.resultMap.totalPages?true:false;
	}
	
	
	
	
	//排序查询
	$scope.sortSearch=function(sortFeild,sort){
		$scope.searchMap.sortFeild=sortFeild;
		$scope.searchMap.sort=sort;
		$scope.search();
	}
	
	
	//判断关键字是否为品牌
	$scope.keywordsIsBrand=function(){
		for(var i=0;i<$scope.resultMap.brandList.length;i++){
			if($scope.searchMap.keywords.indexOf($scope.resultMap.brandList[i].text)>=0){
				return true;
			}
		}
		return false;
	}
	
	//添加搜索项
	$scope.addSearchItem=function(key,value){
		if(key=='category' || key=='brand'|| key=='price'){//如果点击的是分类或者是品牌
			$scope.searchMap[key]=value;
			$scope.searchNavMap[key]=value;
		}else{
			$scope.searchMap.spec[key]=value;
			$scope.searchNavMap.spec[key]="/"+value;
			console.log($scope.searchNavMap.spec[key]);
		}	
		$scope.search();
	}
	
	
	//移除搜索项
	$scope.removeSearchItem=function(key){
		if(key=='category' || key=='brand'|| key=='price'){//如果点击的是分类或者是品牌
			$scope.searchMap[key]="";
			$scope.searchNavMap[key]="";
		}else{
			
			delete $scope.searchMap.spec[key];//移除key
			$scope.searchNavMap.spec[key]='';//移除key
		}	
		$scope.search();
	}

	//移除搜索项
	$scope.setKeywords=function(keywords){
		$scope.searchMap.keywords=keywords;
		$scope.search();
	}
	$scope.loginName="";
	//显示当前用户名
	$scope.showName=function(){
		searchService.showName().success(function(response){
				console.log(response);	
				$scope.loginName=response.loginName;
		});
		
	}
	
});