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
    <title><fmt:message key="patient.page"/></title>
</head>

<body>
<%--===========================================================--%>
<%--=========================header============================--%>
<%--===========================================================--%>
<div class="header">

    <div class="header_section">
        <div class="header_item">
            <h2><fmt:message key="patient.page"/></h2>
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
            <form method="post" action="${pageContext.request.contextPath}/en/patientPage">
                <input type="hidden" name="command" value="language"/>
                <input type="hidden" name="language" value="en"/>
                <p><input class="button" type="submit" value=<fmt:message key="en"/>></p>
            </form>
            <%--=====================LANGUAGE RU BUTTON======================================--%>
            <form method="post" action="${pageContext.request.contextPath}/ru/patientPage">
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
        <form method="post" action="${pageContext.request.contextPath}/patient/sortTreatments">
            <input type="hidden" name="command" value="getPatientTreatments"/>
            <label for="treatmentsSortParameter"><fmt:message key="sort_by"/></label>
            <select name="treatmentsSortParameter" id="treatmentsSortParameter">
                <option selected value="treatment_type_name"><fmt:message key="treatment_type"/></option>
                <option value="disease_name"><fmt:message key="disease"/></option>
            </select>
            <input class="button" type="submit" value=<fmt:message key="sort"/>>
        </form>
        </p>
        <%--=====================TREATMENTS LIST TABLE======================================--%>
        <p>
        <table class="table" cellspacing="2" border="1" cellpadding="5" width="600">
            <thead>
            <tr>
                <td class="nameColonn"><b><fmt:message key="item.id"/></b></td>
                <td class="nameColonn"><b><fmt:message key="name"/></b></td>
                <td class="nameColonn"><b><fmt:message key="treatment_type"/></b></td>
                <td class="nameColonn"><b><fmt:message key="for"/> <fmt:message key="disease"/></b></td>
            </tr>
            </thead>
            <c:set var="k" value="0"/>
            <c:forEach var="item" items="${treatments}">
            <c:set var="k" value="${k+1}"/>
            <tr>
                <td>${item.id}</td>
                <td>${item.name}</td>
                <td>${item.treatmentType.name}</td>
                <td>${item.disease.name}</td>
                </c:forEach>
        </table>

        <%--=====================TREATMENTS LIST PAGES INFO======================================--%>
        <c:if test="${treatmentsCurrentPage != null}">
            <h4><fmt:message key="page" /> ${treatmentsCurrentPage} <fmt:message key="from" /> ${treatmentsNoOfPages}</h4>
        </c:if>

        <%--=====================TREATMENTS LIST previous BUTTON======================================--%>
        <c:if test="${treatmentsCurrentPage != 1 && treatmentsCurrentPage != null}">

            <form method="post" action="${pageContext.request.contextPath}/patient/sortTreatments">
                <input type="hidden" name="command" value="getPatientTreatments"/>
                <input type="hidden" name="treatmentsSortParameter" value="${treatmentsPageSortBy}"/>
                <input type="hidden" name="page" value="${treatmentsCurrentPage-1}"/>
                <input class="button" type="submit" value=<fmt:message key="previous"/>>
            </form>

        </c:if>
        <%--=====================TREATMENTS LIST next BUTTON======================================--%>
        <c:if test="${treatmentsCurrentPage != null && treatmentsCurrentPage < treatmentsNoOfPages}">

            <form method="post" action="${pageContext.request.contextPath}/patient/sortTreatments">
                <input type="hidden" name="command" value="getPatientTreatments"/>
                <input type="hidden" name="treatmentsSortParameter" value="${treatmentsPageSortBy}"/>
                <input type="hidden" name="page" value="${treatmentsCurrentPage+1}"/>
                <input class="button" type="submit" value=<fmt:message key="next"/>>
            </form>

        </c:if>
        </p>
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
        <form method="post" action="${pageContext.request.contextPath}/patient/sortDiseases">
            <input type="hidden" name="command" value="getMyDiseases"/>

            <label for="diseasesSortParameter"><fmt:message key="sort_by" /></label>
            <select name="diseasesSortParameter" id="diseasesSortParameter">
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
                <td class="nameColonn"><b><fmt:message key="disease" /></b></td>
            </tr>
            </thead>
            <c:set var="k" value="0"/>
            <c:forEach var="item" items="${diseases}">
            <c:set var="k" value="${k+1}"/>
            <tr>
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

            <form method="post" action="${pageContext.request.contextPath}/patient/sortTreatments">
                <input type="hidden" name="command" value="getMyDiseases"/>
                <input type="hidden" name="diseasesSortParameter" value="${diseasesPageSortBy}"/>
                <input type="hidden" name="diseasesCurrentPage" value="${diseasesCurrentPage-1}"/>
                <input class="button" type="submit" value=<fmt:message key="previous"/>>
            </form>

        </c:if>
        <%--=====================DISEASES LIST next BUTTON======================================--%>
        <c:if test="${diseasesCurrentPage != null && diseasesCurrentPage < diseasesNoOfPages}">

            <form method="post" action="${pageContext.request.contextPath}/patient/sortTreatments">
                <input type="hidden" name="command" value="getMyDiseases"/>
                <input type="hidden" name="diseasesSortParameter" value="${diseasesPageSortBy}"/>
                <input type="hidden" name="diseasesCurrentPage" value="${diseasesCurrentPage+1}"/>
                <input class="button" type="submit" value=<fmt:message key="next"/>>
            </form>

        </c:if>
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
        <form method="post" action="${pageContext.request.contextPath}/patient/getMyDoctors">
            <input type="hidden" name="command" value="getMyDoctors"/>

            <label for="doctorsSortParameter"><fmt:message key="sort_by"/></label>
            <select name="doctorsSortParameter" id="doctorsSortParameter">
                <option selected value="full_name"><fmt:message key="alphabetic"/></option>
                <option value="doctor_type"><fmt:message key="doctor_type"/></option>
            </select>
            <input class="button" type="submit" value=<fmt:message key="sort"/>>
        </form>
        </p>
        <%--=====================DOCTORS LIST TABLE======================================--%>
        <p>
        <table class="table" cellspacing="2" border="1" cellpadding="5" width="600">
            <thead>
            <tr>
                <td class="nameColonn"><b><fmt:message key="full_name"/></b></td>
                <td class="nameColonn"><b><fmt:message key="doctor_type"/></b></td>
            </tr>
            </thead>
            <c:set var="k" value="0"/>
            <c:forEach var="item" items="${doctors}">
            <c:set var="k" value="${k+1}"/>
            <tr>
                <td>${item.user.fullName}</td>
                <td>${item.doctorType}</td>
                </c:forEach>
        </table>
        </p>
        <%--=====================DOCTORS LIST PAGES INFO======================================--%>
        <c:if test="${doctorsCurrentPage != null}">
            <h4><fmt:message key="page"/> ${doctorsCurrentPage} <fmt:message key="from"/> ${doctorsCurrentPage}</h4>
        </c:if>
        <%--=====================DOCTORS LIST previous BUTTON======================================--%>
        <c:if test="${doctorsCurrentPage != 1 && doctorsCurrentPage != null}">

            <form method="post" action="${pageContext.request.contextPath}/patient/getDoctors">
                <input type="hidden" name="command" value="getMyDoctors"/>
                <input type="hidden" name="doctorsSortParameter" value="${doctorsPageSortBy}"/>
                <input type="hidden" name="doctorsCurrentPage" value="${doctorsCurrentPage-1}"/>
                <input class="button" type="submit" value=<fmt:message key="previous"/>>
            </form>

        </c:if>
        <%--=====================DOCTORS LIST next BUTTON======================================--%>
        <c:if test="${doctorsCurrentPage != null && doctorsCurrentPage < doctorsNoOfPages}">

            <form method="post" action="${pageContext.request.contextPath}/patient/getDoctors">
                <input type="hidden" name="command" value="getMyDoctors"/>
                <input type="hidden" name="doctorsSortParameter" value="${doctorsPageSortBy}"/>
                <input type="hidden" name="doctorsCurrentPage" value="${doctorsCurrentPage+1}"/>
                <input class="button" type="submit" value=<fmt:message key="next"/>>
            </form>

        </c:if>
    </div>
</div>


</body>
</html>