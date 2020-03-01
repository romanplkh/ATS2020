<%--
  Created by IntelliJ IDEA.
  User: Olena Stepanova
  Date: 2/27/2020
  Time: 4:45 PM

  This view supports display of task details
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Task Details</title>
    <%@include file="WEB-INF/jspf/header.jspf" %>
</head>
<body>
<%@include file="WEB-INF/jspf/navigation.jspf" %>
<main>
    <div class="container py-5">
        <div class="row mb-4">
            <div class="col-12">
                <h1 class="display-4">
                    Task Details
                </h1>
            </div>
        </div>
        <div class="row">

            <c:choose>

                <c:when test="${error.errors != null}">
                    <div class="col-12">
                        <div class="alert alert-dismissible alert-light" role="alert">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <c:forEach items="${error.errors}" var="err">
                                ${err}
                            </c:forEach>
                        </div>
                    </div>

                </c:when>
                <c:otherwise>

                    <div class="col-md-6">
                        <h3 class="font-weight-bolder mb-3">${task.name}</h3>
                        <ul class="list-group list-group-flush">
                            <li class="list-group-item">
                                <strong>Id:</strong> ${task.id}
                            </li>
                            <li class="list-group-item">
                                <strong>Name:</strong> ${task.name}
                            </li>
                            <li class="list-group-item">
                                <strong>Description:</strong> ${task.description}
                            </li>
                            <li class="list-group-item">
                                <strong>Duration:</strong> ${task.duration} min
                            </li>
                        </ul>
                    </div>
                </c:otherwise>
            </c:choose>


        </div>

    </div>
</main>
<%@include file="WEB-INF/jspf/footer.jspf" %>
</body>
</html>
