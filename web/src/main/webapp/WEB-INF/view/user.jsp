<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
<jsp:include page="/WEB-INF/view/elements/header.jsp"/>
<section>
    <h3>Система бронирования билетов LOWCOST</h3>
    <hr/>
    ${user}, hello!
    <hr/>

    <h3>Расписание рейсов</h3>
    <table>

        <form method="post" action="controller">
            <tr>
                <td>Количество строк на страницу:</td>
                <td>
                    <div>
                        <select name="recordsPerPage">
                            <option value="${recordsPerPage}"></option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="5">5</option>
                        </select>
                    </div>
                </td>
                <td>
                    <input type="hidden" name="command" value="clientpage"/>
                    <input type="submit" value="Поиск"/>
                </td>
            </tr>
        </form>
        <form method="post" action="controller">
            <tr>
                <td>
                    <div>
                        <label for="flightDate">Дата:</label><br>
                        <input type="date" name="flightDate"/>
                    </div>
                </td>
                <td>
                    <input type="hidden" name="command" value="clientpage"/>
                    <input type="submit" value="Поиск"/>
                </td>
            </tr>
        </form>
    </table>
    <br>
    <table border="1">
        <thead align="center">
        <tr>
            <th>Номер рейса</th>
            <th>Дата</th>
            <th>Количество мест</th>
            <th>Цена</th>
            <th>Действие</th>
        </tr>
        </thead>
        <tbody align="center">
        <c:forEach items="${flights}" var="flight">
            <tr>
                <td><c:out value="${flight.id}"/></td>
                <td><c:out value="${flight.date}"/></td>
                <td><c:out value="${flight.seats}"/></td>
                <td><c:out value="${flight.cost}"/></td>
                <td>
                    <form method="post" action="controller">
                        <input type="hidden" name="command" value="buyticket"/> <input
                            type="hidden" name="flight_id" value="${flight.id}"> <input
                            type="submit" value="Купить"/>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <c:choose>
        <c:when test="${currentPage != 1}">
            <td><a href="controller?command=clientpage&currentPage=${currentPage - 1}&recordsPerPage=${recordsPerPage}">Предыдущая</a>
            </td>
            <td><a href="controller?command=clientpage&currentPage=1&recordsPerPage=${recordsPerPage}">1</a></td>
        </c:when>
        <c:when test="${currentPage == 1}">
            <td>Предыдущая</td>
            <td>1</td>
        </c:when>
    </c:choose>
    <td>
        <select onchange="location.href=this.value">
            <option>${currentPage}</option>
            <c:forEach begin="2" end="${numberOfPages-1}" var="i">
            <c:choose>
            <c:when test="${currentPage eq i}">
    <td>${i}</td>
    </c:when>
    <c:otherwise>
        <option value="controller?command=clientpage&currentPage=${i}&recordsPerPage=${recordsPerPage}">${i}</option>
    </c:otherwise>
    </c:choose>
    </c:forEach>
    </select>

    </td>
    <c:choose>
        <c:when test="${currentPage lt numberOfPages}">
            <td>
                <a href="controller?command=clientpage&currentPage=${numberOfPages}&recordsPerPage=${recordsPerPage}">${numberOfPages}</a>
            </td>
            <td><a href="controller?command=clientpage&currentPage=${currentPage + 1}&recordsPerPage=${recordsPerPage}">Следующая</a>
            </td>
        </c:when>
        <c:when test="${currentPage == numberOfPages}">
            <td>${numberOfPages}</td>
            <td>Следующая</td>
        </c:when>
    </c:choose>
    <table border="1">
        <thead align="center">
        <tr>
            <th>Номер билета</th>
            <th>Номер рейса</th>
            <th>Багаж (+35)</th>
            <th>Приоритет регистрации (+10)</th>
            <th>Приоритет посадки (+12)</th>
            <th>Цена</th>
            <th>Статус</th>
            <th>Действие</th>
        </tr>
        </thead>
        <tbody align="center">
        <c:forEach items="${tickets}" var="ticket">
            <tr>
                <form method="post" action="controller">
                    <td><c:out value="${ticket.id}"/></td>
                    <td><c:out value="${ticket.flight.id}"/></td>
                    <td>
                        <div>
                            <select name="baggage">
                                <option value="0">Без багажа</option>
                                <option value="1">С багажом</option>
                            </select>
                        </div>
                    </td>
                    <td>
                        <div>
                            <select name="priorityregistration">
                                <option value="0">Нет</option>
                                <option value="1">Да</option>
                            </select>
                        </div>
                    </td>
                    <td>
                        <div>
                            <select name="priorityboarding">
                                <option value="0">Нет</option>
                                <option value="1">Да</option>
                            </select>
                        </div>
                    </td>
                    <td><c:out value="${ticket.cost}"/></td>
                    <c:choose>
                        <c:when test="${ticket.paid == 0}">
                            <td><font color="red">Не оплачен</font></td>
                        </c:when>
                        <c:when test="${ticket.paid == 1}">
                            <td><font color="green">Оплачен</font></td>
                        </c:when>
                    </c:choose>
                    <td><input type="hidden" name="command" value="recalculate"/>
                        <input type="hidden" name="ticket_id" value="${ticket.id}">
                        <input type="hidden" name="baggage" value="1"><input
                                type="submit" value="Пересчитать"/>
                </form>
                <form method="post" action="controller">
                    <input type="hidden" name="command" value="payticket"/> <input
                        type="hidden" name="ticket_id" value="${ticket.id}"> <input
                        type="submit" value="Оплатить"/>
                </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <br>
    <a href="controller?command=logout">Logout</a>
</section>
<jsp:include page="/WEB-INF/view/elements/footer.jsp"/>
</body>
</html>