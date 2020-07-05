<%-- 
    Document   : comparison
    Created on : Jul 2, 2020, 11:22:09 AM
    Author     : shuu1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="http://localhost:8080/FoodSupplement/css/common.css"/>
        <link rel="stylesheet" href="http://localhost:8080/FoodSupplement/css/home.css"/>

        <script src="http://localhost:8080/FoodSupplement/js/localStorage.service.js"></script>
        <script src="http://localhost:8080/FoodSupplement/js/base.controller.js"></script>
    </head>
    <body class="container">
        <a href="/FoodSupplement"><h1>Bảng xếp hạng Chỉnh ảnh</h1></a>
        <c:set var="PRODUCTS" value="${requestScope.PRODUCTS}"/>
        <c:import charEncoding="UTF-8" var="XSL" url="http://localhost:8080/FoodSupplement/xsl/comparison.xsl"/>
        <x:transform  doc="${PRODUCTS}" xslt="${XSL}" />
        <button onclick="goToComparison()" id="btnComparison">Đến trang so sánh (0)</button>
        <button onclick="clearSelected()"  id="btnComparisonClear">Xóa dữ liệu so sánh</button>

    </body>
</html>
