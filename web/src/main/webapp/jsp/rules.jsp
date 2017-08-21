<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${curLocale}"/>
<fmt:setBundle basename="i18n.jsp"/>

<!DOCTYPE html>
<html lang="${curLocale}">
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="header.title"/></title>
    <link rel="icon" href="../img/favicon.ico" type="image/x-icon">
    <link rel="stylesheet" href="../css/normalize.css">
    <link rel="stylesheet" href="../css/craps.css">
    <link rel="stylesheet" href="../../css/player.css">
    <link rel="stylesheet" href="../../css/admin.css">
    <link href="https://fonts.googleapis.com/css?family=PT+Sans" rel="stylesheet">
</head>
<body>
<jsp:include page="/jsp/elements/header.jsp"/>
<section>
    <div class="login-form">
        <h2><fmt:message key="page.rules.title"/></h2>
        <hr>
        <h3><fmt:message key="page.rules1"/></h3>
        <p><fmt:message key="page.rules2"/></p>
        <p><fmt:message key="page.rules3"/></p>
        <p><fmt:message key="page.rules4"/></p>
        <p><fmt:message key="page.rules5"/></p>
        <p><fmt:message key="page.rules6"/></p>
        <p><fmt:message key="page.rules7"/></p>
        <p><fmt:message key="page.rules8"/></p>
        <p><fmt:message key="page.rules9"/></p>
    </div>
</section>
<jsp:include page="/jsp/elements/footer.jsp"/>
</body>
</html>