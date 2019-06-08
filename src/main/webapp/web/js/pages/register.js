$(function() {
    $('input,textarea').placeholder();
    $("#number").click(function(){
    	var obj=$("input");
    	var phone=obj[3].value;
    	if(phone==""){
    		alert("手机号不能为空！");
    		return;
    	}
    	if(isNaN(phone)||phone.charAt(0)!='1'){
    		alert("手机号格式不合法！");
    		return;
    	}
    	var i=0;
    	if(i==1){return ;}
    	var numer=60;
	    var timer=setInterval(function(){
	    	numer--;
	    	$("#number").html("重新发送("+numer+"s)");
	    	if(!(numer>1)){
	    		$("#number").text("重新发送");
	    	 clearInterval(timer);  
	    	}
	    	
	    },1000);
	 });
})
