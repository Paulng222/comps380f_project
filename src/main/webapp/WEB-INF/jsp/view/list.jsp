<!DOCTYPE html>
<html>
    <head>
        <title>Online Course</title>
    </head>
    <body>
        <i><h1>Comps380f Course</h1></i>

        <h2>Lectures</h2>
        <security:authorize access="hasRole('LECTURER') or
                            principal.username=='${lecture.userName}'">
            <a href="<c:url value="/course/createLecture" />">Create a Lecture</a><br /><br />
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
                    <security:authorize access="hasRole('LECTURER') or
                                        principal.username=='${lecture.userName}'">
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
