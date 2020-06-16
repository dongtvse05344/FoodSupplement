<%-- 
    Document   : login
    Created on : Jun 13, 2020, 4:53:08 PM
    Author     : Tran Dong
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<!DOCTYPE html>
<link rel="stylesheet" href="http://localhost:8080/FoodSupplement/css/common.css"/>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Login Form</h1>
        <form action="/FoodSupplement/LoginServlet" method="post">
            <p>User name</p>
            <input name="username" type="text" value="${param.username}"/>
            <p>Password</p>
            <input name="password" type="password" value="${param.password}"/>
            <br/>
            <p class="danger">${requestScope.ERROR}</p>
            <button class="btn" type="submit">Login</button>
            <br/>
            <a href="HomeServlet">Back to home</a>
        </form>
    </body>
</html>
