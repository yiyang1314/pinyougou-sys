app.controller("itemController",function($scope,itemService,$location){
	
	$scope.specificationItems={};//存储用户选择的规格
	
	$scope.itemEntity={itemList:[],goodsDec:{},goods:{},itemcat:[]};
	$scope.goodsId=0;
	$scope.skuList=[];
	$scope.sku={};//当前选择的SKU
/*goodsDesc: {…}
​​
customAttributeItems: "[{\"text\":\"颜色\",\"value\":\"蓝色\"}]"
​​
itemEntity.goodsDec.goodsId: 149187842867991
​​
itemEntity.goodsDec.introduction: "春季新款 休闲男士中年夏季"
​​
itemImages: "[{\"color\":\"蓝色\",\"url\":\"../../images/20190423184326888rfJX.jpg\"}]"
​​
itemEntity.goodsDec.packageList: "春季新款 休闲男士中"
​​
itemEntity.goodsDec.saleService: "春季新"
​​
specificationItems: "[{\"attributeName\":\"服装尺码\",\"attributeValue\":[\"182cm - 190 cm/XXXL\",\"178cm - 182 cm/XXL\",\"170cm - 178 cm/XL\",\"165cm - 170 cm/L\",\"155cm - 165 cm/M\"]}]"
	*/
	
	
	//获取商品信息
	$scope.itemReload=function(){
		$scope.itemId=$location.search()['itemId'];

		itemService.findOne($scope.itemId).success(function(response){
			$scope.itemEntity.itemList=response.itemList;
			$scope.itemEntity.goodsDec=response.goodsDesc;
			$scope.itemEntity.goods=response.goods;
			$scope.itemEntity.itemcat.push(response.itemCat1);
			$scope.itemEntity.itemcat.push(response.itemCat2);
			$scope.itemEntity.itemcat.push(response.itemCat3);
			console.log(response);
			$scope.getItemlist();
			$scope.getGoodsDescAttr();
			$scope.loadSku();
		});
	}
	/*
		customAttributeItems: "[{\"text\":\"内存大小\"},{\"text\":\"颜色\"}]"​​
		itemImages: "[]"​​
		specificationItems: "[{\"attributeName\":\"网络制式\",\"attributeValue\":[\"移动3G\",\"移动4G\"]},{\"attributeName\":\"屏幕尺寸\",\"attributeValue\":[\"6寸\",\"5.5寸\"]}]"
	*/
	$scope.imageList=[];//商品展示图片地址
	$scope.customAttributeItems=[];//商品属性
	$scope.specificationItemsList=[];//规格属性
	
	//规格初始化
	$scope.getGoodsDescAttr=function(){
		console.log($scope.itemEntity.goodsDec);
		var DESC=$scope.itemEntity.goodsDec;
		

		if(JSON.parse(DESC.customAttributeItems).length>0)
			$scope.customAttributeItems=JSON.parse(DESC.customAttributeItems);//商品属性
		if(JSON.parse(DESC.specificationItems).length>0)
			$scope.specificationItemsList=JSON.parse(DESC.specificationItems);//规格属性
		if(JSON.parse(DESC.itemImages).length>0)
			$scope.imageList=JSON.parse(DESC.itemImages);//商品展示图片地址
		console.log($scope.specificationItemsList.length);
		
	}
	
	//属性初始化
	$scope.getItemlist=function(){
		for(var i=0;i<$scope.itemEntity.itemList.length;i++){
			var item={};
			item.price=$scope.itemEntity.itemList[i].price;
			item.title=$scope.itemEntity.itemList[i].title;
			item.spec=$scope.itemEntity.itemList[i].spec;
			item.id=$scope.itemEntity.itemList[i].id;
			$scope.skuList.push(item);
		}
		console.log($scope.skuList);
	}
	
		//数量加减
		$scope.addNum=function(x){
			$scope.num+=x;
			if($scope.num<1){
				$scope.num=1;
			}		
		}

	
	//用户选择规格
	$scope.selectSpecification=function(key,value){
		$scope.specificationItems[key]=value;		
		searchSku();//查询SKU
	}
	
	
	//判断某规格是否被选中
	$scope.isSelected=function(key,value){
		if($scope.specificationItems[key]==value){
			return true;
		}else{
			return false;
		}	
	}
	
	//加载默认SKU
	$scope.loadSku=function(){
		$scope.sku=$scope.skuList[0];
		$scope.specificationItems=JSON.parse($scope.sku.spec);
	}
	
	//匹配两个对象是否相等
	matchObject=function(map1,map2){
		map1=JSON.parse(map1);
		for(var k in map1){
			if(map1[k]!=map2[k]){
				return false;
			}			
		}
		for(var k in map2){
			if(map2[k]!=map1[k]){
				return false;
			}			
		}		
		return true;
		
	}
	
	//根据规格查询sku
	searchSku=function(){
		for(var i=0;i<$scope.skuList.length;i++){
			 if(matchObject($scope.skuList[i].spec ,$scope.specificationItems)){
				 $scope.sku=$scope.skuList[i];
				 return ;
			 }			
		}
		$scope.sku=$scope.skuList[0];
	}
	//评价生成
	$scope.parsenum=parseInt(Math.random()*10000);
	//评价生成
	$scope.price=parseFloat(Math.random()*3000+2500);
	//添加商品到购物车
	$scope.addToCart=function(){
		if(!($scope.num>0)){
			return ;
		}
		
		location.href="cart.html#?itemId="+$scope.sku.id+"&num="+$scope.num;
	}
	$scope.loginName="";
	//显示当前用户名
	$scope.showName=function(){
		itemService.showName().success(function(response){
				console.log(response);	
				$scope.loginName=response.loginName;
		});
		
	}
});