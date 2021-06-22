<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle basename="messages"/>

<html lang="${param.language}">
<head>
    <style>
        <%@ include file="../../css/style.css" %>
    </style>
    <title><fmt:message key="success"/></title>
</head>

<body>
<div class="header">

    <div class="header_section">
        <div class="header_item">
            <h2><fmt:message key="success" /></h2>
        </div>
    </div>

    <div class="header_section">
        <div class="header_item headerButton">
            <form method="post" action="${pageContext.request.contextPath}/mainPage">
                <input type="hidden" name="command" value="logout"/>
                <p><input class="button" type="submit" value=<fmt:message key="logout"/>></p>
            </form>
        </div>
    </div>
</div>

<div class="simple_body_section">
    <p>
    <h3><fmt:message key="message"/> :</h3>
    <div class="success">
        <h3>${message}</h3>
    </div>
    </p>
    <form method="post" action="${pageContext.request.contextPath}/mainPage">
        <input type="hidden" name="command" value="mainPage"/>
        <p><input class="button" type="submit" value=<fmt:message key="back"/>></p>
    </form>
</div>

</body>
</html>
