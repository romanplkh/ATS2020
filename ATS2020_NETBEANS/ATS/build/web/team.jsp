<%-- 
    Document   : team
    Created on : Mar 4, 2020, 10:38:23 AM
    Author     : Olena Stepanova
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Team</title>
        <%@include file="WEB-INF/jspf/header.jspf" %>
    </head>
    <body>
        <%@include file="WEB-INF/jspf/navigation.jspf" %>
        <main>
            <div class="container py-5">


                <div class="row mb-4">
                    <div class="col-12 text-center">
                        <h1 class="display-4">Create Team</h1>
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

                       <!--validation errors here-->

                    </div>

                    <div class="col-md-8">
                        <form method="post">

                            <input type="hidden" name="teamId" value="${team.id}">

                            <div class="form-group">
                                <label>Team Name</label>
                                <input type="text" class="form-control"
                                       value="${team.name}"
                                       name="teamName">
                            </div>

                            <div class="form-group">
                                <label>Team member 1</label>
                                <select class="form-control" name="member1">
                                    <option value="0">Select an employee</option>
                                    <c:forEach items="${vm.listEmployees1}" var="employee">
                                        <option value="${employee.id}">${employee}</option>
                                    </c:forEach>
                                </select>
                            </div>    

                            <div class="form-group">
                                <label>Team member 2</label>
                                <select class="form-control" name="member2">
                                    <option value="0">Select an employee</option>
                                    <c:forEach items="${vm.listEmployees2}" var="employee">
                                        <option value="${employee.id}">${employee}</option>
                                    </c:forEach>
                                </select>
                            </div>    

                            <div class="form-check">
                                <input class="form-check-input" type="checkbox"
                                       value="${team.isOnCall}" name="isOnCall">
                                <label class="form-check-label" 
                                       for="defaultCheck1">
                                    Is on Call
                                </label>
                            </div>

                            <button class="btn btn-success btn-lg mt-3" value="save"
                                    name="action">Save</button>


                        </form>
                    </div>

                </div>
            </div>
        </main>
        <%@include file="WEB-INF/jspf/footer.jspf" %>
    </body>
</html>
