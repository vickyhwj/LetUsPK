<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<% String path=request.getContextPath(); %>
<head>
<link rel="stylesheet" href="<%=path %>/css/bootstrap.css">
<script type="text/javascript" src="<%=path %>/js/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/bootstrap.js"></script>
<style type="text/css">
.search-div {
	margin-top: 0px;
}

input#index {
	width: 50px;
}
#friendList,#roles{
	overflow: auto;
	padding-left: 0px;
}
#friendList li,#roles li{
	margin-top:10px;
    list-style: none;
    float: left; 
    margin-left: 10px;
    
}

</style>

</head>

<body>
<!-- 模态框（Modal） -->
<div class="modal fade" id="addRolesModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
						&times;
					</button>
					<h4 class="modal-title" id="myModalLabel">
						添加角色
					</h4>
				</div>
				<div class="modal-body">
					
					<div class="table-responsive">
						<table class="table table-hover">
							<thead>
								<tr>
									<th>roleId</th>
									<th></th>
								</tr>
							</thead>
							<tbody id="tb_role">
								<c:forEach var="role" items="${roles}">
									<tr><td>${role.roleid }</td><td class="add" data="${role.roleid }"></td></tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭
					</button>
					<button type="button" class="btn btn-primary">
						提交更改
					</button>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal -->
	</div>



	

	<div class="container-fluid">

		<div class="row">
			<div class="col-sm-2">
				<div class="page-header search-div">
					<h1>
						<div class="input-group">
							<input type="text" class="form-control"
								placeholder="Search for..." id="userId"> <span
								class="input-group-btn">
								<button class="btn btn-default" type="button" id="searchBu">Go!</button>
							</span>
						</div>
					</h1>
				</div>

				<table class="table table-bordered table-hover">
					<thead>
						<tr>
							<th>name</th>
						</tr>
					</thead>
					<tbody id="userList">
						
					</tbody>
				</table>
				<nav aria-label="...">
					<ul class="pager">
						<li><a href="javascript:void(0)" id="pre" data="">Previous</a></li>
						<li><a href="javascript:void(0)" id="next" data="">Next</a></li>
						<li><input id="index" /></li>
						<li>
							<button type="button" class="btn btn-default" id="jump">go</button>
						</li>
						<li>共<small id="sum"></small>页</li>
					</ul>
				</nav>


			</div>
			<div class="col-sm-10" style="background-color: ">
				
				<div class="row">
					<div class="col-sm-6">					
							<img src="../muwen.jpg" class="img-responsive" alt="Image" id="userImg" style="width:100%">									
					</div>
					<div class="col-sm-6" style="padding-right: 100px;">					
							
							<div class="page-header" style="text-align: right;">
							  <h1>UserId:<small id="userId">subtext</small></h1>
							</div>
							<div style="text-align: right;">
								<h1>Win:<small id="win">10</small></h1>
							</div>
							<div style="text-align: right;">
								<h1>Lose:<small id="lose">5</small></h1>
							</div>
							<div style="text-align: right;">
								
								<a class="btn btn-default" href="" role="button" id="delUser">delete</a>
														
							</div>
															
					</div>
				</div>
				
				<div class="row">
					<div class="col-sm-5">
						
						<div class="page-header">
						  <h2>好友</h2>
						</div>
						
						<ul id="friendList">
							
						</ul>
					</div>
					<div class="col-sm-5">
						
							<div class="page-header">
							  <h2>权限								  
								  <button type="button" class="btn btn-default" style="float:right" data-toggle="modal" data-target="#addRolesModal" id="addRolesModal">添加角色</button>								  								  							  
							  </h2>
							</div>
							
							<ul id="roles">
								
							</ul>
						</div>
				</div>
				
				

			</div>
		</div>

	</div>





</body>

</html>
<script>
	function findPage(userid,index,len){
		$.ajax({
			type: "get",
			url: "<%= path%>/getUserListByUserId",
			data: "userId="+userid+"&index="+index+"&len="+len,
			success: function (response) {
				response=JSON.parse(response)
				$("#userList").html("");
				var userList=response.userlist;
				for(var i=0;i<userList.length;++i){
					$("#userList").append("<tr><td>"+
												userList[i].userid+
										"</td></tr>");
				}
				$("#pre").attr("data",response.now-1);
				$("#next").attr("data",response.now+1);
				$("#index").val(response.now)
				$("#sum").html(response.sum)
				
			},
			error:function(e){
				console.log(e)
			}
		});

	}
	$("#searchBu").click(function (e) { 
		e.preventDefault();
		findPage($("#userId").val(),1,10)
		
	});
	$("#pre").click(function () {
		findPage($("#userId").val(),$(this).attr("data"),10)
	})
	$("#next").click(function () {
		findPage($("#userId").val(),$(this).attr("data"),10)
	})
	$("#jump").click(function () {
		findPage($("#userId").val(),$("#index").val(),10)
	})
	function getUserDetail(userId){
		$.ajax({
			type: "get",
			url: "<%= path%>/loadUserAllDetailByUserId",
			data: "userId="+userId,
			success: function (response) {
				response=JSON.parse(response);
				console.log(response)
				$("#userImg").attr("src","/upload/"+response.userRoles.userid+".png");
				$("small#userId").text(response.userRoles.userid);
				$("#win").text(response.userRoles.win)
				$("#lose").text(response.userRoles.fail)
				$("#delUser").attr("href","removeUser?userid="+response.userRoles.userid)

				var friendList=response.friendList;
				$("#friendList").html('')
				for(var i=0;i<friendList.length;++i){
					$("#friendList").append(
						"<li>"								
								+'<span class="label label-info friend">'+friendList[i].userid+'</span>'+								
						"</li>"
					
					)
				}
				var roles=response.userRoles.roles;
				$('#roles').html('');
				$('.add').html("");
				for(var i=0;i<roles.length;++i){
					$('#roles').append(
						'<li>'+								
							'<span class="label label-info">'+roles[i].roleid+'<a href="javascript:void(0)" class="delRole" roleid="'+roles[i].roleid+'" userid="'+response.userRoles.userid+'">(del)</a></span>'	+					
						'</li>'
					)
				}
				var has=function (roles,x) {
						for(var i=0;i<roles.length;++i)
							if(roles[i].roleid==x){
								return 1;
							}
						return 0;
					}
				$('.add').each(function(){
					if(has(roles,$(this).attr('data'))==0){
						$(this).html('<button roleid="'+$(this).attr('data')+'" userid="'+userId+'">add</button>');
					}

				})
			}
		});
	}
	$("#userList").on('click', 'td',function () {
		getUserDetail($(this).text())
		
	});
	$("#friendList").on("click",".friend", function () {
		getUserDetail($(this).text())
	});
	$('.add').on('click','button',function(){
		var userid=$(this).attr("userid");
		$.ajax({
			type: "get",
			url: "<%= path%>/giveUserRole",
			data: "userid="+$(this).attr("userid")+"&roleid="+$(this).attr('roleid'),
			success: function (response) {
				getUserDetail(userid)
			}
		});
	});
	$('#roles').on('click','.delRole',function(){
		var userid=$(this).attr("userid");
		$.ajax({
			type: "get",
			url: "<%= path%>/removeUserRole",
			data: "userid="+$(this).attr("userid")+"&roleid="+$(this).attr('roleid'),
			success: function (response) {
				getUserDetail(userid)
			}
		});
	});
	
	

	
</script>