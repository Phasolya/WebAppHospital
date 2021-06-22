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
    <title><fmt:message key="doctor_page"/></title>
</head>

<body>
<%--===========================================================--%>
<%--=========================header============================--%>
<%--===========================================================--%>
<div class="header">

    <div class="header_section">
        <div class="header_item">
            <h2><fmt:message key="doctor_page" /></h2>
        </div>
    </div>

    <div class="header_section">
        <div class="header_item headerButton">
            <form method="post" action="${pageContext.request.contextPath}/loginPage">
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

<div class="split_screen">
    <div class="body_section">
        <p>
        <form method="post" action="${pageContext.request.contextPath}/admin/setDiagnose">
            <input type="hidden" name="command" value="setDiagnose"/>
            <fmt:message key="set_treatment" /> <fmt:message key="for_patient" />
            <input type="text" name="patientLogin" placeholder=<fmt:message key="login" />>
            <fmt:message key="disease" />
            <input type="text" name="disease" placeholder=<fmt:message key="disease" /> <fmt:message key="name" />>
            <fmt:message key="treatment" />
            <input type="text" name="treatment" placeholder=<fmt:message key="treatment" /> <fmt:message key="name" />>

            <label for="treatmentType"><fmt:message key="treatment_type" /></label>
            <select name="treatmentType" id="treatmentType">
                <option selected value="procedure"><fmt:message key="procedure" /></option>
                <option value="medicine"><fmt:message key="medicine" /></option>
                <option value="operation"><fmt:message key="operation" /></option>
            </select>

            <input class="button" type="submit" value=<fmt:message key="set" />>
        </form>
        </p>
    </div>

    <div class="body_section">
        <p>
        <form method="post" action="${pageContext.request.contextPath}/admin/setDisease">
            <input type="hidden" name="command" value="setDisease"/>
        <fmt:message key="set" /> <fmt:message key="disease" />
            <input type="text" name="disease" placeholder=<fmt:message key="disease" /> <fmt:message key="name" />>
            <fmt:message key="for_patient" />
            <input type="text" name="patient" placeholder=<fmt:message key="login" />>
            <input class="button" type="submit" value=<fmt:message key="delete" />>
        </form>
        </p>
    </div>

    <div class="body_section">
        <p>
        <form method="post" action="${pageContext.request.contextPath}/admin/doTreatment">
            <input type="hidden" name="command" value="doTreatment"/>
            <fmt:message key="do" /> <fmt:message key="treatment" />
            <input type="text" name="Treatment id" placeholder=<fmt:message key="treatment" /> <fmt:message key="id" />>
            <input class="button" type="submit" value=<fmt:message key="do" />>
        </form>
        </p>
    </div>
</div>

<div class="split_screen">
    <div class="simple_body_section">
        <p>
        <h3><fmt:message key="treatments" /> </h3>
        <form method="post" action="${pageContext.request.contextPath}/admin/sortTreatments">
            <input type="hidden" name="command" value="sortTreatments"/>

            <label for="treatmentsSortBy"><fmt:message key="sort_by" /></label>
            <select name="treatmentsSortBy" id="treatmentsSortBy">
                <option selected value="treatment_type"><fmt:message key="treatment_type" /></option>
                <option value="patient"><fmt:message key="patient" /></option>
            </select>
            <input class="button" type="submit" value=<fmt:message key="sort" />>
        </form>
        </p>

        <p>
        <table class="table" cellspacing="2" border="1" cellpadding="5" width="600">
            <thead>
            <tr>
                <td class="nameColonn"><b><fmt:message key="id" /></b></td>
                <td class="nameColonn"><b><fmt:message key="name" /></b></td>
                <td class="nameColonn"><b><fmt:message key="treatment_type" /></b></td>
                <td class="nameColonn"><b><fmt:message key="user_login" /></b></td>
                <td class="nameColonn"><b><fmt:message key="disease" /></b></td>
            </tr>
            </thead>
            <c:set var="k" value="0"/>
            <c:forEach var="item" items="${doctors}">
            <c:set var="k" value="${k+1}"/>
            <tr>
                <td>${item.user.login}</td>
                <td>${item.user.password}</td>
                <td>${item.user.fullName}</td>
                <td>${item.user.email}</td>
                <td>${item.user.birthDate}</td>
                <td>${item.doctorType}</td>
                <td>${item.patientAmount}</td>
                </c:forEach>
        </table>
        </p>

    </div>
</div>

<div class="split_screen">
    <div class="simple_body_section">
        <p>
        <h3><fmt:message key="my_patients" /></h3>
        <form method="post" action="${pageContext.request.contextPath}/admin/sortUsers">
            <input type="hidden" name="command" value="sortPatients"/>

            <label for="patientsSortBy"><fmt:message key="sort_by" /></label>
            <select name="patientsSortBy" id="patientsSortBy">
                <option value="full_name"><fmt:message key="alphabetic" /></option>
                <option value="birth_date"><fmt:message key="birth_date" /></option>
            </select>
            <input class="button" type="submit" value=<fmt:message key="sort" />>
        </form>
        </p>

        <p>
        <table class="table" cellspacing="2" border="1" cellpadding="5" width="600">
            <thead>
            <tr>
                <td class="nameColonn"><b><fmt:message key="login" /></b></td>
                <td class="nameColonn"><b><fmt:message key="full_name" /></b></td>
                <td class="nameColonn"><b><fmt:message key="email" /></b></td>
                <td class="nameColonn"><b><fmt:message key="birth_date" /></b></td>
            </tr>
            </thead>
            <c:set var="k" value="0"/>
            <c:forEach var="item" items="${patients}">
            <c:set var="k" value="${k+1}"/>
            <tr>
                <td>${item.login}</td>
                <td>${item.fullName}</td>
                <td>${item.email}</td>
                <td>${item.birthDate}</td>
                </c:forEach>
        </table>
        </p>

    </div>
</div>

</body>
</html>