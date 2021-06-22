<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="messages"/>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <style>
        <%@ include file="/css/style.css" %>
    </style>
    <title>
        <fmt:message key="hospital"/>
    </title>
</head>

<body>
<div class="header">
    <div class="header_section">
        <div class="header_item">
            <h2><fmt:message key="hospital"/></h2>
        </div>
    </div>

    <div class="header_section">
        <div class="header_item headerButton">
            <form method="post" action="${pageContext.request.contextPath}/en/loginPage">
                <input type="hidden" name="command" value="language"/>
                <input type="hidden" name="language" value="en"/>
                <p><input class="button" type="submit" value=<fmt:message key="en"/>></p>
            </form>

            <form method="post" action="${pageContext.request.contextPath}/ru/loginPage">
                <input type="hidden" name="command" value="language"/>
                <input type="hidden" name="language" value="ru"/>
                <p><input class="button" type="submit" value=<fmt:message key="ru"/>></p>
            </form>
        </div>
    </div>
</div>

<div class="body_section">
    <form method="post" action="${pageContext.request.contextPath}/guest/checkLogin">
        <input type="hidden" name="command" value="login"/>
        <p><input type="text" name="email"> <fmt:message key="email"/></p>
        <p><input type="text" name="password"> <fmt:message key="password"/></p>
        <p><input class="button" type="submit" value=<fmt:message key="enter"/>></p>
    </form>
</div>

</body>
</html>