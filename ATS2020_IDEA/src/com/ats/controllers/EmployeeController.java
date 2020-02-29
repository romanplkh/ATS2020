package com.ats.controllers;

import com.ats.MockData;
import com.ats.controllers.CommonController;
import com.ats.models.Employee;
import com.ats.models.EmployeeDetailsViewModel;
import com.ats.models.ErrorViewModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "EmployeeController")
public class EmployeeController extends CommonController {

    private static final String EMPLOYEES_VIEW = "/employees.jsp";
    private static final String EMPLOYEE_MAINT_VIEW = "/employee.jsp";
    private static final String EMPLOYEE_DETAILS_VIEW = "/employeeDetails.jsp";
    private static final String PAGE_404 = "/404.jsp";


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            ///WHERE TO REDIRECT AFTER SUCCESSFUL POST
            super.setView(request, EMPLOYEES_VIEW);

            //Get action of button
            String action = super.getValue(request, "action").toLowerCase();
            Employee emp = new Employee();
            int empId = super.getInteger(request, "empId");


            switch (action) {
                case "save":
                    populateEmployeeModel(request, emp);
                    if (emp.getErrors().size() > 0) {
                        request.setAttribute("modelErrors", emp.getErrors());
                        request.setAttribute("employee", emp);
//                        request.setAttribute("employees", MockData.getEmployees());
                        super.setView(request, EMPLOYEE_MAINT_VIEW);
                    } else {
                        super.setView(request, EMPLOYEES_VIEW);
                    }


                    //Add employee to DB

                    //If rows affected == 0
                    //request.setAttribute("error", new ErrorViewModel("Something went wrong. Employee is not added"));
                    //super.setView(request, EMPLOYEE_MAINT_VIEW);

                    break;
                case "update":
                    break;
                case "delete":
                    break;


            }
        } catch (Exception e) {
            super.setView(request, EMPLOYEE_MAINT_VIEW);
            request.setAttribute("error", new ErrorViewModel("Something bad happened when attempting to maintain employee"));
        }

        //REDIRECT TO VIEW
        super.getView().forward(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //GET URL ATTRIBUTES
        String pathInfo = request.getPathInfo();


        //IF WE DO NOT HAVE ANYTHING IN URL
        //Show all employees
        if (pathInfo == null) {


            //Get data from service


            //Show all employees
            request.setAttribute("employees", MockData.getEmployees());

            //Attach employees object to view

            super.setView(request, EMPLOYEES_VIEW);

        } else {


            Employee emp = new Employee();
            String[] pathParts = getUrlParts(pathInfo);


            //Check update or details

            // employee/:id - get ID
            int id = getInteger(pathParts[1]);
            String urlContext = getPathContext(pathParts, id);


            switch (urlContext) {
                case "create":

                    //Add employee to DB



                    request.setAttribute("employee", emp);
                    super.setView(request, EMPLOYEE_MAINT_VIEW);
                    break;
                case "update":
                    //find employee


                    Employee queryEmployee = null;

                    //Try get employee from DB
//                    if (queryEmployee != null) {
//
//                        //Set book to view model;
//                        emp = queryEmployee;
//
//
//                        //Return view model with a view
//                        request.setAttribute("employee", emp);
//
//                    } else {
//                        //Set error message to view
////                        request.setAttribute("error",
////                                new ErrorViewModel("Book with ID: " + id + " not found!"));
//
//                    }


                    //REmove it after test
                    emp.setId(1);

                    request.setAttribute("employee", emp);
                    super.setView(request, EMPLOYEE_MAINT_VIEW);
                    break;
                case "details":
                    EmployeeDetailsViewModel evm = new EmployeeDetailsViewModel();
                    //Get employee from db by id

                    //Populate employee model with data

                    //Employee emp = data from db()


                    request.setAttribute("employeeDetailsVM", evm);
                    super.setView(request, EMPLOYEE_DETAILS_VIEW);
                    break;

                default:
                    request.setAttribute("error", new ErrorViewModel("Employee with this id does not exists"));
                    super.setView(request, PAGE_404);
                    break;
            }

        }


        super.getView().forward(request, response);

    }

    private String getPathContext(String[] pathParts, int id) {

        String mode = "";

        if (id != 0) {
            mode = "update";

            //employee/:id/details
            if (pathParts.length == 3 && pathParts[2].equalsIgnoreCase("details")) {
                mode = "details";
            }

        } else if (pathParts[1].equalsIgnoreCase("create")) {
            mode = "create";
        }

        return mode;
    }


    private String[] getUrlParts(String pathInfo) {
        return pathInfo.split("/");
    }

    private void populateEmployeeModel(HttpServletRequest request, Employee emp) {

        String firstName = super.getValue(request, "firstName");
        String lastName = super.getValue(request, "lastName");
        String sin = super.getValue(request, "sin");
        double hRate = super.getDouble(request, "hRate");

        emp.setFirstName(firstName);
        emp.setLastName(lastName);
        emp.setSin(sin);
        emp.setHourlyRate(hRate);


    }
}

