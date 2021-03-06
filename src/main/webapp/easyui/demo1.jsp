<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<!-- 先引入 jquery的 js -->
<script type="text/javascript"
	src="${pageContext.request.contextPath }/easyui/js/jquery-1.8.0.min.js">
	
</script>
<!-- 引入 easyui的js -->
<script type="text/javascript"
	src="${pageContext.request.contextPath }/easyui/js/jquery.easyui.min.js"></script>
<!-- 引入国际化 js -->
<script type="text/javascript"
	src="${pageContext.request.contextPath }/easyui/js/locale/easyui-lang-zh_CN.js"></script>
<!-- 引入 默认样式 css -->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/easyui/js/themes/default/easyui.css" />
<!-- 引入 icon图标 css  -->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/easyui/js/themes/icon.css" />

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/easyui/ztree/zTreeStyle.css" />
<script type="text/javascript" src="${pageContext.request.contextPath }/easyui/ztree/jquery.ztree.all-3.5.js"></script>
<title>Insert title here</title>
</head>
<body class="easyui-layout">

	<div data-options="region:'north',title:'North Title',split:true"
		style="height:100px;"></div>

	<div data-options="region:'south',title:'South Title',split:true"
		style="height:100px;"></div>

	<div
		data-options="region:'east',iconCls:'icon-reload',title:'East',split:true"
		style="width:100px;"></div>

	<div data-options="region:'west',title:'West',split:true"
		style="width:100px;">
		<!-- 折叠面板  -->
		<!-- fit属性，使当前div大小占满父容器  -->
		<div class="easyui-accordion" data-options="fit:true">
			<!-- 通过iconCls 设置图标，找 icon.css中 类定义 -->
			<div data-options="title:'基本功能',iconCls:'icon-mini-add'">面板一</div> <!-- 这里每个div就是一个面板 -->
			<div data-options="title:'高级功能'">面板二</div>
			<div data-options="title:'管理员功能'">
				 <ul id="treeDemo" class="ztree"></ul>
			</div>
		</div>



	</div>

	<div data-options="region:'center'">
		<!-- 选项卡面板 -->
		<div class="easyui-tabs" data-options="fit:true">
			<div data-options="title:'选项卡一',closable:true">内容一</div> <!-- 这里每个div 就是一个选项卡 -->
			<!-- closeable 可关闭 -->
			<div data-options="title:'选项卡二',closable:true">内容二</div>
			<div data-options="title:'选项卡三',closable:true">内容三</div>
		</div>
	</div>


</body>

<script type="text/javascript">
 
  var setting = {
	data: {
		simpleData: {
			enable: true,
			idKey: "id",
			pIdKey: "pId",
			rootPId: 0
		}
	}
};
var treeNodes = [
    {"id":1, "pId":0, "name":"test1","icon":"./ztree/img/diy/2.png"},
    {"id":11, "pId":1, "name":"test11"},
    {"id":12, "pId":1, "name":"test12"},
    {"id":111, "pId":11, "name":"test111"},
    {"id":1111,"pId":111,"name":"test1111","url":"http://www.baidu.com"},
    {"id":2,"pId":0,"name":"test2"},
    {"id":21,"pId":2,"name":"test21"}
    
];
   $(document).ready(function(){
      zTreeObj = $.fn.zTree.init($("#treeDemo"), setting, treeNodes);
   });
</script>
</html>
