<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${param.language}" />
<fmt:setBundle basename="messages"/>

<html lang="${param.language}">
<head>
    <style>
        <%@ include file="../../css/style.css" %>
    </style>
    <title><fmt:message key="error"/></title>
</head>

<body>
<%--===========================================================--%>
<%--=========================header============================--%>
<%--===========================================================--%>
<div class="header">

    <div class="header_section">
        <div class="header_item">
            <h2><fmt:message key="hospital" /></h2>
        </div>
    </div>

    <div class="header_section">
        <div class="header_item headerButton">
            <form method="post" action="${pageContext.request.contextPath}/">
                <input type="hidden" name="command" value="logout"/>
                <p><input class="button" type="submit" value=<fmt:message key="logout"/>></p>
            </form>
        </div>

        <div class="header_item headerButton">
            <ul>
                <li><a href="?language=en"><fmt:message key="en" /></a></li>
                <li><a href="?language=ru"><fmt:message key="ru" /></a></li>
            </ul>
        </div>

    </div>
</div>

<%--===========================================================--%>
<%--========================= page ============================--%>
<%--===========================================================--%>

<div class="simple_body_section">
    <p>
    <h3><fmt:message key="message"/> :</h3>
    <div class="error">
        <h3>${message}</h3>
    </div>
    </p>
    <form method="post" action="${pageContext.request.contextPath}/menu">
        <input type="hidden" name="command" value="mainPage"/>
        <p><input class="button" type="submit" value=<fmt:message key="back"/>></p>
    </form>
</div>

</body>
</html>

