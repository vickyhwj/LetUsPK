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
						
						<form action="<%=path %>/addRole" method="POST" role="form">
						
							<div class="form-group">
								<label for="">roleid</label>
								<input type="text" class="form-control" id="" placeholder="Input field" name="roleid">
							</div>
							<div class="form-group">
									<label for="">description</label>
									<input type="text" class="form-control" id="" placeholder="Input field" name="description">
								</div>
							
						
							<button type="submit" class="btn btn-primary">Submit</button>
						</form>
						
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

<!-- 模态框（Modal） -->
<div class="modal fade" id="addAuthModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
						&times;
					</button>
					<h4 class="modal-title" id="myModalLabel">
						添加Auth
					</h4>
				</div>
				<div class="modal-body">
					
					<div class="table-responsive">
						
						<form action="<%=path %>/addAuth" method="POST" role="form">
						
							<div class="form-group">
								<label for="">url</label>
								<input type="text" class="form-control" id="" placeholder="Input field" name="url">
								<select name="url"  class="form-control" >
										<option value=""></option>										
										<c:forEach items="${urls }" var="url">
											<option value="${url }">${url }</option>
										</c:forEach>
								</select>
							</div>
							<div class="form-group">
									<label for="">role</label>
									
									<select name="roleid"  class="form-control" required="required">										
										<c:forEach items="${roles }" var="role">
											<option value="${role.roleid }">${role.roleid }</option>
										</c:forEach>
									</select>
									
							</div>
							
						
							<button type="submit" class="btn btn-primary">Submit</button>
						</form>
						
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
			<div class="col-sm-6">
				<div class="page-header search-div">
					<h1>
						角色
						
						<button type="button" class="btn btn-default" style="float:right" data-toggle="modal" data-target="#addRolesModal" id="addRole">添加角色</button>	
						
					</h1>
				</div>

				<table class="table table-bordered table-hover">
					<thead>
						<tr>
							<th>roleid</th>
							<th>description</th>
							<th></th>
						</tr>
					</thead>
					<tbody id="roles">
						<c:forEach items="${roles }" var="role">
							<tr>
								<td>${role.roleid }</td>
								<td>${role.description }</td>
								<td>
								<a class="btn btn-default" href="delRole?roleid=${role.roleid }" role="button">delete</a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				


			</div>
			<div class="col-sm-6">
				<div class="page-header search-div">
					<h1>
						角色
						
						<button type="button" class="btn btn-default" style="float:right" data-toggle="modal" data-target="#addAuthModal" id="addAuth">添加Auth</button>	
						
					</h1>
				</div>

				<table class="table table-bordered table-hover">
					<thead>
						<tr>
							<th>url</th>
							<th>roleid</th>
							<th></th>
						</tr>
					</thead>
					<tbody id="roles">
						<c:forEach items="${auths }" var="auth">
							<tr>
								<td>${auth.url }</td>
								<td>${auth.role.roleid }</td>
								<td>
								<a class="btn btn-default" href="delAuth?id=${auth.id }" role="button">delete</a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				


			</div>
	</div>





</body>

</html>
<script>
	

	
</script>