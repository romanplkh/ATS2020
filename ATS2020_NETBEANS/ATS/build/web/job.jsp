<%--
    Document   : job
    Created on : 10-Mar-2020, 7:51:03 PM
    Author     : Roman Pelikh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="WEB-INF/jspf/header.jspf" %>
        <title>Create Job</title>
    </head>
    <body>
        <%@include file="WEB-INF/jspf/navigation.jspf" %>
        <main>
            <div class="container my-5" >




                <div class="row mb-3">
                    <div class="col-12">
                        <h1 class="text-center display-4">Create Job</h1>
                    </div>
                </div>

                <div id="errorJs"></div>

                <c:if test="${jvm.job.errors.size() > 0}">
                    <div class="row justify-content-center">
                        <div class="col-12">
                            <div class="alert alert-danger alert-dismissible" role="alert">
                                <c:forEach items="${jvm.job.errors}" var="errVm">
                                    <p class="m-0 font-weight-bold">${errVm.description}</p>
                                </c:forEach>
                                <button type="button" class="close" 
                                        data-dismiss="alert" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                        </div>
                    </div>
                </c:if>

                <c:choose>
                    <c:when test="${error.errors != null}">
                        <div class="row justify-content-center">
                            <c:forEach items="${error.errors}" var="errVm">
                                <div class="alert alert-danger" role="alert">
                                    <p class="m-0 font-weight-bold">${errVm}</p>
                                </div>
                            </c:forEach>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <form method="post">
                            <div class="row mt-5">
                                <div class="col-md-6">
                                    <h3 class="text-center">Job Details</h1>
                                        <div class="form-group">
                                            <label>Client</label>
                                            <input type="text" class="form-control" name="client" 
                                                   value="${jvm.job.clientName}">
                                        </div>
                                        <div class="form-group">
                                            <label>Task</label>
                                            <div class="row ">
                                                <div class="col-md-9 py-1">
                                                    <select class="form-control" name="task" 
                                                            id="listTasks">                                                        
                                                        <c:forEach items="${jvm.tasks}" var="task">
                                                            <option value="${task.id}">${task.name}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                                <div class="col-md-3 py-1">
                                                    <button id="addTask" class="btn btn-link">Add</button></div>
                                            </div>
                                        </div>


                                        <div class="form-group">
                                            <label>Team</label>
                                            <select class="form-control" name="team">
                                                <c:forEach items="${jvm.teams}" var="team">
                                                    <option value="${team.id}" 
                                                            ${team.id == jvm.job.team.id ? "selected": ""} >${team.name}</option>
                                                </c:forEach>
                                            </select>
                                        </div>

                                        <div class="form-group">
                                            <label>Job Start Date</label>
                                            <input type="datetime-local"  class="form-control" 
                                                   name="startDate" value="${jvm.job.start}">
                                        </div>

                                        <div class="form-group">
                                            <label>Description</label>
                                            <textarea class="form-control" 
                                                      name="description" rows="3" style="resize: none">${jvm.job.description}</textarea>
                                        </div>

                                        <div class="form-group">
                                            <div class="form-check form-check-inline">
                                                <input class="form-check-input" name="emergency" 
                                                       type="checkbox"
                                                       ${jvm.job.isEmergency ? "checked" : ""}>
                                                <label class="form-check-label" for="defaultCheck1">
                                                    Emergency
                                                </label>
                                                <input class="form-check-input ml-4" name="onSite" 
                                                       ${jvm.job.isOnSite ? "checked" : ""}
                                                       type="checkbox">
                                                <label class="form-check-label" for="defaultCheck2">
                                                    On site
                                                </label>
                                            </div>
                                        </div>


                                        <input type="submit" value="Create" 
                                               class="btn btn-success btn-lg" name="action">

                                        <a href="${pageContext.request.contextPath}/employees" 
                                           class="btn btn-secondary btn-lg">Cancel</a>
                                </div>
                                <div class="col-md-6">
                                    <h3 class="text-center">Required Tasks</h1>
                                        <label></label>
                                        <p id="taskListPlaceHolder">No tasks added</p>
                                        <ul id="requiredTasks" class="list-group px-4">

                                        </ul>
                                        <input type="hidden" name="tasksToAdd" value="">

                                        </div>
                                        </div>
                                        </form>
                                    </c:otherwise>
                                </c:choose>
                                </div>
                                </main>



                                <%@include file="WEB-INF/jspf/footer.jspf" %>

                                <script>


                                    const btnAddTask = document.querySelector("#addTask").addEventListener("click", (ev) => {
                                        ev.preventDefault();
                                        addTask();
                                    })

                                    const ul = document.querySelector("#requiredTasks");
                                    const listTasks = document.querySelector("#listTasks")
                                    const errorJs = document.querySelector("#errorJs");
                                    const taskListPlaceHolder = document.querySelector("#taskListPlaceHolder");

                                    let listOfTaskIds = [];


                                    //REF TO INPUT WHERE TO RECORD ALL TASKS TO ADD
                                    const tasksToAdd = document.querySelector("input[name='tasksToAdd']");


                                    const hideElement = (selector) => {
                                        const element = document.querySelector(selector);
                                        if (element) {
                                            element.style.display = "none";
                                        }

                                    }

                                    const showElement = (selector) => {
                                        const element = document.querySelector(selector);
                                        if (element) {
                                            element.style.display = "block";
                                        }
                                    }

                                    const hideErrorMessage = () => {
                                        if (document.querySelector("#error-alert")) {
                                            errorJs.removeChild(document.querySelector("#error-alert"))
                                        }

                                    }

                                    const addTask = () => {
                                        const taskValue = parseInt(listTasks.value);
                                        const taskText = listTasks.options[listTasks.selectedIndex].text;

                                        if (listOfTaskIds.indexOf(taskValue) == -1) {
                                            hideErrorMessage();
                                            const li = document.createElement("li");
                                            const span = document.createElement("span");
                                            const input = document.createElement("input");

                                            span.addEventListener("click", () => {
                                                span.parentNode.remove();
                                                listOfTaskIds = listOfTaskIds.filter(el => el != input.value);

                                                if (listOfTaskIds.length == 0) {
                                                    showElement("#taskListPlaceHolder")
                                                }
                                            })

                                            li.classList.add("list-group-item");
                                            li.classList.add("list-group-item-action");
                                            li.classList.add("active");
                                            li.classList.add("my-1");
                                            li.classList.add("d-flex");
                                            li.classList.add("justify-content-between");
                                            li.classList.add("align-items-center");
                                            li.appendChild(document.createTextNode(taskText));

                                            span.appendChild(document.createTextNode("X"));
                                            span.classList.add("text-danger");
                                            span.style.cursor = "pointer";
                                            li.appendChild(span)


                                            input.setAttribute("name", "requiredTask");
                                            input.setAttribute("type", "hidden");
                                            input.setAttribute("value", taskValue);


                                            li.appendChild(input);

                                            ul.appendChild(li);

                                            hideElement("#taskListPlaceHolder")
                                            listOfTaskIds.push(taskValue)


                                            const taskId = parseInt(document.querySelector("#requiredTasks")
                                                    .lastElementChild.lastElementChild.value);

                                            if (tasksToAdd.value == "") {
                                                tasksToAdd.value = taskId;
                                            } else {
                                                tasksToAdd.value += "," + taskId;
                                            }


                                        } else {


                                            const alertMessage = `
                                                <div id="error-alert" class="alert alert-danger alert-dismissible fade show" role="alert">
                                                This task already exist in a list of required tasks
                                                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                                 <span aria-hidden="true">&times;</span>
                                                </button>
                                                    </div>
                                                `


                                            errorJs.insertAdjacentHTML("afterbegin", alertMessage)
                                        }




                                    }








                                </script>
                                </body>
                                </html>
