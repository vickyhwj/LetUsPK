<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
<link rel="stylesheet" href="../css/bootstrap.css">
<script type="text/javascript" src="../js/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="../js/bootstrap.js"></script>
<style type="text/css">
.search-div {
	margin-top: 0px;
}

input#index {
	width: 50px;
}

</style>

</head>

<body>




	<nav class="navbar navbar-default" role="navigation">
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target=".navbar-ex1-collapse">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#">Title</a>
		</div>

		<!-- Collect the nav links, forms, and other content for toggling -->
		<div class="collapse navbar-collapse navbar-ex1-collapse">
			<ul class="nav navbar-nav">
				<li data="<%=request.getContextPath() %>/ManagerUser" class="active"><a href="javascript:void(0)" >用户管理</a></li>
				<li data="<%=request.getContextPath() %>/ManagerAuth"><a href="javascript:void(0)" >系统管理</a></li>
			</ul>

			<ul class="nav navbar-nav navbar-right">
				<li><a href="#">Manager</a></li>

			</ul>
		</div>
		<!-- /.navbar-collapse -->
	</nav>
<div style="width:100%;height:1600px;overflow:hidden;" id="frame">
	<iframe src="<%=request.getContextPath() %>/ManagerUser" scrolling="auto" style="width:100%;height:100%;border:0;"></iframe>
</div>
<script type="text/javascript">
	$('li').click(function(){
			$("#frame").html('<iframe src="'+$(this).attr("data")+'" scrolling="auto" style="width:100%;height:100%;border:0;"></iframe>');
			$('li').removeClass('active')
			$(this).addClass('active')
	})
</script>



</body>

</html>