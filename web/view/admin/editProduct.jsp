<%-- 
    Document   : editProduct
    Created on : Jun 18, 2020, 9:49:45 PM
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
        <c:set var="PRODUCT" value="${requestScope.PRODUCT}"/>
        <c:import charEncoding="UTF-8" var="product_item_detail_edit_form" url="http://localhost:8080/FoodSupplement/xsl/product_item_detail_edit_form.xsl"/>

        <h1>Edit Product</h1>
        <x:transform doc="${PRODUCT}" xslt="${product_item_detail_edit_form}"/>

    </body>
</html>
