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
<script src="http://localhost:8080/FoodSupplement/js/localStorage.service.js"></script>
<script src="http://localhost:8080/FoodSupplement/js/base.controller.js"></script>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head> 
    <body class="container">
        <c:set var="PRODUCTS" value="${requestScope.PRODUCTS}"/>
        <c:set var="PRODUCTS2" value="${requestScope.PRODUCTS2}"/>
        <c:set var="PRODUCTS3" value="${requestScope.PRODUCTS3}"/>
        <c:set var="PRODUCTS4" value="${requestScope.PRODUCTS4}"/>

        <c:set var="CATEGORIES" value="${requestScope.CATEGORIES}"/>
        <x:transform doc="${CATEGORIES}" xslt="${applicationScope.XSL_CATE_ITEM}"/>

        <h1> Bảng xếp hạng Chỉnh Ảnh </h1>
        <div class="row">
            <form action="SearchServlet">
                Tìm kiếm theo tên:<input name="txtSearch" value="${param.txtSearch}"/>
                <input type="submit" value="Tìm kiếm" class="btn"/>
            </form>
        </div>
        <h3>Sản phẩm chất lượng cao</h3>
        <a class="info" href="SearchServlet?type=isTop" >Xem thêm >></a>

        <div class="row">
            <x:transform doc="${PRODUCTS}" xslt="${applicationScope.XSL_PRODUCT_ITEM}"/>
        </div>

        <h3>Top sản phẩm độ phân giải điểm ảnh cao</h3>
        <a class="info" href="SearchServlet?type=isTopDpg">Xem thêm >></a>

        <div class="row">
            <x:transform doc="${PRODUCTS2}" xslt="${applicationScope.XSL_PRODUCT_ITEM}"/>
        </div>
        <h3>Top sản phẩm độ nhạy sáng ISO cao</h3> 
        <a class="info" href="SearchServlet?type=isTopIso">Xem thêm >></a>
        <div class="row">
            <x:transform doc="${PRODUCTS3}" xslt="${applicationScope.XSL_PRODUCT_ITEM}"/>
        </div> 
        <h3>Top sản phẩm có tốc độ màn chụp cao</h3>
        <a class="info" href="SearchServlet?type=isTopFps">Xem thêm >></a>
        <div class="row">
            <x:transform doc="${PRODUCTS4}" xslt="${applicationScope.XSL_PRODUCT_ITEM}"/>
        </div>
        <button onclick="goToComparison()"  id="btnComparison">Đến trang so sánh (0)</button>
        <button onclick="clearSelected()"  id="btnComparisonClear">Xóa dữ liệu so sánh</button>

    </body>
</html>
