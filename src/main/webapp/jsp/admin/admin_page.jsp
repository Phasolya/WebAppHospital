<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="messages"/>

<html lang="${param.language}">
<head>
    <style>
        <%@ include file="../../css/style.css" %>
    </style>
    <title><fmt:message key="admin_page"/></title>
</head>


<body>
<%--===========================================================--%>
<%--=========================header============================--%>
<%--===========================================================--%>
<div class="header">

    <div class="header_section">
        <div class="header_item">
            <h2><fmt:message key="admin_page"/></h2>
        </div>
    </div>
    <%--=====================LOGOUT BUTTON======================================--%>
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
                    <%--=====================LANGUAGE EN BUTTON======================================--%>
                    <form method="post" action="${pageContext.request.contextPath}/en/adminPage">
                        <input type="hidden" name="command" value="language"/>
                        <input type="hidden" name="language" value="en"/>
                        <p><input class="button" type="submit" value=<fmt:message key="en"/>></p>
                    </form>
                    <%--=====================LANGUAGE RU BUTTON======================================--%>
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
<%--===========================================================--%>
<%--========================= page ============================--%>
<%--===========================================================--%>
<div class="split_screen">
    <div class="body_section">
        <p>
            <%--=====================SET DOCTOR FOR PATIENT FORM======================================--%>
        <form method="post" action="${pageContext.request.contextPath}/admin/Doc-Patient">
            <input type="hidden" name="command" value="docForPatient"/>
            <fmt:message key="set_doctor"/>
            <input type="text" name="doctorEmail" placeholder=<fmt:message key="login"/>>
            <fmt:message key="for_patient"/>
            <input type="text" name="patientEmail" placeholder=<fmt:message key="login"/>>
            <input class="button" type="submit" value=<fmt:message key="set"/>>
        </form>
        </p>
    </div>
    <%--=====================DELETE USER FORM======================================--%>
    <div class="body_section">
        <p>
        <form method="post" action="${pageContext.request.contextPath}/admin/deleteUser">
            <input type="hidden" name="command" value="deleteUser"/>
            <fmt:message key="delete_user"/>
            <input type="text" name="email" placeholder=<fmt:message key="login"/>>
            <input class="button" type="submit" value=<fmt:message key="delete"/>>
        </form>
        </p>
    </div>
    <%--=====================GET REGISTRATION FORM======================================--%>
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
<%--=========================================================================================--%>
<%--===============================DOCTORS LIST PART=========================================--%>
<%--=========================================================================================--%>
<div class="split_screen">
    <div class="simple_body_section">
        <p>
            <%--=====================DOCTORS LIST HEADER======================================--%>
        <h3><fmt:message key="doctors.list"/></h3>
        <c:if test="${doctorsSortParameter != null}">
            <h4><fmt:message key="sort.type"/>: ${doctorsSortParameter}</h4>
        </c:if>
        <%--=====================DOCTORS SORT FORM======================================--%>
        <form method="post" action="${pageContext.request.contextPath}/admin/getDoctors">
            <input type="hidden" name="command" value="getDoctors"/>

            <label for="doctorsSortParameter"><fmt:message key="sort_by"/></label>
            <select name="doctorsSortParameter" id="doctorsSortParameter">
                <option selected value="full_name"><fmt:message key="alphabetic"/></option>
                <option value="doctor_type"><fmt:message key="doctor_type"/></option>
                <option value="patients_amount"><fmt:message key="patient_amount"/></option>
            </select>
            <input class="button" type="submit" value=<fmt:message key="sort"/>>
        </form>
        </p>
        <%--=====================DOCTORS LIST TABLE======================================--%>
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
        <%--=====================DOCTORS LIST PAGES INFO======================================--%>
        <c:if test="${doctorsCurrentPage != null}">
            <h4>страница ${doctorsCurrentPage} из ${doctorsCurrentPage}</h4>
        </c:if>
        <%--=====================DOCTORS LIST previous BUTTON======================================--%>
        <c:if test="${doctorsCurrentPage != 1 && doctorsCurrentPage != null}">

            <form method="post" action="${pageContext.request.contextPath}/admin/getDoctors">
                <input type="hidden" name="command" value="getDoctors"/>
                <input type="hidden" name="doctorsSortParameter" value="${doctorsPageSortBy}"/>
                <input type="hidden" name="doctorsCurrentPage" value="${doctorsCurrentPage-1}"/>
                <input class="button" type="submit" value=<fmt:message key="previous"/>>
            </form>

        </c:if>
        <%--=====================DOCTORS LIST next BUTTON======================================--%>
        <c:if test="${doctorsCurrentPage != null && doctorsCurrentPage < doctorsNoOfPages}">

            <form method="post" action="${pageContext.request.contextPath}/admin/getDoctors">
                <input type="hidden" name="command" value="getDoctors"/>
                <input type="hidden" name="doctorsSortParameter" value="${doctorsPageSortBy}"/>
                <input type="hidden" name="doctorsCurrentPage" value="${doctorsCurrentPage+1}"/>
                <input class="button" type="submit" value=<fmt:message key="next"/>>
            </form>

        </c:if>
    </div>
