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

            <h2 class="display-4 text-center my-5">Welcome to ATS!</h2>

            <div class="container">

                <div class="row">
                    <div class="col-md-3">
                        <div class="card border-primary mb-3 " style="max-width: 20rem; min-height: 16rem">
                            <div class="card-header text-center font-weight-bold">Current Jobs</div>
                            <div class="card-body d-flex justify-content-center align-items-center">
                                <h1 class=" display-4 card-title text-success">${cardData.jobsCountToday}</h2>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="card border-primary mb-3" style="max-width: 20rem; min-height: 16rem">
                            <div class="card-header text-center font-weight-bold">Team On Call</div>
                            <div class="card-body">
                                <h2 class="card-title text-center">${cardData.teamOnCall.name}</h2>
                                <ul class="list-group list-group-flush">
                                    <c:forEach items="${cardData.teamOnCall.teamMembers}" var="member">
                                        <li class="list-group-item text-muted text-center">${member.fullName}</li>
                                        </c:forEach>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="card border-primary mb-3" style="max-width: 20rem; min-height: 16rem">
                            <div class="card-header text-center font-weight-bold">Monthly Finances</div>
                            <div class="card-body d-flex justify-content-center align-items-center">
                                <ul class="list-group list-group-flush"> 
                                    <span class="text-success display-4 font-weight-bold"><fmt:formatNumber value="${cardData.monthlyRevenue}" type="currency" currencySymbol="$"/></span> Revenue: 
                                    <span class="text-success display-4 font-weight-bold"><fmt:formatNumber value="${cardData.monthlyCost}" type="currency" currencySymbol="$"/> </span>Cost: 
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="card border-primary mb-3" style="max-width: 20rem; min-height: 16rem">
                            <div class="card-header text-center font-weight-bold">Yearly Finances</div>
                            <div class="card-body d-flex justify-content-center align-items-center">
                                <ul class="list-group list-group-flush"> 
                                    <li class="list-group-item  text-center font-weight-bold">Revenue:  <span class="text-success font-weight-bold"><fmt:formatNumber value="${cardData.yearlyRevenue}" type="currency" currencySymbol="$"/></span>  </li>
                                    <li class="list-group-item  text-center font-weight-bold">Cost:  <span class="text-success font-weight-bold"><fmt:formatNumber value="${cardData.yearlyCost}" type="currency" currencySymbol="$"/></span>  </li>
                                </ul>
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
                    text: "Yearly Revenue"
                },
                axisY: {
                    title: "$ Amount",
                    titleFontColor: "#4F81BC",
                    lineColor: "#4F81BC",
                    labelFontColor: "#4F81BC",
                    tickColor: "#4F81BC"
                },
                axisY2: {
                    title: "Millions of Barrels/day",
                    titleFontColor: "#C0504E",
                    lineColor: "#C0504E",
                    labelFontColor: "#C0504E",
                    tickColor: "#C0504E"
                },
                toolTip: {
                    shared: true
                },
                legend: {
                    cursor: "pointer",
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
                        name: "Cost Prevoious Year  ($/month)",
                        legendText: "Prevoious Year Cost",
                        showInLegend: true,
                        dataPoints: previousYearDataSet.cost
                    },
                    {
                        type: "column",
                        name: "Revenue Prevoious Year ($/month)",
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