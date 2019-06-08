 //控制层 
app.controller('orderController' ,function($scope,$controller,$location,orderService){	
	
	$controller('baseController',{$scope:$scope});//继承
	$scope.status=["未付款","已付款","已下单","已退单","订单关闭","已退款","已过期"];
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		alert(15555);
		orderService.findAll().success(
			function(response){
				$scope.list=response;
				console.log(response);
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){			
		orderService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(id){				
		orderService.findOne(id).success(
			function(response){
				$scope.entity= response;					
			}
		);				
	}
	
	//保存 
	$scope.save=function(){				
		var serviceObject;//服务层对象  				
		if($scope.entity.id!=null){//如果有ID
			serviceObject=orderService.update( $scope.entity ); //修改  
		}else{
			serviceObject=orderService.add( $scope.entity  );//增加 
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
		orderService.dele( $scope.selectIds ).success(
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
		
		orderService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
    /**
     * 
buyerMessage: null
​​buyerNick: null
​​buyerRate: null
​​closeTime: null
​​consignTime: null
​​createTime: "2017-08-24 20:42:25"
​​endTime: null
​​expire: null
​​invoiceType: null
​​orderId: 1
​​payment: null
​​paymentTime: null
​​paymentType: "1"
​​postFee: null
​​receiver: "李嘉诚"
​​receiverAreaName: "金燕龙办公楼"
​​receiverMobile: "13900112222"
​​receiverZipCode: null
​​sellerId: null
​​shippingCode: null
​​shippingName: null
​​sourceType: null
​​status: "0"
​​updateTime: "2017-08-24 20:42:25"
userId: "lijialong"
     */
	$scope.sellerId="";
	$scope.loadSeller=function(){
		$scope.sellerId=$location.search()['sellerId'];
		$scope.searchEntity.sellerId=$scope.sellerId;
		$scope.reloadList();
	}
});	
