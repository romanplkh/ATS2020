<%--
  Created by IntelliJ IDEA.
  User: Olena Stepanova
  Date: 2/25/2020
  Time: 7:22 PM

  This view supports display of all tasks
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>List of Tasks</title>
    <%@include file="WEB-INF/jspf/header.jspf" %>
</head>
<body>
<%@include file="WEB-INF/jspf/navigation.jspf" %>
<main>
    <div class="container py-5">
        <div class="row mb-4">
            <div class="col-12 text-center">
                <h1 class="display-4">All Tasks</h1>
            </div>
        </div>
        <div class="row">
            <div class="col-12">

                <c:set var="tasksCount" value="${taskList.size()}"/>

                <c:choose>
                    <c:when test="${tasksCount > 0}">
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th>Name</th>
                                <th>Description</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${taskList}" var="task">
                                <tr>
                                    <td>${task.name}</td>
                                    <td>${task.description}</td>
                                    <td><a class="mr-5"
                                            href="${pageContext.request.contextPath}/task/${task.id}/details">Details</a>
                                        <a href="${pageContext.request.contextPath}/task/${task.id}/update">Edit</a></td>
                                </tr>
                            </c:forEach>

                            </tbody>
                        </table>
                    </c:when>
                    <c:otherwise>
                        <h4>There are no tasks in a system yet</h4>
                    </c:otherwise>
                </c:choose>


            </div>
        </div>

    </div>
</main>

<%@include file="WEB-INF/jspf/footer.jspf" %>
</body>
</html>
