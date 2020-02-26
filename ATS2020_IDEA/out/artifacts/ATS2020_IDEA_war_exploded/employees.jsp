<%--
  Created by IntelliJ IDEA.
  User: Roman Pelikh
  Date: 2020-02-24
  Time: 7:44 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <c:set var="employeeCount" value="${employees.size()}"/>
    <h1 class="display-4 text-center mt-5">Employees</h1>
    <div class="row justify-content-center">
        <div class="col-md-8">
            <c:choose>

                <c:when test="${employeeCount > 0}">
                    <table class="table">
                        <thead class="thead-dark">
                        <tr>
                            <th scope="col">First Name</th>
                            <th scope="col">Last Name</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${employees}" var="employee">
                            <tr>
                                <td>${employee.firstName}</td>
                                <td>${employee.lastName}</td>
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
</main>

</body>
</html>
