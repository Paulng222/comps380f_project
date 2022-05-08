<%-- 
    Document   : userPage
    Created on : Mar 29, 2022, 9:16:03 PM
    Author     : emilylau
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Page</title>
    </head>
    <body>
        <a href="<c:url value="/">
                </c:url>"><c:out value="to Index" /></a><br />
        <h1>User Detail</h1>
        <a href="<c:url value="/user/edit/${username}">
                </c:url>"><c:out value="Edit" /></a><br />
        <p>Username: ${username}</p>
        <p>Password: ${password}</p>
        <p>Full name: ${fullname}</p>
        <p>Phone number: ${phone}</p>
        <p>Address: ${address}</p>
        <p>Role: ${role}</p>
        
        <a href="<c:url value="/user">
                </c:url>"><c:out value="To User List" /></a><br />
    </body>
</html>
