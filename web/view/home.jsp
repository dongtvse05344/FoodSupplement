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
<link rel="stylesheet" href="http://localhost:8080/FoodSupplement/css/common.css"/>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <c:set var="PRODUCTS" value="${requestScope.PRODUCTS}"/>
        <c:set var="PRODUCTS2" value="${requestScope.PRODUCTS2}"/>
        <c:set var="PRODUCTS3" value="${requestScope.PRODUCTS3}"/>
        <c:set var="PRODUCTS4" value="${requestScope.PRODUCTS4}"/>

        <c:set var="CATEGORIES" value="${requestScope.CATEGORIES}"/>
        <c:import charEncoding="UTF-8" var="category_item" url="http://localhost:8080/FoodSupplement/xsl/category_item.xsl"/>
        <c:import charEncoding="UTF-8" var="product_item" url="http://localhost:8080/FoodSupplement/xsl/product_item.xsl"/>
        <h1>Đệm Êm Đềm</h1>
        <h3>Combo bán chạy</h3>
        <a class="info">Xem thêm >></a>

        <div class="row">
            <x:transform doc="${PRODUCTS}" xslt="${product_item}"/>
        </div>

        <h3>Top sản phẩm cao cấp: trên 8 triệu</h3>
        <a class="info">Xem thêm >></a>

        <div class="row">
            <x:transform doc="${PRODUCTS2}" xslt="${product_item}"/>
        </div>
        <h3>Top sản phẩm trung cấp: 5 - triệu</h3> 
        <a class="info">Xem thêm >></a>
        <div class="row">
            <x:transform doc="${PRODUCTS3}" xslt="${product_item}"/>
        </div> 
        <h3>Top sản phẩm bình dân: dưới 5 triệu</h3>
        <a class="info">Xem thêm >></a>
        <div class="row">
            <x:transform doc="${PRODUCTS4}" xslt="${product_item}"/>
        </div>
        <%--<x:transform doc="${CATEGORIES}" xslt="${category_item}"/>--%>
    </body>
</html>
