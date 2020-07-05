<%-- 
    Document   : home
    Created on : Jun 13, 2020, 5:21:03 PM
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
        <c:set var="PRODUCTS" value="${requestScope.PRODUCTS}"/>
        <c:set var="PAGING" value="${requestScope.PAGING}"/>

        <c:import charEncoding="UTF-8" var="product_item" url="http://localhost:8080/FoodSupplement/xsl/product_item_table.xsl"/>
        <c:import charEncoding="UTF-8" var="paging" url="http://localhost:8080/FoodSupplement/xsl/paging.xsl"/>

        <div id="menu-left">
            <ul>
                <li><a class="success">Quản lý sản phẩm</a></li>
                <li><a href="CategoryAdminServlet">Quản lý danh mục</a></li>
                <li><a href="CrawlServlet">Quản lý cào</a></li>
                <li><a href="LogoutServlet">Đăng xuất</a></li>
            </ul>
        </div>
        <h1>Hello Admin: ${sessionScope.NAME}!</h1>
        <div class="row">
            <form action="HomeAdminServlet">
                <input type="text" name="txtSearch" value="${param.txtSearch}" placeholder="Tìm kiếm theo tên"/>
                <input type="submit" class="btn" value="Tìm kiếm"/>
            </form>
        </div>
        <div class="row">
            <form action="QuantifyDataServlet">
                <input type="submit" class="btn" value="Lượng hoá dữ liệu"/>
            </form>
        </div>
        <x:transform doc="${PRODUCTS}" xslt="${product_item}"/>
        <x:transform doc="${PAGING}" xslt="${paging}"/>

    </body>
</html>
