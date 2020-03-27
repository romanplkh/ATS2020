<%--
  Created by IntelliJ IDEA.
  User: Olena Stepanova
  Date: 2/22/2020
  Time: 3:29 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>

    <head>
        <title>Dashboard</title>
        <%@include file="WEB-INF/jspf/header.jspf" %>
        <script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>
    </head>

    <body>
        <%@include file="WEB-INF/jspf/navigation.jspf" %>
        <main class="py-5">

            <h2 class="display-4 text-center my-5">Welcome to Advanced Technology Solutions</h2>

            <div class="container-fluid">

                <div class="row">
                    <div class="col-md-3">
                        <div class="card border-primary mb-3 " style="max-width: 20rem; height: 17rem">
                            <div class="card-header text-center" style="font-size: 1.2em">Jobs Today</div>
                            <div class="card-body d-flex justify-content-center align-items-center">
                                <h1 class=" display-4 card-title text-info"
                                    style="font-size: 5em;"
                                    >${cardData.jobsCountToday}</h2>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="card border-primary mb-3" style="max-width: 20rem; height: 17rem">
                            <div class="card-header text-center" style="font-size: 1.2em">On Call Team</div>
                            <div class="card-body">
                                <c:choose >
                                    <c:when test="${cardData.teamOnCall.name != null}">
                                        <h2 class="card-title display-4 text-danger text-center">
                                            ${cardData.teamOnCall.name}</h2>
                                        <ul class="list-group list-group-flush mt-0">
                                            <c:forEach items="${cardData.teamOnCall.teamMembers}" var="member">
                                                <li class="list-group-item text-muted text-center">${member.fullName}</li>
                                                </c:forEach>
                                        </ul>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="display-4 text-center text-muted">No team on call</div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="card border-primary mb-3" style="max-width: 20rem; height: 17rem">
                            <div class="card-header text-center" style="font-size: 1.2em">Monthly Statistic</div>
                            <div class="card-body d-flex justify-content-center align-items-center">
                                <div class="row text-center">
                                    <div class="col-12 text-success display-4">
                                        <fmt:formatNumber value="${cardData.monthlyCost}" 
                                                          type="currency" currencySymbol="$"/>
                                    </div>
                                    <div class="col-12">
                                        <p class="text-center text-muted p-0 m-0">cost</p>
                                    </div>
                                    <div class="col-12 text-success display-4">
                                        <fmt:formatNumber value="${cardData.monthlyRevenue}" 
                                                          type="currency" currencySymbol="$"/>
                                    </div>
                                    <div class="col-12">
                                        <p class="text-center text-muted p-0 m-0">revenue</p>
                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="card border-primary mb-3" style="max-width: 20rem; height: 17rem">
                            <div class="card-header text-center" style="font-size: 1.2em">Yearly Statistic</div>
                            <div class="card-body d-flex justify-content-center align-items-center">
                                <div class="row text-center">
                                    <div class="col-12 text-success display-4">
                                        <fmt:formatNumber value="${cardData.yearlyCost}" 
                                                          type="currency" currencySymbol="$"/>
                                    </div>
                                    <div class="col-12">
                                        <p class="text-center text-muted p-0 m-0">cost</p>
                                    </div>
                                    <div class="col-12 text-success display-4">
                                        <fmt:formatNumber value="${cardData.yearlyRevenue}" 
                                                          type="currency" currencySymbol="$"/>
                                    </div>
                                    <div class="col-12">
                                        <p class="text-center text-muted p-0 m-0">revenue</p>
                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>

                </div>


                <div class="container mt-5">
                    <div class="row">
                        <div class="col">



                            <div id="chartContainer" class="mt-5" style="height: 370px; width: 100%;"></div>
                        </div>
                    </div>
                </div>
        </main>
        <%@include file="WEB-INF/jspf/footer.jspf" %>





        <script>

            const {currentYear, previousYear, monthlyRevenue, yearlyRevenue, monthlyCost, yearlyCost, jobsCountToday} = ${vm}


            console.log(${vm})

            const monthNames = ["January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
            ];


            const formatter = (monthNumValue, point) => {
                return (el) => {
                    if (el.start.date.month === monthNumValue) {
                        point.rev.y = el.totalRevenue
                        point.cost.y = el.totalCost
                    }
                    point.rev.y = point.rev.y ? point.rev.y : 0;
                    point.cost.y = point.cost.y ? point.cost.y : 0;
                }
            }


            const financesByMonth = (year, formatter) => {
                return (dataSet, month, i) => {
                    let point = {rev: {label: month}, cost: {label: month}};
                    year.forEach(formatter(i + 1, point))
                    dataSet.cost.push(point.cost)
                    dataSet.revenue.push(point.rev)
                    return dataSet
                }

            }


            const currentYearDataSet = monthNames.reduce(financesByMonth(currentYear, formatter), {cost: [], revenue: []})
            const previousYearDataSet = monthNames.reduce(financesByMonth(previousYear, formatter), {cost: [], revenue: []})



            var chart = new CanvasJS.Chart("chartContainer", {
                animationEnabled: true,
                title: {
                    text: "Yearly Statistic Breakdown By Month",
                    fontFamily: "Lato",
                    fontSize: 46
                },
                axisY: {
                    title: "$ Amount",
                    titleFontColor: "#4F81BC",
                    lineColor: "#4F81BC",
                    labelFontColor: "#4F81BC",
                    tickColor: "#4F81BC"
                },
//                axisY2: {
//                    title: "Millions of Barrels/day",
//                    titleFontColor: "#C0504E",
//                    lineColor: "#C0504E",
//                    labelFontColor: "#C0504E",
//                    tickColor: "#C0504E"
//                },
                toolTip: {
                    shared: true
                },
                legend: {
                    cursor: "pointer",
                    fontFamily: "Lato",
                    itemclick: toggleDataSeries

                },
                data: [{
                        type: "column",
                        name: "Cost Current Year ($/month)",
                        legendText: "Current Year Cost",
                        showInLegend: true,
                        dataPoints: currentYearDataSet.cost

                    },
                    {
                        type: "column",
                        name: "Revenue Current Year ($/month)",
                        legendText: "Current Year Revenue",
                        showInLegend: true,
                        dataPoints: currentYearDataSet.revenue
                    },
                    {
                        type: "column",
                        name: "Cost Previous Year  ($/month)",
                        legendText: "Previous Year Cost",
                        showInLegend: true,
                        dataPoints: previousYearDataSet.cost
                    },
                    {
                        type: "column",
                        name: "Revenue Previous Year ($/month)",
                        legendText: "Previous Year Revenue",
                        showInLegend: true,
                        dataPoints: previousYearDataSet.revenue
                    },
                ]
            });
            chart.render();

            function toggleDataSeries(e) {
                if (typeof (e.dataSeries.visible) === "undefined" || e.dataSeries.visible) {
                    e.dataSeries.visible = false;
                } else {
                    e.dataSeries.visible = true;
                }
                chart.render();
            }


        </script>
    </body>

</html>