<!DOCTYPE html>
<html>
    <head><title>Customer Support</title></head>
    <body>
        <c:url var="logoutUrl" value="/cslogout"/>
        <form action="${logoutUrl}" method="post">
            <input type="submit" value="Log out" />
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>

        <h2>Create a User</h2>
        <form:form method="POST" enctype="multipart/form-data" modelAttribute="onlineUser">
            <form:label path="username">Username</form:label><br/>
            <form:input type="text" path="username" /><br/><br/>
            <form:label path="password">Password</form:label><br/>
            <form:input type="text" path="password" /><br/><br/>
            <form:label path="roles">Roles</form:label><br/>
            <form:checkbox path="roles" value="ROLE_STUDENT" />ROLE_STUDENT
            <form:checkbox path="roles" value="ROLE_LECTURER" />ROLE_LECTURER<br /><br />
            <form:label path="fullname">Full name</form:label><br/>
            <form:input type="text" path="fullname" /><br/><br/>
            <form:label path="phone">Phone number</form:label><br/>
            <form:input type="text" path="phone" /><br/><br/>
            <form:label path="address">Address</form:label><br/>
            <form:input type="text" path="address" /><br/><br/>
            <br /><br />
            <input type="submit" value="Add User"/>
        </form:form>

        <a href="<c:url value="/course" />">Return to index</a>

    </body>
</html>