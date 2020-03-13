<%--
  Created by IntelliJ IDEA.
  User: Roman Pelikh
  Date: 2020-02-27
  Time: 4:51 p.m.
  To change this template use File | Settings | File Templates.
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
            <c:set var="emp" value="${empDetails}"/>
            <div class="container my-5">
                <h1 class="display-4 text-center mb-5">Employee Information</h1>

                <div class="row">
                    <div class="col-lg-8 col-md-6">
                        <c:if test="${error.errors != null}">
                            <div class="alert alert-danger" role="alert">
                                <c:forEach items="${error.errors}" var="err">
                                    <p>${err}</p>
                                </c:forEach>
                            </div>
                        </c:if>

                        <div class="card" style="width: 100%;">
                            <div class="card-body">
                                <h3 class="card-title">Personal Information</h3>
                                <ul class="list-group list-group-flush">
                                    <li class="list-group-item"><span class="font-weight-bold">
                                            First Name: </span>&nbsp; ${emp.employee.firstName}
                                    </li>
                                    <li class="list-group-item"><span class="font-weight-bold">
                                            Last Name: </span>&nbsp; ${emp.employee.lastName}
                                    </li>
                                    <li class="list-group-item"><span class="font-weight-bold">
                                            SIN: </span>&nbsp; ${emp.employee.sin}
                                    </li>
                                    <li class="list-group-item"><span class="font-weight-bold">
                                            Hourly Rate: </span> <fmt:formatNumber value="${emp.employee.hourlyRate}" type="currency" currencySymbol="$"/>
                                    </li>
                                    <li class="list-group-item"><span class="font-weight-bold">
                                            Deleted:</span>&nbsp; ${emp.employee.isDeleted ? "Yes":"No"}
                                    </li>
                                    <li class="list-group-item"><span class="font-weight-bold">
                                            Created At:</span>&nbsp; ${emp.employee.createdAt}
                                    </li>
                                    <li class="list-group-item"><span class="font-weight-bold">
                                            Updated At:</span>&nbsp;${emp.employee.updatedAt != null ? emp.employee.updatedAt : "Not applicable" }
                                    </li>
                                    <li class="list-group-item"><span class="font-weight-bold">
                                            Deleted At:</span>&nbsp; ${emp.employee.deletedAt != null ? emp.employee.deletedAt : "Not applicable" }
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-4 col-md-6 mt-md-0 mt-sm-4 ">
                        <div class="card" style="width: 100%">
                            <div class="card-body">
                                <h3 class="card-title">Current Team</h3>
                                <h1 class="card-subtitle mb-2 text-success display-4">${emp.team.name != null ? emp.team.name : "No Team"}</h1>
                            </div>
                        </div>

                        <div class="card mt-5" style="width: 100%;">
                            <div class="card-body">
                                <h3 class="card-title">Skills</h3>
                                <ul class="list-group list-group-flush">
                                    <c:if test="${emp.skills.size() > 0}">
                                        <c:forEach items="${emp.skills}" var="skill">
                                            <li class="list-group-item text-muted">${skill.name}</li>
                                            </c:forEach>


                                    </c:if>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>


            </div>
        </main>


        <%@include file="WEB-INF/jspf/footer.jspf" %>
    </body>

