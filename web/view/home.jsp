<%-- 
    Document   : home
    Created on : Jun 9, 2020, 11:37:54 AM
    Author     : Tran Dong
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<!DOCTYPE html>
<link rel="stylesheet" href="http://localhost:8080/FoodSupplement/css/home.css"/>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <c:set var="PRODUCTS" value="${requestScope.PRODUCTS}"/>
        <c:set var="CATEGORIES" value="${requestScope.CATEGORIES}"/>
        <c:import charEncoding="UTF-8" var="category_item" url="http://localhost:8080/FoodSupplement/xsl/category_item.xsl"/>
        <c:import charEncoding="UTF-8" var="product_item" url="http://localhost:8080/FoodSupplement/xsl/product_item.xsl"/>

        <x:transform doc="${PRODUCTS}" xslt="${product_item}"/>
        <x:transform doc="${CATEGORIES}" xslt="${category_item}"/>
    </body>
</html>
