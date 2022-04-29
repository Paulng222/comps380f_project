<!DOCTYPE html>
<html>
    <head>
        <title>Online Course</title>
    </head>
    <body>


        <h2>Create a Lecture</h2>
        <form:form method="POST" enctype="multipart/form-data"
                   modelAttribute="lectureForm">
            <form:label path="subject">Subject</form:label><br />
            <form:input type="text" path="subject" /><br /><br />
            <form:label path="body">Body</form:label><br />
            <form:textarea path="body" rows="5" cols="30" /><br /><br />
            <b>Notes</b><br />
            <input type="file" name="notes" multiple="multiple" /><br /><br />
            <input type="submit" value="Create"/>
        </form:form>
    </body>
</html>
