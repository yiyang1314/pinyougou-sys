 //控制层 
app.controller('itemCatController' ,function($scope,$controller,typeTemplateService,itemCatService){	
	
	$controller('baseController',{$scope:$scope});//继承
	$scope.category=[];
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		itemCatService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	$scope.entity={};
	//分页
	$scope.findPage=function(page,rows){			
		itemCatService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	$scope.updateEntity={};
	//查询实体 
	$scope.findOne=function(id){
		itemCatService.findOne(id).success(
			function(response){
				$scope.entity= response;
				$scope.updateEntity= response;
				if($scope.category.length>0){$scope.category=[];}
				if($scope.entity.parentId!=0)	 
					$scope.getCategory($scope.entity.parentId);
			}
		);
		$scope.tempLateFindAll();

	}
	
	//保存 
	$scope.save=function(){	
		var serviceObject;//服务层对象  				
		if($scope.entity.id!=null){//如果有ID
			serviceObject=itemCatService.update( $scope.entity ); //修改  
		}else{
			serviceObject=itemCatService.add( $scope.entity  );//增加 
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
		itemCatService.dele( $scope.selectIds ).success(
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
		itemCatService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
    
	//根据上级分类ID查询列表
	$scope.findByParentId=function(parentId){
		itemCatService.findByParentId(parentId).success(
			function(response){
				$scope.list=response;				
			}
		);		
	}
	
	$scope.grade=1;//当前级别
	//设置级别 
	$scope.setGrade=function(value){
		$scope.grade=value;
	}
	
	
	$scope.selectList=function(p_entity){
		//alert($scope.grade);
		
		if($scope.grade==1){
			$scope.entity_1=null;
			$scope.entity_2=null;
		}
		if($scope.grade==2){
			
			$scope.entity_1=p_entity;
			$scope.entity_2=null;
		}
		if($scope.grade==3){
			$scope.entity_2=p_entity;
		}
		
		$scope.findByParentId(p_entity.id);
		
	}
	

	
	$scope.templateList=[];
	$scope.tempLateFindAll=function(){
		typeTemplateService.findAll().success(function(response){
			$scope.templateList=$scope.strtoObj(response);
		});	
	}
	
	$scope.strtoObj=function(str){
		var temp=[];
		for(var i=0;i<str.length;i++){
			var obj={};
			obj.brandIds=JSON.parse(str[i].brandIds);
			obj.customAttributeItems=JSON.parse(str[i].customAttributeItems);
			obj.id=str[i].id;
			obj.name=str[i].name;
			obj.specIds=JSON.parse(str[i].specIds);
			temp.push(obj);
		}
		console.log(temp);
		return temp;
	}
	
	//查找分类实体
	$scope.getCategory=function(id){
		itemCatService.findOne(id).success(function(response){
			$scope.category.push(response);
			if(response.parentId!=0){
				$scope.getCategory(response.parentId);
			}
		});
	}
	
	//批量删除 
	$scope.iSdele=function(){
		itemCatService.findBySubId($scope.selectIds).success(function(response){
			console.log(response);
			confirm("目前存在子元素有："+response.subIds+" 可删除"+response.num+"条");
		});
		//获取选中的复选框			
		$scope.dele();
	}
	
	$scope.itemcatNames="顶级分类/";//显示目录
	$scope.itemcatName="顶级分类";//显示分类名称
	$scope.itemcatShow="";//显示分类名称
	/**
	 * 新建分类
	 */
	$scope.insert=function(s){
		$scope.itemcatShow=s;
		console.log($scope.entity_1);
		console.log($scope.entity_2);
		if($scope.entity_1!=null){
			$scope.itemcatNames+=$scope.entity_1.name+"/";
			$scope.itemcatName=$scope.entity_1.name;
			 $scope.entity.parentId=$scope.entity_1.id;
		}else{
			 //$scope.itemcatNames="顶级分类/";
			 $scope.entity.parentId=0;
		}
		if($scope.entity_2!=null){
			$scope.itemcatNames+=$scope.entity_2.name+"/";
			$scope.itemcatName=$scope.entity_2.name;
			 $scope.entity.parentId=$scope.entity_2.id;
		}
		$scope.tempLateFindAll();
		alert($scope.entity.parentId);
	}
	
	//显示分类名称
	$scope.getItemCatName=function(){
		if($scope.entity_1!=null){
			$scope.itemcatName=$scope.entity_1.name;
		}else{
			$scope.itemcatName="顶级分类";//显示分类名称
		}
		if($scope.entity_2!=null){
			$scope.itemcatName=$scope.entity_2.name;
		}	
	}
	
	
});	
