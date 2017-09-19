<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>My JSP 'user.jsp' starting page</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <!--
        <link rel="stylesheet" type="text/css" href="styles.css">
        -->
</head>

<body>
这是在site-web-user-0.0.1-SNAPSHOT.jar中的jsp文件
<br> 这个图片文件同样来自该模块的jar
<img alt="hp" src="<%=basePath%>resources/hp.gif">
跳转到site-web-finance-0.0.1-SNAPSHOT.jar中的
<a href="<%=basePath%>views/finance/finance.jsp">views/finance/finance.jsp</a>
<br>
<img alt="hp" src="<%=basePath%>resources/hp.gif">
</body>
</html>
