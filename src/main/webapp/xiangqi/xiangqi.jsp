<%@page language="java" pageEncoding="utf-8"
	contentType="text/html; charset=utf-8"%>
	<!DOCTYPE html>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
		<html>

		<head>
			<meta charset="UTF-8">
			<meta name="viewport" content="width=device-width, initial-scale=1">
			<title>canvas绘图_象棋盘</title>
			<link href="<%=request.getContextPath() %>/css/bootstrap.css" rel="stylesheet">
			<script src="<%= request.getContextPath() %>/js/jquery-2.1.3.js"></script>
			<script src="<%= request.getContextPath() %>/js/bootstrap.js"></script>
			<style type="text/css">
			body{
	background-color: #ebebeb
}
				div#out {
    background-color: yellow;
    z-index: 10;
}
button.btn.btn-default.show {
    width: 40px;
    height: 40px;
    float: right;
    border-radius: 0;

}
#global-footer {
    padding: 40px 20px;
    color: #a6a6a6;
    font-size: 0.8em;
    text-align: center;
    background: #18191b;
    border-bottom: #0a0a0a 5px solid;
}
.h1_friends{
	padding-right: 0px;
}
.h1_friends>p {

    height: 40px;
    padding:0;
    margin:0;
    font-size:30px;
    background-color: brown;color: white;
    padding-left: 10px;
}
.online{
	float: right;margin-top:15px
}
span#turn {
    position: fixed;
    top: 0;
}
span#color {
    position: fixed;
    right: 0;
    top: 0;
}
span#who {
    position: fixed;
    top: 0;
    left: 50%;
    margin-left: inherit;
}
.huiqi{
	width: 20px;
	padding: 3px;
	background-color: rgba(193, 172, 172, 0.67);
	position: absolute;
	left: 0px;
	top:20px;
	z-index: 20;
}
#huiqilist {
    position: absolute;
    left: 20px;
    top: 0px;
    width: 100px;
	display: none;
	
}
#timer{
	position: fixed;
	left: 75%;
	top:0;
}
			</style>
		</head>

		<body>
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
						<div class="canvas_div">
							<canvas id="canvas1" style="background-color:burlywood;width: 100%;height: 100%" width="1005" height="1105">不支持Canvas</canvas>
							<img src="<%= request.getContextPath() %>/xiangqi/img/blue-ju.gif" id="ju" hidden="hidden" />
							<img src="<%= request.getContextPath() %>/xiangqi/img/blue-ma.gif" id="ma" hidden="hidden" />
							<img src="<%= request.getContextPath() %>/xiangqi/img/blue-xiang.gif" id="xiang" hidden="hidden" />
							<img src="<%= request.getContextPath() %>/xiangqi/img/blue-shi.gif" id="shi" hidden="hidden" />
							<img src="<%= request.getContextPath() %>/xiangqi/img/blue-jiang.gif" id="jiang" hidden="hidden" />
							<img src="<%= request.getContextPath() %>/xiangqi/img/blue-pao.gif" id="pao" hidden="hidden" />
							<img src="<%= request.getContextPath() %>/xiangqi/img/blue-bing.gif" id="bing" hidden="hidden" />

							<img src="<%= request.getContextPath() %>/xiangqi/img/red-ju.gif" id="r_ju" hidden="hidden" />
							<img src="<%= request.getContextPath() %>/xiangqi/img/red-ma.gif" id="r_ma" hidden="hidden" />
							<img src="<%= request.getContextPath() %>/xiangqi/img/red-xiang.gif" id="r_xiang" hidden="hidden" />
							<img src="<%= request.getContextPath() %>/xiangqi/img/red-shi.gif" id="r_shi" hidden="hidden" />
							<img src="<%= request.getContextPath() %>/xiangqi/img/red-jiang.gif" id="r_jiang" hidden="hidden" />
							<img src="<%= request.getContextPath() %>/xiangqi/img/red-pao.gif" id="r_pao" hidden="hidden" />
							<img src="<%= request.getContextPath() %>/xiangqi/img/red-bing.gif" id="r_bing" hidden="hidden" />
						</div>			
					</div>
					<div class="col-sm-4">
						<div class="row" >
							<div class="col-xs-9 h1_friends">
								<p>
									friends
								</p>
							</div>
							<div class="col-xs-3 text-right" style="padding-left: 0px; padding-right: 15px;">
								<div style="width: 100%;height: 100%;overflow: auto;background-color:brown ">
									<button id="show" type="button" class="btn btn-default show"><span class="glyphicon glyphicon-list"></span></button>
								</div>
							</div>
						</div>
						<div class="row" id="friendlist">
							<div class="col-xs-12">
								<ul class="list-group">
								
								<c:forEach items="${list}" var="keyword">
									<li class="list-group-item" da="${ keyword.userid}" style=" padding-bottom: 0px;padding-top: 0px;padding-left: 0px;padding-right: 0px;border-bottom-width: 2px;">
											<img src="/upload/${ keyword.userid}.png" style="width: 50px;height: 50px" alt="Image">
										
										${ keyword.userid}
										
									</li>
								</c:forEach>
								</ul>
							</div>
						</div>
					</div>
				</div>	
				<div class="row">
					<span class="label label-danger" id="turn">Label</span>
					<span class="label label-info" id="color">Label</span>
					<span class="label label-info" id="timer">Label</span>
					
					<span class="label label-success" id="who">Label</span>
					
					
					<button type="button" class="btn btn-large btn-block btn-danger" id="out">out</button>
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
			//棋子图片

