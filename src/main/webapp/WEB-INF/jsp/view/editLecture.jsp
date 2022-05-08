<!DOCTYPE html>
<html>
    <head>
        <title>Edit Lecture</title>
    </head>
    <body>
       

        <h2>Lecture ${lectureId}</h2>
        <form:form method="POST" enctype="multipart/form-data" 
                   modelAttribute="lectureForm">
            <form:label path="subject">Subject</form:label><br/>
            <form:input type="text" path="subject" /><br/><br/>
            <form:label path="body">Body</form:label><br/>
            <form:textarea path="body" rows="5" cols="30" /><br/><br/>
            <c:if test="${lecture.numberOfNotes > 0}">
                <b>Notes:</b><br/>
                <ul>
                    <c:forEach items="${lecture.notes}" var="notes">
                        <li>
                            <c:out value="${notes.name}" />
                            [<a href="<c:url value="/course/lectureId=${lectureId}/deleteNotes/${notes.name}" />">Delete</a>]
                        </li>
                    </c:forEach>
                </ul>
            </c:if>
            <b>Add notes</b><br />
            <input type="file" name="notes" multiple="multiple"/><br/><br/>
            <input type="submit" value="Save"/>
        </form:form>
    </body>
</html> 