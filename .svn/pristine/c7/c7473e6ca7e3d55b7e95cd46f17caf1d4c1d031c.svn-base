<%@page language="java" pageEncoding="utf-8"
	contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
<title></title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<script src="js/JSXTransformer.js">
	
</script>
<script src="js/react.js">
	
</script>
<script src="js/jquery-2.1.3.js">
	
</script>
<script>
	var message = '${messages}';
	var userid = '${user.userid}';
</script>
<script type="text/javascript" src="js/userIndexWs.js"></script>
<script type="text/jsx;harmony=true" src="app.jsx">

    </script>
<link href="css/bootstrap.min.css" rel="stylesheet">
<style>
.con1 {
	width: 1200px;
	height: 600px;
	margin: auto;
}

.con1>* {
	float: left;
	height: 100%;
}

.con11 {
	width: 70%;
	border: 2px solid blue;
}

.con12 {
	width: 30%;
	border: 2px solid black;
	border-left-width: 2px;
	border-left-width: 0px;
}

.fengexian {
	width: 100%;
	margin-top: 10px;
	margin-bottom: 10px;
	height: 2px;
	background-color: #c5c5ec;
}

.searchDiv>.input-group {
	width: 60%;
	float: right;
}

.searchDiv {
	overflow: auto;
}

li>input {
	margin-left: 20px;
	width: 80px;
}

.con12 {
	padding: 20px;
	overflow-y: scroll;
}

.con12>div>div {
	width: 100%;
	overflow: auto;
	margin-bottom: 5px;
	background-color: #f9f3f3;
	border-radius: 10px;
	padding: 10px;
}

.con12>div>div>* {
	width: 100%;
	word-wrap: break-word;
}

.removebu {
	float: right;
	margin-top: -5px;
}

.con111 {
	height: 20%;
	position: relative;
	padding: 4px;
}

#logo {
	width: 90px;
	height: 100%;
	float: left;
	left: 5px;
	top: 5px;
}

.con111 {
	margin-bottom: 5px;
}

.con1112 {
	float: left;
	height: 100%;
	width: 730px;
	margin-left: 4px;
}

.f1 {
	margin-top: 2px;
	margin-bottom: 2px;
}

#pageDiv {
	margin-top: -10px;
}

.fas {
	border: 2px solid;
	border-right-width: 2px;
	border-bottom-width: 2px;
	border-left-width: 2px;
	border-left-width: 0;
	border-right-width: 3px;
	border-bottom-width: 0;
	height: 475px;
	width: 75%;
	float: left;
}

.friend {
	float: left;
	width: 25%;
	height: 475px;
	border: 2px solid black;
	border-top-width: 2px;
	border-right-width: 2px;
	border-bottom-width: 2px;
	border-left-width: 2px;
	border-top-style: solid;
	border-right-style: solid;
	border-bottom-style: solid;
	border-left-style: solid;
	border-top-color: black;
	border-right-color: black;
	border-bottom-color: black;
	border-left-color: black;
	-moz-border-top-colors: none;
	-moz-border-right-colors: none;
	-moz-border-bottom-colors: none;
	-moz-border-left-colors: none;
	border-image-source: none;
	border-image-slice: 100% 100% 100% 100%;
	border-image-width: 1 1 1 1;
	border-image-outset: 0 0 0 0;
	border-image-repeat: stretch stretch;
	border-left-width: 0;
	border-right-width: 0;
	border-bottom-width: 0;
}
#userList {
    height: 360px;
}

</style>
</head>

<body>
	<div class="con1">
		<div class="con11">
			<div class="con111">
				<img alt="" src="logo.png" id="logo">
				<div class="con1112">
					<h5 style="padding-left: 10px;">
						${user.userid }<a style="float: right;"
							href="login?username=${user.userid} ">进入游戏</a>
					</h5>
					<div class="fengexian f1"></div>
					<h5 style="padding-left: 10px;">胜：${user.win}</h5>
					<h5 style="padding-left: 10px;">负：${user.fail }</h5>
				</div>
			</div>
			<div class="fas">
				<div class="searchDiv">
					<div class="input-group">
						<input type="text" class="form-control"
							placeholder="Search for..." id="searchInput"> <span
							class="input-group-btn">
							<button class="btn btn-default" type="button" id="searchButton">Go!</button>
						</span>
					</div>

				</div>
				<div class="fengexian"></div>

				<div id="userList"></div>
				<div class="fengexian f1"></div>
				<div id="pageDiv">
					<ul class="pager">
						<li><a href="#">Previous</a></li>
						<li>11</li>
						<li><a href="#">Next</a></li>
						<li>共10页</li>
						<li><input type="text" placeholder="dump"></li>
						<li><button>go</button></li>
					</ul>
				</div>
			</div>
			<div class="friend">
				<h4 style="padding-left: 20px;">
					好友列表
					<button id="refresh">刷新</button>
				</h4>
				<div id="friendList">
					<ul class="list-group">
						<li class="list-group-item">Item 1</li>
						<li class="list-group-item">Item 2</li>
						<li class="list-group-item">Item 3</li>
					</ul>
				</div>
			</div>
		</div>
		<div class="con12" id="con12"></div>
	</div>

</body>

</html>