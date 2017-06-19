<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${curLocale}"/>
<fmt:setBundle basename="i18n.jsp"/>

<header>
    <div class="title"><h1><fmt:message key="header.title"/></h1></div>
    <nav class="menu">
        <a href="/" class="menu-link">CRAPS HOME</a>
        <a href="/play/" class="menu-link"> PLAY</a>
        <a href="/rules/" class="menu-link">RULES</a>
        <a href="/history/" class="menu-link">HISTORY</a>
        <a href="?curLocale=ru" class="menu-locale">Ru</a>
        <a href="?curLocale=en" class="menu-locale">En</a>
    </nav>
</header>