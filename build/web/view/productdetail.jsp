<%-- 
    Document   : productdetail
    Created on : Jun 10, 2020, 10:10:00 PM
    Author     : Tran Dong
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<!DOCTYPE html>
<link rel="stylesheet" href="http://localhost:8080/FoodSupplement/css/common.css"/>
<link rel="stylesheet" href="http://localhost:8080/FoodSupplement/css/home.css"/>
<script src="http://localhost:8080/FoodSupplement/js/localStorage.service.js"></script>
<script src="http://localhost:8080/FoodSupplement/js/base.controller.js"></script>


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body class="container">
        <a href="/FoodSupplement"><h1>Bảng xếp hạng Chỉnh ảnh</h1></a>
        <c:set var="PRODUCT" value="${requestScope.PRODUCT}"/>
        <c:set var="SUBPRODUCTS" value="${requestScope.SUBPRODUCTS}"/>
        <c:set var="PRODUCTS" value="${requestScope.PRODUCTS}"/>


        <x:transform doc="${PRODUCT}" xslt="${applicationScope.XSL_PRODUCT_ITEM_DETAIL}"/>
        <h3>Sản phẩm cùng tên</h3>
        <div class="row">
            <x:transform doc="${SUBPRODUCTS}" xslt="${applicationScope.XSL_SUB_PRODUCT_ITEM}"/>
        </div>
        <h3>Sản phẩm tương tự</h3>
        <div class="row">
            <x:transform doc="${PRODUCTS}" xslt="${applicationScope.XSL_PRODUCT_ITEM}"/>
        </div>
        <button onclick="goToComparison()" id="btnComparison">Đến trang so sánh (0)</button>
        <button onclick="clearSelected()"  id="btnComparisonClear">Xóa dữ liệu so sánh</button>

    </body>
</html>