//	object.init();
//	object.clean();
		</script>
		<script src="<%= request.getContextPath() %>/xiangqi/js/stepCheck.js"></script>
		<script src="<%= request.getContextPath() %>/js/common.js"></script>
		<script>
			var je;
			var ip = commonIp;
			var username = "${username}";
			var datafrom = null;
			var A,B;
			var a = [];
			var websocket;
			var second;
			var secondTimer;
			window.onload = function () {
				var ju = document.getElementById("ju");
				var ma = document.getElementById("ma");
				var xiang = document.getElementById("xiang");
				var shi = document.getElementById("shi");
				var jiang = document.getElementById("jiang");
				var bing = document.getElementById("bing");
				var pao = document.getElementById("pao");

				var r_ju = document.getElementById("r_ju");
				var r_ma = document.getElementById("r_ma");
				var r_xiang = document.getElementById("r_xiang");
				var r_shi = document.getElementById("r_shi");
				var r_jiang = document.getElementById("r_jiang");
				var r_bing = document.getElementById("r_bing");
				var r_pao = document.getElementById("r_pao");


				
				for (var i = 0; i < 10; ++i) {
					a[i] = [];
					for (var j = 0; j < 9; ++j)
						a[i][j] = '0';
				}
				a[0][0] = 'ju';
				a[0][1] = 'ma';
				a[0][2] = 'xiang';
				a[0][3] = 'shi';
				a[0][4] = 'jiang';
				a[0][8] = 'ju';
				a[0][7] = 'ma';
				a[0][6] = 'xiang';
				a[0][5] = 'shi';
				a[2][1] = 'pao';
				a[2][7] = 'pao';
				a[3][0] = 'bing';
				a[3][2] = 'bing';
				a[3][4] = 'bing';
				a[3][6] = 'bing';
				a[3][8] = 'bing';

				a[9][0] = 'r_ju';
				a[9][1] = 'r_ma';
				a[9][2] = 'r_xiang';
				a[9][3] = 'r_shi';
				a[9][4] = 'r_jiang';
				a[9][8] = 'r_ju';
				a[9][7] = 'r_ma';
				a[9][6] = 'r_xiang';
				a[9][5] = 'r_shi';
				a[7][1] = 'r_pao';
				a[7][7] = 'r_pao';
				a[6][0] = 'r_bing';
				a[6][2] = 'r_bing';
				a[6][4] = 'r_bing';
				a[6][6] = 'r_bing';
				a[6][8] = 'r_bing';

				var canvas1;
				var oldX = -1, oldY = -1;
				var stepX = -11, stepY = -1;
				var object = {
					//初始化

					init: function () {
						//棋盘外框
						canvas1 = document.getElementById("canvas1");
						this.ctx = canvas1.getContext("2d");
						this.ctx.lineWidth = 5;
						this.ctx.strokeStyle = "brown";
						this.ctx.strokeRect(100, 100, 800, 900);

						this.row();
						this.cols();
						this.drawFont();
						//注意：现在的原点中心为（100,100），所以下面的所有坐标在原来的基础上加（x+100,y+100）；
						//中心点一（1000,200）
						this.center(200, 300);
						//中心点二（700,200）
						this.center(800, 300);
						//中心点三（5,300）
						this.center(105, 400);
						//中心点四（200,300）
						this.center(300, 400);
						//中心点五（400,300）
						this.center(500, 400);
						//中心点六（600,300）
						this.center(700, 400);
						//中心点七（800,300）
						this.center(900, 400);
						//中心点八（800,600）
						this.center(900, 700);
						//中心点九（600,600）
						this.center(700, 700);
						//中心点十（400,600）
						this.center(500, 700);
						//中心点十一（200,600）
						this.center(300, 700);
						//中心点十二（5,600）
						this.center(105, 700);
						//中心点十三（700,700）
						this.center(800, 800);
						//中心点十四（100,700）
						this.center(200, 800);

						//必须当页面中的图片资源加载成功,再画棋子


						//将棋子图像绘制到画布上
						this.ctx.drawImage(ju, 50, 150, 100, 100);
						this.ctx.drawImage(ma, 150, 50, 100, 100);
						this.ctx.drawImage(xiang, 250, 50, 100, 100);
						this.ctx.drawImage(shi, 350, 50, 100, 100);
						this.ctx.drawImage(jiang, 450, 50, 100, 100);
						this.ctx.drawImage(shi, 550, 50, 100, 100);
						this.ctx.drawImage(xiang, 650, 50, 100, 100);
						this.ctx.drawImage(ma, 750, 50, 100, 100);
						this.ctx.drawImage(ju, 850, 50, 100, 100);
						this.ctx.drawImage(pao, 150, 250, 100, 100);
						this.ctx.drawImage(pao, 750, 250, 100, 100);
						this.ctx.drawImage(bing, 50, 350, 100, 100);
						this.ctx.drawImage(bing, 250, 350, 100, 100);
						this.ctx.drawImage(bing, 450, 350, 100, 100);
						this.ctx.drawImage(bing, 650, 350, 100, 100);
						this.ctx.drawImage(bing, 850, 350, 100, 100);

						this.ctx.drawImage(r_ju, 50, 950, 100, 100);
						this.ctx.drawImage(r_ma, 150, 950, 100, 100);
						this.ctx.drawImage(r_xiang, 250, 950, 100, 100);
						this.ctx.drawImage(r_shi, 350, 950, 100, 100);
						this.ctx.drawImage(r_jiang, 450, 950, 100, 100);
						this.ctx.drawImage(r_shi, 550, 950, 100, 100);
						this.ctx.drawImage(r_xiang, 650, 950, 100, 100);
						this.ctx.drawImage(r_ma, 750, 950, 100, 100);
						this.ctx.drawImage(r_ju, 850, 950, 100, 100);
						this.ctx.drawImage(r_pao, 150, 750, 100, 100);
						this.ctx.drawImage(r_pao, 750, 750, 100, 100);
						this.ctx.drawImage(r_bing, 50, 650, 100, 100);
						this.ctx.drawImage(r_bing, 250, 650, 100, 100);
						this.ctx.drawImage(r_bing, 450, 650, 100, 100);
						this.ctx.drawImage(r_bing, 650, 650, 100, 100);
						this.ctx.drawImage(r_bing, 850, 650, 100, 100);


					},
					//此方法用来画棋盘线
					LineDrawing: function (mx, my, lx, ly) {
						this.ctx.beginPath();
						this.ctx.moveTo(mx, my);
						this.ctx.lineTo(lx, ly);
						this.ctx.stroke();
					},
					//棋盘行
					row: function () {
						for (var i = 200; i <= 900; i += 100) {
							this.ctx.beginPath();
							this.ctx.moveTo(105, i);
							this.ctx.lineTo(900, i);
							this.ctx.stroke();
						}
					},
					//棋盘列
					cols: function () {
						for (var i = 200; i <= 800; i += 100) {
							this.ctx.beginPath();
							this.ctx.moveTo(i, 105);
							this.ctx.lineTo(i, 1000);
							this.ctx.stroke();
						}
						//清除指定的矩形区域
						//this.ctx.clearRect(5, 402,795, 95);
						this.ctx.clearRect(102.5, 502, 795, 95);
						//斜线
						this.LineDrawing(400, 105, 600, 300);
						this.LineDrawing(400, 805, 600, 1000);
						//反斜线
						this.LineDrawing(600, 105, 400, 300);
						this.LineDrawing(600, 805, 400, 1000);
					},
					//坐标的中心点
					center: function (x, y) {
						this.ctx.lineWidth = 5;
						//中心点一（100,200）
						//左上
						this.LineDrawing(x - 10, y - 30, x - 10, y - 10);
						this.LineDrawing(x - 10, y - 10, x - 30, y - 10);
						//右上
						this.LineDrawing(x + 10, y - 30, x + 10, y - 10);
						this.LineDrawing(x + 10, y - 10, x + 30, y - 10);
						//左下
						this.LineDrawing(x - 10, y + 30, x - 10, y + 10);
						this.LineDrawing(x - 10, y + 10, x - 30, y + 10);
						//右下
						this.LineDrawing(x + 10, y + 30, x + 10, y + 10);
						this.LineDrawing(x + 10, y + 10, x + 30, y + 10);
					},
					drawFont: function () {
						this.ctx.lineWidth = 1;
						//绘制文字
						this.ctx.font = "60px microsoft yahei";
						this.ctx.save(); //保存点
						//将坐标中心作为起启点
						this.ctx.translate(canvas1.width / 2, canvas1.height / 2);
						var radian = Math.PI / 2; // 弧度制 Math.PI=π
						this.ctx.rotate(radian); // 旋转画布绘制刻度
						//填充
						this.ctx.fillText("楚", -30, -270);
						this.ctx.fillText("河", -30, -150);
						this.ctx.restore(); //恢复到保存点
						this.ctx.save();
						//将坐标中心作为起点
						this.ctx.translate(canvas1.width / 2, canvas1.height / 2);
						var radian = Math.PI / -2;
						this.ctx.rotate(radian);
						this.ctx.fillText("汉", -30, -270);
						this.ctx.fillText("界", -30, -150);
						this.ctx.restore();
					},
					jiaocha: function (x, y) {
						x = x + 1; y = y + 1;
						this.LineDrawing(100 * y - 50, 100 * x - 50, 100 * y + 50, 100 * x - 50);
						this.LineDrawing(100 * y + 50, 100 * x - 50, 100 * y + 50, 100 * x + 50);
						this.LineDrawing(100 * y + 50, 100 * x + 50, 100 * y - 50, 100 * x + 50);
						this.LineDrawing(100 * y - 50, 100 * x + 50, 100 * y - 50, 100 * x - 50);
					},
					//clean
					clean: function () {
						//	canvas1 = document.getElementById("canvas1");
						//	this.ctx = canvas1.getContext("2d");
						this.ctx.clearRect(0, 0, canvas1.width, canvas1.height);
						this.ctx.lineWidth = 5;
						this.ctx.strokeStyle = "brown";
						this.ctx.strokeRect(100, 100, 800, 900);

						this.row();
						this.cols();
						this.drawFont();
						//注意：现在的原点中心为（100,100），所以下面的所有坐标在原来的基础上加（x+100,y+100）；
						//中心点一（1000,200）
						this.center(200, 300);
						//中心点二（700,200）
						this.center(800, 300);
						//中心点三（5,300）
						this.center(105, 400);
						//中心点四（200,300）
						this.center(300, 400);
						//中心点五（400,300）
						this.center(500, 400);
						//中心点六（600,300）
						this.center(700, 400);
						//中心点七（800,300）
						this.center(900, 400);
						//中心点八（800,600）
						this.center(900, 700);
						//中心点九（600,600）
						this.center(700, 700);
						//中心点十（400,600）
						this.center(500, 700);
						//中心点十一（200,600）
						this.center(300, 700);
						//中心点十二（5,600）
						this.center(105, 700);
						//中心点十三（700,700）
						this.center(800, 800);
						//中心点十四（100,700）
						this.center(200, 800);

						for (var i = 0; i < 10; ++i)
							for (var j = 0; j < 9; ++j) {
								if (a[i][j] != null && a[i][j] != '0') {
									var xxx = document.getElementById(a[i][j]);
									this.ctx.drawImage(xxx, 50 + 100 * j, 50 + 100 * i, 100, 100);
								}
							}
						if (stepX != -1)
							this.jiaocha(stepX, stepY);
					}
				};






				object.init();
				object.clean();

				websocket = new WebSocket("ws://" + ip + "/ssmws/websocketxiangqi?username=" + username);
				websocket.onopen = function () {
					console.log("success")
				}
				websocket.onmessage = function (res) {
					var data = JSON.parse(res.data);
					console.log(data);
					//在线用户
					if (data.type == 2) {
						var list = data.list;
						for (var i = 0; i < data.list.length; ++i)
							$("li[da=" + list[i] + "]").append('<span class="label label-info online" >online</span>')
					}
					//对方上线
					else if (data.type == 1) {
						$("li[da=" + data.from + "]").append('<span class="label label-info online" >online</span>')
					}
					//对方下线
					else if (data.type == 3) {
						$("li[da=" + data.from + "]").find("span").remove();
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
						
						a = data.state;
						datafrom = data.A == username ? data.B : data.A;
						A=data.A;
						B=data.B;
						turn = data.turn;
						$("#turn").text('轮到' + (turn == username ? '你' : '对方'));
						$("#color").html(username==A? "你是红方":"你是黑方")
						$("#who").text("vs "+datafrom);
						object.clean();
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
					else if(data.type==25){
						alert(data.content)
					}
					else if(data.type==33){
						second=data.second;
						clearInterval(secondTimer)
						secondTimer=setInterval(function(){
							$("#timer").html("time:"+second);
							second--;
						},1000);
						
					}

				}
				websocket.onclose=function(){
					console.log("close")
				}
				websocket.onerror=function(){
					console.log("error")
				}
				$('li').click(function (e) {
					var userid = $(this).attr('da');
					if (datafrom != null) {
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

				canvas1.onclick = function (e) {
					je=e;
					var bbox = canvas1.getBoundingClientRect();
					var XX=e.offsetX*canvas1.width/bbox.width;
					var YY=e.offsetY*canvas1.height/bbox.height;
					console.log(XX+" "+YY)
				//	console.log("e.offerx:"+(e.offsetX*canvas1.width/bbox.width)+"e.offerY:"+(e.offsetY*canvas1.width/bbox.width));
					var x = Math.floor(XX / 100) - 1, y = Math.floor(YY / 100) - 1;
					//	alert(x+" "+y);
					var point = [
						[100 * x + 100, 100 * y + 100],
						[100 * x + 100 + 100, 100 * y + 100],
						[100 * x + 100, 100 * y + 100 + 100],
						[100 * x + 100 + 100, 100 * y + 100 + 100]
					];
					for (var i = 0; i < point.length; ++i)
						if ((point[i][0] - XX) * (point[i][0] - XX) +
							(point[i][1] - YY) * (point[i][1] - YY) <= 2500){
							var ii = point[i][0] / 100 - 1;
							var jj = point[i][1] / 100 - 1;
							stepX = jj;
							stepY = ii;
							if (stepX == oldX && stepY == oldY) {
								stepX = stepY = oldX = oldY = -1;
							}
							else {
								console.log(oldX + " " + oldY);
								if (oldX != -1 && a[oldX][oldY] != null) {
								//	a[stepX][stepY] = a[oldX][oldY];
								//	a[oldX][oldY] = null;
									var panIsMe= function(ox,oy){
										//alert(a[ox][oy])
										if(username==A){
											if(a[ox][oy].indexOf("r_") )return false;
											return true;
										}
										else{
											if(a[ox][oy].indexOf("r_") )return true;
											return false;
										}
									}
									var panIsNextMe= function(ox,oy){
									//	alert(a[ox][oy])
										if(a[ox][oy]==null||a[ox][oy]=='0') return true;
										if(username==A){
											
											if(a[ox][oy].indexOf("r_") )return true;
											return false;
										}
										else{
											if(a[ox][oy].indexOf("r_") )return false;
											return true;
										}
									}
									if (  turn == username&&panIsMe(oldX,oldY)&&panIsNextMe(stepX,stepY)&&
									stepCheck(a, oldX, oldY, stepX, stepY)
									) {
										var msg = {
											type: 9,
											to: datafrom,
											startx: oldX,
											starty: oldY,
											endx:stepX,
											endy:stepY
										}
										turn = datafrom;
										websocket.send(
											JSON.stringify(msg)
										)
										console.log(msg)
									}
									
								}
								oldX = stepX;
								oldY = stepY;
							}
							object.clean();

						}
				}

			}
			setInterval(function(){
	        	if(websocket.readyState==3){
	        		console.log("disconnect")
	        		if(confirm("连接异常，是否刷新")){
	        			location.reload(true);
	        		}
	        	}
        	}, 2000);
		</script>
		<script>
			$("#out").click(function(){
				$.ajax({
					url: "removeXiangqiGameState",
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
				$("#show").click(function(){
					$("#friendlist").css("display",$("#friendlist").css("display")=="none"?"block":"none");
				
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
						data: "userid="+username+"&type=xiangqi",
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