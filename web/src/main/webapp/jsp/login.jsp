<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
    <link href="https://fonts.googleapis.com/css?family=PT+Sans" rel="stylesheet">
</head>
<body>
<jsp:include page="/jsp/elements/header.jsp"/>
<section>
    <div class="login-form">
        <h2><fmt:message key="login.authorization"/></h2>
        <form method="POST" action="craps">
            <fieldset>
                <div class="message">${registrationResultMessage}
                    ${errorLoginPassMessage}</div>
                <div><input type="hidden" name="command" value="login"/></div>
                <div><input type="email" name="email" placeholder="<fmt:message key="login.email" />" required/></div>
                <div><input type="password" name="password" placeholder="<fmt:message key="login.password" />"
                            required/></div>
                <div><input type="submit" value="<fmt:message key="login.login" />"/></div>
            </fieldset>
            <a href="craps?command=registration"><fmt:message key="login.registration"/></a>
        </form>
    </div>
</section>
<jsp:include page="/jsp/elements/footer.jsp"/>
</body>
</html>