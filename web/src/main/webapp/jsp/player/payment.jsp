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
            ${user.username}, <fmt:message key="user.hello"/>
        </div>
        <div class="avatar">
            <ctg:user-avatar/>
        </div>
        <div>
            <fmt:message key="user.balance"/>: ${user.balance}
        </div>
        <br>
        <form method="POST" action="craps">
            <fieldset>
                <div><input type="hidden" name="command" value="pay"/></div>
                <div class="form-group">
                    <label class="col-sm-3 control-label" for="card-holder-name"><fmt:message key="card.name"/></label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control" name="card-holder-name" id="card-holder-name"
                               placeholder=
                               <fmt:message key="card.name"/> required>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label" for="card-number"><fmt:message key="card.number"/></label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control" name="card-number" id="card-number" placeholder=
                        <fmt:message key="card.number"/> required>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label" for="expiry-month"><fmt:message
                            key="card.expiration"/></label>
                    <div class="col-sm-9">
                        <div class="row">
                            <div class="col-xs-3">
                                <select class="form-control col-sm-2" name="expiry-month" id="expiry-month">
                                    <option><fmt:message key="card.month"/></option>
                                    <option value="01">Jan (01)</option>
                                    <option value="02">Feb (02)</option>
                                    <option value="03">Mar (03)</option>
                                    <option value="04">Apr (04)</option>
                                    <option value="05">May (05)</option>
                                    <option value="06">June (06)</option>
                                    <option value="07">July (07)</option>
                                    <option value="08">Aug (08)</option>
                                    <option value="09">Sep (09)</option>
                                    <option value="10">Oct (10)</option>
                                    <option value="11">Nov (11)</option>
                                    <option value="12">Dec (12)</option>
                                </select>
                            </div>
                            <div class="col-xs-3">
                                <select class="form-control" name="expiry-year">
                                    <option><fmt:message key="card.year"/></option>
                                    <option value="17">2017</option>
                                    <option value="18">2018</option>
                                    <option value="19">2019</option>
                                    <option value="20">2020</option>
                                    <option value="21">2021</option>
                                    <option value="22">2022</option>
                                    <option value="23">2023</option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label" for="cvv">CVV</label>
                    <div class="col-sm-3">
                        <input type="text" class="form-control" name="cvv" id="cvv" placeholder="CVV" required>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label" for="amount"><fmt:message key="card.money"/></label>
                    <div class="col-sm-3">
                        <input type="number" min="10" max="10000" value="10" step="10" class="form-control" name="amount"
                               id="amount" placeholder=<fmt:message key="card.money"/> required>
                    </div>
                </div>
                <br>
                <div>
                    <div>
                        <button type="submit"><fmt:message
                                key="user.button.payment"/></button>
                        <button type="reset"><fmt:message
                                key="user.button.reset"/></button>
                    </div>
                </div>
            </fieldset>
        </form>
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