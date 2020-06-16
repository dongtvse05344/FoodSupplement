<%-- 
    Document   : category
    Created on : Jun 14, 2020, 9:50:02 AM
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
        <c:set var="CATEGORIES" value="${requestScope.CATEGORIES}"/>
        <c:set var="PAGING" value="${requestScope.PAGING}"/>
        <c:import charEncoding="UTF-8" var="product_item" url="http://localhost:8080/FoodSupplement/xsl/category_item_table.xsl"/>
        <c:import charEncoding="UTF-8" var="paging" url="http://localhost:8080/FoodSupplement/xsl/paging.xsl"/>

        <div id="menu-left">
            <ul>
                <li><a href="HomeAdminServlet">Quản lý sản phẩm</a></li>
                <li><a href="CategoryAdminServlet">Quản lý danh mục</a></li>
                <li><a href="CrawlServlet">Quản lý cào</a></li>
                <li><a  href="LogoutServlet">Đăng xuất</a></li>
            </ul>
        </div>
        <h1>Hello Admin: ${sessionScope.NAME}!</h1>
        <table border="1">
            <tr>
                <th>STT</th>
                <th>Name</th>
            </tr>
            <x:transform doc="${CATEGORIES}" xslt="${product_item}"/>

        </table>
        <x:transform doc="${PAGING}" xslt="${paging}"/>
    </body>
</html>
