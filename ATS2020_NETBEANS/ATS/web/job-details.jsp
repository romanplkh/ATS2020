<%--
    Document   : job-details
    Created on : Mar 10, 2020, 4:53:53 PM
    Author     : Olena Stepanova
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="WEB-INF/jspf/header.jspf" %>
        <title>Job Details</title>
    </head>
    <body>
        <%@include file="WEB-INF/jspf/navigation.jspf" %>


        <main>

            <div class="container my-5">
                <h1 class="display-4 text-center mb-5">Job Details Information</h1>


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
                            <fmt:parseDate value="${job.start}" pattern="yyyy-MM-dd'T'HH:mm" var="start" type="both" />
                            <fmt:parseDate value="${job.end}" pattern="yyyy-MM-dd'T'HH:mm" var="end" type="both" />
                            <div class="col-lg-8 col-md-6">
                                <div class="card" style="width: 100%;">
                                    <div class="card-body">

                                        <h3 class="card-title">Job Information</h3>
                                        <ul class="list-group list-group-flush">
                                            <li class="list-group-item"><span class="font-weight-bold">
                                                    Description: </span>&nbsp; ${job.description}
                                            </li>
                                            <li class="list-group-item"><span class="font-weight-bold">
                                                    Client: </span>&nbsp; ${job.clientName}
                                            </li>
                                            <li class="list-group-item"><span class="font-weight-bold">
                                                    Start: </span>&nbsp; <fmt:formatDate pattern="dd/MM/yyyy HH:mm" value="${start}" />
                                            </li>
                                            <li class="list-group-item"><span class="font-weight-bold">
                                                    End: </span>&nbsp; <fmt:formatDate pattern="dd/MM/yyyy HH:mm" value="${end}" />
                                            </li>

                                        </ul>
                                    </div>


                                </div>

                                <form method="POST" class="mt-4">
                                    <input type="hidden" name="jobId" value="${job.id}">
                                    <input class="btn btn-danger btn-lg" type="submit" value="Delete" name="action"/>
                                </form>
                            </div>
                            <div class="col-lg-4 col-md-6 mt-md-0 mt-sm-4 ">
                                <div class="card" style="width: 100%">
                                    <div class="card-body">
                                        <h3 class="card-title">Current Team</h3>
                                        <h1 class="card-subtitle mb-2 text-success display-4">
                                            ${job.getTeam().name}</h1>
                                    </div>
                                </div>

                                <div class="card mt-5" style="width: 100%;">
                                    <div class="card-body">
                                        <h3 class="card-title">Tasks</h3>
                                        <ul class="list-group list-group-flush">

                                            <c:forEach items="${job.getTasksList()}" var="task">
                                                <li class="list-group-item text-muted">${task.name}</li>
                                                </c:forEach>

                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>


            </div>
        </main>



    </body>
    <%@include file="WEB-INF/jspf/footer.jspf" %>
</html>
