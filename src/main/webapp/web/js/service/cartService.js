//购物车服务层
app.service('cartService',function($http){
	//购物车列表
	this.findCartList=function(){
		return $http.get('../cart/findCartList.do');		
	}
	
	
	this.addGoodsToCartList=function(itemId,num){
		return $http.get('../cart/addGoodsToCartList.do?itemId='+itemId+"&num="+num);			
	}
	
	this.sum=function(cartList,ids){
		console.log(cartList);
		console.log("ids="+ids);
		var totalmap={totalNum:0,totalFee:0};
		for(var i=0;i<cartList.length;i++){
			var cart=cartList[i];
			console.log(cart.orderItemList);
			for(var j=0;j<cart.orderItemList.length;j++){
				var orderItem=cart.orderItemList[j];
				console.log(cart.orderItemList[j]);
				totalmap.totalNum=totalmap.totalNum+orderItem.num;
				totalmap.totalFee=totalmap.totalFee+orderItem.totalFee;
			}
			
		}
		console.log(totalmap);
		return totalmap;
	}
	
	
	//获取当前登录账号的收货地址
	this.findAddressList=function(){
		return $http.get('../address/findListByLoginUser.do');		
	}
	
	
	
	//获取当前登录账号的收货地址
	this.submitOrder=function(order){
		return $http.post('../order/add.do',order);		
	}
	
	this.showName=function(){
		return $http.get('../login/name.do');
	}
	
});