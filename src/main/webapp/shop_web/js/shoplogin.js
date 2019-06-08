$(function() {
    $("#number").click(function(){
    	alert("倒计时");
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