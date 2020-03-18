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
        <title>JSP Page</title>
    </head>
    <style>
        table,
        th {
            border: solid black 1px;
            padding: 2px;
            text-align: center;
        }

        td {
            padding: 5px;
            height: 20px;
            border-bottom: 1px solid #fff;
        }

        .booked {
            background-color: #07f8b0;
        }

        .divider-r {
            border-right: 4px solid rgb(1, 10, 90);
        }

        .divider-l {
            border-left: 4px solid rgb(1, 10, 90);
        }

        table {
            border-collapse: separate;
            border-spacing: 0 1em;

        }
    </style>

    <body>
    <body>
        <table>
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
                <tr style="background: red; padding: 10px"></tr>
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
                                                <td class="booked divider-l">${currentTime}</td>
                                            </c:when>
                                            <c:when test="${currentTime >= startTime && currentTime <= endTime}">
                                                <c:choose>
                                                    <c:when test="${end.getMinute() == min && end.getHour() == h}">
                                                        <td class="booked divider-r">${currentTime} RRRRR</td>
                                                        <c:set var="cJCount" value="${holder + 1}"/>
                                                    </c:when>
                                                    <c:otherwise>

                                                        <td class="booked">${currentTime} TEST</td>
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





        <!--        <div id="table" style="width:100px">

                </div>-->








        <script>

            //            const team = [
            //                {
            //                    name: "team1",
            //                    booking: [
            //                        {start: {hour: 8, minute: 00}, end: {hour: 8, minute: 30}},
            //                        {start: {hour: 9, minute: 00}, end: {hour: 12, minute: 00}},
            //                        {start: {hour: 14, minute: 30}, end: {hour: 15, minute: 30}}
            //
            //                    ]
            //                },
            //                {
            //                    name: "team2",
            //                    booking: [
            //                        {start: {hour: 8, minute: 30}, end: {hour: 9, minute: 45}},
            //                        {start: {hour: 10, minute: 15}, end: {hour: 12, minute: 00}}
            //                    ]
            //                },
            //                {
            //                    name: "team3",
            //                    booking: [
            //                        {start: {hour: 9, minute: 00}, end: {hour: 10, minute: 45}},
            //                        {start: {hour: 13, minute: 30}, end: {hour: 15, minute: 30}}
            //                    ]
            //                },
            //                {
            //                    name: "team4",
            //                    booking: [
            //                        {start: {hour: 9, minute: 15}, end: {hour: 10, minute: 00}},
            //                        {start: {hour: 16, minute: 30}, end: {hour: 17, minute: 00}}
            //                    ]
            //                    ,
            //                },
            //                {
            //                    name: "team5",
            //                    booking: [
            //                        {start: {hour: 8, minute: 00}, end: {hour: 9, minute: 00}}
            //                    ]
            //                }
            //            ];
            //
            //            const insert = document.querySelector("#table");
            //            const table = document.createElement("table");
            //
            //            let h = 8;
            //            let teamLength = team.length;
            //            let curTeamCount = 0;
            //
            //
            //            while (curTeamCount <= teamLength) {
            //                h = 8;
            //                let tr = document.createElement("tr");
            //
            //
            //                if (curTeamCount == 0) {
            //                    //ADD EMPTY column before time
            //                    tr.appendChild(document.createElement("td"))
            //                    //CREATE ROW WITH TABLES
            //                    while (h <= 17) {
            //                        if (h == 17) {
            //                            let td = document.createElement("td");
            //                            let text = document.createTextNode("17:00");
            //                            td.appendChild(text);
            //                            tr.appendChild(td);
            //                            // tr.appendChild(document.createElement("td"));
            //                        } else {
            //                            for (let i = 0; i < 60; i += 15) {
            //                                let td = document.createElement("td");
            //                                //stupid netbeans do not format
            //                                let text = document.createTextNode(h + ":" + i + (i == 0 ? "0" : ""));
            //                                td.appendChild(text);
            //                                tr.appendChild(td);
            //                            }
            //                        }
            //                        h++;
            //                    }
            //
            //                    //ONCE HEADERS WITH TIME ARE CRATED START BUILD TIMELINES FOR TEAMS
            //                } else {
            //                    //ADD EMPTY ROW JUST TO SEPARATE HEADERS
            //                    if (curTeamCount == 1) {
            //                        table.appendChild(document.createElement("tr"));
            //                    }
            //
            //                    //ADD TEAMNAMES TO 1st COLUMN
            //                    let tdTeam = document.createElement("td");
            //                    tdTeam.appendChild(document.createTextNode(team[curTeamCount - 1].name));
            //                    tr.append(tdTeam);
            //
            //
            //                    h = 8;
            //                    let currentJob = 0;
            //                    let jobSizes = team[curTeamCount - 1].booking.length;
            //                    while (h <= 17) {
            //                        //let colSpanNum = 1;
            //                        //let td = document.createElement("td");
            //
            //                        let numMinutsHour = 60;
            //
            //                        if (h == 17) {
            //                            numMinutsHour = 15;
            //                        }
            //
            //                        //CREATE MINUTES INTERVAL COLUMNS
            //                        for (let minInt = 0; minInt < numMinutsHour; minInt += 15) {
            //                            let td = document.createElement("td");
            //                            //TRY TO FIND JOB BOKINGS
            //                            if (currentJob < jobSizes) {
            //                                let start =
            //                                        team[curTeamCount - 1].booking[currentJob].start;
            //                                let end = team[curTeamCount - 1].booking[currentJob].end;
            //
            //                                //START TIME
            //                                if (start.hour == h && start.minute == minInt) {
            //                                    // td.setAttribute("colspan", colSpanNum);
            //                                    td.classList.add("booked");
            //                                    td.classList.add("divider-l");
            //                                    //   colSpanNum++;
            //                                }
            //
            //                                let currTime = new Date("01/01/2020 " + h + ":" + minInt)
            //                                let startTime = new Date("01/01/2020 " + start.hour + ":" + start.minute);
            //                                let endTime = new Date("01/01/2020 " + end.hour + ":" + end.minute)
            //
            //                                if (currTime > startTime && currTime < endTime) {
            //                                    td.classList.add("booked");
            //                                }
            //
            //
            //
            //                                //END TIME
            //                                if (h == end.hour && minInt == end.minute) {
            //                                    td.classList.add("booked");
            //                                    td.classList.add("divider-r");
            //                                    currentJob++;
            //                                }
            //                            }
            //
            //                            tr.appendChild(td);
            //                        }
            //                        // tr.appendChild(td);
            //
            //                        //GO TO NEXT HOUR
            //                        h++;
            //                    }
            //                }
            //
            //                table.appendChild(tr);
            //
            //                //GO TO NEXT TEAM
            //                curTeamCount++;
            //            }
            //
            //            //WHEN TABLE IS BUILT -> INSERT IT IN DOM
            //            insert.appendChild(table);
        </script>

    </body>
</html>
