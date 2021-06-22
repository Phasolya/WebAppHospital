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
    <title><fmt:message key="admin_page" /></title>
</head>


<body>
<div class="header">

    <div class="header_section">
        <div class="header_item">
            <h2><fmt:message key="admin_page" /></h2>
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
            <div class="header_section">
                <div class="header_item headerButton">
                    <form method="post" action="${pageContext.request.contextPath}/en/adminPage">
                        <input type="hidden" name="command" value="language"/>
                        <input type="hidden" name="language" value="en"/>
                        <p><input class="button" type="submit" value=<fmt:message key="en"/>></p>
                    </form>

                    <form method="post" action="${pageContext.request.contextPath}/ru/adminPage">
                        <input type="hidden" name="command" value="language"/>
                        <input type="hidden" name="language" value="ru"/>
                        <p><input class="button" type="submit" value=<fmt:message key="ru"/>></p>
                    </form>
                </div>
            </div>
        </div>

    </div>
</div>

<div class="split_screen">
    <div class="body_section">
        <p>
        <form method="post" action="${pageContext.request.contextPath}/admin/Doc-Patient">
            <input type="hidden" name="command" value="docForPatient"/>
        <fmt:message key="set_doctor"/>
            <input type="text" name="doctorLogin" placeholder=<fmt:message key="login"/>>
        <fmt:message key="for_patient"/>
            <input type="text" name="patientLogin" placeholder=<fmt:message key="login"/>>
            <input class="button" type="submit" value=<fmt:message key="set"/>>
        </form>
        </p>
    </div>

    <div class="body_section">
        <p>
        <form method="post" action="${pageContext.request.contextPath}/admin/deleteUser">
            <input type="hidden" name="command" value="deleteUser"/>
        <fmt:message key="delete_user"/>
            <input type="text" name="userEmail" placeholder=<fmt:message key="login"/>>
            <input class="button" type="submit" value=<fmt:message key="delete"/>>
        </form>
        </p>
    </div>

    <div class="body_section">
        <p>
        <form method="post" action="${pageContext.request.contextPath}/admin/registrationForm">
            <input type="hidden" name="command" value="getRegistrationForm"/>
        <fmt:message key="register_user"/>
            <input class="button" type="submit" value=<fmt:message key="start"/>>
        </form>
        </p>
    </div>
</div>

<div class="split_screen">
    <div class="simple_body_section">
        <p>
        <h3><fmt:message key="doctors"/> <fmt:message key="sorted.by"/> ${doctorsSortBy} </h3>
        <form method="post" action="${pageContext.request.contextPath}/admin/getDoctors">
            <input type="hidden" name="command" value="getDoctors"/>

            <label for="doctorsSortBy"><fmt:message key="sort_by"/></label>
            <select name="doctorsSortBy" id="doctorsSortBy">
                <option selected value="full_name"><fmt:message key="alphabetic"/></option>
                <option value="doctor_type"><fmt:message key="doctor_type"/></option>
                <option value="patients_amount"><fmt:message key="patient_amount"/></option>
            </select>
            <input class="button" type="submit" value=<fmt:message key="sort"/>>
        </form>
        </p>

        <p>
        <table class="table" cellspacing="2" border="1" cellpadding="5" width="600">
            <thead>
            <tr>
                <td class="nameColonn"><b><fmt:message key="login"/></b></td>
                <td class="nameColonn"><b><fmt:message key="password"/></b></td>
                <td class="nameColonn"><b><fmt:message key="full_name"/></b></td>
                <td class="nameColonn"><b><fmt:message key="birth_date"/></b></td>
                <td class="nameColonn"><b><fmt:message key="doctor_type"/></b></td>
                <td class="nameColonn"><b><fmt:message key="patient_amount"/></b></td>
            </tr>
            </thead>
            <c:set var="k" value="0"/>
            <c:forEach var="item" items="${doctors}">
            <c:set var="k" value="${k+1}"/>
            <tr>
                <td>${item.user.email}</td>
                <td>${item.user.password}</td>
                <td>${item.user.fullName}</td>
                <td>${item.user.birthDate}</td>
                <td>${item.doctorType}</td>
                <td>${item.patientsAmount}</td>
                </c:forEach>
        </table>
        </p>

    </div>
</div>

<div class="split_screen">
    <div class="simple_body_section">
        <p>
        <h3><fmt:message key="patients"/> <fmt:message key="sorted.by"/> ${patientsSortBy} </h3>
        <form method="post" action="${pageContext.request.contextPath}/admin/getPatients">
            <input type="hidden" name="command" value="getPatients"/>

            <label for="patientsSortBy"><fmt:message key="sort_by"/></label>
            <select name="patientsSortBy" id="patientsSortBy">
                <option value="full_name"><fmt:message key="alphabetic"/></option>
                <option value="birth_date"><fmt:message key="birth_date"/></option>
            </select>
            <input class="button" type="submit" value=<fmt:message key="sort"/>>
        </form>
        </p>

        <p>
        <table class="table" cellspacing="2" border="1" cellpadding="5" width="600">
            <thead>
            <tr>
                <td class="nameColonn"><b><fmt:message key="login"/></b></td>
                <td class="nameColonn"><b><fmt:message key="password"/></b></td>
                <td class="nameColonn"><b><fmt:message key="full_name"/></b></td>
                <td class="nameColonn"><b><fmt:message key="birth_date"/></b></td>
            </tr>
            </thead>
            <c:set var="k" value="0"/>
            <c:forEach var="item" items="${patients}">
            <c:set var="k" value="${k+1}"/>
            <tr>
                <td>${item.email}</td>
                <td>${item.password}</td>
                <td>${item.fullName}</td>
                <td>${item.birthDate}</td>
                </c:forEach>
        </table>
        </p>

    </div>
</div>

</body>
</html>