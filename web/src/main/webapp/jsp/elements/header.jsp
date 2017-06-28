<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${curLocale}"/>
<fmt:setBundle basename="i18n.jsp"/>

<header>
    <div class="title"><h1><fmt:message key="header.title"/></h1></div>
    <nav class="menu">
        <a href="/craps" class="menu-link"><fmt:message key="menu.main"/></a>
        <a href="/craps?command=play" class="menu-link"> PLAY</a>
        <a href="/jsp/rules.jsp" class="menu-link"><fmt:message key="menu.rules"/></a>
        <a href="/jsp/history.jsp" class="menu-link"><fmt:message key="menu.history"/></a>
        <div>
            <a href="?curLocale=ru" class="menu-locale">Рус</a>
            <a href="?curLocale=en" class="menu-locale">En</a>
        </div>
    </nav>
</header>