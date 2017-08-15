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
    <link rel="stylesheet" href="../../css/admin.css">
    <link href="https://fonts.googleapis.com/css?family=PT+Sans" rel="stylesheet">
</head>
<body>
<jsp:include page="/jsp/elements/header.jsp"/>
<section>
    <div class="login-form">
        <h2><fmt:message key="admin.panel"/></h2>
        <hr>
        <h3><fmt:message key="admin.users"/></h3>
        <form method="get" action="craps">
            <fmt:message key="pagination.linesperpage"/>:
            <select name="recordsPerPage">
                <option>${recordsPerPage}</option>
                <option value="5">5</option>
                <option value="10">10</option>
                <option value="15">15</option>
            </select>
            <input type="hidden" name="command" value="adminpage"/>
            <input type="submit" value="<fmt:message key="pagination.search"/>"/>
        </form>
        <hr>
        <table border="1">
            <thead align="center">
            <tr>
                <th>Id</th>
                <th><fmt:message key="admin.users.name"/></th>
                <th><fmt:message key="admin.users.registration"/></th>
                <th>Email</th>
                <th><fmt:message key="admin.users.balance"/></th>
                <th><fmt:message key="admin.users.role"/></th>
                <th><fmt:message key="admin.users.newrole"/></th>
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
                        <div><c:out value="${user.balance}"/></div>
                    </td>
                    <td>
                        <div><c:out value="${user.userRole}"/></div>
                    </td>
                    <form method="post" action="craps">
                        <td>
                            <select name="newRole">
                                <option value="1">admin</option>
                                <option value="2">user</option>
                                <option value="3">blocked</option>
                            </select>
                        </td>
                        <td>
                            <div>
                                <input type="hidden" name="command" value="changerole"/>
                                <input type="hidden" name="user_id" value="${user.id}">
                                <input type="hidden" name="newRole" value="${newRole}">
                                <input type="submit" value="<fmt:message key="button.change"/>"/>
                            </div>
                        </td>
                    </form>
                    <td>
                        <div>
                            <form onclick="return confirm('<fmt:message key="button.delete"/>?');" method="post"
                                  action="craps">
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
        <c:choose>
            <c:when test="${currentPage != 1}">
                <td>
                    <a href="craps?command=adminpage&currentPage=${currentPage - 1}&recordsPerPage=${recordsPerPage}"><fmt:message
                            key="pagination.previous"/></a>
                </td>
                <td><a href="craps?command=adminpage&currentPage=1&recordsPerPage=${recordsPerPage}">1</a></td>
            </c:when>
            <c:when test="${currentPage == 1}">
                <td><fmt:message key="pagination.previous"/></td>
                <td>1</td>
            </c:when>
            <c:otherwise>
                <td></td>
            </c:otherwise>
        </c:choose>
        <td>
            <select onchange="location.href=this.value">
                <option>${currentPage}</option>
                <c:forEach begin="2" end="${numberOfPages-0}" var="i">
                <c:choose>
                <c:when test="${currentPage eq i}">
        <td>${i}</td>
        </c:when>
        <c:otherwise>
            <option value="craps?command=adminpage&currentPage=${i}&recordsPerPage=${recordsPerPage}">${i}</option>
        </c:otherwise>
        </c:choose>
        </c:forEach>
        </select>
        </td>
        <c:choose>
            <c:when test="${currentPage lt numberOfPages}">
                <td>
                    <a href="craps?command=adminpage&currentPage=${numberOfPages}&recordsPerPage=${recordsPerPage}">${numberOfPages}</a>
                </td>
                <td>
                    <a href="craps?command=adminpage&currentPage=${currentPage + 1}&recordsPerPage=${recordsPerPage}"><fmt:message
                            key="pagination.next"/></a>
                </td>
            </c:when>
            <c:when test="${currentPage == numberOfPages}">
                <td>${numberOfPages}</td>
                <td><fmt:message key="pagination.next"/></td>
            </c:when>
            <c:otherwise>
                <td></td>
            </c:otherwise>
        </c:choose>
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