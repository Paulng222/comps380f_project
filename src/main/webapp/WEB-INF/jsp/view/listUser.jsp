<!DOCTYPE html>
<html>
    <head>
        <title>Customer Support Online User Management</title></head>
    <body>
        <c:url var="logoutUrl" value="/cslogout"/>
        <form action="${logoutUrl}" method="post">
            <input type="submit" value="Log out" />
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>


        <h2>Users</h2>
        <a href="<c:url value="/user/create" />">Create a User</a><br /><br />
        <c:choose>
            <c:when test="${fn:length(onlineUsers) == 0}">
                <i>There are no users in the system.</i>
            </c:when>
            <c:otherwise>
                <table>
            <tr>
                <th>Username</th>
                <th>Password</th>
                <th>Roles</th>
                <th>Action</th>
            </tr>
                    <c:forEach items="${onlineUsers}" var="user">
                <tr>
                    <td><a href="<c:url value="/user/list/${user.username}" />">
                        <c:out value="${user.username}" /></a></td>
                    <td>${user.password}</td>
                    <td>
                    
                    <security:authorize access="hasRole('LECTURER') or
                                        principal.username=='${user.username}'">
                        [<a href="<c:url value="/user/edit/${user.username}" />">Edit</a>]
                    </security:authorize>
                        </td>
                        <td>
                    <security:authorize access="hasRole('LECTURER')">
                        [<a href="<c:url value="/user/delete/${user.username}" />">Delete</a>]
                    </security:authorize>
                </td>
                 </tr>
                 </table>
                </c:forEach>
        </c:otherwise>
    </c:choose>





    <br /><br /><a href="<c:url value="/course/list" />">Return to list</a>
</body></html>