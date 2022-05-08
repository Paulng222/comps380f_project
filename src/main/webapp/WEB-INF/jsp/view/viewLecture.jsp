<!DOCTYPE html>
<html>
    <head>
        <title>View Lecture</title>
    </head>
    <body>

        <h2>Lecture ${lectureId}: <c:out value="${lecture.subject}" /></h2>
        <security:authorize access="hasRole('LECTURER') or principal.username=='${lecture.userName}'">
            [<a href="<c:url value="/course/editLecture/lectureId=${lectureId}" />">Edit</a>]
        </security:authorize>
        &nbsp;
        <security:authorize access="hasRole('LECTURER')">
            [<a href="<c:url value="/course/deleteLecture/lectureId=${lectureId}" />">Delete</a>]
        </security:authorize> 

        <br /><br />
        <i>Created by <c:out value="${lecture.userName}" /></i><br /><br />
        <c:out value="${lecture.body}" /><br /><br />
        <c:if test="${lecture.numberOfNotes > 0}">
            Notes:
            <c:forEach items="${lecture.notes}" var="notes"
                       varStatus="status">
                <c:if test="${!status.first}">, </c:if>
                <a href="<c:url value="/course/lectureId=${lectureId}/notes/${notes.name}" />">
                    <c:out value="${notes.name}" /></a>
            </c:forEach><br /><br /> 
        </c:if>
        <br />

        <h2>Comments</h2>
        <a href="<c:url value="/course/lectureId=${lectureId}/createLectureComment" />">Create a Comment</a><br /><br />
        <c:choose>
            <c:when test="${fn:length(lectureComment) == 0}">
                <i>There are no comment for the lecture.</i>
            </c:when>
            <c:otherwise>
                <table border="1">
                    <tr><th>Username</th><th>Comment</th><th>Created Time</th>
                            <c:forEach items="${lectureComment}" var="lectureComment">
                        <tr>
                            <td><c:out value="${lectureComment.username}" /></td>
                            <td><c:out value="${lectureComment.comment}" /></td>
                            <td><c:out value="${lectureComment.time}" /></td>
   
                           
                        </tr>
                    </c:forEach>
                </table>
            </c:otherwise>
        </c:choose>
        <br /><br /> <br />
        <a href="<c:url value="/course" />">Return to list page</a>
    </body>
</html>