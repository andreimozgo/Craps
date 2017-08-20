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
        <h2><fmt:message key="play.title"/></h2>
        <fmt:message key="play.player"/>: ${user.username}
        <br>
        <fmt:message key="play.balance"/>: ${user.balance}
        <br>
        <hr>
        <form method="POST" action="craps" onsubmit="return isBets()">
            <fieldset>
                <div id="play-message" class="message">${playMessage}</div>
                <div><input type="hidden" name="command" value="play"/></div>
                <div>Pass Line <input type="text" min="1" max="100" step="1" name="passBet"
                                      placeholder="<fmt:message key="play.enter" />"/></div>
                <div>Don't Pass Line<input type="number" min="1" max="100" step="1" name="dontPassBet"
                                           placeholder="<fmt:message key="play.enter" />"/></div>
                <div><input type="submit" value="<fmt:message key="button.roll" />"/></div>
            </fieldset>
        </form>
        <br>
        <c:if test="${user.game != null}">
            <div><span><fmt:message key="play.dice"/>: </span><span>${dice.dice1} </span><span>${dice.dice2}</span>
            </div>
            <table border="1">
                <thead align="center">
                <tr>
                    <th><fmt:message key="play.bettype"/></th>
                    <th><fmt:message key="play.bet"/></th>
                    <th><fmt:message key="play.point"/></th>
                    <th><fmt:message key="play.profit"/></th>
                    <th></th>
                </tr>
                <tbody align="center">
                <c:forEach items="${betList}" var="bet">
                    <tr>
                        <td>
                            <div><c:out value="${bet.betType}"/></div>
                        </td>
                        <td>
                            <div><c:out value="${bet.amount}"/></div>
                        </td>
                        <td>
                            <div><c:out value="${bet.point}"/></div>
                        </td>
                        <td>
                            <div><c:out value="${bet.profit}"/></div>
                        </td>
                        <td>
                            <c:if test="${bet.profit == 0}">
                                <fmt:message key="play.lose"/>
                            </c:if>
                            <c:if test="${bet.profit > 0}">
                                <fmt:message key="play.win"/>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>
        <br>
        <hr>
        <a href="craps?command=logout"><fmt:message key="logout"/></a>
        <br>
        <br>
    </div>
</section>
<jsp:include page="/jsp/elements/footer.jsp"/>
<script>
    function get(name){
        if(name=(new RegExp('[?&]'+encodeURIComponent(name)+'=([^&]*)')).exec(location.search))
            return decodeURIComponent(name[1]);
    }

    function isBets() {
        var result = true;

        var FILL_FIELD = "<fmt:message key="play.make"/>";

        var playMessage = document.getElementById("play-message");

        playMessage.innerHTML = "";

        var pass = document.forms[0]["passBet"].value,
            dontPass = document.forms[0]["dontPassBet"].value,
            bets = "${betList}";

        if(!bets) {
            if (!pass) {
                if (!dontPass) {
                    playMessage.innerHTML = FILL_FIELD;
                    result = false;
                }
            }
        }
        return result;
    }
</script>
</body>
</html>