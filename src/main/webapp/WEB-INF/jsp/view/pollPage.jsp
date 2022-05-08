<%-- 
    Document   : poll
    Created on : Mar 22, 2022, 5:10:56 PM
    Author     : emilylau
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Poll Page</title>
    </head>
    <style>
        table, th, td {
          border: 1px solid black;
          border-collapse: collapse;
        }
        th, td {
          padding: 5px;
        }
    </style>
    <body>
        <a href="<c:url value="/">
                </c:url>"><c:out value="to Index" /></a><br />
                
                
        <security:authorize access="hasRole('LECTURER')">
            <a href="<c:url value="/course/poll/${pollId}/delete">
            </c:url>"><c:out value="Delete this Poll" /></a><br />
        </security:authorize>
        
        <h1>Poll ${pollId} question: ${question}</h1>
     
        <c:url var="vote_url"  value="/course/poll/${pollId}/vote" />
            
        
        <form:form mehtod="POST" action="${vote_url}" modelAttribute="vote" >
            <form:radiobutton  path="voteOption" value="1" checked="${voteOption eq 1 ? 'checked' : ''}" />${option1}(${voteOptionTotal[0]})<br>
            <form:radiobutton  path="voteOption" value="2" checked="${voteOption eq 2 ? 'checked' : ''}" />${option2}(${voteOptionTotal[1]})<br>
            <form:radiobutton  path="voteOption" value="3" checked="${voteOption eq 3 ? 'checked' : ''}" />${option3}(${voteOptionTotal[2]})<br>
            <form:radiobutton  path="voteOption" value="4" checked="${voteOption eq 4 ? 'checked' : ''}" />${option4}(${voteOptionTotal[3]})<br>
            <input type="submit" value="Vote"></input><br>
        </form:form>
            
            <c:choose>
            <c:when test="${empty histories}">
               <p>No vote histories</p> 
            </c:when>
            <c:otherwise>
                <table>
                    <tr>
                        <th>voted option</th>
                        <th>voted at</th>
                    </tr>
                    <c:forEach var="history" items="${histories}">
                        <tr>
                            <td>${history.getVoteOption()} </td>
                            <td>${history.getCreatedAt()} </td>
                            
                        </tr>
                    </c:forEach>
                 </table>  
            </c:otherwise>
        </c:choose>
            
        <h2>Comments</h2>
        <a href="<c:url value="/course/poll/${pollId}/comment/create">            
        </c:url>">
            Create comment
        </a>
        <c:choose>
            <c:when test="${empty comments}">
               <p>No comments</p> 
            </c:when>
            <c:otherwise>
                <table>
                    <tr>
                        <th>Users</th>
                        <th>Comments</th>
                        <security:authorize access="hasRole('LECTURER')">
                            <th>Delete</th>
                        </security:authorize>
                        
                    </tr>
                    <c:forEach var="comment" items="${comments}">
                        <tr>
                            <td>${comment.getUsername()} </td>
                            <td>${comment.getContent()} </td>
                            <security:authorize access="hasRole('LECTURER')">
                                <td><a href="<c:url value="/course/poll/${pollId}/comment/${comment.getId()}/delete"></c:url>">
                                        <c:out value="Delete"/>
                                    </a> 
                                </td>
                            </security:authorize>
                        </tr>
                    </c:forEach>
                 </table>  
            </c:otherwise>
        </c:choose>
    </body>
</html>
