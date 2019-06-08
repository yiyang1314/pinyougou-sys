 //控制层 
app.controller('orderController' ,function($scope,$controller ,orderItemService  ,orderService){	
	
	//$controller('baseController',{$scope:$scope});//继承
	$scope.status=["未付款","已付款","已下单","已退单","订单关闭","已退款","已过期"];
	$scope.searchEntity={};//定义搜索对象 
	//订单明细
	$scope.orderItemEntitys={};
	$scope.orderItems={};
	$scope.loginName="";
	$scope.list=[];
	//分页控件配置 
	$scope.paginationConf = {
         currentPage: 1,
         totalItems: 10,
         itemsPerPage: 2,
         perPageOptions: [5, 10, 15, 20, 25],
         onChange: function(){
        	 $scope.reloadList();//重新加载
     	 }
	}; 
	
	//搜索
	$scope.search=function(page,rows){
		if($scope.loginName==""){
			$scope.showName();
			console.log($scope.loginName);
		}
		orderService.findOrderOne(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;
				//$scope.paginationConf.totalItems=response.total;//更新总记录数
				console.log("初始化");
				console.log($scope.list);
				//$scope.foreachOrder();
			}			
		);
	}
	
	//重新加载列表 数据
    $scope.reloadList=function(){
    	//切换页码  
    	$scope.search($scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage);	   	
    }
    

	
	$scope.selectIds=[];//选中的ID集合 

	//更新复选
	$scope.updateSelection = function($event, id) {		
		if($event.target.checked){//如果是被选中,则增加到数组
			$scope.selectIds.push( id);			
		}else{
			var idx = $scope.selectIds.indexOf(id);
            $scope.selectIds.splice(idx, 1);//删除 
		}
	}
	
	
	$scope.jsonToString=function(jsonString,key){
		
		var json= JSON.parse(jsonString);
		var value="";
		
		for(var i=0;i<json.length;i++){
			if(i>0){
				value+=",";
			}			
			value +=json[i][key];			
		}
				
		return value;
	}
	//分页
	$scope.findPage=function(page,rows){
		orderService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
				console.log(response.rows);
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

	//获取用户名
	$scope.showName=function(){
		orderService.showName().success(
			function(response){
				$scope.loginName=response.loginName;
				console.log($scope.loginName);
				$scope.searchEntity.userId=$scope.loginName;
			}			
		);
		
	}

   



	$scope.OderItemLists=[];
	$scope.orderEntity={};
	
	//获取支付订单
	$scope.findOrder=function(){
		console.log(1223);
		orderService.findOrder().success(
				function(response){
					console.log(response);
					$scope.orderEntity=response;
					console.log($scope.orderEntity);
				}			
			);
	}
	/**
	 * 生成二维码
	 */
	var qr=new QRious({
		element:document.getElementById("qrious"),
		size:250,
		level:'H',
		value:'http://169.254.3.154:8888/SM-pinyougou/orderid='+
				$scope.orderEntity.orderId+'&payment='+
				$scope.orderEntity.payment+'&status='+2
	});
	
	//遍历订单
	$scope.foreachOrder=function(){
		console.log('foreachOrder：'+$scope.list.length);
		for(var i=0;i<$scope.list.length;i++){
			var obj=$scope.list[i];
			$scope.findGoodsItem(obj.orderId);
		}
	}
	
	/**
	 * 查询商品详情
	 */
	$scope.findGoodsItem=function(orderId){
		$scope.orderItems.orderId=orderId;
		orderItemService.findOrderItemOne($scope.orderItems,$scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage).success(
				function(response){
					console.log(response);
				}			
			);
	}

	

	
});	
