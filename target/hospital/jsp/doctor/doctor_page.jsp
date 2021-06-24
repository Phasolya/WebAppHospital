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
    <%--=====================LOGOUT BUTTON======================================--%>
    <div class="header_section">
        <div class="header_item headerButton">
            <form method="post" action="${pageContext.request.contextPath}/loginPage">
                <input type="hidden" name="command" value="logout"/>
                <p><input class="button" type="submit" value=<fmt:message key="logout"/>></p>
            </form>
        </div>
        <%--=====================LANGUAGE EN BUTTON======================================--%>
        <div class="header_item headerButton">
            <form method="post" action="${pageContext.request.contextPath}/en/doctorPage">
                <input type="hidden" name="command" value="language"/>
                <input type="hidden" name="language" value="en"/>
                <p><input class="button" type="submit" value=<fmt:message key="en"/>></p>
            </form>
            <%--=====================LANGUAGE RU BUTTON======================================--%>
            <form method="post" action="${pageContext.request.contextPath}/ru/doctorPage">
                <input type="hidden" name="command" value="language"/>
                <input type="hidden" name="language" value="ru"/>
                <p><input class="button" type="submit" value=<fmt:message key="ru"/>></p>
            </form>
        </div>

    </div>
</div>

<%--===========================================================--%>
<%--========================= page ============================--%>
<%--===========================================================--%>

<div class="split_screen">
    <%--=====================SET TREATMENT FORM======================================--%>
    <div class="body_section">
        <p>
        <form method="post" action="${pageContext.request.contextPath}/admin/setTreatment">
            <input type="hidden" name="command" value="setTreatment"/>
            <fmt:message key="set_treatment" /> <fmt:message key="for_patient" />
            <input type="text" name="patientLogin" placeholder=<fmt:message key="login" />>
            <fmt:message key="disease" />
            <input type="text" name="diseaseName" placeholder=<fmt:message key="disease" /> <fmt:message key="name" />>
            <fmt:message key="treatment" />
            <input type="text" name="treatmentName" placeholder=<fmt:message key="treatment" /> <fmt:message key="name" />>

            <label for="treatmentType"><fmt:message key="treatment_type" /></label>
            <select name="treatmentTypeName" id="treatmentType">
                <option selected value="procedure"><fmt:message key="procedure" /></option>
                <option value="medicine"><fmt:message key="medicine" /></option>
                <option value="operation"><fmt:message key="operation" /></option>
            </select>

            <input class="button" type="submit" value=<fmt:message key="set" />>
        </form>
        </p>
    </div>

    <%--=====================SET DISEASE FORM======================================--%>
    <div class="body_section">
        <p>
        <form method="post" action="${pageContext.request.contextPath}/admin/setDisease">
            <input type="hidden" name="command" value="setDisease"/>
            <fmt:message key="set" /> <fmt:message key="disease" />
            <input type="text" name="diseaseName" placeholder= <fmt:message key="disease" /> <fmt:message key="name" />>
            <fmt:message key="for_patient" />
            <input type="text" name="patientLogin" placeholder=<fmt:message key="login" />>
            <input class="button" type="submit" value=<fmt:message key="set" />>
        </form>
        </p>
    </div>
    <%--=====================DO TREATMENT FORM======================================--%>
    <div class="body_section">
        <p>
        <form method="post" action="${pageContext.request.contextPath}/admin/doTreatment">
            <input type="hidden" name="command" value="doDoctorsTreatment"/>
            <fmt:message key="do" /> <fmt:message key="treatment" />
            <input type="text" name="treatmentId" placeholder=<fmt:message key="item.id" /> <fmt:message key="treatment" /> >
            <input class="button" type="submit" value=<fmt:message key="do" />>
        </form>
        </p>
    </div>