</div>
<%--=========================================================================================--%>
<%--===============================PATIENTS LIST PART=========================================--%>
<%--=========================================================================================--%>
<div class="split_screen">
    <div class="simple_body_section">
        <p>
            <%--=====================PATIENTS LIST HEADER======================================--%>
        <h3><fmt:message key="patients.list"/></h3>
        <c:if test="${patientsSortParameter != null}">
            <h4><fmt:message key="sort.type"/>: ${patientsSortParameter}</h4>
        </c:if>
        <%--=====================PATIENTS SORT FORM======================================--%>
        <form method="post" action="${pageContext.request.contextPath}/admin/getPatients">
            <input type="hidden" name="command" value="getPatients"/>

            <label for="patientsSortParameter"><fmt:message key="sort_by"/></label>
            <select name="patientsSortParameter" id="patientsSortParameter">
                <option value="full_name"><fmt:message key="alphabetic"/></option>
                <option value="birth_date"><fmt:message key="birth_date"/></option>
            </select>
            <input class="button" type="submit" value=<fmt:message key="sort"/>>
        </form>
        </p>
        <%--=====================PATIENTS LIST TABLE======================================--%>
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
        <%--=====================PATIENTS LIST PAGES INFO======================================--%>
        <c:if test="${patientsCurrentPage != null}">
            <h4>страница ${patientsCurrentPage} из ${patientsCurrentPage}</h4>
        </c:if>
        <%--=====================PATIENTS LIST previous BUTTON======================================--%>
        <c:if test="${patientsCurrentPage != 1 && patientsCurrentPage != null}">

            <form method="post" action="${pageContext.request.contextPath}/admin/getPatients">
                <input type="hidden" name="command" value="getPatients"/>
                <input type="hidden" name="patientsSortParameter" value="${patientsPageSortBy}"/>
                <input type="hidden" name="patientsCurrentPage" value="${patientsCurrentPage-1}"/>
                <input class="button" type="submit" value=<fmt:message key="previous"/>>
            </form>

        </c:if>
        <%--=====================PATIENTS LIST next BUTTON======================================--%>
        <c:if test="${doctorsCurrentPage != null && doctorsCurrentPage < doctorsNoOfPages}">

            <form method="post" action="${pageContext.request.contextPath}/admin/getPatients">
                <input type="hidden" name="command" value="getPatients"/>
                <input type="hidden" name="patientsSortParameter" value="${patientsPageSortBy}"/>
                <input type="hidden" name="patientsCurrentPage" value="${patientsCurrentPage+1}"/>
                <input class="button" type="submit" value=<fmt:message key="next"/>>
            </form>

        </c:if>
    </div>
</div>

</body>
</html>