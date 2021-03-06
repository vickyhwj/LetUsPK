<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>
<title></title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="css/bootstrap.css" rel="stylesheet">
<script src="js/jquery-2.1.3.js">
	
</script>
<script src="js/bootstrap.js">
	
</script>
<style>
.huiqi{
	width: 20px;
	padding: 3px;
	background-color: rgba(193, 172, 172, 0.67);
	position: absolute;
	left: 0px;
	top:100px;
	z-index: 20;
}
#huiqilist {
    position: absolute;
    left: 20px;
    top: 0px;
    width: 100px;
	display: none;
	
}
.body{
	background-color: #ebebeb
}
.controll>div {
	text-align: center;
}

#firstCanvas_div {
	width: 80%;
	margin: auto;
	background: url('muwen.jpg');
}

#firstCanvas {
	width: 100%;
}

.btn.btn-default {
	width: 50px;
	height: 50px;
}

#show {
	width: 100%;
	height: 20px;
}
.row.rank {
    background-color: white;
    padding: 5px;
    border-radius: 10px;
    margin-top: 20px;
    margin-bottom: 20px; 
}
#global-footer {
    padding: 40px 20px;
    color: #a6a6a6;
    font-size: 0.8em;
    text-align: center;
    background: #18191b;
    border-bottom: #0a0a0a 5px solid;
}
.out {
    position: fixed;
    top: 60px;
    background-color: yellow;
    padding: 10px;
    z-index: 100;
}
</style>
</head>

<body style="background-color: #ebebeb">
<div class="out">
out
</div>
<div class="huiqi" >
				悔<br>棋
				<div id="huiqilist">
					<ul class="list-group" style="list-style: none;">
					
					</ul>
				</div>
				
			</div>

    
	<div class="container">
		<div class="row">
			<div class="col-sm-8">
				<div id="firstCanvas_div">
					<canvas id="firstCanvas" width="300" height="300"></canvas>

				</div>
			</div>
			<div class="col-sm-4">
				<div class="friendlist">
					<button id="show"></button>
					<ul class="list-group" id="fl">

						<c:forEach items="${list}" var="keyword">
							<li da="${keyword.userid }" class="list-group-item">${keyword.userid }</li>

						</c:forEach>
					</ul>
				</div>
				<div class="controll">
					<div>
						<button type="button" class="btn btn-default" tag="dir" da="0">
							<span class="glyphicon glyphicon-arrow-up" aria-hidden="true"></span>
						</button>
					</div>
					<div>
						<button type="button" class="btn btn-default" tag="dir" da="2">
							<span class="glyphicon glyphicon-arrow-left" aria-hidden="true"></span>
						</button>
						<button type="button" class="btn btn-default" id="go">go</button>
						<button type="button" class="btn btn-default" tag="dir" da="3">
							<span class="glyphicon glyphicon-arrow-right" aria-hidden="true"></span>
						</button>
					</div>
					<div >
						<button type="button" class="btn btn-default" tag="dir" da="1">
							<span class="glyphicon glyphicon-arrow-down" aria-hidden="true"></span>
						</button>
						<span id="turn" style="position: fixed;left: 0px;top:10px;font-size: 20px">轮到</span>
					</div>
				</div>
			</div>
		</div>
		<div class="row rank" >
			<h3><span class="label label-success">rank(top20)</span></h3>
			<table class="table table-hover">
				<thead>
					<tr>
						<th>userid</th>
						<th>win</th>
						<th>lose</th>
					</tr>
				</thead>
				<tbody id="tb">
				
					
				</tbody>
			</table>

		</div>
	</div>

	<footer id="global-footer">
	  <p>Lovingly created and maintained by <a href="http://john.onolan.org">John O'Nolan</a> + <a href="http://erisds.co.uk">Hannah Wolfe</a> + an amazing group of <a href="https://github.com/TryGhost/Ghost/contributors">contributors</a>.</p>
	    <hr>
	  <p>All code copyright (C) 2014 The Ghost Foundation - Released under the <a href="http://tryghost.org/about/license.html">MIT</a> License.<br>
	    All documentation is released under the Creative Commons <a href="http://creativecommons.org/licenses/by/3.0/">By-3.0</a> License.</p>
	</footer>
</body>

