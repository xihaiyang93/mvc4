<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>websocket</title>

<script src="https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
<script src="http://cdn.sockjs.org/sockjs-0.3.min.js"></script>
<script>

$(function(){
    var webSocket = newWebSocket();
    
    sendMessage(webSocket);
});


/**
 * 创建webSocket
 */
function newWebSocket(){
    var webSocket;
    
    if ('WebSocket' in window) {
    	 webSocket = new WebSocket("ws:http://localhost:8080/mvc4/webSocketServer?user=123");
    	  alert(1);
    }
          
    else if ('MozWebSocket' in window) {
        webSocket = new MozWebSocket("ws:http://localhost:8080/websocket/webSocketServer");
         alert(2);
    }else {
        webSocket = new SockJS("http://localhost:8080/websocket/sockjs/webSocketServer");
        alert(3);
    }

     
    //打开webSocket连接
    webSocket.onopen = function (evnt) {
    };
    
    //接收信息
    webSocket.onmessage = function (evnt) {
        $("#console").append("(<font color='red'>"+evnt.data+"</font>)</br>")
    };
    
    //错误处理
    webSocket.onerror = function (evnt) {
    };
    
    //关闭webSocket
    webSocket.onclose = function (evnt) {
    }
    
    
    webSocket.onopen();
    return webSocket;
}


/**
 * 发送信息
 */
function sendMessage(webSocket){
    
    $("#send").click(function(){
        webSocket.send($("#message").val());
    });
}
</script>
</head>


<body>
	<div>
		<textarea id="message" style="width: 350px">send message!</textarea>
	</div>

	</br>

	<div>
		<button id="send">send</button>
	</div>

	<div id="console"></div>
</body>
</html>