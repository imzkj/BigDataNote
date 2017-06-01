<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Hadoop2 demo</title>
<link rel="stylesheet", href="http://code.jquery.com/ui/1.9.2/themes/base/jquery-ui.css" />
<script type="text/javascript" src="http://code.jquery.com/jquery-1.8.3.js"></script>
<script type="text/javascript" src="http://code.jquery.com/ui/1.9.2/jquery-ui.js"></script>

<link rel="stylesheet", href="../bootstrap/css/bootstrap.css" />

</head>
<body>
	<div class="container">
		<h1><s:property value="username" /></h1>
		<div class="well">
			<form action="fri_login.do">
			   <input id="username" name="username"></input>
			   <input type="submit" value="登陆"></input>	   
			</form>
		</div>
		
		<div class="well">
			<label>你可能认识的朋友</label>
			<table class='table'>
				<s:iterator value="might" id='people'>
    				<tr><td><s:property value='people'/></td><td><a href="fri_login!watch.do?username=<s:property value='username' />&&watch=<s:property value='people'/>">关注</a></td></tr>
				</s:iterator> 
			</table>
		</div>
		
		<div class="well">
			<label>朋友们</label>
			<table class='table'>
				<s:iterator value="friends" id='friend'>
					<%--调用Java里面的方法方式：struts里面配置的action（实际上为该action对应的类），--%>
					<%--中间用感叹号，后面加上对应的方法--%>
    				<tr><td><s:property value='friend'/></td><td><a href="fri_login!unwatch.do?username=<s:property value='username' />&&unwatch=<s:property value='friend'/>">取消关注</a></td></tr>
				</s:iterator> 
			</table>
		</div>
		
		<div class="well">
			<label>未关注的人</label>
			<table class='table'>
				<s:iterator value="peoples.keySet()" id='people'>
    				<tr><td><s:property value='people'/></td><td><a href="fri_login!watch.do?username=<s:property value='username' />&&watch=<s:property value='people'/>">关注</a></td></tr>
				</s:iterator> 
			</table>
		</div>
	</div>
</body>
</html>