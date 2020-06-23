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

        <c:import charEncoding="UTF-8" var="product_item_detail" url="http://localhost:8080/FoodSupplement/xsl/product_item_detail.xsl"/>
        <c:import charEncoding="UTF-8" var="product_item_volumes" url="http://localhost:8080/FoodSupplement/xsl/volumes_product.xsl"/>
        <c:import charEncoding="UTF-8" var="products_item" url="http://localhost:8080/FoodSupplement/xsl/product_item.xsl"/>
        <c:import charEncoding="UTF-8" var="sub_product_item" url="http://localhost:8080/FoodSupplement/xsl/sub_product_item.xsl"/>

        <x:transform doc="${PRODUCT}" xslt="${product_item_detail}"/>
        <h3>Sản phẩm cùng tên</h3>
        <div class="row">
            <x:transform doc="${SUBPRODUCTS}" xslt="${sub_product_item}"/>
        </div>
        <h3>Sản phẩm tương tự</h3>
        <div class="row">
            <x:transform doc="${PRODUCTS}" xslt="${products_item}"/>
        </div>

    </body>
</html>
