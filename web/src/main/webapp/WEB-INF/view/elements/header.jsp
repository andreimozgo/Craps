<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="curLocale"
       value="${not empty param.curLocale ? param.curLocale : not empty curLocale ? curLocale : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${curLocale}"/>
<fmt:setBundle basename="i18n.jsp"/>

<header>
    <div class="title"><h1><fmt:message key="header.title"/></h1></div>
    <nav class="menu">
        <a href="/" class="menu-link">CRAPS HOME</a>
        <a href="/play/" class="menu-link"> PLAY</a>
        <a href="/rules/" class="menu-link">RULES</a>
        <a href="/history/" class="menu-link">HISTORY</a>
        <form>
            <select id="locale" name="curLocale" onchange="submit()">
                <option value="en" ${curLocale == 'en' ? 'selected' : ''}>English</option>
                <option value="ru" ${curLocale == 'ru' ? 'selected' : ''}>Русский</option>
            </select>
        </form>
    </nav>
</header>
