$(function() {
    $('input,textarea').placeholder();
    $("#number").click(function(){
//    	console.log($("#phone"));
//    	var v=$("#phone").val();
//    	if(v.length==0){
//    		alert("请填写手机号");
//    		return ;
//    	}
    	var numer=60;
	    var timer=setInterval(function(){
	    	numer--;
	    	$("#number").html("倒计时 "+numer+"s");
	    	if(!(numer>1)){
	    		$("#number").text("发送验证码");
	    	 clearInterval(timer);  
	    	}
	    	
	    },1000);
    });
})