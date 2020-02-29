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

    private static final String EMPLOYEES_VIEW = "/employees";
    private static final String EMPLOYEE_MAINT_VIEW = "/employee.jsp";
    private static final String EMPLOYEE_DETAILS_VIEW = "/employeeDetails.jsp";


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
        if (pathInfo == null) {


            //Get data from service


            //Show all employees
            request.setAttribute("employees", MockData.getEmployees());

            //Attach employees object to view

            super.setView(request, EMPLOYEES_VIEW);

        } else {


            String[] pathParts = getUrlParts(pathInfo);


            //Check update or details

            // employee/:id - get ID
            int id = getInteger(pathParts[1]);
            String mode = "";

            if (id != 0) {
                mode = "update";
            }

            if (pathParts.length == 3 && pathParts[2].equalsIgnoreCase("details") && id != 0) {
                mode = "details";
            }

            if (id == 0) {
                mode = "create";
            }


            switch (mode) {
                case "create":
                    super.setView(request, EMPLOYEE_MAINT_VIEW);
                    break;
                case "update":
                    //find employee

                    //Populate employee model with data

                    //Employee emp = data from db()

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
                    super.setView(request, EMPLOYEE_MAINT_VIEW);
                    break;
            }

        }


        super.getView().forward(request, response);

    }


    private String[] getUrlParts(String pathInfo) {
        return pathInfo.split("/");
    }

    private void populateEmployeeModel(HttpServletRequest request, Employee emp) {

        String firstName = super.getValue(request, "firstName");
        String lastName = super.getValue(request, "lastName");
        String sin = super.getValue(request, "sin");
        double hRate = super.getDouble(request, "hRate");


        if (firstName.trim().isEmpty()) {
            emp.addError("First Name is required");
        } else {
            emp.setFirstName(firstName);
        }

        if (lastName.trim().isEmpty()) {
            emp.addError("Last Name is required");
        } else {
            emp.setLastName(lastName);
        }

        String sinPattern = "\\d{3}-\\d{3}-\\d{3}";
        if (!sin.matches(sinPattern)) {
            emp.addError("SIN is invalid");
        } else {
            emp.setSin(sin);
        }

        if (hRate == 0 || hRate < 0) {
            emp.addError("Rate should be a valid number greater than zero");
        } else {
            emp.setHourlyRate(hRate);
        }


    }
}

