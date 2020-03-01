
package com.ats.atssystem.controllers;

import com.ats.atssystem.business.EmployeeServiceFactory;
import com.ats.atssystem.business.IEmployeeService;
import com.ats.atssystem.models.Employee;
import com.ats.atssystem.models.EmployeeDetailsViewModel;
import com.ats.atssystem.models.EmployeeFactory;
import com.ats.atssystem.models.ErrorViewModel;
import com.ats.atssystem.models.IEmployee;
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

        //GET URL ATTRIBUTES
        String pathInfo = request.getPathInfo();
        IEmployeeService employeeService = EmployeeServiceFactory.createInstance();

        if (pathInfo == null) {
            //Show all employees

            request.setAttribute("employees", employeeService.getEmployees());
            super.setView(request, EMPLOYEES_VIEW);

        } else {

            super.setView(request, EMPLOYEE_MAINT_VIEW);
            String[] pathParts = getUrlParts(pathInfo);

            // employee/:id - get ID
            int id = super.getInteger(pathParts[1]);
            String urlContext = getPathContext(pathParts, id);

            IEmployee employee = EmployeeFactory.createInstance();

            switch (urlContext) {
                case "update":
                    Employee queryEmployee = null;

                    break;
                case "details":
                    employee = employeeService.getEmployee(id);
                    EmployeeDetailsViewModel evm = new EmployeeDetailsViewModel();

                    if (employee != null) {
                        evm.setEmployee(employee);
                        request.setAttribute("evm", evm);
                    } else {
                        request.setAttribute("error", new ErrorViewModel(String.format("Employee ID: %s not found", id)));
                    }

                    super.setView(request, EMPLOYEE_DETAILS_VIEW);
                    break;
                default:
                    //Create
                    request.setAttribute("employee", employee);
            }

        }

        super.getView().forward(request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

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

    private void populateEmployeeModel(HttpServletRequest request, IEmployee emp) {

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
