<%--
  Created by IntelliJ IDEA.
  User: Roman Pelikh
  Date: 2020-02-24
  Time: 6:47 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Employee</title>
    <%@include file="WEB-INF/jspf/header.jspf" %>
</head>
<body>
<%@include file="WEB-INF/jspf/navigation.jspf" %>


<div class="container my-5">

    <div class="row justify-content-center">
        <div class="col-md-8">
            <h1 class="display-4 text-center">Add Employee</h1>

            <form>
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

                <input type="submit" value="Add" class="btn btn-success">
                <a href="#" class="btn btn-secondary">Cancel</a>
            </form>
        </div>
    </div>


</div>


<%@include file="WEB-INF/jspf/footer.jspf" %>
</body>
</html>
