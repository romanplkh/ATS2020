<%--
    Document   : employeeSkills
    Created on : 10-Mar-2020, 10:04:12 PM
    Author     : Roman Pelikh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Employee Skills</title>
        <%@include file="WEB-INF/jspf/header.jspf" %>
    </head>
    <body>
        <%@include file="WEB-INF/jspf/navigation.jspf" %>
        <main>
            <div class="container my-5" id="errorJs">


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
                        <h1 class="text-center">Add Skill</h1>
                        <form method="post">
                            <div class="row mt-5">
                                <div class="col-md-6">
                                    <h3 class="text-center">Skills Management</h1>
                                        <div class="form-group w-80">
                                            <label>Employees</label>
                                            <select class="form-control" name="employee">
                                                <option>Employee 1</option>
                                                <c:forEach items="${jvm.employees}" var="emp">
                                                    <option value="${emp.id}">${emp.fullName}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <label>Skills</label>
                                            <div class="row no-gutters">
                                                <div class="col-md-11 py-1">
                                                    <select class="form-control" name="task" id="listTasks">
                                                        <option value="1">Skill 1</option>
                                                        <option value="2">Skill 2</option>
                                                        <option value="3">Skill 3</option>
                                                        <option value="4">Skill 4</option>
                                                        <c:forEach items="${jvm.tasks}" var="task">
                                                            <option value="${task.id}">${task.name}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                                <div class="col-md-1 pl-0 py-1 ml-0"><button id="addTask" class="btn btn-link">Add</button></div>
                                            </div>
                                        </div>





                                        <c:choose>
                                            <c:when test="${0 == 0}">
                                                <input type="submit" value="Create" class="btn btn-success btn-lg" name="action">
                                            </c:when>
                                            <c:otherwise>
                                                <input class="btn btn-danger btn-lg" type="submit" value="Delete" name="action"/>
                                                <input class="btn btn-warning btn-lg" type="submit" value="Update" name="action"/>
                                            </c:otherwise>
                                        </c:choose>
                                        <a href="${pageContext.request.contextPath}/employees" class="btn btn-secondary btn-lg">Cancel</a>
                                </div>
                                <div class="col-md-6">
                                    <h3 class="text-center">Added Skills</h1>
                                        <label></label>
                                        <p id="taskListPlaceHolder">No skills added</p>
                                        <ul id="requiredTasks" class="list-group px-4">
                                        </ul>
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
                        listOfTaskIds = listOfTaskIds.filter(el => el != input.value)

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




                } else {


                    const alertMessage = `
                        <div id="error-alert" class="alert alert-danger alert-dismissible fade show" role="alert">
                        This skill has already been selected to add
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
