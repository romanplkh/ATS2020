<%-- 
    Document   : teams
    Created on : 23-Mar-2020, 6:27:35 PM
    Author     : Roman Pelikh
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

            <div class="container">

                <h1 class="display-4 text-center mt-5 mb-4">Teams</h1>

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


                <div class="row justify-content-center">
                    <div class="col">
                        <c:choose>
                            <c:when test="${teams.size() > 0}">
                                <table class="table table-striped mt-5">
                                    <thead>
                                        <tr>
                                            <th>Team Name</th>
                                            <th>1 Member</th>
                                            <th>2 Member</th>
                                            <th></th>

                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${teams}" var="team">
                                            <tr>
                                                <td>${team.name}</td>
                                                <c:forEach items="${team.teamMembers}" var="member">
                                                    <td>
                                                        <a href="employee/${member.id}/details">${member.fullName}</a>  
                                                    </td>
                                                </c:forEach>
                                                <td><a href="team/${team.id}/details">Details</a></td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </c:when>
                            <c:otherwise>
                                <h4 class="mt-5">No teams to display</h4>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </main>
        <%@include file="WEB-INF/jspf/footer.jspf" %>
    </body>

</html>