</html>
<script type="text/javascript">
	ip="localhost:8080"
	var websocket;
 	var username = "${username}";
         var datafrom;
            var turn;
            var friendlist = [];
            
            var chessBoard = [];
            for (var i = 0; i < 15; ++i) {
                chessBoard[i] = [];
                for (var j = 0; j < 15; ++j)
                    chessBoard[i][j] = 'o';
            }
            var newX = 7, newY = 7;
	var canvas = document.getElementById('firstCanvas');
	context = canvas.getContext('2d');
	var clean = function() {
		var drawLine = function(x1, y1, x2, y2) {
			context.beginPath();
			context.moveTo(x1, y1);
			context.lineTo(x2, y2);
			context.stroke();
			context.closePath();

		}
		drawPoint = function(x, y, color) {
			context.beginPath();
			var xx = 20 * x + 10;
			var yy = 20 * y + 10;
			context.arc(xx, yy, 8, 0, 2 * Math.PI, true);
			context.fillStyle = color;
			//  context.fillStyle = 'black';
			context.fill();
			context.closePath();

		}
		var jiaocha = function(x, y, color) {
			var xx = 20 * x;
			var yy = 20 * y;
			drawLine(xx, yy, xx + 20, yy + 20);
			drawLine(xx, yy + 20, xx + 20, yy);
		}

		context.clearRect(0, 0, 300, 300);
		context.fillStyle = 'black';
		context.lineWidth = 2;

		for (var i = 0; i <= 300; i += 20)
			drawLine(i, 0, i, 300);
		for (var i = 0; i <= 300; i += 20)
			drawLine(0, i, 300, i);
		for (var i = 0; i < chessBoard.length; ++i)
			for (var j = 0; j < chessBoard[i].length; ++j)
				if (chessBoard[i][j] == 'a')
					drawPoint(j, i, "red");
				else if (chessBoard[i][j] == 'b')
					drawPoint(j, i, "black");
		jiaocha(newY, newX, "blue");
	}
	clean();
	
	 $('button[tag=dir]').click(function (e) {
                var d = $(this).attr('da')
                e.preventDefault();
                var move = [
                    [-1, 0], [1, 0], [0, -1], [0, 1]
                ];
                if (newX + move[d][0] < 0 || newX + move[d][0] >= 15 || newY + move[d][1] < 0 || newY + move[d][1] >= 15)
                    return;
                newX += move[d][0];
                newY += move[d][1];
                clean();

            });
            function connect(){
				websocket = new WebSocket("ws://"+ip+"/ssmws/websocket?username="+username);
	            websocket.onopen = function () {
	                console.log("success");
	            }
	            websocket.onmessage = function (res) {
	                var data = res.data;
	                data = JSON.parse(data);
	                console.log(data)
	                //读取在线用户列表
	                if (data.type == 2) {
	                    data = data.list;
	                    var fl = friendlist;
	                    for (var i = 0; i < fl.length; ++i) {
	                        for (var j = 0; j < data.length; ++j)
	                            if (data[j] == fl[i].userid) {
	                                fl[i].online = true;
	                                $('li[da=' + data[j] + ']').css({
	                                    "background-color": 'red'
	                                })
	                            }
	                    }
	
	                }
	                //对方上线
	                else if (data.type == 1) {
	                    var fl = friendlist;
	                    for (var i = 0; i < fl.length; ++i) {
	                        if (data.from == fl[i].userid) {
	                            fl[i].online = true;
	                            $('li[da=' + fl[i].userid + ']').css({
	                                'background-color': 'red'
	                            })
	
	                            break;
	                        }
	                    }
	                }
	                //对方下线
	                else if (data.type == 3) {
	                    var fl = friendlist;
	                    for (var i = 0; i < fl.length; ++i) {
	                        if (data.from == fl[i].userid) {
	                            fl[i].online = false;
	                            $('li[da=' + fl[i].userid + ']').css({
	                                'background-color': 'white'
	                            });
	                            if(data.from==datafrom){
	                            	if(confirm("对方短线了;是否刷新？")){
	                            		location.reload(true);
	                            	}
	                            }
	                         
	                      
	                            break;
	                        }
	                    }
	                }
	                //对方邀请
	                else if (data.type == 5) {
	                    if (confirm('来自' + data.from + '的邀请')) {
	                        datafrom = data.from;
	                        var msg = {
	                            type: 7,
	                            to: datafrom
	
	                        }
	                        websocket.send(
	                            JSON.stringify(msg)
	                        );
	
	                    }
	                    else {
	                      
	                          
	
	                            var msg = {
	                                type: 6,
	                                to: datafrom
	
	                            }
	                            websocket.send(
	                                JSON.stringify(msg)
	                            );
	                        
	                    }
	                }
	                //start
	                 else if (data.type == 71) {
	                    
	                        alert(data.from + '同意了;对方先攻')
	                    
	
	
	                }
	                else if (data.type == 8) {
	                    for (var i = 0; i < 15; ++i)
	                        for (var j = 0; j < 15; ++j)
	                            chessBoard = data.state;
	                     datafrom=data.A==username? data.B:data.A;
	                    turn = data.turn;
	                    $("#turn").text('轮到'+(turn==username?'你':'对方'))
	                    clean();
	                    if (data.winner != null) {
	                        alert(data.winner + 'wins')
	                    }
	
	
	                }
					else if(data.type==23){
						if(confirm("对方想悔棋到回合"+data.ju)){
							var msg={
								ju:data.ju,
								kind:1,
								type:24,
								to:datafrom,
								content:username+"同意了悔棋"	 
							}
							websocket.send(JSON.stringify(msg))
						}
						else {
							var msg={
								kind:0,
								type:24,
								to:datafrom,
								content:username+"拒绝了了悔棋"
							}
							websocket.send(JSON.stringify(msg))
						}

					}
	
	            }
		         websocket.onclose=function(){
	             console.log('close');
	            // alert("websocket reconect");
                
            }
	            websocket.onerror=function(){
	                console.log("error");
	               // alert("websocket reconect");
	              //  connect();
	            }
	         }
	         connect();
          	 setInterval(function(){
	        	if(websocket.readyState==3){
	        		console.log("disconnect")
	        		if(confirm("连接异常，是否刷新")){
	        			location.reload(true);
	        		}
	        	}
        	}, 2000);
            
            $('#go').click(function (e) {
                if (chessBoard[newX][newY] != 'o' || turn != username) return;
                var msg = {
                    type: 9,
                    to: datafrom,
                    x: newX,
                    y: newY
                }
                turn=datafrom;
                websocket.send(
                    JSON.stringify(msg)
                )
            });
            $('li').each(function () {
                var da = $(this).attr('da');
                friendlist.push({
                    userid: da,
                    online: false
                });
            })
            //邀请
            $('li').click(function (e) {
                var userid = $(this).attr('da');
                if(datafrom!=null){
                    location.reload(true);
                    return;
                } 
              //  datafrom = userid;
                var msg = {
                    type: 4,
                    to: userid
                }
                websocket.send(
                    JSON.stringify(msg)
                )
				alert("你已发送邀请")
            })
            $('#show').click(function () {
            	var s=$('#fl').css("display");
                $('#fl').css({
                	"display":(s=="none"? "block":"none")
                })
            });
           
