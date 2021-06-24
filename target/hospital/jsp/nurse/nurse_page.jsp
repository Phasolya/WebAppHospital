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
    <title><fmt:message key="nurse_page"/></title>
</head>

<body>
<%--===========================================================--%>
<%--=========================header============================--%>
<%--===========================================================--%>
<div class="header">

    <div class="header_section">
        <div class="header_item">
            <h2><fmt:message key="nurse_page"/></h2>
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
            <form method="post" action="${pageContext.request.contextPath}/en/nursePage">
                <input type="hidden" name="command" value="language"/>
                <input type="hidden" name="language" value="en"/>
                <p><input class="button" type="submit" value=<fmt:message key="en"/>></p>
            </form>
            <%--=====================LANGUAGE RU BUTTON======================================--%>
            <form method="post" action="${pageContext.request.contextPath}/ru/nursePage">
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
    <%--=====================DO TREATMENT FORM======================================--%>
    <div class="body_section">
        <p>
        <form method="post" action="${pageContext.request.contextPath}/admin/doTreatment">
            <input type="hidden" name="command" value="doNurseTreatment"/>
            <fmt:message key="do"/> <fmt:message key="treatment"/>
            <input type="text" name="treatmentId" placeholder=
            <fmt:message key="item.id"/>
            <fmt:message key="treatment"/>>
            <input class="button" type="submit" value=<fmt:message key="do"/>>
        </form>
        </p>
    </div>
</div>
<%--=====================TREATMENTS LIST PART======================================--%>
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
            <input type="hidden" name="command" value="getNurseTreatments"/>
            <label for="treatmentsSortParameter"><fmt:message key="sort_by"/></label>
            <select name="treatmentsSortParameter" id="treatmentsSortParameter">
                <option selected value="treatment_type_name"><fmt:message key="treatment_type"/></option>
                <option value="patients_login"><fmt:message key="patient"/></option>
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
                <td class="nameColonn"><b><fmt:message key="user_login"/></b></td>
                <td class="nameColonn"><b><fmt:message key="full_name"/></b></td>
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
                <td>${item.disease.user.email}</td>
                <td>${item.disease.user.fullName}</td>
                <td>${item.disease.name}</td>
                </c:forEach>
        </table>

        <%--=====================TREATMENTS LIST PAGES INFO======================================--%>
        <c:if test="${currentPage != null}">
            <h4><fmt:message key="page" /> ${currentPage} <fmt:message key="from" /> ${noOfPages}</h4>
        </c:if>

        <%--=====================TREATMENTS LIST previous BUTTON======================================--%>
        <c:if test="${currentPage != 1 && currentPage != null}">

            <form method="post" action="${pageContext.request.contextPath}/admin/sortTreatments">
                <input type="hidden" name="command" value="getNurseTreatments"/>
                <input type="hidden" name="treatmentsSortParameter" value="${pageSortBy}"/>
                <input type="hidden" name="page" value="${currentPage-1}"/>
                <input class="button" type="submit" value=<fmt:message key="previous"/>>
            </form>

        </c:if>
        <%--=====================TREATMENTS LIST next BUTTON======================================--%>
        <c:if test="${currentPage != null && currentPage < noOfPages}">

            <form method="post" action="${pageContext.request.contextPath}/admin/sortTreatments">
                <input type="hidden" name="command" value="getNurseTreatments"/>
                <input type="hidden" name="treatmentsSortParameter" value="${pageSortBy}"/>
                <input type="hidden" name="page" value="${currentPage+1}"/>
                <input class="button" type="submit" value=<fmt:message key="next"/>>
            </form>

        </c:if>
        </p>
    </div>
</div>

</body>
</html>