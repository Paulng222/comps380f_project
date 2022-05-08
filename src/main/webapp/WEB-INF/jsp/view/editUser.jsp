<!DOCTYPE html>
<html>
    <head>
        <title>Edit User</title>
    </head>
    <body>
        <c:url var="logoutUrl" value="/cslogout"/>
        <form action="${logoutUrl}" method="post">
            <input type="submit" value="Log out" />
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
${user.getAddress()}
        <h2>User ${user.username}</h2>
        <form:form method="POST" modelAttribute="onlineUser">
            <form:label path="username">Username</form:label><br/>
            <form:input type="text" path="username" value="${user.getUsername()}" /><br/><br/>
            <form:label path="password">Password</form:label><br/>
            <form:input type="text" path="password" value="${user.getPassword()}" /><br/><br/>
            <form:label path="fullname">Full Name</form:label><br/>
            <form:input type="text" path="fullname" value="${user.getFullname()}" /><br/><br/>
            <form:label path="address">Address</form:label><br/>
            <form:input type="text" path="address" value="${user.getAddress()}" /><br/><br/>
            <form:label path="phone">Phone</form:label><br/>
            <form:input type="text" path="phone" value="${user.getPhone()}" /><br/><br/>
            <form:label path="roles">Roles</form:label><br/>
            <form:checkbox path="roles" value="ROLE_STUDENT" checked="${studentRole eq true ? 'checked' : ''}"/>ROLE_STUDENT
            <form:checkbox path="roles" value="ROLE_LECTURER" checked="${lecturerRole eq true ? 'checked' : ''}"/>ROLE_LECTURER
            <br /><br />
            <input type="submit" value="Save"/>
        </form:form>
    </body>
</html>