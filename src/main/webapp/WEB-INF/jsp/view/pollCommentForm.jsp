<%-- 
    Document   : pollCommentForm
    Created on : Mar 22, 2022, 10:44:15 PM
    Author     : emilylau
--%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Comment Page</title>
    </head>
    <body>
        <h1>Comment Page</h1>
        <form:form method="POST" modelAttribute="comment">
            <form:label path="content">Your Comment</form:label><br>
            <form:textarea path="content"/><br>
            <input type="submit" value="Submit"/>
        </form:form>
    </body>
</html>
