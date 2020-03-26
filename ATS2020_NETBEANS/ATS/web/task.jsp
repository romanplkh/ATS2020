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
        <title>Task Record</title>
        <%@include file="WEB-INF/jspf/header.jspf" %>
    </head>

    <body>
        <%@include file="WEB-INF/jspf/navigation.jspf" %>
        <main>
            <div class="container py-5">
                <div class="row mb-4">
                    <div class="col-12 text-center">
                        <c:if test="${task.id != 0}">
                            <h1 class="display-4">Manage Task</h1>
                        </c:if>
                        <c:if test="${task.id == 0}">
                            <h1 class="display-4">Create Task</h1>
                        </c:if>

                    </div>
                </div>
                <div class="row justify-content-center">
                    <div class="col-md-8">


                        <c:if test="${error.errors != null}">
                            <div class="alert alert-danger" role="alert">
                                <c:forEach items="${error.errors}" var="err">
                                    ${err.description}
                                </c:forEach>
                            </div>
                        </c:if>

                        <c:if test="${task.getErrors().size() > 0}">
                            <div class="alert alert-danger" role="alert">
                                <ul class="list-unstyled mb-0">
                                    <c:forEach items="${task.getErrors()}" var="err">
                                        <li>${err.description}</li>
                                    </c:forEach>
                                </ul>
                            </div>
                        </c:if>



                    </div>

                    <div class="col-md-8 ">

                        <form method="post">

                            <input type="hidden" name="taskId" value="${task.id}">

                            <div class="form-group">
                                <label>Name</label>
                                <input type="text" class="form-control" value="${task.name}" name="taskName">
                            </div>

                            <div class="form-group">
                                <label>Description</label>
                                <textarea name="taskDescription" class="form-control" cols="10"
                                    rows="6">${task.description}</textarea>
                            </div>

                            <div class="form-group">
                                <label>Duration</label>
                                <input type="text" class="form-control"
                                    value="${task.duration == 0 ? '' : task.duration}" name="taskDuration">
                                <small class="form-text text-muted">Task duration in minutes</small>
                            </div>

                            <c:choose>
                                <c:when test="${task.id == 0}">
                                    <button class="btn btn-success btn-lg" value="save" name="action">Save</button>
                                </c:when>
                                <c:otherwise>
                                    <button class="btn btn-warning btn-lg" value="update" name="action">Update</button>
                                    <button class="btn btn-danger btn-lg" value="delete" name="action">Delete</button>
                                </c:otherwise>
                            </c:choose>
                            <a href="${pageContext.request.contextPath}/tasks"
                                class="btn btn-secondary btn-lg">Cancel</a>
                        </form>

                    </div>
                </div>
            </div>
        </main>
        <%@include file="WEB-INF/jspf/footer.jspf" %>
    </body>

</html>