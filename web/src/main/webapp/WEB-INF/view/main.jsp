<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    <link href="https://fonts.googleapis.com/css?family=PT+Sans" rel="stylesheet">
</head>
<body>
<jsp:include page="/WEB-INF/view/elements/header.jsp"/>
<section>
    <h1><fmt:message key="admin.panel"/></h1>
    <hr>
    <div> ${username}, hello!</div>
    <hr>
    <h3><fmt:message key="admin.users"/></h3>
    <table border="1">
        <thead align="center">
        <tr>
            <th>Id</th>
            <th><fmt:message key="admin.users.name"/></th>
            <th><fmt:message key="admin.users.registration"/></th>
            <th>Email</th>
            <th><fmt:message key="admin.users.balance"/></th>
            <th><fmt:message key="admin.users.role"/></th>
        </tr>
        <tbody align="center">
        <c:forEach items="${users}" var="user">
            <tr>
                <td>
                    <div><c:out value="${user.id}"/></div>
                </td>
                <td>
                    <div><c:out value="${user.username}"/></div>
                </td>
                <td>
                    <div><c:out value="${user.createTime}"/></div>
                </td>
                <td>
                    <div><c:out value="${user.email}"/></div>
                </td>
                <td>
                    <div><c:out value="${user.money}"/></div>
                </td>
                <td>
                    <div><c:out value="${user.userRole}"/></div>
                </td>
                <td>
                    <select name="newRoleId">
                        <option value="1"><s:message code="admin"/></option>
                        <option value="2"><s:message code="new"/></option>
                        <option value="1"><s:message code="user"/></option>
                        <option value="2"><s:message code="banned"/></option>
                    </select>
                </td>
                <td>
                    <div>
                        <form method="post" action="controller" style="float:left">
                            <input type="hidden" name="command" value="deleteuser"/> <input
                                type="hidden" name="user_id" value="${user.id}">
                            <input type="submit" value="<fmt:message key="button.delete"/>"/>
                        </form>
                    </div>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <br>
    <a href="controller?command=logout"><fmt:message key="logout"/></a>
</section>
>
<jsp:include page="/WEB-INF/view/elements/footer.jsp"/>
</body>
</html>