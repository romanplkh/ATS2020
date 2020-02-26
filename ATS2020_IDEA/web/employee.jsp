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
                <h1 class="display-4 text-center">Add Employee</h1>

                <form action="employee.jsp" method="post">
                    <div class="form-group">
                        <label>First Name</label>
                        <input type="text" class="form-control" name="firstName" required>
                    </div>
                    <div class="form-group">
                        <label>Last Name</label>
                        <input type="text" class="form-control" name="firstName" required>
                    </div>
                    <div class="form-group">
                        <label>SIN</label>
                        <input type="text" class="form-control" name="sin" max="11" min="11" required
                               pattern="^\d{3}-\d{3}-\d{3}$">
                        <small class="text-muted">XXX-XXX-XXX</small>
                    </div>
                    <div class="form-group">
                        <label>Hourly Rate</label>
                        <input type="text" class="form-control" name="hRate" required>
                    </div>

                    <c:choose>
                        <c:when test="${$employee.id == 0 || employee == null}">
                            <input type="submit" value="Add" class="btn btn-success btn-lg" name="action">
                        </c:when>
                        <c:otherwise>
                            <input class="btn btn-danger btn-lg" type="submit" value="Delete" name="action"/>
                            <input class="btn btn-warning btn-lg" type="submit" value="Update" name="action"/>
                        </c:otherwise>
                    </c:choose>

                    <a href="${pageContext.request.contextPath}/dashboard" class="btn btn-secondary btn-lg">Cancel</a>
                </form>
            </div>
        </div>


    </div>
</main>


<%@include file="WEB-INF/jspf/footer.jspf" %>
</body>
</html>
