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
    <link rel="stylesheet" href="../../css/player.css">
    <link href="https://fonts.googleapis.com/css?family=PT+Sans" rel="stylesheet">
</head>
<body>
<jsp:include page="/jsp/elements/header.jsp"/>
<section>
    <div class="login-form">
        <h2><fmt:message key="user.stats"/></h2>
        <div>
            ${user.username}
        </div>
        <div class="avatar">
            <ctg:user-avatar/>
        </div>
        <br>
        <div>
            <div><fmt:message key="user.balance"/>: ${user.balance}</div>
            <div><fmt:message key="stats.games.played"/>: ${gamesNumber}</div>
            <div><fmt:message key="stats.bets.made"/>: ${betsNumber}</div>
            <div><fmt:message key="stats.bets.won"/>: ${wonBetsNumber}</div>
        </div>
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