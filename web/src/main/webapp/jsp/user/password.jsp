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
        <div class="avatar">
            <ctg:user-avatar/>
        </div>
        <div>
            ${user.username}
        </div>
        <form method="POST" action="craps">
            <fieldset>
                <div class="message">${changePwdMessage}</div>
                <div><input type="hidden" name="command" value="changepwd"/></div>
                <div>* <input type="password" name="oldPwd" placeholder="<fmt:message key="changepwd.oldpass"/>" required/></div>
                <div>* <input type="password" name="newPwd1" placeholder="<fmt:message key="changepwd.newpass1"/>" required/></div>
                <div>* <input type="password" name="newPwd2" placeholder="<fmt:message key="changepwd.newpass2"/>" required/></div>
                <fmt:message key="changepwd.limitations"/>
                <div><input type="submit" value="<fmt:message key="user.changepwd"/>"/></div>
            </fieldset>
        </form>
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