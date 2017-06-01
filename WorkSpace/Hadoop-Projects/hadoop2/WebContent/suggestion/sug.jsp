<?xml version="1.0" encoding="UTF-8" ?> 
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Hadoop2 demo</title>
<link rel="stylesheet", href="http://code.jquery.com/ui/1.9.2/themes/base/jquery-ui.css" />
<script type="text/javascript" src="../jquery-3.2.1.js"></script>
<script type="text/javascript" src="../jquery-ui-1.12.1/jquery-ui.js"></script>

<link rel="stylesheet", href="../bootstrap/css/bootstrap.css" />

<script type="text/javascript">
	//jquery方法，加载页面时触发
	$(document).ready(function(){
	    //autocomplete方法在jquery-ui.js定义
		$("#query").autocomplete({
		    //定义了一个source（即输入关键词下面弹出的可选项），由接下来的
			//function(request, response)提供
			source : function(request, response){
			    //调用ajax方法向后台发送ajax请求
				$.ajax({
				    //定义url
					url : "ajax/sug.do",
					//返回数据格式为json
					dataType : "json",
					data : {
					    //$("#query")引用id为query的元素，.val()为获取到它的值
						query : $("#query").val()
					},
					success : function(data){
						response($.map(data.result, function(item){
							return {value:item}
						}));
					}
				});
			},
			//定义有多少字符改变后向后台发送请求
			minLength:1,
		});
	});

</script>
</head>
<body>
	<div class="container">
		<h1>搜索预测</h1>
		<div class="well">
			<form action="sug.jsp">
			   <label>Search</label>
			   <input id="query" name="query"></input>
			   <input type="submit"></input>	   
			</form>
		</div>
	</div>
</body>
</html>