<%-- 
    Document   : teamDetails
    Created on : 24-Mar-2020, 3:10:23 PM
    Author     : Roman Pelikh
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>

    <head>
        <title>Employee Details</title>
        <%@include file="WEB-INF/jspf/header.jspf" %>
    </head>

    <body>
        <%@include file="WEB-INF/jspf/navigation.jspf" %>

        <main>
            <div class="container my-5">
                <h1 class="display-4 text-center mb-5">Team Information</h1>

                <div class="row">

                    <c:if test="${error.errors != null}">
                        <div class="col-12">
                            <div class="alert alert-dismissible alert-light" role="alert">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <c:forEach items="${error.errors}" var="err">
                                    ${err}
                                </c:forEach>
                            </div>
                        </div>
                    </c:if>
                    
                    <c:if test="${team.getErrors().size() > 0}">
                        <div class="col-12">
                            <div class="alert alert-dismissible alert-danger" role="alert">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <c:forEach items="${team.getErrors()}" var="err">
                                    ${err.description}
                                </c:forEach>
                            </div>
                        </div>
                    </c:if>

                    <c:if test="${team != null}">
                        <div class="col-lg-8 col-md-6">
                            <div class="card" style="width: 100%;">
                                <div class="card-body">
                                    <h3 class="card-title">Team Details</h3>
                                    <ul class="list-group list-group-flush">
                                        <li class="list-group-item"><span class="font-weight-bold">
                                                Team Name: </span>&nbsp; ${team.name}
                                        </li>
                                        <li class="list-group-item"><span class="font-weight-bold">
                                                On Call: </span>&nbsp; ${team.isOnCall ? "Yes": "No"}
                                        </li>
                                        <li class="list-group-item"><span class="font-weight-bold">
                                                Deleted:  </span>&nbsp; ${team.isDeleted ? "Yes" : "No"}
                                        </li>
                                        <li class="list-group-item"><span class="font-weight-bold">
                                                Created At: </span>&nbsp; ${team.createdAt}
                                        </li>
                                        <li class="list-group-item"><span class="font-weight-bold">
                                                Updated At: </span>&nbsp; ${team.updatedAt != null ? team.updatedAt : "Not applicable"}
                                        </li>
                                        <li class="list-group-item"><span class="font-weight-bold">
                                                Deleted At:</span>&nbsp; ${team.deletedAt != null ?
                                                                           team.deletedAt : "Not applicable" }
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-4 col-md-6 mt-md-0 mt-sm-4 ">
                            <div class="card mb-3" style="width: 100%;">
                                <div class="card-body">
                                    <h3 class="card-title">Team Members</h3>
                                    <ul class="list-group list-group-flush">
                                        <c:if test="${team.teamMembers.size() > 0}">
                                            <c:forEach items="${team.teamMembers}" var="member">
                                                <li class="list-group-item text-muted"><a href="${pageContext.request.contextPath}/employee/${member.id}/details">${member.fullName}</a> </li>
                                                </c:forEach>
                                            </c:if>
                                            <c:if test="${team.teamMembers.size()  == 0}">
                                            <p class="text-muted">No Members</p>
                                        </c:if>
                                    </ul>
                                </div>
                            </div>
                            
                            <div class="pt-3">
                                <form method="post">
                                    <input type="hidden" name="teamId" value="${team.id}" />
                                    <button class="btn btn-danger btn-lg" value="delete" name="action">Delete</button>
                                </form>
                            </div> 

                            <div class="pt-3">
                                <form method="post">
                                    <input type="hidden" name="teamId" value="${team.id}" />
                                    <button class="btn btn-secondary btn-lg" value="onCall" name="action">Place On Call</button>
                                </form>
                            </div>             
                        </div>
                    </c:if>

                </div>


            </div>
        </main>


        <%@include file="WEB-INF/jspf/footer.jspf" %>
    </body>
