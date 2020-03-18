<%--
    Document   : jobs
    Created on : 16-Mar-2020, 8:45:41 PM
    Author     : Roman Pelikh
--%>

<%@page import="java.time.LocalTime"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="WEB-INF/jspf/header.jspf" %>
        <title>JSP Page</title>
    </head>
    <style>



        td {
            padding: 15px;
            height: 80px;
            border-bottom: 1px solid #fff;
            width: 20px;
        }




        .booked {
            background-color: #18BC9C;
        }

        .divider-r {
            border-right: 4px solid #2C3E50;
        }

        .divider-l {
            border-left: 4px solid #2C3E50;
        }

        table {
            border-collapse: separate;
            border-spacing: 0 1em;
            padding: 12px;
            text-align: center;
        }


    </style>

    <body>

        <%@include file="WEB-INF/jspf/navigation.jspf" %>
        <main>
            <div class="container-fluid">
                <div class="row">
                    <div class="col">
                        <h1 class="text-left my-5">Job Schedule</h1>
                        <div class="row justify-content-start">
                            <div class="col-md-3">
                                <form method="get">
                                    <div class="form-group">
                                        <div class="input-group mb-3">
                                            <input type="date" class="form-control" value="${searchDate}" name="searchDate" max="2021-01-01" min="2000-01-01" >
                                            <div class="input-group-append">
                                                <button class="btn btn-outline-primary" type="submit">Search</button>
                                            </div>
                                        </div>
                                        <small class="form-text text-muted">Find by Date.</small>
                                    </div>

                                </form>
                            </div>

                        </div>




                        <c:choose>
                            <c:when test="${teams.size() > 0}">
                                <div>
                                    <table >
                                        <c:forEach items="${teams}" var="team" varStatus="currTeam">
                                            <!--BUILD TIME HEADERS-->
                                            <c:if test="${currTeam.index == 0}">
                                                <tr>
                                                    <!--1st EMPTY-->
                                                    <td></td>
                                                    <c:forEach var="h" begin="8" end="17">
                                                        <c:choose>
                                                            <c:when test="${h != 17}">
                                                                <c:forEach var="min" begin="0" end="45" step="15">
                                                                    <td>
                                                                        <c:choose>
                                                                            <c:when test="${min == 0}">
                                                                                <!--TEST-->
                                                                                ${h}:${min}0
                                                                            </c:when>
                                                                            <c:otherwise>
                                                                                ${h}:${min}
                                                                            </c:otherwise>
                                                                        </c:choose>
                                                                    </td>
                                                                </c:forEach>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <td>
                                                                    17:00
                                                                </td>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:forEach>
                                                </tr>


                                            </c:if>
                                            <!--BUILD TEAMS SCHEDULE-->
                                            <!--EMPTY ROW SWPARATOR-->
                                            <!--                                            <tr style="background: red; padding: 10px"></tr>-->
                                            <tr>
                                                <td>${team.name}</td>
                                                <!--FIRST COL TEAM NAME-->

                                                <!--ALL JOBS TEAM-->
                                                <c:set var="tJobs" value="${ team.jobs }" />
                                                <!--NUM OF JOBS-->
                                                <c:set var="jSize" value="${ tJobs.size()}"/>
                                                <!--CURRENT JOB COUNTER-->
                                                <c:set var="cJCount" value="0"/>



                                                <c:forEach var="h" begin="8" end="17">

                                                    <c:if  test="${h < 17}">
                                                        <c:forEach var="min" begin="0" end="45" step="15">
                                                            <c:choose>
                                                                <c:when test="${cJCount < jSize}">


                                                                    <c:set var="start" value="${ tJobs.get(cJCount).startTime}" />

                                                                    <!--J END-->
                                                                    <c:set var="end" value="${tJobs.get(cJCount).endTime}" />



                                                                    <!--STORE CURRENT MINUTE-->
                                                                    <c:set var="mm" value="${min}" />

                                                                    <!--TO AVOID CRUSHING ADD 0 FOR PARSING-->
                                                                    <c:if test="${min == 0}">
                                                                        <c:set var="mm" value="00"/>
                                                                    </c:if>

                                                                    <!--PARSE DATES-->
                                                                    <fmt:parseDate value="${h}:${mm}:00" var="currentTime" pattern="HH:mm:ss" />
                                                                    <fmt:parseDate value="${start.getHour()}:${start.getMinute()}:00" var="startTime"
                                                                                   pattern="HH:mm:ss" />
                                                                    <fmt:parseDate value="${end.getHour()}:${end.getMinute()}:00" var="endTime"
                                                                                   pattern="HH:mm:ss" />


                                                                    <!--WHAT TO COLOR-->
                                                                    <c:choose>
                                                                        <c:when test="${start.getHour() == h && start.getMinute() == min}">
                                                                            <td class="booked divider-l"></td>
                                                                        </c:when>
                                                                        <c:when test="${currentTime >= startTime && currentTime <= endTime}">
                                                                            <c:choose>
                                                                                <c:when test="${end.getMinute() == min && end.getHour() == h}">
                                                                                    <td class="booked divider-r"></td>
                                                                                    <c:set var="cJCount" value="${holder + 1}"/>
                                                                                </c:when>
                                                                                <c:otherwise>

                                                                                    <td class="booked"></td>
                                                                                </c:otherwise>
                                                                            </c:choose>
                                                                        </c:when >
                                                                        <c:otherwise>
                                                                            <td></td>
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <!--ADD TD-->
                                                                    <td></td>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </c:forEach>

                                                    </c:if>
                                                </c:forEach>
                                                <%--</c:forEach>--%>
                                            </tr>
                                        </c:forEach>
                                    </table>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <h1 class="text-center text-success my-auto">No Jobs For  ${searchDate} found</h1>
                            </c:otherwise>
                        </c:choose>
                    </div>


                </div>

            </div>


        </main>



        <%@include file="WEB-INF/jspf/footer.jspf" %>
    </body>
</html>
