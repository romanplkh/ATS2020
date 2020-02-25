package ats.controllers;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "EmployeesController")
public class EmployeesController extends CommonController {

    private static final String EMPLOYEES_VIEW = "/employees.jsp";
    private static final String EMPLOYEE_MAINT_VIEW = "/employee.jsp";


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
}
