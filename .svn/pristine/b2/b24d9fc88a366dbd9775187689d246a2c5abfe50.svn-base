<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
<title>index Page</title>
<link href="css/bootstrap.css" rel="stylesheet" />
<style>
li:hover {
	background-color: #bab0b0;
}

#gameshow {
	width: 1050px;
	overflow: auto;
}

#gameshow>button[type=button] {
	float: left;
	width: 50px;
	height: 50px;
}

#userList {
	position: absolute;
	width: 250px;
	z-index: 100;
}

.head {
	height: 80px;
	width: 100%;
}

.head>* {
	float: left;
}

.h1vs {
	padding-left: 100px;
	padding-right: 100px;
}

.mydiv {
	width: 40px;
	height: 40px;
	background-color: blue;
	margin-top: 20px;
	margin-left: 10px;
}

.oppdiv {
	width: 40px;
	height: 40px;
	background-color: blue;
	margin-top: 20px;
	margin-right: 10px;
}

.middiv {
	width: 100%;
	overflow: auto;
	height: 1900px;
	background-color: black;
	padding-left: 10%;
}

.middiv>div {
	float: left;
}

.leftside {
	position: relative;
}

#userList {
	left: 40px;
	top: 0px;
}

.btn.btn-default {
	border-radius: 0px;
}

.head.alert.alert-warning {
	padding-bottom: 100px;
	padding-left: 10%;
}

#msgdiv {
	position: absolute;
	width: 400px;
	height: 250px;
	top: -10px;
	left: 800px;
}

#msgdiv1 {
	width: 300px;
	height: 150px;

	position: absolute;
	top: 40px;
	left: 50px;
	overflow-y: scroll;
	
}
#msgdiv1 *{
	padding: 4px;
	color: white;
	word-wrap: break-word;
}
h1, .h1 ,h2,h3{
    font-size: 36px;
    text-shadow: 3px 3px 3px;
}
body>*{
	min-width: 1300px;
}
.shengfu{
	padding-left: 10px;
	padding-top: 10px;
}
.shengfu1{
	padding-right: 10px;
	padding-top: 10px;
}
</style>
</head>

<body>





	<div class="head alert alert-warning">
		<h1>${username }</h1>
		<div class="mydiv"></div>
		<div class="shengfu">
			<h6>胜：6</h6>
			<h6>负：6</h6>
		</div>
		<h1 class="h1vs">vs</h1>
		<div class="shengfu1">
			<h6>胜：6</h6>
			<h6>负：6</h6>
		</div>
		<div class="oppdiv"></div>
		<h1 id="opp">jb</h1>
		<div id="msgdiv">
			<div style="width:100%;height:100%;position: relative;">
				<img alt="" src="blackboard.png" style="width: 100%;height:100% ">

				<div id="msgdiv1">
				<ul class="list-group" id="msgContent">
					
				</ul>
				</div>
				
			</div>
		</div>
	</div>
	<div style="overflow: auto;padding-left: 10%;">
		<button type="button" class="btn btn-default" style="float: left;">
			<span class="glyphicon glyphicon-time"></span>
		</button>
		<h2 id="time"
			style="float: left;margin-top:0px;margin-bottom: 0px;padding-left: 10px"></h2>
	</div>

	<h3 id="turn" class="alert alert-info" style="padding-left: 10%"></h3>
	<div class="middiv">
		<div class="leftside">
			<div>
				<button type="button" class="btn btn-default"
					aria-label="Left Align"
					onclick="$('#userList').css('display')=='block'?  $('#userList').hide():$('#userList').show()">
					<span class="glyphicon glyphicon-align-left" aria-hidden="true"></span>
				</button>
			</div>
			<div>
				<button type="button" class="btn btn-default"
					aria-label="Left Align"
					onclick="$('#userList').css('display')=='block'?  $('#userList').hide():$('#userList').show()">
					<span class="glyphicon glyphicon-heart-empty" aria-hidden="true"></span>
				</button>
			</div>
			<ul class="list-group" id="userList">
				<c:forEach items="${list }" var="keyword">
					<li tag="${keyword.userid }" class="list-group-item">${keyword.userid }</li>

				</c:forEach>
			</ul>
		</div>


		<div id="gameshow">
			<canvas id="qipan" width="1050" height="1050"> </canvas>
		</div>
	</div>
</body>

</html>

<script src="<%=request.getContextPath()%>/js/jquery-2.1.3.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.myConfirm.js"></script>
<script>
	var websocket = null;
	var A, B;
	var username = "${username}";
	var timer;

	var state, turn;
