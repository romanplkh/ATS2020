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
        <title>Employee's Skills</title>
        <%@include file="WEB-INF/jspf/header.jspf" %>
    </head>
    <noscript>
    <!-- anchor linking to external file -->
    <style>
        #noJS{
            display: none;
        }

        #noJSMessage{
            display: block;
        }
    </style>

    <h1 id="noJSMessage" class="display-4 p-5">You do not have Java Script enabled in 2020! Good luck with that.</h1>
    </noscript>
    <body>


        <%@include file="WEB-INF/jspf/navigation.jspf" %>


        <main id="noJS">



            <div class="container my-5" id="errorJs">
                <c:if test="${evm.employee.errors.size() > 0}">
                    <div class="row justify-content-center">
                        <c:forEach items="${evm.employee.errors}" var="errVm">
                            <div class="alert alert-danger" role="alert">
                                <p class="m-0 font-weight-bold">${errVm.description}</p>
                            </div>
                        </c:forEach>
                    </div>
                </c:if>

                <c:choose>
                    <c:when test="${errorsVM.errors != null}">
                        <div class="row justify-content-center">
                            <c:forEach items="${errorsVM.errors}" var="errVm">
                                <div class="alert alert-danger" role="alert">
                                    <p class="m-0 font-weight-bold">${errVm}</p>
                                </div>
                            </c:forEach>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <h1 class="text-center">Skill Management</h1>
                        <form method="post">
                            <div class="row mt-5">
                                <div class="col-md-6">

                                    <div class="form-group">
                                        <h3 class="text-center">Employee Details</h1>
                                            <div class="card" style="width: 100%;">
                                                <div class="card-body">
                                                    <input type="hidden" name="employeeId" value="${evm.employee.id}">
                                                    <ul class="list-group list-group-flush">
                                                        <li class="list-group-item"><span class="font-weight-bold">
                                                                First Name: </span>&nbsp; ${evm.employee.firstName}
                                                        </li>
                                                        <li class="list-group-item"><span class="font-weight-bold">
                                                                Last Name: </span>&nbsp; ${evm.employee.lastName}
                                                        </li>
                                                    </ul>
                                                </div>
                                            </div>
                                    </div>


                                    <div class="form-group">
                                        <h3 class="text-center">Skills</h3>
                                        <div class="card" style="width: 100%;">

                                            <div class="card-body">
                                                <div class="row no-gutters">
                                                    <div class="col-md-11 py-1">
                                                        <select class="form-control" name="task" id="listTasks">
                                                            <c:forEach items="${evm.tasks}" var="task">
                                                                <option value="${task.id}">${task.name}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                    <div class="col-md-1 pl-0 py-1 ml-0">
                                                        <button id="addTask" class="btn btn-link">Add</button></div>
                                                </div>
                                            </div>

                                        </div>


                                    </div>

                                </div>
                                <div class="col-md-6">
                                    <h3 class="text-center">Employee Skills</h3>
                                    <p id="taskListPlaceHolder">No skills added</p>
                                    <ul id="requiredTasks" class="list-group px-4">
                                        <c:if test="${evm.employee.skills.size() > 0}">
                                            <c:forEach items="${evm.employee.skills}" var="skill">
                                                <li class="list-group-item list-group-item-action active my-1
                                                    d-flex justify-content-between align-items-center ">
                                                    ${skill.name}
                                                    <span class="text-danger employeeSkillRemove" style="cursor: pointer;">
                                                        X
                                                    </span><input type="hidden" value="${skill.id}">
                                                </li>
                                            </c:forEach>
                                        </c:if>
                                    </ul>
                                </div>
                            </div>
                            <input type="hidden" name="skillsToDelete" value="">
                            <input type="hidden" name="skillsToAdd" value="">

                            <div class="row justify-content-center">

                                <c:choose>
                                    <c:when test="${evm.employee.skills.size() == 0}">
                                        <input type="submit" value="Add Skills" 
                                               class="btn btn-success btn-lg" name="action">
                                    </c:when>
                                    <c:otherwise>
                                        <input class="btn btn-warning btn-lg" 
                                               type="submit" value="Update Skills" name="action"/>
                                    </c:otherwise>
                                </c:choose>
                                <a href="${pageContext.request.contextPath}/employees" 
                                   class="btn btn-secondary btn-lg ml-2">Cancel</a>

                            </div>

                        </form>
                    </c:otherwise>
                </c:choose>
            </div>
        </main>



        <%@include file="WEB-INF/jspf/footer.jspf" %>

        <script>

            let listOfTaskIds = [];

            let addedTasks = [].concat(listOfTaskIds);

            const updateBtn = document.querySelector("input[value='Update Skills']");



            //On load set disabled
            setEnabled(updateBtn, false)


            //REF TO INPUT WHERE TO RECORD ALL SKILLS TO REMOVE
            const skillsToDelete = document.querySelector("input[name='skillsToDelete']");

            //REF TO INPUT WHERE TO RECORD ALL SKILLS TO ADD
            const skillsToAdd = document.querySelector("input[name='skillsToAdd']");

            //DATA FROM DB
            const empSkills = document.querySelectorAll(".employeeSkillRemove");


            //Add to hidden input skills we want to delete so we can pass them to controller
            const removeSkill = (el) => {
                const taskId = parseInt(el.parentNode.lastElementChild.value);

                if (listOfTaskIds.indexOf(taskId) != -1) {
                    if (!skillsToDelete.value) {
                        skillsToDelete.value += taskId;
                    } else {
                        skillsToDelete.value += "," + taskId;
                    }
                }



                listOfTaskIds = listOfTaskIds.filter(el => el != taskId)
                el.parentNode.remove();

                if (listOfTaskIds.length == 0) {
                    showElement("#taskListPlaceHolder")
                }

                //Enabled button Submit
                if (updateBtn.hasAttribute("disabled")) {
                    setEnabled(updateBtn, true)
                }
            }



            //ATTACH LISTENERS ON ELEMENTS FROM DB
            Array.from(empSkills).forEach(el => {
                listOfTaskIds.push(parseInt(el.nextSibling.value));
                el.addEventListener("click", (ev) => {

                    removeSkill(el)
                })
            })

            if (listOfTaskIds.length > 0) {
                hideElement("#taskListPlaceHolder")
            }




            // ----------------------------HERE WE MESS AROUND WITH UI---------------
            //------ ADD TASK FROM UI

            //ELEMENT REFERENCES
            const ul = document.querySelector("#requiredTasks");
            const listTasks = document.querySelector("#listTasks")
            const errorJs = document.querySelector("#errorJs");
            const taskListPlaceHolder = document.querySelector("#taskListPlaceHolder");


            //ADD SKILL LISTENER
            const btnAddTask = document.querySelector("#addTask").addEventListener("click", (ev) => {
                ev.preventDefault();
                addTask();
            })



            const addTask = () => {

                //CHECK IF BUTTON IS DISABLED
                if (updateBtn.hasAttribute("disabled")) {
                    setEnabled(updateBtn, true)

                }


                //GET VALUE FROM SELECT MENU
                const taskValue = parseInt(listTasks.value);
                const taskText = listTasks.options[listTasks.selectedIndex].text;

                const taskId = parseInt(el.parentNode.lastElementChild.value);


                //CHECH IF SKILL WAS NOT ALREADY ADDED
                if (addedTasks.indexOf(taskValue) == -1) {
                    hideErrorMessage();


                    //BUILD UI ELEMENT WITH SKILL
                    const li = document.createElement("li");
                    const span = document.createElement("span");
                    const input = document.createElement("input");

                    //ADD LISTENER TO REMOVE TASK
                    span.addEventListener("click", () => removeSkill(span));

                    //ADD BANCH OF CLASSES :-)
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
                    li.appendChild(span);

                    //BUILD HIDDEN INPUT THAT WILL HOLD THE VALUE ID OF ADDED SKILL
                    input.setAttribute("name", "requiredTask");
                    input.setAttribute("type", "hidden");
                    input.setAttribute("value", taskValue);


                    li.appendChild(input);
                    ul.appendChild(li);


                    //IF HAS AT LEAST ONE SKILL HIDE MESSAGE THAT NO SKILLS
                    hideElement("#taskListPlaceHolder");

                    //ADD CREATED TASK ID TO ARRRAY OF IDS
                    addedTasks.push(taskValue);

                } else {


                    //LAZY WAY TO BUILD ERROR MESSAGE
                    const alertMessage = `
                        <div id="error-alert" class="alert alert-danger alert-dismissible fade show" role="alert">
                        This skill is already present
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                         <span aria-hidden="true">&times;</span>
                        </button>
                            </div>
                        `;


                    //WHERE TO ADD IN DOM ERROR MESSAGE
                    errorJs.insertAdjacentHTML("afterbegin", alertMessage);
                }
                
                
            };



            function setEnabled(element, enabled) {
                enabled ? element.removeAttribute("disabled") : element.setAttribute("disabled", !enabled);
                element.style.cursor = enabled ? "pointer" : "not-allowed";

            };

            function  hideElement(selector) {
                const element = document.querySelector(selector);
                if (element) {
                    element.style.display = "none";
                }

            }

            function showElement(selector) {
                const element = document.querySelector(selector);
                if (element) {
                    element.style.display = "block";
                }
            }

            function hideErrorMessage() {
                if (document.querySelector("#error-alert")) {
                    errorJs.removeChild(document.querySelector("#error-alert"))
                }

            }</script>
    </body>
</html>
