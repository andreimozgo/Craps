<%@ page isErrorPage="true" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${curLocale}"/>
<fmt:setBundle basename="i18n.jsp"/>

<!DOCTYPE html>
<html lang="${curLocale}">
<head>
    <meta charset="UTF-8" http-equiv="refresh" content="4;/craps">
    <title><fmt:message key="header.title"/></title>
    <link rel="icon" href="../../img/favicon.ico" type="image/x-icon">
    <link rel="stylesheet" href="../../css/normalize.css">
    <link rel="stylesheet" href="../../css/craps.css">
    <link rel="stylesheet" href="../../css/user.css">
    <link href="https://fonts.googleapis.com/css?family=PT+Sans" rel="stylesheet">
</head>
<body>
<jsp:include page="/jsp/elements/header.jsp"/>
<section>
    <div class="error-page">
        <fmt:message key="error.error"/>
        <br/> Request from ${pageContext.errorData.requestURI} is failed
        <br/> Servlet name or type: ${pageContext.errorData.servletName}
        <br/> Status code: ${pageContext.errorData.statusCode}
        <br/> Exception: ${pageContext.errorData.throwable}
        <br>
        <fmt:message key="redirect.main"/>
    </div>
</section>
<jsp:include page="/jsp/elements/footer.jsp"/>
</body>
</html>