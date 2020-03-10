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
            <c:set var="job" value="${jobVm}"/>
            <div class="container my-5">
                <h1 class="display-4 text-center mb-5">Job Details Information</h1>

                <div class="row">
                    <div class="col-lg-8 col-md-6">

                        <div class="card" style="width: 100%;">
                            <div class="card-body">
                                <h3 class="card-title">Job Information</h3>
                                <ul class="list-group list-group-flush">
                                    <li class="list-group-item"><span class="font-weight-bold">
                                            Description: </span>&nbsp; ${jobVm.job.description}
                                    </li>
                                    <li class="list-group-item"><span class="font-weight-bold">
                                            Client: </span>&nbsp; ${jobVm.job.client}
                                    </li>
                                    <li class="list-group-item"><span class="font-weight-bold">
                                            Start: </span>&nbsp; ${jobVm.job.start}
                                    </li>
                                    <li class="list-group-item"><span class="font-weight-bold">
                                            End: </span>&nbsp; ${jobVm.job.end}
                                    </li>

                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-4 col-md-6 mt-md-0 mt-sm-4 ">
                        <div class="card" style="width: 100%">
                            <div class="card-body">
                                <h3 class="card-title">Current Team</h3>
                                <h1 class="card-subtitle mb-2 text-success display-4">
                                    ${jobVm.team.name}</h1>
                            </div>
                        </div>

                        <div class="card mt-5" style="width: 100%;">
                            <div class="card-body">
                                <h3 class="card-title">Tasks</h3>
                                <ul class="list-group list-group-flush">
                                    <c:forEach items="${jobVm.tasks.getTasks()}" var="task">
                                        <li class="list-group-item text-muted">${task.name}</li>
                                        </c:forEach>

                                    <li class="list-group-item text-muted">Router configuration</li>
                                    <li class="list-group-item text-muted">Linux Server configuration</li>
                                    <li class="list-group-item text-muted">Windows configuration</li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>


            </div>
        </main>



    </body>
    <%@include file="WEB-INF/jspf/footer.jspf" %>
</html>
