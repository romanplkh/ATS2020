package com.ats.controllers;

import com.ats.controllers.CommonController;
import com.ats.models.Employee;
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


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            ///WHERE TO REDIRECT AFTER SUCCESSFUL POST
            super.setView(request, EMPLOYEES_VIEW);

            //Get action of button
            String action = super.getValue(request, "action").toLowerCase();
            Employee emp = new Employee();
            int empId = super.getInteger(request, "empId");


            switch (action) {
                case "create":
                    populateEmployeeModel(request, emp);

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

            //Show all employees


            //Attach employees object to view

            super.setView(request, EMPLOYEES_VIEW);

        } else {


            String[] pathParts = pathInfo.split("/");

            // employee/:id - get ID
            int id = getInteger(pathParts[1]);


//            if (id != 0) {
//
//                if (employee founded in db) {
//                    //find employee
//                    throw new UnsupportedOperationException("Not supported yet");
//                } else {
//                    //Set error message to view Em,ployee not found
//                    throw new UnsupportedOperationException("Not supported yet");
//                }
//
//
//            } else {
//
//                //If not a valid number -> THEN CREATE
//                throw new UnsupportedOperationException("Not supported yet");
//
//            }

            super.setView(request, EMPLOYEE_MAINT_VIEW);


        }


        super.getView().forward(request, response);

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

