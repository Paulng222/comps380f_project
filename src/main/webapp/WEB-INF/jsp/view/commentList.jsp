<%-- 
    Document   : commentList
    Created on : May 8, 2022, 7:02:48 PM
    Author     : emilylau
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Comment History Page</title>
    </head>
    <body>
        <h1>Poll comment</h1>
        <c:choose>
            <c:when test="${empty comments}">
               <p>No comments</p> 
            </c:when>
            <c:otherwise>
                <table>
                    <tr>
                        
                        <th>Comments</th>
                        <th>Created at</th>
                        
                    <c:forEach var="pollComment" items="${pollComments}">
                        <tr>
                            <td>${pollComment.getContent()} </td>
                            <td>${pollComment.getCreatedAt()} </td>
                            
                        </tr>
                    </c:forEach>
                 </table>  
            </c:otherwise>
        </c:choose>
        
        <h1>Lecture comment</h1>
        <c:choose>
            <c:when test="${empty comments}">
               <p>No comments</p> 
            </c:when>
            <c:otherwise>
                <table>
                    <tr>
                        <th>Comments</th>
                        <th>Created at</th>
                        
                    </tr>
                    <c:forEach var="lectureComment" items="${lectureComments}">
                        <tr>
                            <td>${lectureComment.getContent()} </td>
                            <td>${lectureComment.getCreatedAt()} </td>
                           
                        </tr>
                    </c:forEach>
                 </table>  
            </c:otherwise>
        </c:choose>
        
    </body>
</html>