</script>
<script>
	var qipan, cxt;
	function build() {

		qipan = document.getElementById("qipan");
		cxt = qipan.getContext('2d');
		cxt.clearRect(0, 0, 1050, 1050);
		var img = new Image();
		img.width = 1050;
		img.height = 1050;
		img.src = "timg.jpg";
		cxt.drawImage(img, 0, 0, 1050, 1050);

		cxt.moveTo(0, 0);
		cxt.lineTo(0, 1050);
		cxt.lineTo(1050, 1050);
		cxt.lineTo(1050, 0);
		cxt.lineTo(0, 0);
		cxt.stroke();

		cxt.beginPath();
		cxt.moveTo(50, 50);
		cxt.lineTo(50, 1000);
		cxt.lineTo(1000, 1000);
		cxt.lineTo(1000, 50);
		cxt.lineTo(50, 50);
		cxt.stroke();

		drawPoint = function(x, y, color) {
			cxt.beginPath();
			cxt.arc(x * 50 + 50, y * 50 + 50, 20, 0, 2 * Math.PI, true);
			cxt.fillStyle = color;
			cxt.fill();
			cxt.stroke();
		}

		var drawLine = function(x1, y1, x2, y2) {
			cxt.beginPath();
			cxt.moveTo(x1, y1);
			cxt.lineTo(x2, y2);
			cxt.stroke();
		}
		for (var y = 100; y < 1000; y += 50)
			drawLine(50, y, 1000, y);
		for (var x = 100; x < 1000; x += 50)
			drawLine(x, 50, x, 1000);

		for (var i = 0; i < state.length; ++i)
			for (var j = 0; j < state[i].length; ++j) {
				if (state[i][j] == 'a') {
					drawPoint(j, i, '#cccccc')
				} else if (state[i][j] == 'b') {
					drawPoint(j, i, 'black')
				}
			}

		timer = daojishi();
		qipan.onclick = function(e) {
			if (turn != username)
				return;
			var move = [ [ 0, 0 ], [ 50, 0 ], [ 50, 50 ], [ 0, 50 ] ];
			var x = Math.floor(e.offsetX / 50) * 50, y = Math
					.floor(e.offsetY / 50) * 50;
			//    alert(x + " " + y);
			for (var i = 0; i < 4; ++i) {
				var xx = x + move[i][0], yy = y + move[i][1];
				if (((e.offsetX - xx) * (e.offsetX - xx) + (e.offsetY - yy)
						* (e.offsetY - yy)) <= 400) {
					var xindex = xx / 50 - 1, yindex = yy / 50 - 1;
					if (state[yindex][xindex] != 'o')
						return;

					var message = new Object();
					message['type'] = 9;
					message['to'] = (A == "${username}" ? B : A);
					message['x'] = yindex;
					message['y'] = xindex;
					message = JSON.stringify(message);
					websocket.send(message);
					window.clearInterval(timer);

					return;
				}
			}

		}
	}
