<%-- 
    Document   : search
    Created on : Jun 23, 2020, 9:28:36 AM
    Author     : Tran Dong
--%>

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
        <c:set var="PAGING" value="${requestScope.PAGING}"/>

        <c:import charEncoding="UTF-8" var="product_item" url="http://localhost:8080/FoodSupplement/xsl/product_item.xsl"/>
        <c:import charEncoding="UTF-8" var="paging" url="http://localhost:8080/FoodSupplement/xsl/paging.xsl"/>

        <a href="/FoodSupplement"><h1>B?ng x?p h?ng Ch?nh ?nh</h1></a>
        <div class="row">
            <form action="">
                <input type="hidden" name="type" value="${param.type}"  />
                <input type="text" name="txtNameSearch" value="${param.txtNameSearch}" placeholder="Tìm ki?m theo tên"/>
                <input type="submit" class="btn" value="Tìm ki?m"/>
            </form>
        </div>

        <x:transform doc="${PRODUCTS}" xslt="${product_item}"/>
        <div class="row">
            <x:transform doc="${PAGING}" xslt="${paging}"/>
        </div>
    </body>
</html>