</div>
<%--=========================================================================================--%>
<%--===============================TREATMENTS LIST PART======================================--%>
<%--=========================================================================================--%>
<div class="split_screen">
    <div class="simple_body_section">
        <p>
            <%--=====================TREATMENTS LIST HEADER======================================--%>
        <h3><fmt:message key="treatments.list"/></h3>
        <c:if test="${treatmentsSortParameter != null}">
            <h4><fmt:message key="sort.type"/>: ${treatmentsSortParameter}</h4>
        </c:if>
        <%--=====================TREATMENTS SORT FORM======================================--%>
        <form method="post" action="${pageContext.request.contextPath}/admin/sortTreatments">
            <input type="hidden" name="command" value="getDoctorsTreatments"/>

            <label for="treatmentsSortParameter"><fmt:message key="sort_by" /></label>
            <select name="treatmentsSortParameter" id="treatmentsSortParameter">
                <option selected value="treatment_type_name"><fmt:message key="treatment_type" /></option>
                <option value="patients_login"><fmt:message key="patient" /></option>
                <option value="disease_name"><fmt:message key="disease" /></option>
            </select>
            <input class="button" type="submit" value=<fmt:message key="sort" />>
        </form>
        </p>
        <%--=====================TREATMENTS LIST TABLE======================================--%>
        <p>
        <table class="table" cellspacing="2" border="1" cellpadding="5" width="600">
            <thead>
            <tr>
                <td class="nameColonn"><b><fmt:message key="item.id" /></b></td>
                <td class="nameColonn"><b><fmt:message key="name" /></b></td>
                <td class="nameColonn"><b><fmt:message key="treatment_type" /></b></td>
                <td class="nameColonn"><b><fmt:message key="user_login" /></b></td>
                <td class="nameColonn"><b><fmt:message key="full_name" /></b></td>
                <td class="nameColonn"><b><fmt:message key="for" /> <fmt:message key="disease" /></b></td>
            </tr>
            </thead>
            <c:set var="k" value="0"/>
            <c:forEach var="item" items="${treatments}">
            <c:set var="k" value="${k+1}"/>
            <tr>
                <td>${item.id}</td>
                <td>${item.name}</td>
                <td>${item.treatmentType.name}</td>
                <td>${item.disease.user.email}</td>
                <td>${item.disease.user.fullName}</td>
                <td>${item.disease.name}</td>
                </c:forEach>
        </table>
        </p>
        <%--=====================TREATMENTS LIST PAGES INFO======================================--%>
        <c:if test="${treatmentsCurrentPage != null}">
            <h4><fmt:message key="page"/> ${treatmentsCurrentPage} <fmt:message key="from"/> ${treatmentsNoOfPages}</h4>
        </c:if>
        <%--=====================TREATMENTS LIST previous BUTTON======================================--%>
        <c:if test="${treatmentsCurrentPage != 1 && treatmentsCurrentPage != null}">

            <form method="post" action="${pageContext.request.contextPath}/admin/sortTreatments">
                <input type="hidden" name="command" value="getDoctorsTreatments"/>
                <input type="hidden" name="treatmentsSortParameter" value="${treatmentsPageSortBy}"/>
                <input type="hidden" name="treatmentsCurrentPage" value="${treatmentsCurrentPage-1}"/>
                <input class="button" type="submit" value=<fmt:message key="previous"/>>
            </form>

        </c:if>
        <%--=====================TREATMENTS LIST next BUTTON======================================--%>
        <c:if test="${treatmentsCurrentPage != null && treatmentsCurrentPage < treatmentsNoOfPages}">

            <form method="post" action="${pageContext.request.contextPath}/admin/sortTreatments">
                <input type="hidden" name="command" value="getDoctorsTreatments"/>
                <input type="hidden" name="treatmentsSortParameter" value="${treatmentsPageSortBy}"/>
                <input type="hidden" name="treatmentsCurrentPage" value="${treatmentsCurrentPage+1}"/>
                <input class="button" type="submit" value=<fmt:message key="next"/>>
            </form>

        </c:if>
    </div>
</div>

<%--=========================================================================================--%>
<%--===============================PATIENTS LIST PART======================================--%>
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
        <form method="post" action="${pageContext.request.contextPath}/admin/sortUsers">
            <input type="hidden" name="command" value="getMyPatients"/>

            <label for="patientsSortParameter"><fmt:message key="sort_by" /></label>
            <select name="patientsSortParameter" id="patientsSortParameter">
                <option value="full_name"><fmt:message key="alphabetic" /></option>
                <option value="birth_date"><fmt:message key="birth_date" /></option>
            </select>
            <input class="button" type="submit" value=<fmt:message key="sort" />>
        </form>
        </p>
        <%--=====================PATIENTS LIST TABLE======================================--%>
        <p>
        <table class="table" cellspacing="2" border="1" cellpadding="5" width="600">
            <thead>
            <tr>
                <td class="nameColonn"><b><fmt:message key="email" /></b></td>
                <td class="nameColonn"><b><fmt:message key="full_name" /></b></td>
                <td class="nameColonn"><b><fmt:message key="birth_date" /></b></td>
            </tr>
            </thead>
            <c:set var="k" value="0"/>
            <c:forEach var="item" items="${patients}">
            <c:set var="k" value="${k+1}"/>
            <tr>
                <td>${item.email}</td>
                <td>${item.fullName}</td>
                <td>${item.birthDate}</td>
                </c:forEach>
        </table>
        </p>
        <%--=====================PATIENTS LIST PAGES INFO======================================--%>
        <c:if test="${patientsCurrentPage != null}">
            <h4><fmt:message key="page"/> ${patientsCurrentPage} <fmt:message key="from"/> ${patientsNoOfPages}</h4>
        </c:if>
        <%--=====================PATIENTS LIST previous BUTTON======================================--%>
        <c:if test="${patientsCurrentPage != 1 && patientsCurrentPage != null}">

            <form method="post" action="${pageContext.request.contextPath}/admin/sortTreatments">
                <input type="hidden" name="command" value="getMyPatients"/>
                <input type="hidden" name="patientsSortParameter" value="${patientsPageSortBy}"/>
                <input type="hidden" name="patientsCurrentPage" value="${patientsCurrentPage-1}"/>
                <input class="button" type="submit" value=<fmt:message key="previous"/>>
            </form>

        </c:if>
        <%--=====================PATIENTS LIST next BUTTON======================================--%>
        <c:if test="${patientsCurrentPage != null && patientsCurrentPage < patientsNoOfPages}">

            <form method="post" action="${pageContext.request.contextPath}/admin/sortTreatments">
                <input type="hidden" name="command" value="getMyPatients"/>
                <input type="hidden" name="patientsSortParameter" value="${patientsPageSortBy}"/>
                <input type="hidden" name="patientsCurrentPage" value="${patientsCurrentPage+1}"/>
                <input class="button" type="submit" value=<fmt:message key="next"/>>
            </form>

        </c:if>
    </div>
