<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Welcome to Food Supplement !</h1>
        <c:set var="abc" value="${requestScope.ABC}"/>
        <c:import charEncoding="UTF-8" var="xsltl" url="index.xsl"/>
        <x:transform doc="${abc}" xslt="${xsltl}"/>
    </body>
</html>
