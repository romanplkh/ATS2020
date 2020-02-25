<%--
  Created by IntelliJ IDEA.
  User: Olena Stepanova
  Date: 2/24/2020
  Time: 5:24 PM

  This view supports a retrieved task or creation of a new task
--%>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Task</title>
    <%@include file="WEB-INF/jspf/header.jspf" %>
</head>
<body>
<%@include file="WEB-INF/jspf/navigation.jspf" %>
<div class="container py-5">
    <div class="row mb-4">
        <div class="col-12 text-center">
            <h1 class="display-4">Add Task</h1>
        </div>
    </div>
    <div class="row justify-content-center">
        <div class="col-md-8 ">

            <form action="" method="post">

                <div class="form-group">
                    <label>Name</label>
                    <input type="text" class="form-control" name="taskName">
                </div>

                <div class="form-group">
                    <label>Duration</label>
                    <input type="text" class="form-control" name="taskDuration">
                    <small class="form-text text-muted">Task duration in minutes</small>
                </div>

                <div class="form-group">
                    <label>Description</label>
                    <textarea name="taskDescription"
                              class="form-control"
                              cols="10" rows="6"></textarea>
                </div>

                <button class="btn btn-success btn-lg" name="saveBtn">Save</button>

            </form>

        </div>
    </div>
</div>

<%@include file="WEB-INF/jspf/footer.jspf" %>
</body>
</html>
