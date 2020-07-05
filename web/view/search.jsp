<%-- 
    Document   : search
    Created on : Jun 23, 2020, 9:28:36 AM
    Author     : Tran Dong
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<!DOCTYPE html>
<link rel="stylesheet" href="http://localhost:8080/FoodSupplement/css/home.css"/>
<link rel="stylesheet" href="http://localhost:8080/FoodSupplement/css/common.css"/>
<script src="http://localhost:8080/FoodSupplement/js/localStorage.service.js"></script>
<script src="http://localhost:8080/FoodSupplement/js/base.controller.js"></script>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body class="container">
        <c:set var="PRODUCTS" value="${requestScope.PRODUCTS}"/>
        <c:set var="PAGING" value="${requestScope.PAGING}"/>
        
        <c:set var="CATEGORIES" value="${requestScope.CATEGORIES}"/>
        <x:transform doc="${CATEGORIES}" xslt="${applicationScope.XSL_CATE_ITEM}"/>

        <a href="/FoodSupplement"><h1>Bảng xếp hạng chỉnh ảnh</h1></a>
        <div class="row">
            <form action="">
                <input type="hidden" name="type" value="${param.type}"  />
                <input type="text" name="txtSearch" value="${param.txtSearch}" placeholder="Tìm kiếm theo tên"/>
                <input type="submit" class="btn" value="Tìm kiếm"/>
            </form>
        </div>

        <x:transform doc="${PRODUCTS}" xslt="${applicationScope.XSL_PRODUCT_ITEM}"/>
        <div class="row">
            <x:transform doc="${PAGING}" xslt="${applicationScope.XSL_PAGING}"/>
        </div>
        <button onclick="goToComparison()" id="btnComparison">Đến trang so sánh (0)</button>
        <button onclick="clearSelected()"  id="btnComparisonClear">Xóa dữ liệu so sánh</button>

    </body>
</html>