</script>
<script type="text/javascript">
	function clear(gameState) {
		window.clearInterval(timer)
		if (gameState.winner != null) {
			//alert("win " + gameState.winner);
			$.confirm({
				title : gameState.winner + "赢了"
			})
		}
		A = gameState.A;
		B = gameState.B;
		if (A == username) {
			$(".mydiv").css("background-color", '#cccccc');
			$(".oppdiv").css("background-color", 'black');
			$("#opp").text(B)
		} else {
			$(".mydiv").css("background-color", 'black');
			$(".oppdiv").css("background-color", '#cccccc');
			$("#opp").text(A)
		}
		state = gameState.state;
		turn = gameState.turn;
		$("#turn").text("轮到" + turn);
		build();
		//	$('#gameshow').html('');
		/* 	for (var i = 0; i < state.length; ++i)
				for (var j = 0; j < state[i].length; ++j) {
					$('#gameshow')
							.append(
									"<button type='button' value='" + state[i][j] + "' x='" + i + "' y='" + j + "'></button>");
					var by = $("#gameshow")
							.find('button[x=' + i + '][y=' + j + ']');
					if (by.val() == 'a') {
						by.css('background-color', 'red');
						by.attr("class", "glyphicon glyphicon-ok")
					} else if (by.val() == 'b') {
						by.css('background-color', 'blue');
						by.attr("class", "glyphicon glyphicon-remove")
					}
				}
			//if(turn=="${username}")
			timer = daojishi();
		 */
	}
	//判断当前浏览器是否支持WebSocket
	if ('WebSocket' in window) {
		websocket = new WebSocket(
				"ws://localhost:8080/ssmws/websocket?username=${username}");
		//连接发生错误的回调方法
		websocket.onerror = function() {
			//  setMessageInnerHTML("WebSocket连接发生错误");
		};

		//连接成功建立的回调方法
		websocket.onopen = function() {
			// setMessageInnerHTML("WebSocket连接成功");
			alert("success")
		}

		//接收到消息的回调方法
		websocket.onmessage = function(event) {

			event = JSON.parse(event.data);

			//  setMessageInnerHTML(event.data);
			if (event.type == 2) {
				var list = event.list;
				for (var i = 0; i < list.length; ++i)
					$('li[tag=' + list[i] + ']').append(
							'<span class="badge">online</span>')
			} else if (event.type == 1) {
				$('li[tag=' + event.from + ']').append(
						'<span class="badge">online</span>')
			} else if (event.type == 3) {
				$('li[tag=' + event.from + ']').find('span').remove();
			} else if (event.type == 5) {

				//var r = confirm(event.from + " add");
				$.confirm({
					title : event.from + "邀请你对战",
					content : 'Simple confirm!',
					confirmButton : 'Yes',
					cancelButton : 'NO',
					confirm : function() {
						var message = new Object();
						message['type'] = 7;
						message['to'] = event.from;
						message = JSON.stringify(message);
						websocket.send(message);
					},
					cancel : function() {
						var message = new Object();
						message['type'] = 6;
						message['to'] = event.from;
						message = JSON.stringify(message);
						websocket.send(message);
					}
				});
				// if (r == true) {
				// 	var message = new Object();
				// 	message['type'] = 7;
				// 	message['to'] = event.from;
				// 	message = JSON.stringify(message);
				// 	websocket.send(message);

				// } else {
				// 	var message = new Object();
				// 	message['type'] = 6;
				// 	message['to'] = event.from;
				// 	message = JSON.stringify(message);
				// 	websocket.send(message);

				// }
			} else if (event.type == 71) {
				
				var d="("+new Date().toString()+")"
				$("#msgContent").append("<li>"+d+event.from + "同意了"+"</li>");
				var div=document.getElementById("msgdiv1");
				div.scrollTop = div.scrollHeight;
				//build();
				//alert(event.from + "同意了")
			} else if (event.type == 61) {
				
				var d="("+new Date().toString()+")"
				$("#msgContent").append("<li>"+d+event.from + "拒绝了"+"</li>");
				var div=document.getElementById("msgdiv1");
				div.scrollTop = div.scrollHeight;
				//alert(event.from + "拒绝了")
			} else if (event.type == 8) {
				//	alert(JSON.stringify(event))
				clear(event);
			} else if (event.type == 11) {

				
				var d="("+new Date().toString()+")"
				$("#msgContent").append("<li>"+d+event.from + "离开了"+"</li>");
				var div=document.getElementById("msgdiv1");
				div.scrollTop = div.scrollHeight;
				
			}

		}

		//连接关闭的回调方法
		websocket.onclose = function() {
			// setMessageInnerHTML("WebSocket连接关闭");
		}

		//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
		window.onbeforeunload = function() {

			closeWebSocket();
		}
	} else {
		alert('当前浏览器 Not support websocket')
	}

	//关闭WebSocket连接
	function closeWebSocket() {
		var x;
		if (A != undefined && B != undefined) {
			x = (A == "${username}" ? B : A);
			var message = new Object();
			message['type'] = 11;
			message['to'] = x;
			message = JSON.stringify(message);
			websocket.send(message);
		}
		websocket.close();
	}

	//发送消息
	function send() {
		var message = document.getElementById('text').value;
		websocket.send(message);
	}
</script>
<script>
	$('li').click(function(e) {
		e.preventDefault();
		var tag = $(this).attr('tag');
		var message = new Object();
		$.confirm({
			title :   "向"+tag+"宣战",
			content : '',
			confirmButton : 'Yes',
			cancelButton : 'NO',
			confirm : function() {
				var message = new Object();
				message['type'] = 4;
				message['to'] = tag;
				message = JSON.stringify(message);
				websocket.send(message);
			},
			cancel : function() {
				
			}
		});

	});
	$('#gameshow').on('click', 'button[type=button]', function() {
		if (turn != "${username}")
			return;
		if ($(this).val() != 'o')
			return;
		var x = $(this).attr('x');
		var y = $(this).attr('y');
		var message = new Object();
		message['type'] = 9;
		message['to'] = (A == "${username}" ? B : A);
		message['x'] = parseInt(x);
		message['y'] = parseInt(y);
		message = JSON.stringify(message);
		websocket.send(message);
		window.clearInterval(timer);

	})
</script>
<script src="<%=request.getContextPath()%>/js/index1.js"
	charset="gb2312"></script>