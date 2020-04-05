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
        <script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>
        <title>Jobs Schedule</title>
    </head>

    <body>

        <%@include file="WEB-INF/jspf/navigation.jspf" %>
        <main>
            <div class="container-fluid">
                <div class="row">
                    <div class="col">
                        <h1 class="text-left my-5 display-4">Jobs Schedule</h1>
                        <div class="row justify-content-start">
                            <div class="col-md-3">
                                <form method="get">
                                    <div class="form-group">
                                        <div class="input-group mb-3">
                                            <input type="date" class="form-control" value="${searchDate}"
                                                name="searchDate" max="2021-01-01" min="2000-01-01">
                                            <div class="input-group-append">
                                                <button class="btn btn-outline-primary" type="submit">Search</button>
                                            </div>
                                        </div>
                                        <small class="form-text text-muted">Search by date</small>
                                    </div>

                                </form>
                            </div>

                        </div>
                    </div>
                </div>


                <c:if test="${teams.size() > 0}">
                    <div class="container-fluid mt-5">
                        <div class="row">
                            <div class="col">
                                <div id="chartContainer" class="mt-5" style="height: 600px; width: 100%;"></div>
                            </div>
                        </div>
                    </div>
                </c:if>


                <c:if test="${teams.size() == 0}">
                    <div class="row justify-content-center">
                        <h1 class="display-4 text-success">No Jobs Scheduled</h1>
                    </div>
                </c:if>




                <div class="container-fluid mt-5">
                    <div class="row">
                        <div class="col-md-4">
                            <div class="card mb-3 p-3">
                                <h3 class="card-header text-danger mb-3">Emergency Jobs Schedule</h3>
                                <c:set var="count" value="0" scope="page" />
                                <c:forEach items="${teams}" var="t">
                                    <ul class="list-group list-group-flush">
                                        <c:forEach items="${t.jobs}" var="job">
                                            <c:set var="startJob" value="${ job.startTime}" />
                                            <c:set var="endJob" value="${job.endTime}" />

                                            <c:if test="${startJob.getHour() >= 17}">
                                                <li class="list-group-item  justify-content-between">
                                                    <span class="badge badge-primary badge-pill mr-5">${startJob} -
                                                        ${endJob} </span>
                                                    <a href="job/${job.id}/details" class="pl-5">Details</a>
                                                </li>
                                                <c:set var="count" value="${count + 1}" scope="page" />
                                            </c:if>

                                        </c:forEach>
                                    </ul>
                                </c:forEach>
                                <c:if test="${count == 0}">
                                    <p class="text-muted">No emergency jobs scheduled</p>
                                </c:if>
                            </div>

                        </div>
                    </div>

                </div>
            </div>

        </main>




        <%@include file="WEB-INF/jspf/footer.jspf" %>

        <script>




            let data = ${ teamsJSON }
            let searchDate = ${ searchDateJSON } + " 08:00";
            let srchDate = ${ searchDateJSON };


            let dataPoints = [];

            let datesArray = [];


            data.forEach(el => {

                let x = dataPoints.length + 1;

                el.jobs.forEach(job => {
                    let dataEntry = { x: x, label: "", y: [], click: null }

                    let datePointStart = undefined;
                    let datePointEnd = undefined;

                    dataEntry.label = el.name;
                    dataEntry.y = [];
                    dataEntry.click = null;


                    datesArray = [];

                    let startTime = job.startTime;
                    let endTime = job.endTime;
                    datePointStart = Date.parse(srchDate + " " + startTime.hour + ":" + startTime.minute);
                    datePointEnd = Date.parse(srchDate + " " + endTime.hour + ":" + endTime.minute);


                    datesArray.push(datePointStart);
                    datesArray.push(datePointEnd);



                    dataEntry.y = datesArray;


                    dataEntry.click = function (e) {
                        onClick(job.id)
                    }



                    dataPoints.push(dataEntry);



                })



            })



            const onClick = (id) => {
                window.location.href = "job/" + id + "/details";
            };



            var chart = new CanvasJS.Chart("chartContainer",
                {
                    title: {
                        text: "Jobs Schedule For " + ${ searchDateJSON },
                fontFamily: "Lato, sans-serif",
                fontSize: 46
                        },
            axisY: {
                minimum: Date.parse(searchDate),
                    interval: ((1 * 60 * 60 * 1000) / 2),
                        labelFormatter: function (e) {
                            return CanvasJS.formatDate(e.value, "h:mm TT");
                        },
                gridThickness: 2
            },

            toolTip: {
                contentFormatter: function (e) {
                    return "Start: " + CanvasJS.formatDate(e.entries[0].dataPoint.y[0], "h:mm TT") + "</br>End : " + CanvasJS.formatDate(e.entries[0].dataPoint.y[1], "h:mm TT");
                }
            },

            data: [
                {
                    type: "rangeBar",
                    dataPoints: dataPoints
                }

            ]
                    });


            chart.render();


        </script>

    </body>

</html>