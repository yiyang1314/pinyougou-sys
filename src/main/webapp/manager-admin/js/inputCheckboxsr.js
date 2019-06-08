$(document).ready(function(){
    //选择所有的input类型为checkbox且name="items"，通过attr改变其checked属性
    $("input").click(function(){
        if($(this).attr("checked")){
            $(":checkbox[type='checkbox']").attr("checked",true);
        }else{
            $(":checkbox[type='checkbox']").attr("checked",false);
        }
    });
    
});
