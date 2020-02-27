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
        <div class="row mb-4 justify-content-center">
            <div class="col-12">
                <h1 class="display-4">
                    Task Details
                </h1>
            </div>
        </div>
        <div class="row">
            <div class="col-12">
                <h3 class="font-weight-bolder">${task.name}</h3>
                <ul class="list-unstyled">
                    <li>Id: ${task.id}</li>
                    <li>Name: ${task.name}</li>
                    <li>Description: ${task.description}</li>
                    <li>Duration: ${task.duration} min</li>
                </ul>
            </div>

        </div>

    </div>
</main>
<%@include file="WEB-INF/jspf/footer.jspf" %>
</body>
</html>
