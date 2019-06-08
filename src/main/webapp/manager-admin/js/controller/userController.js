 //控制层 
app.controller('userController' ,function($scope,$controller   ,userService){	
	
	$controller('baseController',{$scope:$scope});//继承
	/*
	{{entity.accountBalance}}	null
	{{entity.birthday}}	null
	{{entity.created}}	"2017-08-20 12:09:27"
	{{entity.email}}	null
	{{entity.experienceValue}}	null
	{{entity.headPic}}	null
	{{entity.id}}	7
	{{entity.isEmailCheck}}	null
	{{entity.isMobileCheck}}	null
	{{entity.lastLoginTime}}	null
	{{entity.name}}	null
	{{entity.nickName}}	null
	{{entity.password}}	"123456"
	{{entity.phone}}	"13669669966"
	{{entity.points}}	null
	{{entity.qq}}	null
	{{entity.sex}}	null
	{{entity.sourceType}}	null
	{{entity.status}}	"1"
	{{entity.updated}}	"2017-08-20 12:09:27"
	{{entity.userLevel}}	null
	{{entity.username}}	"zhaoliu"
	*/
	
	
	//读取列表数据绑定到表单中  
	$scope.findAll=function(){
		userService.findAll().success(
			function(response){
				$scope.list=response;
				console.log($scope.list);
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){			
		userService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(id){
		alert("5555");
		userService.findOne(id).success(
			function(response){
				console.log(response);
				$scope.entity= response;
				
			}
		);				
	}
	
	//保存 
	$scope.save=function(){				
		var serviceObject;//服务层对象  				
		if($scope.entity.id!=null){//如果有ID
			serviceObject=userService.update( $scope.entity ); //修改  
		}else{
			serviceObject=userService.add( $scope.entity  );//增加 
		}				
		serviceObject.success(
			function(response){
				if(response.success){
					//重新查询 
		        	$scope.reloadList();//重新加载
				}else{
					alert(response.message);
				}
			}		
		);				
	}
	
	 
	//批量删除 
	$scope.dele=function(){			
		//获取选中的复选框			
		userService.dele( $scope.selectIds ).success(
			function(response){
				if(response.success){
					$scope.reloadList();//刷新列表
					$scope.selectIds=[];
				}						
			}		
		);				
	}
	
	$scope.searchEntity={};//定义搜索对象 
	
	//搜索
	$scope.search=function(page,rows){			
		userService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
    
});	
