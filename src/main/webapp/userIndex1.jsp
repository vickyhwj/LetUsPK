<%@page language="java" pageEncoding="utf-8"
	contentType="text/html; charset=utf-8"%>
	<!DOCTYPE html>
	<html lang="en">

	<head>
		<title></title>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link href="css/bootstrap.css" rel="stylesheet">
		<script src="js/jquery-2.1.3.js"></script>
		<!-- <script type="text/javascript" src="js/jquery-1.8.3.js"></script> -->

		<script src="js/bootstrap.js">

		</script>
		<style>
			.col-xs-9.text-right {
				padding-right: 0;
			}

			.col-xs-3.text-left {
				padding-left: 0;
			}

			.col-sm-12.search {
				padding: 20px;
				overflow: auto;
				border-bottom: 1px solid black;
			}

			.col-sm-4.col-left {
				padding: 0;
				background-color: #f0f0f0;
			}

			.col-sm-12.msgitem {

				background: white;
				border-radius: 3px;
				overflow: auto;
			}

			.col-sm-4.col-right {
				background-color: #f0f0f0;
			}

			.col-sm-12.msglist {
				padding-bottom: 10px;
			}

			.col-sm-12.msgitem>.col-xs-2 {
				padding-left: 0;
				border-left: 1px solid black padding-right:0;
			}

			.col-sm-12.msgitem>.col-xs-2 * {
				width: 100%;
			}

			.col-sm-12.msgitem {
				padding: 0px;
				margin-bottom: 10px:
			}

			.col-sm-12.msgitem>.col-xs-2 {
				padding-right: 0px;
			}

			.col-xs-10.msgcontent {
				word-break: break-all;
			}

			.col-sm-12.msgitem {
				padding: 0px;

				margin-bottom: 10px;
			}

			.col-sm-10.col-xs-7>h2 {
				padding-bottom: 10px;
				padding-top: 10px;
				margin: 0px;
			}

			.logo {
				width: 50px;
				height: 50px;
			}

			.col-xs-10.msgcontent {
				padding: 10px;
			}

			.table th,
			.table td {
				text-align: center;
				vertical-align: middle!important;
			}

			button.btn.btn-large.btn-block.btn-danger {
				margin-top: 8px;
			}

			div#msglist {
				max-height: 600px;
				overflow: scroll;
			}

			#global-footer {
				padding: 40px 20px;
				color: #a6a6a6;
				font-size: 0.8em;
				text-align: center;
				background: #18191b;
				border-bottom: #0a0a0a 5px solid;
			}

			div#msglist {
				overflow-x: hidden;
			}
		</style>




	</head>

	<body>
		<div class="container">

			<div class="row">
				<div class="col-sm-2 col-xs-5" style="padding-left: 0;padding-right: 0">
					<img src="/upload/${user.userid }.png" class="img-responsive" alt="Image" id="mylogo">

					<!-- 	<form action="upload" method="post" enctype="multipart/form-data" id="uploadForm">
						<input type="file" name="file" >

					</form> -->
				<!-- 	<input type="button" id="uploadButton" value="上传" class="btn" /> -->
					<input id="fileupload" type="file" name="file" data-url="upload" multiple>
					<div class="progress">
						<div class="progress-bar" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width:0% ;" id="progressBar">
							<span class="sr-only"> % Complete</span>
						</div>
					</div>

					<a style="font-size: 10px;" href="loginwuziqi?username=${user.userid}">进入五子棋</a>
					<a style="font-size: 10px;" href="loginxiangqi?username=${user.userid}">进入象棋</a>
					<br/>
					<a href="#msglist">我的消息</a>&nbsp;
					<a href="#friendul">我的好友</a>
				</div>
				<div class="col-sm-10 col-xs-7">
					<h2 class="text-right" style="border-bottom: 2px solid black">${user.userid }</h2>
					<h2 class="text-right">win:${user.win} &nbsp;lose:${user.fail } </h2>
				</div>
			</div>
		</div>


		<div class="container">
			<div class="row" style="background-color: #f0f0f0">
				<div class="col-sm-8 col-left">
					<div class="col-sm-12 search">
						<div class="col-xs-9 text-right">
							<input type="search" name="" id="findInput" class="form-control" value="" required="required" title="">
						</div>
						<div class="col-xs-3 text-left">
							<button type="button" class="btn btn-default" id="searchButton">button</button>
						</div>
					</div>
					<div class="col-sm-12">
						<table class="table table-hover">
							<thead>
								<tr>
									<th>logo</th>
									<th>userid</th>
									<th>win</th>
									<th>lose</th>
									<th>add</th>

								</tr>
							</thead>
							<tbody id="ul">

							</tbody>
						</table>

					</div>
					<ul class="pager">
						<li>
							<a href="#" id="last" da="1">Last</a>
						</li>
						<li id="now">第几页</li>
						<li>
							<a href="#" id="next" da="2">Next</a>
						</li>
						<li>
							<input type="text" id="jump" style="width: 25px">
						</li>
						<li id="sum">共几页</li>
						<li>
							<button id="go" class="btn btn-info">go</button>
						</li>
					</ul>
				</div>

				<div class="col-sm-4 col-right">
					<h1 class="text-center">Message</h1>
					<div class="col-sm-12 msglist" id="msglist">

					</div>
				</div>

			</div>
			<div class="row">
				<div class="col-sm-12 col-mid">
					<h1 class="text-center">my friends</h1>
					<ul class="list-group" id="friendul">



					</ul>
				</div>
			</div>
		</div>
		<footer id="global-footer">
			<p>Lovingly created and maintained by
				<a href="http://john.onolan.org">John O'Nolan</a> +
				<a href="http://erisds.co.uk">Hannah Wolfe</a> + an amazing group of
				<a href="https://github.com/TryGhost/Ghost/contributors">contributors</a>.</p>
			<hr>
			<p>All code copyright (C) 2014 The Ghost Foundation - Released under the
				<a href="http://tryghost.org/about/license.html">MIT</a> License.
				<br> All documentation is released under the Creative Commons
				<a href="http://creativecommons.org/licenses/by/3.0/">By-3.0</a> License.</p>
		</footer>
	</body>

	</html>
	<script>
		var username = "${user.userid}";
		var password = "${user.password}";

	</script>
	<script src="js/jquery-2.1.3.js"></script>
	<script src="js/handlebars-v4.0.10.js"></script>
	<script type="text/x-handlebars-template" id="useritem">
		<tr>
			<td><img class="logo" src="/upload/{{userid }}.png" alt="未上传"></td>
			<td>
				<font>{{userid}}</font>
			</td>
			<td>
				<font>{{win}}</font>
			</td>
			<td>
				<font>{{fail}}</font>
			</td>
			<td><button type="button" class="btn btn-success" da="{{userid}}" work="add">add</button></td>
		</tr>
	</script>
	<script type="text/x-handlebars-template" id="frienditem">
		<li class="list-group-item">
			<div class="row">
				<div class="col-xs-8">
					<img class="logo" src="/upload/{{userid }}.png" alt="未上传"> {{userid}}
				</div>
				<div class="col-xs-4">
					<button type="button" class="btn btn-large btn-block btn-danger" da="{{userid}}" work="del">del</button>
				</div>
			</div>
		</li>
	</script>
	<script type="text/x-handlebars-template" id="msgtype1">
		<div class="col-sm-12 msgitem ">

			<div class="col-xs-10 msgcontent">
				from{{from}}的请求({{createDate1}})
			
			</div>
			<div class="col-xs-2">
				<div>
					<button class="btn-danger removeMsg1" da="{{msgId}}" tp="{{type}}">del</button>
				</div>
				<div>
					<button class="btn-primary acceptMsg1" da="{{msgId}}" from="{{from}}" tp="{{type}}">yes</button>
				</div>
				<div>
					<button class="btn-info refuseMsg1" da="{{msgId}}" from="{{from}}" tp="{{type}}">no</button>
				</div>
			</div>

		</div>
	</script>
	<script type="text/x-handlebars-template" id="msgtype2">
		<div class="col-sm-12 msgitem ">

			<div class="col-xs-10 msgcontent">
				{{from}}同意了你({{createDate1}})
			</div>
			<div class="col-xs-2">
				<div>
					<button class="btn-danger removeMsg1" da="{{msgId}}" tp="{{type}}">del</button>
				</div>

			</div>

		</div>
	</script>
	<script type="text/x-handlebars-template" id="msgtype3">
		<div class="col-sm-12 msgitem ">

			<div class="col-xs-10 msgcontent">
				{{from}}{{cc}}({{createDate1}})
			</div>
			<div class="col-xs-2">
				<div>
					<button class="btn-danger removeMsg1" da="{{msgId}}" tp="{{type}}">del</button>
				</div>

			</div>

		</div>
	</script>

	<script src="js/common.js"></script>
	<script src="js/mobileIndex.js"></script>
