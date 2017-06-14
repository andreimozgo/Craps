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
    <title>Play Craps Online</title>
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
        <h2><fmt:message key="login.registration"/></h2>
        <form id="user" onsubmit="return validateForm()" method="POST" action="controller">
            <fieldset id="fieldset">
                <div class="message">${registrationResultMessage}</div>
                <div><input form="user" type="email" name="email" placeholder="<fmt:message key="login.email"/>"
                            value="${email}"/>
                    <div class="err" id="err-email"></div>
                </div>
                <div><input form="user" type="password" name="pwd1" placeholder="<fmt:message key="login.password"/>"/>
                    <div class="err" id="err-pwd1"></div>
                </div>
                <div><input form="null" type="password" name="pwd2" placeholder="<fmt:message key="login.confirm"/>"/>
                    <div class="err" id="err-pwd2"></div>
                </div>
                <div><input form="user" type="text" name="username" placeholder="<fmt:message key="login.username"/>"
                            value="${username}"/>
                    <div class="err" id="err-uname"></div>
                </div>
                <div><input form="null" type="number" name="age" placeholder="<fmt:message key="login.age"/>"/>
                    <div class="err" id="err-age"></div>
                </div>
                <div><input type="submit" value="<fmt:message key="login.submit"/>"/></div>
                <input type="hidden" name="command" value="addregistration"/>
            </fieldset>
            <a href="/"><fmt:message key="login.login"/></a>
        </form>
        <form id="null"></form>
    </div>
</section>
<script>
    function validateForm() {
        var result = true;

        var FILL_FIELD = "<fmt:message key="registration.error.fill"/>",
            PWD_NOT_EQUAL = "<fmt:message key="registration.error.repeatpwd"/>",
            BAD_NAME = "<fmt:message key="registration.error.badname"/>",
            BAD_PASS_LENGTH = "<fmt:message key="registration.error.pwdlength"/>",
            BAD_PASS_UPPER = "<fmt:message key="registration.error.pwdupper"/>",
            BAD_PASS_LOWER = "<fmt:message key="registration.error.pwdlower"/>",
            BAD_PASS_NUMBER = "<fmt:message key="registration.error.pwdnumeral"/>",
            BAD_AGE = "<fmt:message key="registration.error.age"/>",
            BAD_EMAIL_AT = "<fmt:message key="registration.error.emailat"/>",
            BAD_EMAIL_DOT = "<fmt:message key="registration.error.emaildot"/>";

        var errUname = document.getElementById("err-uname"),
            errPwd1 = document.getElementById("err-pwd1"),
            errPwd2 = document.getElementById("err-pwd2"),
            errAge = document.getElementById("err-age"),
            errEmail = document.getElementById("err-email");

        errUname.innerHTML = "";
        errPwd1.innerHTML = "";
        errPwd2.innerHTML = "";
        errAge.innerHTML = "";
        errEmail.innerHTML = "";

        var usr = document.forms[0]["username"].value,
            pwd1 = document.forms[0]["pwd1"].value,
            pwd2 = document.forms[1]["pwd2"].value,
            age = document.forms[1]["age"].value,
            email = document.forms[0]["email"].value;

        if (!usr) {
            errUname.innerHTML = FILL_FIELD;
            result = false;
        }
        if (usr && usr.search(/^\w{4,}$/i) !== 0) {
            errUname.innerHTML = BAD_NAME;
            result = false;
        }

        if (!pwd1) {
            errPwd1.innerHTML = FILL_FIELD;
            result = false;
        } else if (pwd1.length < 6) {
            errPwd1.innerHTML = BAD_PASS_LENGTH;
            result = false;
        }
        else if (pwd1.search(/[A-Z]+/) < 0) {
            errPwd1.innerHTML = BAD_PASS_UPPER;
            result = false;
        }
        else if (pwd1.search(/[a-z]+/) < 0) {
            errPwd1.innerHTML = BAD_PASS_LOWER;
            result = false;
        }
        else if (pwd1.search(/\d+/) < 0) {
            errPwd1.innerHTML = BAD_PASS_NUMBER;
            result = false;
        }

        if (!pwd2) {
            errPwd2.innerHTML = FILL_FIELD;
            result = false;
        }
        if (pwd1 && pwd2 && pwd1 !== pwd2) {
            errPwd2.innerHTML = PWD_NOT_EQUAL;
            document.forms[0]["pwd1"].value = "";
            document.forms[0]["pwd2"].value = "";
            result = false;
        }

        if (!age) {
            errAge.innerHTML = FILL_FIELD;
            result = false;
        }
        else if (age && (age < 18 || age > 120)) {
            errAge.innerHTML = BAD_AGE;
            result = false;
        }

        if (!email) {
            errEmail.innerHTML = FILL_FIELD;
            result = false;
        }
        else if (email.search(/@/) < 0) {
            errEmail.innerHTML = BAD_EMAIL_AT;
            result = false;
        }
        else if (email.search(/\./) < 0) {
            errEmail.innerHTML = BAD_EMAIL_DOT;
            result = false;
        }

        return result;
    }
</script>
</body>
</html>