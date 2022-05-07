<!DOCTYPE html>
<html>
    <head>
        <title>Customer Support Login</title>
    </head>
    <body>
        <c:if test="${param.error != null}">
            <p>Login failed.</p>
        </c:if>
        <c:if test="${param.logout != null}">
            <p>You have logged out.</p>
        </c:if>
        <h2>Customer Support Login</h2>
        <form action="cslogin" method="POST">
            <label for="username">Username:</label><br/>
            <input type="text" id="username" name="username" /><br/><br/>
            <label for="password">Password:</label><br/>
            <input type="password" id="password" name="password" /><br/><br/>
            <input type="checkbox" id="remember-me" name="remember-me" />
            <label for="remember-me">Remember me</label><br/><br/>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <input type="submit" value="Log In"/>
        </form>

        <h2>Create a User</h2>
        <form action="addUser" method="POST">
            <label path="username">Username</label><br/>
            <input type="text" path="username" /><br/><br/>
            <label path="password">Password<//label><br/>
            <input type="text" path="password" /><br/><br/>
            <label path="roles">Roles</label><br/>
            <checkbox path="roles" value="ROLE_STUDENT" />ROLE_STUDENT
           
            <br /><br />
            <input type="submit" value="Add User"/>
        </form>

    </body>
</html>