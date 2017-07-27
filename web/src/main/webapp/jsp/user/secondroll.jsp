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
    <link rel="stylesheet" href="../../css/user.css">
    <link href="https://fonts.googleapis.com/css?family=PT+Sans" rel="stylesheet">
</head>
<body>
<jsp:include page="/jsp/elements/header.jsp"/>
<section>
    <h1><fmt:message key="page.play.title"/></h1>
    <br>
    <fmt:message key="page.play.player"/>: ${user.username}
    <br>
    <fmt:message key="page.play.balance"/>: ${user.balance}
    <br>
    <form method="POST" action="craps">
        <fieldset>
            <div><input type="hidden" name="command" value="play"/></div>
            <div>Come <input type="number" name="comeBet" placeholder="Enter your bet"/></div>
            <div>Don't Come<input type="number" name="dontComeBet" placeholder="Enter your bet"/></div>
            <div><input type="submit" value="<fmt:message key="button.roll" />"/></div>
        </fieldset>
    </form>
    <div><span>Dice: </span><span>${dice.dice1} </span><span>${dice.dice2}</span></div>
    <table border="1">
        <thead align="center">
        <tr>
            <th>Bet type</th>
            <th>Bet</th>
            <th>Point</th>
            <th>Profit</th>
            <th>Result</th>
        </tr>
        <tbody align="center">
        <c:forEach items="${user.game.bets}" var="bet">
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
                        You lose!
                    </c:if>
                    <c:if test="${bet.profit > 0}">
                        You win!
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <br>
    <a href="craps?command=logout"><fmt:message key="logout"/></a>
</section>
<jsp:include page="/jsp/elements/footer.jsp"/>
</body>
</html>