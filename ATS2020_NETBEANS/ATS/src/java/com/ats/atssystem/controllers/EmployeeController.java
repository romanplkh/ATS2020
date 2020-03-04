package com.ats.atssystem.controllers;

import com.ats.atssystem.business.EmployeeServiceFactory;
import com.ats.atssystem.business.IEmployeeService;
import com.ats.atssystem.models.Employee;
import com.ats.atssystem.models.EmployeeDTO;
import com.ats.atssystem.models.EmployeeDTOFactory;
import com.ats.atssystem.models.EmployeeFactory;
import com.ats.atssystem.models.ErrorFactory;
import com.ats.atssystem.models.ErrorViewModel;
import com.ats.atssystem.models.IEmployee;
import com.ats.atssystem.models.IEmployeeDTO;
import com.ats.atssystem.models.ITeam;
import com.ats.atssystem.models.TeamFactory;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Roman Pelikh
 */
public class EmployeeController extends CommonController {

    private static final String EMPLOYEES_VIEW = "/employees.jsp";
    private static final String EMPLOYEE_MAINT_VIEW = "/employee.jsp";
    private static final String EMPLOYEE_DETAILS_VIEW = "/employeeDetails.jsp";
    private static final String PAGE_404 = "/404.jsp";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        IEmployeeService employeeService = EmployeeServiceFactory.createInstance();

        if (pathInfo == null) {
            //Show all employees
            request.setAttribute("employees", employeeService.getEmployees());
            super.setView(request, EMPLOYEES_VIEW);
        } else {
            //// employee/:id/[details : update]

            IEmployee employee = EmployeeFactory.createInstance();

            String[] pathParts = super.getUrlParts(pathInfo);

            // employee/:id - get ID
            int id = super.getInteger(pathParts[1]);

            if (id != 0) {

                //Update OR Details  
                String urlContext = pathParts[2].toLowerCase();

                employee = employeeService.getEmployee(id);

                if (employee != null) {
                    switch (urlContext) {
                        case "update":
                            request.setAttribute("employee", employee);
                            super.setView(request, EMPLOYEE_MAINT_VIEW);
                            break;
                        case "details":
                            IEmployeeDTO employeeDetails = EmployeeDTOFactory.createInstance();

                            employeeDetails = employeeService.getEmployeeDetails(id);

                            request.setAttribute("empDetails", employeeDetails);
                            super.setView(request, EMPLOYEE_DETAILS_VIEW);
                            break;
                    }

                } else {
                    request.setAttribute("error", new ErrorViewModel(String.format("Employee ID: %s not found", id)));
                    super.setView(request, EMPLOYEE_MAINT_VIEW);
                }

            } else {
                //Create
                request.setAttribute("employee", employee);
                super.setView(request, EMPLOYEE_MAINT_VIEW);
            }

        }

        super.getView().forward(request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        IEmployeeService employeeService = EmployeeServiceFactory.createInstance();
        IEmployee emp = EmployeeFactory.createInstance();

        try {
            //Get action of button
            String action = super.getValue(request, "action").toLowerCase();

            //Get hidden id
            int empId = super.getInteger(request, "empId");

            switch (action) {
                case "create":
                    //Populate employee object
                    emp = populateEmployeeModel(request);

                    if (!employeeService.isValid(emp)) {
                        request.setAttribute("modelErrors", emp.getErrors());
                        request.setAttribute("employee", emp);
                        super.setView(request, EMPLOYEE_MAINT_VIEW);
                    } else {
                        emp = employeeService.createEmployee(emp);
                    }
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

        if (!employeeService.isValid(emp)) {
            super.getView().forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + extractNameFromJSP());
        }

    }

    /**
     * Extracts name from jsp file without extension
     *
     * @return name of a file without extention
     */
    private static String extractNameFromJSP() {
        return EMPLOYEES_VIEW.substring(0, EMPLOYEES_VIEW.indexOf("."));
    }

    /**
     * Gets values from form and creates instance of employee based on input values
     *
     * @param request HttpServletRequest
     * @return instance of employee with populated values
     */
    private IEmployee populateEmployeeModel(HttpServletRequest request) {

        String firstName = super.getValue(request, "firstName");
        String lastName = super.getValue(request, "lastName");
        String sin = super.getValue(request, "sin");
        double hRate = super.getDouble(request, "hRate");

        IEmployee emp = EmployeeFactory.createInstance(firstName, lastName, sin, hRate);

        return emp;

    }

}
