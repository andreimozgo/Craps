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
    <link rel="stylesheet" href="../css/user.css">
    <link href="https://fonts.googleapis.com/css?family=PT+Sans" rel="stylesheet">
</head>
<body>
<jsp:include page="/jsp/elements/header.jsp"/>
<section>
    <h1><fmt:message key="page.history.title"/></h1>
    <hr>
    <h2><fmt:message key="page.history1"/></h2>
    <p><fmt:message key="page.history2"/></p>
    <p><fmt:message key="page.history3"/></p>
    <p><fmt:message key="page.history4"/></p>

</section>
<jsp:include page="/jsp/elements/footer.jsp"/>
</body>
</html>