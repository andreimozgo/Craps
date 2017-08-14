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
            ${user.username}
        </div>
        <div class="avatar">
            <ctg:user-avatar/>
        </div>
        <br>
        <form method="POST" action="craps" onsubmit="return validateForm()">
            <fieldset>
                <div class="message" id="message-logic">${changePwdMessage}</div>
                <div><input type="hidden" name="command" value="changepwd"/></div>
                <div>* <input type="password" name="oldPwd" placeholder="<fmt:message key="changepwd.oldpass"/>"/></div>
                <div class="err" id="err-old-pwd"></div>
                <div>* <input type="password" name="newPwd1" placeholder="<fmt:message key="changepwd.newpass1"/>"/>
                </div>
                <div class="err" id="err-pwd1"></div>
                <div>* <input type="password" name="newPwd2" placeholder="<fmt:message key="changepwd.newpass2"/>"/>
                </div>
                <div class="err" id="err-pwd2"></div>
                <br>
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
<script>
    function validateFor() {
        var result = true;

        var FILL_FIELD = "<fmt:message key="registration.error.fill"/>",
            PWD_WRONG = "<fmt:message key="changepwd.wrong"/>",
            PWD_NOT_EQUAL = "<fmt:message key="registration.error.repeatpwd"/>",
            BAD_PASS_LENGTH = "<fmt:message key="registration.error.pwdlength"/>",
            BAD_PASS_UPPER = "<fmt:message key="registration.error.pwdupper"/>",
            BAD_PASS_LOWER = "<fmt:message key="registration.error.pwdlower"/>",
            BAD_PASS_NUMBER = "<fmt:message key="registration.error.pwdnumeral"/>",
            BAD_PASS_EQUALS = "<fmt:message key="changepwd.equals"/>";

        var errOldPwd = document.getElementById("err-old-pwd"),
            errPwd1 = document.getElementById("err-pwd1"),
            errPwd2 = document.getElementById("err-pwd2"),
            messageLogic = document.getElementById("message-logic");

        errOldPwd.innerHTML = "";
        errPwd1.innerHTML = "";
        errPwd2.innerHTML = "";
        messageLogic.innerHTML = "";

        var oldPwd = document.forms[0]["oldPwd"].value,
            pwd1 = document.forms[0]["newPwd1"].value,
            pwd2 = document.forms[0]["newPwd2"].value;

        if (!oldPwd) {
            errOldPwd.innerHTML = FILL_FIELD;
            result = false;
        }

        if (!pwd1) {
            errPwd1.innerHTML = FILL_FIELD;
            result = false;
        } else if (pwd1.length < 6) {
            errPwd1.innerHTML = BAD_PASS_LENGTH;
            result = false;
        }
        else if (pwd1.search(/[A-ZА-ЯЁ]+/) < 0) {
            errPwd1.innerHTML = BAD_PASS_UPPER;
            result = false;
        }
        else if (pwd1.search(/[a-zа-яё]+/) < 0) {
            errPwd1.innerHTML = BAD_PASS_LOWER;
            result = false;
        }
        else if (pwd1.search(/\d+/) < 0) {
            errPwd1.innerHTML = BAD_PASS_NUMBER;
            result = false;
        } else if (oldPwd && pwd2 && pwd1 === pwd2) {
            if (oldPwd.length < 6) {
                messageLogic.innerHTML = PWD_WRONG;
                result = false;
            }
            else if (oldPwd.search(/[A-ZА-ЯЁ]+/) < 0) {
                messageLogic.innerHTML = PWD_WRONG;
                result = false;
            }
            else if (oldPwd.search(/[a-zа-яё]+/) < 0) {
                messageLogic.innerHTML = PWD_WRONG;
                result = false;
            }
            else if (oldPwd.search(/\d+/) < 0) {
                messageLogic.innerHTML = PWD_WRONG;
                result = false;
            }
            else if (oldPwd === pwd1){
                messageLogic.innerHTML = BAD_PASS_EQUALS;
                result = false;
            }
        }

        if (!pwd2) {
            errPwd2.innerHTML = FILL_FIELD;
            result = false;
        }
        if (pwd1 && pwd2 && pwd1 !== pwd2) {
            errPwd2.innerHTML = PWD_NOT_EQUAL;
            document.forms[0]["newPwd1"].value = "";
            document.forms[0]["newPwd2"].value = "";
            result = false;
        }

        return result;
    }
</script>
</body>
</html>