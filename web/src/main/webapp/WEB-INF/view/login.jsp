<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="curLocale"
       value="${not empty param.curLocale ? param.curLocale : not empty curLocale ? curLocale : pageContext.request.locale}"
       scope="session"/>
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
<header>
    <div class="title"><h1><fmt:message key="header.title"/></h1></div>
    <nav class="menu">
        <a href="/" class="menu-link">CRAPS HOME</a>
        <a href="/play/" class="menu-link"> PLAY</a>
        <a href="/rules/" class="menu-link">RULES</a>
        <a href="/history/" class="menu-link">HISTORY</a>
        <form>
            <select name="curLocale" onchange="submit()">
                <option value="en" ${curLocale == 'en' ? 'selected' : ''}>English</option>
                <option value="ru" ${curLocale == 'ru' ? 'selected' : ''}>Русский</option>
            </select>
        </form>
    </nav>
</header>
<section>
    <div class="login-form">
        <h2><fmt:message key="login.authorization"/></h2>
        <form method="POST" action="controller">
            <fieldset>
                <div class="message">${registrationResultMessage}
                    ${errorLoginPassMessage}</div>
                <div><input type="hidden" name="command" value="login"/></div>
                <div><input type="email" name="email" placeholder="<fmt:message key="login.email" />" required/></div>
                <div><input type="password" name="password" placeholder="<fmt:message key="login.password" />"
                            required/></div>
                <div><input type="submit" value="<fmt:message key="login.login" />"/></div>
            </fieldset>
            <a href="controller?command=registration"><fmt:message key="login.registration"/></a>
        </form>
    </div>
    ${nullPage}
</section>
<jsp:include page="/WEB-INF/view/elements/footer.jsp"/>
</body>
</html>