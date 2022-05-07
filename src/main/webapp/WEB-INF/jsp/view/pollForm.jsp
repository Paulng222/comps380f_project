<%-- 
    Document   : pollForm
    Created on : Mar 22, 2022, 5:09:05 PM
    Author     : emilylau
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Poll Form</title>
    </head>
    <body>
        <c:url var="logoutUrl" value="/cslogout"/>
        <form action="${logoutUrl}" method="post">
            <input type="submit" value="Log out" />
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>

        <p>${param.action}</p>
        <fieldset>
            <legend>
                Vote options
            </legend>
            <form:form method="POST" modelAttribute="poll">

                <form:label path="question">Question: </form:label>
                <form:input path="question"/><br/>

                <form:label path="option1">option1: </form:label>
                <form:input path="option1"/><br/>

                <form:label path="option2">option2: </form:label>
                <form:input path="option2"/><br/>

                <form:label path="option3">option3: </form:label>
                <form:input path="option3"/><br/>

                <form:label path="option4">option4: </form:label>
                <form:input path="option4"/><br/>

                <input type="submit" value="Submit"/>

            </form:form>
        </fieldset>

    </body>
</html>
