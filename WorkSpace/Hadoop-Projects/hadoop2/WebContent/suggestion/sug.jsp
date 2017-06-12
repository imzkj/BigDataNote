<?xml version="1.0" encoding="UTF-8" ?> 
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Hadoop2 demo</title>
<link rel="stylesheet", href="http://code.jquery.com/ui/1.9.2/themes/base/jquery-ui.css" />
<script type="text/javascript" src="http://code.jquery.com/jquery-1.8.3.js"></script>
<script type="text/javascript" src="http://code.jquery.com/ui/1.9.2/jquery-ui.js"></script>

<link rel="stylesheet", href="../bootstrap/css/bootstrap.css" />

<script type="text/javascript">
	$(document).ready(function(){
		$("#query").autocomplete({
			source : function(request, response){
				$.ajax({
					url : "ajax/sug.do",
					dataType : "json",
					data : {
						query : $("#query").val()
					},
					success : function(data){
						response($.map(data.result, function(item){
							return {value:item}
						}));
					}
				});
			},
			minLength:1,
		});
	});

</script>
</head>
<body>
	<div class="container">
		<h1>Search Demo</h1>
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