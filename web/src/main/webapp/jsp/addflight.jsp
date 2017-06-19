<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Страница добавления рейсов</title>
</head>
<body>

<form action="controller" method="post">
    <fieldset>
        <div>
            <label for="flightDate">Date:</label><br> <input
                type="date" name="flightDate" value="" required/>
        </div>

        <div>
            <label for="seats">Seats:</label><br> <input
                type="text" name="seats" value="" required/>
        </div>
        <br>
        <div>
            <label for="cost">Cost:</label><br> <input
                type="text" name="cost" value="" required/>
        </div>
        <div>
            <label for="upCost">Up cost</label> <select name="upCost">
            <option value="0">0</option>
            <option value="1">1</option>
        </select>
        </div>
        <div>
            <input type="hidden" name="command" value="addflight"/> <input
                type="submit" value="add flight"/>
        </div>
    </fieldset>
</form>
<br>
<br>
<form method="post" action="controller">
    <input type="hidden" name="command" value="Logout"/> <input
        type="submit" value="Log out"/>
</form>

</body>
</html>