<!DOCTYPE html>
<html>
    <head>
        <title>Online Course</title>
    </head>
    <body>

        <c:url var="logoutUrl" value="/cslogout"/>
        <form action="${logoutUrl}" method="post">
            <input type="submit" value="Log out" />
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>

        <c:url var="addUser" value="/user/create"/>
        <form action="${addUser}" method="get">
            <input type="submit" value="Reg User" />
        </form>

        <i><h1>Comps380f Course</h1></i>
        <security:authorize access="hasRole('LECTURER')">
            <a href="<c:url value="/course/poll/create" />">Create a Poll</a><br /><br />
        </security:authorize>
            
        <security:authorize access="hasRole('LECTURER')">
            <h2>Users</h2>
            <a href="<c:url value="/user" />">Manage User Accounts</a><br /><br />
        </security:authorize>


        <h2>Lectures</h2>
        <security:authorize access="hasAnyRole('LECTURER','STUDENT')">
            <a href="<c:url value="/user/comment/list" />">Comment History</a><br /><br />
        </security:authorize>

        <c:choose>
            <c:when test="${fn:length(lectureDatabase) == 0}">
                <i>There are no lecture for the course.</i>
            </c:when>
            <c:otherwise>
                <c:forEach items="${lectureDatabase}" var="lecture">
                    Lecture ${lecture.id}:
                    <a href="<c:url value="/course/viewLecture/lectureId=${lecture.id}" />">
                        <c:out value="${lecture.subject}" /></a>
                    (Lecturer: <c:out value="${lecture.userName}" />)
                    &nbsp;
                    <security:authorize access="hasRole('LECTURER')">
                        [<a href="<c:url value="/course/editLecture/lectureId=${lecture.id}" />">Edit</a>]
                    </security:authorize> 
                    <security:authorize access="hasRole('LECTURER')">
                        [<a href="<c:url value="/course/deleteLecture/lectureId=${lecture.id}" />">Delete</a>]
                    </security:authorize> 
                    <br />
                </c:forEach>
            </c:otherwise>
        </c:choose>
        <br />     

        <h2>Polls</h2>
        <security:authorize access="hasRole('LECTURER')">
            <a href="<c:url value="/course/poll/create" />">Create a Poll</a><br /><br />
        </security:authorize>
        <c:choose>
            <c:when test="${fn:length(pollDatabase) == 0}">

                <i>There are no poll for the course.</i>
            </c:when>
            <c:otherwise>
                <c:forEach items="${pollDatabase}" var="poll">
                    Poll ${poll.id}:
                    <a href="<c:url value="/course/poll/${poll.id}" />">
                        <c:out value="${poll.question}" /></a>
                    &nbsp;

                    <br />
                </c:forEach>
            </c:otherwise>
        </c:choose>

    </body>
