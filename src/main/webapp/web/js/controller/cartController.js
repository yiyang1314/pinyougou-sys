//购物车控制层 
app.controller('cartController',function($scope,$location,cartService,addressService,citiesService,provincesService,areasService,itemService){
	$scope.supperPath='D:/20152203/tts9/workbases/.metadata/.plugins/org.eclipse.wst.server.core/tmp1/wtpwebapps/SSM-pinyougou/';
	$scope.totalmap={};
	$scope.saveaddress={};
	$scope.item={'provinceId':""};
	$scope.city={'cityId':""};
	$scope.address={};
	$scope.loginName="";
	$scope.detailAddress="";
	//添加购物车
	$scope.loadCart=function(){
		var itemId=$location.search()['itemId'];
		var num=$location.search()['num'];
		console.log(itemId);
		console.log(num);
		console.log(itemId!=''&&num!='');
		console.log("num= "+num+ "itemId= "+itemId);
		
		if(itemId!=''&&num!=''){
			$scope.addGoodsToCartList(itemId,num);
		}
	
		$scope.findCartList();
		
		
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
	
	//查询购物车列表
	$scope.findCartList=function(){
		cartService.findCartList().success(
			function(response){
				$scope.cartList=response;
				$scope.totalmap=cartService.sum(response,$scope.selectIds);
				console.log($scope.totalmap);
			}
		);		
	}

	//计算选中的商品价格
	$scope.addGoodsToCartList1=function(iteId,num){
		cartService.addGoodsToCartList(iteId,num).success(
				function(response){
					if(response.success){
						$scope.cartList=response;
						$scope.findCartList();
					}else{
						alert(response.message);
						console.log(response);
					}
					
				}
			);	
	}
	
	
	$scope.addGoodsToCartList=function(iteId,num){
		$scope.addGoodsToCartList1(iteId,num);
		
	}
	
	//获取当前登录账号的收货地址
	$scope.findAddressList=function(){
		cartService.findAddressList().success(
				function(response){
						console.log(response);
						$scope.addressList=response;
						console.log($scope.addressList);
						if($scope.addressList.length==0){
							console.log("没有收货地址！");
							
							return ;
						}
						for(var i=0;i<$scope.addressList.length;i++){
							if($scope.addressList[i].isDefault=="1"){
								$scope.address=$scope.addressList[i];
								console.log($scope.addressList[i]);
								break;
							}
						}
				}
			);
	}
	
	//设置当前选中的收货地址
	$scope.selectAddress=function(address){
		$scope.address=address;
	}
	//判断当前收货地址是否被选中
	$scope.isSelectAddress=function(address){
		if(address==$scope.address){
			return true;
		}
		return false;
	}
	
	$scope.order={paymentType:'1'};
	//设置默认的选择付款方式
	$scope.selectPayType=function(type){
		$scope.order.paymentType=type;
	}
	
	
	
	
	//保存订单
	$scope.submitOrder=function(){
		console.log($scope.address);
		$scope.order.receiverAreaName=$scope.address.address;
		$scope.order.receiverMobile=$scope.address.mobile;
		$scope.order.receiver=$scope.address.contact;
		$scope.order.status='0';
		cartService.submitOrder( $scope.order ).success(
				function(response){
					if(response.success){
						//页面跳转
						if($scope.order.paymentType=='1'){//如果是微信支付，跳转到支付页面
							location.href="pay.html";
						}else{//如果货到付款，跳转到提示页面
							location.href="paysuccess.html";
						}					
					}else{
						alert(response.message);	//也可以跳转到提示页面				
					}				
				}				
			);		
	}
	


	//isDefault: "0"
	$scope.addressENTITY={address:{"province":'',"city":'',"town":''}};
	
	


	//读取省份
	$scope.openAddress=function(obj){
		if($scope.saveaddress.id!=null){
			$scope.saveaddress=obj;
		}
		provincesService.findAll().success(function(response){
			console.log(response);
			$scope.provinces=response;
		});
	}
	//市
	$scope.getCities=function(){
		$scope.saveaddress.provinceId=$scope.provinceId.provinceid;

		$scope.detailAddress+=$scope.provinceId.province+" ";
		citiesService.findCities($scope.provinceId.provinceid).success(function(response){
			console.log(response);
			$scope.cityList=response;
		});
	}
	//县区
	$scope.getAreas=function(){
		$scope.saveaddress.cityId=$scope.city.cityid;
		$scope.detailAddress+=$scope.city.city+" ";
		areasService.findAreas($scope.city.cityid).success(function(response){
			console.log(response);
			$scope.townList=response;
		});
	}
	
	/** 保存地址 */
	$scope.saveAddress=function(){
		$scope.detailAddress+=$scope.town.area+" ";
		$scope.saveaddress.userId=$scope.loginName;
		$scope.saveaddress.address=$scope.detailAddress+$scope.saveaddress.address;
		console.log($scope.saveaddress.address);
		addressService.add($scope.saveaddress).success(function(response){
			console.log(response);
			$scope.findAddressList();
		});
	}
	
	/** 删除地址 */
	$scope.delAddress=function(){
		addressService.dele($scope.selectIds).success(function(response){
			$scope.findAddressList();
			location.reload(); //history.go(0);
		});
	}
	$scope.loginName="";
	//显示当前用户名
	$scope.showName=function(){
		cartService.showName().success(function(response){
				console.log(response);	
				$scope.loginName=response.loginName;
		});
		
	}
	

	$scope.cart0lists=[];
	$scope.allCheckboxs="";
	$scope.cartCheckboxs="";
	$scope.itemCheckboxs="";
	$scope.orderItemLists={orderItemList:[],sellerId: "",sellerName:""};
	

	$scope.selectItem=function($event, cart,shoptype,item){
		$scope.allCheckboxs=shoptype;
		if(shoptype=="all"){
			if($event.target.checked){//如果是被选中,则增加到数组
				$scope.cart0lists=cart;
				console.log($scope.cart0lists);			
			}else{
				//var idx = $scope.selectIds.indexOf(id);
	            $scope.cart0lists.splice(0);//删除 
	            console.log($scope.cart0lists);	
			}
		}
		if(shoptype=="cart"){
			if($event.target.checked){//如果是被选中,则增加到数组
				$scope.orderItemLists=cart;
				$scope.cart0lists.push($scope.orderItemLists);	
			}else{
				for(var i=0;i<$scope.cart0lists.length;i++){
					var id=$scope.cart0lists[i].sellerId;
					if(id==cart.sellerId){
						var idx =$scope.cart0lists.indexOf($scope.cart0lists[i]);
						$scope.cart0lists.splice(idx,1);//删除 
					} 
				}
			}

		}
		if(shoptype=="item"){
			if($event.target.checked){//如果是被选中,则增加到数组
				console.log(item);
				$scope.orderItemLists.orderItemList.push(item);
				$scope.orderItemLists.sellerId=cart.sellerId;
				$scope.orderItemLists.sellerName=cart.sellerName;
				console.log($scope.orderItemLists);
				$scope.cart0lists.push($scope.orderItemLists);
				console.log($scope.cart0lists);	
			}else{
				for(var i=0;i<$scope.cart0lists.length;i++){
					var obj=$scope.cart0lists[i];
					console.log($scope.cart0lists[i]);
					for(var j=0;j<$scope.cart0lists[i].orderItemLists.length;j++){
						var itemObj=obj.orderItemLists[j];
						if(obj.sellerId==cart.sellerId&&itemObj.itemId==item.itemId){
							alert(id==cart.sellerId+"cart");
							var idx =$scope.cart0lists[i].orderItemLists[j].indexOf(item);
							//alert(idx);
							$scope.cart0lists[i].orderItemLists[j].splice(idx,1);//删除 
						} 
					}

				}
			}

		}
		if(shoptype=="delete"){
			
		}
		
		
	}
	
	//选购买的商品
	$scope.SelectionCart = function($event, cart,shoptype) {		
		console.log($scope.orderItemLists);
	}
	
	//删除购物车中的商品
	$scope.deleteCartItem=function(){
		for(var j=0;j<$scope.orderItemLists.length;j++){
			var itemobj=($scope.orderItemLists)[j];
			$scope.addGoodsToCartList1(itemobj.itemId,-itemobj.num);
			$scope.findCartList();
		}
	}

	//移除选中的商品
	$scope.deleteCartItem=function($event,item){
		alert(item.itemId);
		for(var j=0;j<$scope.orderItemLists.length;j++){
			var itemobj=($scope.orderItemLists)[j];
			//alert(itemobj.itemId==item.itemId);
			if(itemobj.itemId==item.itemId){
				var idx = $scope.orderItemLists.indexOf(itemobj);
				$scope.orderItemLists.splice(idx, 1);//删除 
			}
		}
	}
});