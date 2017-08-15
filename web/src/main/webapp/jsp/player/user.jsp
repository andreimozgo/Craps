<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
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
    <link rel="stylesheet" href="../../css/user.css">
    <link href="https://fonts.googleapis.com/css?family=PT+Sans" rel="stylesheet">
</head>
<body>
<jsp:include page="/jsp/elements/header.jsp"/>
<section>
    <div class="login-form">
        <h2><fmt:message key="user.profile"/></h2>
        <div>
            ${user.username}, <fmt:message key="user.hello"/>
        </div>
        <div class="avatar">
            <ctg:user-avatar/>
        </div>
        <div>
            <fmt:message key="user.balance"/>: ${user.balance}
        </div>
        <br>
        <div><a href="/craps?command=play" class="menu-link" title="<fmt:message key="play.title"/>"><fmt:message key="menu.play"/></a></div>
        <div><a href="craps?command=stats"><fmt:message key="user.stats"/></a></div>
        <div><a href="craps?command=pay"><fmt:message key="user.payment"/></a></div>
        <div><a href="craps?command=changepwd"><fmt:message key="user.changepwd"/></a></div>
        <br>
        <hr>
        <br>
        <a href="craps?command=logout"><fmt:message key="logout"/></a>
        <br>
        <br>
    </div>
</section>
<jsp:include page="/jsp/elements/footer.jsp"/>
</body>
</html>