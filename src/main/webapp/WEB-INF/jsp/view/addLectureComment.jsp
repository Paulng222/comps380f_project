<!DOCTYPE html>
<html>
    <head>
        <title>Add Lecture Comment</title>
    </head>
    <body>

        <h2>Add a Comment</h2>
        <form:form method="POST" enctype="multipart/form-data"
                   modelAttribute="lectureCommentForm">
            <form:label path="comment">Comment</form:label><br /><br />
            <form:textarea path="comment" rows="5" cols="30" /><br /><br />
            <input type="submit" value="Submit"/>
        </form:form>
    </body>
</html>