<!-- 	<script type="text/javascript" src="js/jquery.ocupload-1.1.2.js"></script> -->
	<script>
	/*
		$(function () {
			var fun;
			$('#uploadButton').upload({
				name: 'file', // <input type="file" name="upload" 
				action: 'upload', // 表单提交路径
				onComplete: function (response) {
					alert("yes");
					$("#progressBar").attr("style", "width: " + 100 + "%");
					$("#mylogo").attr({
						"src": "/upload/" + username + ".png?id=" + Math.random()
					})
					clearInterval(fun)
				},
				onSubmit: function () {
					fun = setInterval(function () {
						$.ajax({
							type: "get",
							url: "uploadState",
							success: function (response) {
								var data = JSON.parse(response);
								console.log(data)
								if (data.have == 1) {
									$("#progressBar").attr("style", "width: " + (data.now / data.max * 100) + "%");
									console.log($("#progressBar").attr("style"));
									if (data.now == data.max) {
										clearInterval(fun);
									}
								}
							}

						});

					}, 2000)
				},
				onProgress: function(e) {
					console.log("onProgress");
				}
			});
		})
*/

	</script>

<script src="js/upload/jquery.ui.widget.js"></script>
<script src="js/upload/jquery.iframe-transport.js"></script>
<script src="js/upload/jquery.fileupload.js"></script>
<script>
 $('#fileupload').fileupload({
        dataType: 'json',
        done: function (e, data) {
            alert("ok");
            var d=new Date();
            console.log(d.toString())
            $("#mylogo").attr({
				"src": "/upload/" + username + ".png?id="+d.getTime()
			})
			
        },
        progressall: function (e, data) {
        	var progress = parseInt(data.loaded / data.total * 100, 10);
	        $("#progressBar").attr("style", "width: "+progress+"%");
    	}
    });
</script>