</script>

<script type="text/javascript">
	$.ajax({
        url: "getUserRank",
        type: "GET",
        data: "index=0&len=20",
        
        success: function (response) {
            var data=JSON.parse(response);
            var data=data.userlist;
            console.log(data)
            for(var i=0;i<data.length;++i)
                $("#tb").append("<tr>"+
                                     "<td>"+data[i].userid+"</td>"+   
                                     "<td>"+data[i].win+"</td>"+ 
                                     "<td>"+data[i].fail+"</td>"+ 
                                "</tr>" 
                );
        }
    });


</script>
<script>
$(".out").click(function(){
	$.ajax({	
        url: "removeWuziqiGameState",
        type: "GET",
        data: "userid="+username,
        
        success: function (response) {
         alert(response)  
         location.reload(true);;
         }
    });
	
})


</script>
<script>

			$(".huiqi").mouseleave(function () { 
					$("#huiqilist").hide();
				});
				$(".huiqi").mouseenter(function () { 
					$.ajax({
						type: "get",
						url: "getRecords",
						data: "userid="+username+"&type=wuziqi",
						success: function (response) {
							response=parseInt(response);
							var x=$("#huiqilist>ul");
							x.html('');
							for(var i=1;i<=response;++i)
								x.append("<li><a href='#' da='"+i+"'>"+"回合"+i+"</a></li>");
							$("#huiqilist").show();
						}
					});
					
				});
				//悔棋请求
				$("body").on('click','#huiqilist a',function(){
					var da=$(this).attr('da');
					var obj={
						type:22,
						ju:da,
						to:datafrom
					}
					websocket.send(JSON.stringify(obj))
				})

</script>