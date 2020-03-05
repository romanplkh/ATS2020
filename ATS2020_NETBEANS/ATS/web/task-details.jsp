<%--
  Created by IntelliJ IDEA.
  User: Olena Stepanova
  Date: 2/27/2020
  Time: 4:45 PM

  This view supports display of task details
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
    <head>
        <title>Task Details</title>
        <%@include file="WEB-INF/jspf/header.jspf" %>
    </head>
    <body>
        <%@include file="WEB-INF/jspf/navigation.jspf" %>
        <main>
            <div class="container py-5">
                <div class="row mb-4">
                    <div class="col-12">
                        <h1 class="display-4">
                            Task Details
                        </h1>
                    </div>
                </div>
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
                            <fmt:parseDate value="${task.createdAt}" pattern="yyyy-MM-dd" var="createdAt" type="both" />
                            <fmt:parseDate value="${task.updatedAt}" pattern="yyyy-MM-dd" var="updatedAt" type="both" />

                            <div class="col-md-6">
                                <h3 class="font-weight-bolder mb-3">${task.name}</h3>
                                <ul class="list-group list-group-flush">
                                    <li class="list-group-item">
                                        <strong>Description:</strong> ${task.description}
                                    </li>
                                    <li class="list-group-item">
                                        <strong>Duration:</strong> ${task.duration} min
                                    </li>
                                    <li class="list-group-item">
                                        <strong>Created:</strong>
                                        <fmt:formatDate pattern="dd/MM/yyyy" value="${createdAt}" /> 
                                    </li>
                                    <li class="list-group-item">
                                        <strong>Updated:</strong> 

                                        <c:if test="${task.updatedAt == null}">
                                            n/a
                                        </c:if> 
                                        <c:if test="${task.updatedAt != null}">
                                            <fmt:formatDate pattern="dd/MM/yyyy" value="${updatedAt}" /> 
                                        </c:if>
                                    </li>
                                </ul>
                            </div>
                        </c:otherwise>
                    </c:choose>


                </div>

            </div>
        </main>
        <%@include file="WEB-INF/jspf/footer.jspf" %>
    </body>
</html>
