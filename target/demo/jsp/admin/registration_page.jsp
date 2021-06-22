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
    <title><fmt:message key="admin_page" /></title>
</head>


<body>
<%--===========================================================--%>
<%--=========================header============================--%>
<%--===========================================================--%>
<div class="header">

    <div class="header_section">
        <div class="header_item">
            <h2><fmt:message key="admin_page" /></h2>
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
    <h2><fmt:message key="registration_form"/></h2>
    <form method="post" action="${pageContext.request.contextPath}/admin/userRegistration" id="registration">

        <input type="hidden" name="command" value="userRegistration"/>

        <label for="email"><b><fmt:message key="email"/></b></label><br/>
        <input type="email" name="email" id="email"><br/>

        <label for="password"><b><fmt:message key="password"/></b></label><br/>
        <input type="password" name="password" id="password"><br/>

        <label for="fullName"><b><fmt:message key="full_name"/></b></label><br/>
        <input type="text" name="fullName" id="fullName"><br/>

        <label for="birthDate"><b><fmt:message key="birth_date"/></b></label><br/>
        <input type="date" name="birthDate" id="birthDate"><br/>

        <p><label for="userRole"><b><fmt:message key="user_role"/></b></label>
        <br><select name="userRole" id="userRole" form="registration">
            <option selected value=patient><fmt:message key="patient"/></option>
            <option value=doctor><fmt:message key="doctor"/></option>
            <option value=nurse><fmt:message key="nurse"/></option>
        </select></p>

        <label for="doctorType"><b><fmt:message key="doctor_type"/></b>(<fmt:message key="only_for_doc"/>)</label><br/>
        <input type="text" name="doctorType" id="doctorType"><br/>

        <input class="button" type="submit" value=<fmt:message key="register"/>>
    </form>
    <br/>

    <form method="post" action="${pageContext.request.contextPath}/mainPage">
        <input type="hidden" name="command" value="mainPage"/>
        <p><input class="button" type="submit" value=<fmt:message key="back"/>></p>
    </form>
</div>
</body>
</html>
