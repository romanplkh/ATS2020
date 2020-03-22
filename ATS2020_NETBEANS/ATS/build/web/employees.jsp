<%--
  Created by IntelliJ IDEA.
  User: Roman Pelikh
  Date: 2020-02-24
  Time: 7:44 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>

    <head>
        <title>Employee</title>
        <%@include file="WEB-INF/jspf/header.jspf" %>
    </head>

    <body>
        <%@include file="WEB-INF/jspf/navigation.jspf" %>
        <main>
            <c:set var="employeeCount" value="${employees.size()}" />
            <div class="container">

                <h1 class="display-4 text-center mt-5 mb-4">Employees</h1>

                <c:if test="${error.errors != null}">
                    <div class="row mb-3">
                        <div class="col-12">
                            <div class="alert alert-dismissible alert-light" role="alert">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <c:forEach items="${error.errors}" var="err">
                                    ${err}
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </c:if>

                <div class="row">
                    <div class="col-md-10">
                        <form method="get" class="form-inline my-2 my-lg-0">
                            <input class="form-control mr-sm-2 w-50" type="text" name="search" value="${search}"
                                placeholder="Search by SIN or last name">
                            <button class="btn btn-secondary my-2 my-sm-0" type="submit">Search</button>
                        </form>

                    </div>
                </div>

                <div class="row justify-content-center">
                    <div class="col">
                        <c:choose>
                            <c:when test="${employeeCount > 0}">
                                <table class="table table-striped mt-5">
                                    <thead>
                                        <tr>
                                            <th scope="col">First Name</th>
                                            <th scope="col">Last Name</th>
                                            <th></th>
                                            <th></th>
                                            <th></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${employees}" var="employee">
                                            <tr>
                                                <td>${employee.firstName}</td>
                                                <td>${employee.lastName}</td>
                                                <td><a href="employee/${employee.id}/update">Edit</a></td>
                                                <td><a href="employee/${employee.id}/details">Details</a></td>
                                                <td><a href="employee/${employee.id}/skills">Manage Skills</a></td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </c:when>
                            <c:otherwise>
                                <h4 class="mt-5">No employees to display</h4>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </main>
        <%@include file="WEB-INF/jspf/footer.jspf" %>
    </body>

</html>