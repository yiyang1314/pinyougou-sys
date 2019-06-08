 //控制层 
app.controller('orderItemController' ,function($scope,$controller   ,orderItemService){	
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		orderItemService.findAll().success(
			function(response){
				$scope.list=response;
				console.log(response);	
			}		
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){			
		orderItemService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(id){				
		orderItemService.findOne(id).success(
			function(response){
				$scope.entity= response;					
			}
		);				
	}
	
	//保存 
	$scope.save=function(){				
		var serviceObject;//服务层对象  				
		if($scope.entity.id!=null){//如果有ID
			serviceObject=orderItemService.update( $scope.entity ); //修改  
		}else{
			serviceObject=orderItemService.add( $scope.entity  );//增加 
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
		orderItemService.dele( $scope.selectIds ).success(
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
		orderItemService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
    
	
	
	/*
	 *
goodsId: 149187842867960
​​id: 918835712441212900
​​itemId: 1369280
​​num: 1
​​orderId: 918835712441212900
​​picPath: "http://192.168.25.133/group1/M00/00/00/wKgZhVnGbYuAO6AHAAjlKdWCzvg253.jpg"
​​price: 0.01
​​sellerId: "qiandu"
​​title: "精品半身裙（秋款打折） 移动3G 16G"
​​totalFee: 0.01
	 */
});	