</div>
<%--=========================================================================================--%>
<%--===============================DISEASES LIST PART======================================--%>
<%--=========================================================================================--%>
<div class="split_screen">
    <div class="simple_body_section">
        <p>
            <%--=====================DISEASES LIST HEADER======================================--%>
        <h3><fmt:message key="diseases.list"/></h3>
        <c:if test="${diseasesSortParameter != null}">
            <h4><fmt:message key="sort.type"/>: ${diseasesSortParameter}</h4>
        </c:if>
        <%--=====================DISEASES SORT FORM======================================--%>
        <form method="post" action="${pageContext.request.contextPath}/admin/sortDiseases">
            <input type="hidden" name="command" value="getMyPatientsDiseases"/>

            <label for="diseasesSortParameter"><fmt:message key="sort_by" /></label>
            <select name="diseasesSortParameter" id="diseasesSortParameter">
                <option value="full_name"><fmt:message key="patient" /></option>
                <option value="name"><fmt:message key="disease" /></option>
            </select>
            <input class="button" type="submit" value=<fmt:message key="sort" />>
        </form>
        </p>
        <%--=====================DISEASES LIST TABLE======================================--%>
        <p>
        <table class="table" cellspacing="2" border="1" cellpadding="5" width="600">
            <thead>
            <tr>
                <td class="nameColonn"><b><fmt:message key="email" /></b></td>
                <td class="nameColonn"><b><fmt:message key="full_name" /></b></td>
                <td class="nameColonn"><b><fmt:message key="disease" /></b></td>
            </tr>
            </thead>
            <c:set var="k" value="0"/>
            <c:forEach var="item" items="${diseases}">
            <c:set var="k" value="${k+1}"/>
            <tr>
                <td>${item.user.email}</td>
                <td>${item.user.fullName}</td>
                <td>${item.name}</td>
                </c:forEach>
        </table>
        </p>
        <%--=====================DISEASES LIST PAGES INFO======================================--%>
        <c:if test="${diseasesCurrentPage != null}">
            <h4><fmt:message key="page" /> ${diseasesCurrentPage} <fmt:message key="from" /> ${diseasesNoOfPages}</h4>
        </c:if>
        <%--=====================DISEASES LIST previous BUTTON======================================--%>
        <c:if test="${diseasesCurrentPage != 1 && diseasesCurrentPage != null}">

            <form method="post" action="${pageContext.request.contextPath}/admin/sortTreatments">
                <input type="hidden" name="command" value="getMyPatientsDiseases"/>
                <input type="hidden" name="diseasesSortParameter" value="${diseasesPageSortBy}"/>
                <input type="hidden" name="diseasesCurrentPage" value="${diseasesCurrentPage-1}"/>
                <input class="button" type="submit" value=<fmt:message key="previous"/>>
            </form>

        </c:if>
        <%--=====================DISEASES LIST next BUTTON======================================--%>
        <c:if test="${diseasesCurrentPage != null && diseasesCurrentPage < diseasesNoOfPages}">

            <form method="post" action="${pageContext.request.contextPath}/admin/sortTreatments">
                <input type="hidden" name="command" value="getMyPatientsDiseases"/>
                <input type="hidden" name="diseasesSortParameter" value="${diseasesPageSortBy}"/>
                <input type="hidden" name="diseasesCurrentPage" value="${diseasesCurrentPage+1}"/>
                <input class="button" type="submit" value=<fmt:message key="next"/>>
            </form>

        </c:if>
    </div>
</div>


</body>
</html>