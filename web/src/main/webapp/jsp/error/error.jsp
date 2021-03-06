<%@ page isErrorPage="true" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${curLocale}"/>
<fmt:setBundle basename="i18n.jsp"/>

<!DOCTYPE html>
<html lang="${curLocale}">
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="header.title"/></title>
    <link rel="icon" href="../../img/favicon.ico" type="image/x-icon">
    <link rel="stylesheet" href="../../css/normalize.css">
    <link rel="stylesheet" href="../../css/craps.css">
    <link href="https://fonts.googleapis.com/css?family=PT+Sans" rel="stylesheet">
</head>
<body>
<jsp:include page="/jsp/elements/header.jsp"/>
<section>
    <div class="error-page">
        <fmt:message key="error.error"/>
        <br>
        <fmt:message key="error.404"/>
        <br>
        <a href="/craps"><fmt:message key="redirect.main"/></a>
    </div>
    <div>
        <!-- Request from ${pageContext.errorData.requestURI} is failed
         Servlet name or type: ${pageContext.errorData.servletName}
         Status code: ${pageContext.errorData.statusCode}
         Exception: ${pageContext.errorData.throwable} -->
    </div>
</section>
<jsp:include page="/jsp/elements/footer.jsp"/>
</body>
</html>