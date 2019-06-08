 //控制层 
app.controller('userController' ,function($scope,$controller,$location,userService,loginService){	
	
	
	
	
	
	
	$scope.entity={};
	//用户注册功能
	$scope.register=function(){
		console.log($scope.entity.password);
		if($scope.password!=$scope.entity.password){
			alert("前后输入的密码不一致，请重新输入！");
			$scope.password="";
			$scope.entity.password="";
		}
		//异步请求
		userService.add($scope.entity,$scope.smscode).success(function(response){
			console.log(response);
			location.href="login.html";
		});
	}
	//发送验证码
	$scope.sendCode=function(){
		if($scope.entity.phone==""||$scope.entity.phone==null){
			alert("请填写验证码！");
			return ;
		}
		userService.sendCode($scope.entity.phone).success(function(response){
			alert(response);
			console.log(response);
		});
	}

	//用户登录功能
	$scope.loginweb=function(){
		if(""==$scope.entity.password){
			alert("请重新填写密码！");
			$scope.entity.password="";
			return;
		}
		if($scope.entity.password.length>6&&$scope.entity.password.length<=16){
			alert("请输入长度为6-16位的密码！");
			$scope.entity.password="";
			return;
		}
		if($scope.entity.username==""){
			alert("用户名不合法！");
			$scope.entity.username="";
			return;
		}
		//异步请求
		loginService.loginweb($scope.entity.username,$scope.entity.password).success(function(response){
			console.log(response);
			console.log($location);
			if(!response.status){
				alert(response.message);
				return ;
			}
			alert(response.user.id);
			var id=256341555+parseInt(response.user.id)-56464;
			location.href="home-index.html#?user="+id;
		});
	}
	
	
	

	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		citiesService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){			
		citiesService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(id){				
		citiesService.findOne(id).success(
			function(response){
				$scope.entity= response;					
			}
		);				
	}
	
	//保存 
	$scope.save=function(){				
		var serviceObject;//服务层对象  				
		if($scope.entity.id!=null){//如果有ID
			serviceObject=citiesService.update( $scope.entity ); //修改  
		}else{
			serviceObject=citiesService.add( $scope.entity  );//增加 
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
		citiesService.dele( $scope.selectIds ).success(
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
		citiesService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}   
	
	$scope.showName==function(){
		alert("csdvd");
		loginService.showName().success(function(response){
			console.log(response);	
			$scope.loginName=response.loginName;
		});
	};
	
	
	
});
