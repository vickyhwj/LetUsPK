<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

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
 		input{
 			width: 100%;
 		}
		.row{
			margin-bottom: 30px: 
		}
		body{
			padding: 40px;
		}
		form{
			margin-bottom: 10px;
		}
    </style>


</head>

<body>
   <form action="j_spring_security_check" method="POST" role="form">
   	<legend>login</legend>
   
   	<div class="form-group">
   		
   		<label for="">username${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message }</label>
   		<input type="text" class="form-control" id="" placeholder="Input field" name="j_username">
   	</div>
   	<div class="form-group">
   		<label for="">password</label>
   		<input type="password" class="form-control" id="" placeholder="Input field" name="j_password">
   	</div>
   
   	<input type="text" name="way" style="display: none;" value="1">
   
   	<button type="submit" class="btn btn-primary">Submit</button>
   </form>
   <a href="index.html">人机对战</a>
   <form action="register" method="POST" role="form">
   	<legend>register</legend>
   
   	<div class="form-group">
   		<label for="">username</label>
   		<input type="text" class="form-control" id="" placeholder="Input field" name="username">
   	</div>
   	<div class="form-group">
   		<label for="">password</label>
   		<input type="password" class="form-control" id="" placeholder="Input field" name="password">
   	</div>
   
   	
   <input type="text" name="way" style="display: none;" value="2">
   	<button type="submit" class="btn btn-primary">Submit</button>
   </form>
</body>

</html>
<script type="text/javascript">


</script>