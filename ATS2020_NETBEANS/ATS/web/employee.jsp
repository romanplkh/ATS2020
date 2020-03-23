<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>

    <head>
        <title>Employee</title>
        <%@include file="WEB-INF/jspf/header.jspf" %>
    </head>

    <body>
        <%@include file="WEB-INF/jspf/navigation.jspf" %>

        <main>
            <div class="container my-5">
                <div class="row justify-content-center">
                    <div class="col-md-8">

                        <h1 class="display-4 text-center">${employee == null ? "" : employee.id == 0 ? "Add Employee" :
                            "Update Employee"}</h1>

                        <c:if test="${employeeErrors != null}">
                            <div class="alert alert-danger" role="alert">
                                <c:forEach items="${employeeErrors}" var="err">
                                    <p class="m-0">${err.description}</p>
                                </c:forEach>
                            </div>
                        </c:if>

                        <c:choose>
                            <c:when test="${vmError.errors.size() > 0}">
                                <c:forEach items="${vmError.errors}" var="errVm">
                                    <div class="alert alert-danger" role="alert">
                                        <p class="m-0 font-weight-bold">${errVm}</p>
                                    </div>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>


                                <form method="post">
                                    <div class="form-group">
                                        <label>First Name</label>
                                        <input type="text" class="form-control" name="firstName"
                                            value="${employee.firstName}">
                                        <input type="hidden" value='${employee.id}' name="empId" />
                                    </div>
                                    <div class="form-group">
                                        <label>Last Name</label>
                                        <input type="text" class="form-control" value="${employee.lastName}"
                                            name="lastName">
                                    </div>
                                    <div class="form-group">
                                        <label>SIN</label>
                                        <input type="text" class="form-control" name="sin" value="${employee.sin}">
                                        <small class="text-muted">XXX-XXX-XXX</small>
                                    </div>
                                    <div class="form-group">
                                        <label>Hourly Rate</label>
                                        <input type="text" class="form-control" name="hRate"
                                            value="${employee.hourlyRate != 0 ? employee.hourlyRate : ""}">
                                    </div>

                                    <c:choose>
                                        <c:when test="${employee.id == 0}">
                                            <input type="submit" value="Create" class="btn btn-success btn-lg"
                                                name="action">
                                        </c:when>
                                        <c:otherwise>
                                            <input class="btn btn-warning btn-lg" type="submit" value="Update"
                                                name="action" />
                                        </c:otherwise>
                                    </c:choose>

                                    <a href="${pageContext.request.contextPath}/employees"
                                        class="btn btn-secondary btn-lg">Cancel</a>
                                </form>

                            </c:otherwise>
                        </c:choose>



                    </div>
                </div>


            </div>
        </main>


        <%@include file="WEB-INF/jspf/footer.jspf" %>
    </body>

</html>