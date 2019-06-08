 //控制层 
app.controller('sellerController' ,function($scope,$controller,$location,sellerService){	
	
	$controller('baseController',{$scope:$scope});//继承
	
	$scope.entity={};
	//读取列表数据绑定到表单中  
	$scope.loadSeller=function(){
		var sellerId=$location.search()['sellerId'];
		$scope.findOne(sellerId);
	}  
	
	$scope.status=["未通过","审核通过","未审核","已关闭"];
	
	//读取列表数据绑定到表单中  
	$scope.findAll=function(){
		sellerService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){			
		sellerService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(id){				
		sellerService.findOne(id).success(
			function(response){
				$scope.entity= response;
				console.log($scope.entity);
				$scope.sellerUser= response;
			}
		);				
	}
	
	//保存 
	$scope.save=function(){				
		var serviceObject;//服务层对象  				
		if($scope.entity.sellerId!=null){//如果有ID
			serviceObject=sellerService.update($scope.sellerUser); //修改  
		}else{
			serviceObject=sellerService.add( $scope.entity  );//增加 
			$scope.entity ={};
		}				
		serviceObject.success(
			function(response){
				if(response.flag){
					//重新查询 
		        	$scope.reloadList();//重新加载
				}else{
					alert(response.message);
				}
			}		
		);				
	}
	
	$scope.add = function(){
		sellerService.add( $scope.entity  ).success(
			function(response){
				if(response.flag){
					// 重新查询 
		        	// $scope.reloadList();//重新加载
					location.href="shoplogin.html";
				}else{
					alert(response.message);
				}
			}		
		);	
	}
	
	 
	//批量删除 
	$scope.dele=function(){			
		//获取选中的复选框			
		sellerService.dele( $scope.selectIds ).success(
			function(response){
				if(response.flag){
					$scope.reloadList();//刷新列表
					$scope.selectIds = [];
				}						
			}		
		);				
	}
	
	$scope.searchEntity={};//定义搜索对象 
	
	//搜索
	$scope.search=function(page,rows){			
		sellerService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
  
	$scope.msg="";
	//检测密码的有效性
	$scope.checkPWD=function(){	
		if($scope.pwd==""||$scope.password==""||$scope.pwd1==""){
			$scope.msg="密码不能为空，请重新输入";
			return false;
		}else{
			$scope.msg="";
			
		}
		if($scope.password!=$scope.entity.password){
			$scope.msg="密码不正确，请重新输入";
			$scope.password="";
			return false;
		}else{
			$scope.msg="";
		}
		if($scope.pwd1!=$scope.pwd){
			alert("前后输入的密码不一致，请重新输入！");
			$scope.msg="前后输入的密码不一致，请重新输入";
			$scope.pwd1="";
			$scope.pwd="";
			return false;
		}else{
			$scope.msg="";
		}
		return true;
	}
	
	/**修改密码*/
	$scope.updatePWD=function(){
		if(!$scope.checkPWD()){
			alert($scope.msg);
			return ;
		}
		if($scope.msg==""){
			$scope.sellerUser.password=$scope.pwd;
			console.log($scope.sellerUser);
			$scope.save();
		}else{
			alert("操作有误，请认真核对信息！");
		}
	}
	
	
	
	
	//清空密码框
	$scope.fillPWD=function(){	
		$scope.pwd="";
		$scope.password="";
		$scope.pwd1="";
			return ;
	}
	
	
});	
