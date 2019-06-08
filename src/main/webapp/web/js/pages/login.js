$(function() {
    $('input,textarea').placeholder();
    $(".sendcode").click(function(){
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