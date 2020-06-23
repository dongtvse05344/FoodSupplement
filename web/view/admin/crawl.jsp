<%-- 
    Document   : crawl
    Created on : Jun 15, 2020, 11:01:39 AM
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
    <body class="container">
        <c:set var="PRODUCTS" value="${requestScope.PRODUCTS}"/>
        <c:set var="PAGING" value="${requestScope.PAGING}"/>
        <c:set var="ERROR" value="${requestScope.ERROR_VALIDATION}"/>
        <c:set var="MESSAGE" value="${requestScope.MESSAGE}"/>

        <c:import charEncoding="UTF-8" var="product_item" url="http://localhost:8080/FoodSupplement/xsl/product_raw_table.xsl"/>
        <c:import charEncoding="UTF-8" var="paging" url="http://localhost:8080/FoodSupplement/xsl/paging.xsl"/>

        <div id="menu-left">
            <ul>
                <li><a href="HomeAdminServlet">Quản lý sản phẩm</a></li>
                <li><a href="CategoryAdminServlet">Quản lý danh mục</a></li>
                <li><a class="success">Quản lý cào</a></li>
                <li><a href="LogoutServlet">Đăng xuất</a></li>
            </ul>
        </div>
        <h1>Hello Admin: ${sessionScope.NAME}!</h1>
        <div class="row">
            <div class="row">
                <form action="GroupByNameServlet">
                    <input type="submit" class="btn" value="Phân nhóm theo tên"/>
                </form>
            </div>
            <div class="row">
                <form action="CreateRawXMLServlet">
                    <input type="submit" class="btn" value="Sinh file XML"/>
                </form>
                Bạn có thể xem file XML _<a href="products.xml" download="true"> tại đây</a>
            </div>
            <div class="row">
                <p class="warning" style="width: 100%">Đảm bảo rằng file XML là dữ liệu mới nhất</p>
                <form action="ValidationServlet">
                    <input type="submit" class="btn" value="Kiểm tra XML"/>
                </form>
                <form action="InsertRawXMLServlet">
                    <input type="submit" class="btn" value="Insert vô Database"/>
                </form>
            </div>

            <div class="row">
                <pre class="info">${MESSAGE}</pre>
                <pre class="danger">${ERROR}</pre>
            </div>
        </div>

        <x:transform doc="${PRODUCTS}" xslt="${product_item}"/>
        <x:transform doc="${PAGING}" xslt="${paging}"/>
    </body>
</html>
