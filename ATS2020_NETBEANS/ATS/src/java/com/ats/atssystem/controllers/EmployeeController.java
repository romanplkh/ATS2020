package com.ats.atssystem.controllers;

import com.ats.atssystem.business.EmployeeServiceFactory;
import com.ats.atssystem.business.IEmployeeService;
import com.ats.atssystem.business.TaskServiceFactory;
import com.ats.atssystem.models.EmployeeDTOFactory;
import com.ats.atssystem.models.EmployeeFactory;
import com.ats.atssystem.models.EmployeeSkillsViewModel;
import com.ats.atssystem.models.ErrorFactory;
import com.ats.atssystem.models.ErrorViewModel;
import com.ats.atssystem.models.IEmployee;
import com.ats.atssystem.models.IEmployeeDTO;
import java.io.IOException;
import java.util.List;
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
    private static final String EMPLOYEE_SKILLS_MANAGEMENT = "/employeeSkills.jsp";
    private static final String PAGE_404 = "/404.jsp";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        IEmployeeService employeeService = EmployeeServiceFactory.createInstance();

        if (pathInfo == null) {

            //Show all employees
            String search = super.getValue(request, "search");

            if (search == null) {
                request.setAttribute("employees", employeeService.getEmployees());
            } else {
                request.setAttribute("employees", employeeService.getEmployees(search));
                request.setAttribute("search", search);
            }

            super.setView(request, EMPLOYEES_VIEW);
        } else {
            //// employee/:id/[details : update : skills]
            IEmployee employee = EmployeeFactory.createInstance();

            String[] pathParts = super.getUrlParts(pathInfo);

            // employee/:id - get ID
            int id = super.getInteger(pathParts[1]);

            if (id != 0) {

                //Update, Details or Skills
                String urlContext = pathParts[2];

                employee = employeeService.getEmployee(id);

                if (employee != null) {
                    switch (urlContext.toLowerCase()) {
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
                        case "skills":
                            EmployeeSkillsViewModel evm = new EmployeeSkillsViewModel();
                            evm.setEmployee(employee);
                            evm.setTasks(TaskServiceFactory.createInstance().getAllTasks());
                            request.setAttribute("evm", evm);
                            super.setView(request, EMPLOYEE_SKILLS_MANAGEMENT);
                    }

                } else {
                    request.setAttribute("vmError", new ErrorViewModel(String.format("Employee ID: %s not found", id)));
                    super.setView(request, EMPLOYEE_MAINT_VIEW);
                }

            } else {
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
        List<IEmployee> employees = EmployeeFactory.createListInstance();

        try {
            //Get action of button
            String action = super.getValue(request, "action");

            //Get hidden id
            switch (action.toLowerCase()) {
                case "create":
                    //Populate employee object
                    emp = populateEmployeeModel(request);

                    if (!employeeService.isValid(emp)) {
                        request.setAttribute("employeeErrors", emp.getErrors());
                        request.setAttribute("employee", emp);
                        super.setView(request, EMPLOYEE_MAINT_VIEW);
                    } else {
                        emp = employeeService.createEmployee(emp);
                    }
                    break;
                case "update":
                    break;
                case "delete":
                    emp = employeeService.deleteEmployee(populateEmployeeModel(request));

                    if (!emp.getErrors().isEmpty()) {
                        request.setAttribute("employee", emp);
                        request.setAttribute("employeeErrors", emp.getErrors());
                        super.setView(request, EMPLOYEE_MAINT_VIEW);
                    }

                    break;

                case "update skills":
                    //HERE WE WILL ADD AND REMOVE SKILLS 
                    int employeeId = super.getInteger(request, "employeeId");
                    emp = employeeService.getEmployee(employeeId);

                    //VALIDATE ONE MORE TIME IF REQUEST WILL BE MADE NOT OVER UI
                    if (emp != null) {

                        if (skillsManagementActionEquals(request) == "delete") {
                            //TRY DELETE SKILLS
                            String skillsToDelete = super.getValue(request, "skillsToDelete");
                            int result = employeeService.deleteEmployeeSkill(employeeId, skillsToDelete);

                            if (result == 0) {
                                EmployeeSkillsViewModel evm = new EmployeeSkillsViewModel();
                                emp.addError(ErrorFactory.createInstance(1, "Something went wrong during delete skills"));
                                evm.setEmployee(emp);
                                evm.setTasks(TaskServiceFactory.createInstance().getAllTasks());
                                request.setAttribute("evm", evm);
                            }
                        } else if ("add".equals(skillsManagementActionEquals(request))) {
                            //Add SKILLS
                            String skills = super.getValue(request, "skillsToAdd");

                            int result = employeeService
                                    .addEmployeeSkill(employeeId, skills);

                            if (result == 0) {
                                EmployeeSkillsViewModel evm = new EmployeeSkillsViewModel();
                                emp.addError(ErrorFactory
                                        .createInstance(1, "Something went wrong during adding skills"));
                                evm.setEmployee(emp);
                                evm.setTasks(TaskServiceFactory.createInstance().getAllTasks());
                                request.setAttribute("evm", evm);

                            }

                        } else if ("addDelete".equals(skillsManagementActionEquals(request))) {

                            String skillsToDelete = super.getValue(request, "skillsToDelete");
                            String skillsToAdd = super.getValue(request, "skillsToAdd");

                            int result = employeeService
                                    .updateEmployeeSkills(employeeId, skillsToDelete, skillsToAdd);

                            if (result == 0) {
                                EmployeeSkillsViewModel evm = new EmployeeSkillsViewModel();
                                emp.addError(ErrorFactory
                                        .createInstance(1, "Something went wrong during updating skills"));
                                evm.setEmployee(emp);
                                evm.setTasks(TaskServiceFactory.createInstance().getAllTasks());
                                request.setAttribute("evm", evm);

                            }
                        } else {
                            EmployeeSkillsViewModel evm = new EmployeeSkillsViewModel();
                            emp.addError(ErrorFactory.createInstance(2, "Please add/remove skills you want to be deleted from employee"));
                            evm.setEmployee(emp);
                            evm.setTasks(TaskServiceFactory.createInstance().getAllTasks());
                            request.setAttribute("evm", evm);
                        }

                    } else {
                        request.setAttribute("errorVM", new ErrorViewModel("Employee with this ID does not exist"));
                    }
                    break;

                case "add skills":
                    int empId = super.getInteger(request, "employeeId");
                    emp = employeeService.getEmployee(empId);
                    if (skillsManagementActionEquals(request) == "add") {
                        //TRY Add SKILLS
                        String skills = super.getValue(request, "skillsToAdd");

                        int result = employeeService
                                .addEmployeeSkill(empId, skills);

                        if (result == 0) {
                            EmployeeSkillsViewModel evm = new EmployeeSkillsViewModel();
                            emp.addError(ErrorFactory
                                    .createInstance(1, "Something went wrong while adding skills"));
                            evm.setEmployee(emp);
                            evm.setTasks(TaskServiceFactory.createInstance().getAllTasks());
                            request.setAttribute("evm", evm);

                        }

                    }
                    break;
            }

        } catch (Exception e) {
            super.setView(request, EMPLOYEE_MAINT_VIEW);
            request.setAttribute("vmError",
                    new ErrorViewModel("Something bad happened when attempting to maintain employee"));
        }

        if (!employeeService.isValid(emp)) {
            super.getView().forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + extractNameFromJSP());

        }

    }

    private String skillsManagementActionEquals(HttpServletRequest request) {
        String action = "";

        if (!super.getValue(request, "skillsToDelete").isEmpty() && super.getValue(request, "skillsToAdd").isEmpty()) {
            action = "delete";
        } else if (super.getValue(request, "skillsToDelete").isEmpty() && !super.getValue(request, "skillsToAdd").isEmpty()) {
            action = "add";
        } else if (!super.getValue(request, "skillsToDelete").isEmpty() && !super.getValue(request, "skillsToAdd").isEmpty()) {
            action = "addDelete";
        }

        return action;
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
     * Gets values from form and creates instance of employee based on input
     * values
     *
     * @param request HttpServletRequest
     * @return instance of employee with populated values
     */
    private IEmployee populateEmployeeModel(HttpServletRequest request) {

        String firstName = super.getValue(request, "firstName");
        String lastName = super.getValue(request, "lastName");
        String sin = super.getValue(request, "sin");
        double hRate = super.getDouble(request, "hRate");
        int id = super.getInteger(request, "empId");

        IEmployee emp = EmployeeFactory.createInstance(firstName, lastName, sin, hRate);
        emp.setId(id);

        return emp;

    }

}
