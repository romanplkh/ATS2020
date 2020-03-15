package com.ats.atssystem.repository;

import com.ats.atssystem.models.EmployeeDTO;
import com.ats.atssystem.models.EmployeeDTOFactory;
import com.ats.atssystem.models.EmployeeFactory;
import com.ats.atssystem.models.IEmployee;
import com.ats.atssystem.models.IEmployeeDTO;
import com.ats.atssystem.models.ITask;
import com.ats.atssystem.models.ITeam;
import com.ats.atssystem.models.TaskFactory;
import com.ats.atssystem.models.TeamFactory;
import com.ats.dataaccess.*;
import com.sun.webkit.dom.XPathResultImpl;

import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

/**
 * @author Roman Pelikh
 */
public class EmployeeRepo extends BaseRepo implements IEmployeeRepo {

    private final String SPROC_INSERT_EMPLOYEE = "CALL spAddEmployee(?,?,?,?,?);";
    private final String SPROC_SELECT_EMPLOYEES = "CALL spGetEmployee(?);";
//    private final String SPROC_SELECT_EMPLOYEE = "CALL spGetEmployee(?);";
    private final String SPROC_GET_EMPLOYEE_FULL_DETAILS = "CALL spGetEmployeeDetails(?);";
    private final String SPROC_GET_EMPLOYEE_WITH_SKILLS = "CALL spGetEmployeeWithSkills(?);";
    private final String SPROC_REMOVE_EMPLOYEE = "CALL spRemoveEmployee(?, ?);";
    private final String SPROC_REMOVE_EMPLOYEE_SKILL = "CALL spRemoveEmployeeSkill(?,?,?);";

    //Dependancy of Dataaccess layer
    private IDAL dataAccess;

    public EmployeeRepo() {
        dataAccess = DALFactory.createInstance();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int addEmployee(IEmployee employee) {
        int returnedId = 0;

        //Get returned values and store them in list
        List<Object> returnedValues;

        List<IParameter> params = ParameterFactory.createListInstance();

        params.add(ParameterFactory.createInstance(employee.getFirstName()));
        params.add(ParameterFactory.createInstance(employee.getLastName()));
        params.add(ParameterFactory.createInstance(employee.getSin()));
        params.add(ParameterFactory.createInstance(employee.getHourlyRate()));

        //Get back id of inserted employee
        params.add(ParameterFactory.createInstance(returnedId, IParameter.Direction.OUT, Types.INTEGER));
        returnedValues = dataAccess.executeNonQuery(SPROC_INSERT_EMPLOYEE, params);

        try {
            if (returnedValues != null) {
                returnedId = Integer.parseInt(returnedValues.get(0).toString());
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return returnedId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int updateEmployee(IEmployee employee) {

        int rowsAffected = 0;

        return rowsAffected;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int deleteEmployee(int id) {
        int result = -1;

        List<Object> returnedValues;

        List<IParameter> params = ParameterFactory.createListInstance();

        params.add(ParameterFactory.createInstance(id));
        params.add(ParameterFactory.createInstance(result, IParameter.Direction.OUT, Types.INTEGER));
        returnedValues = this.dataAccess.executeNonQuery(SPROC_REMOVE_EMPLOYEE, params);

        try {
            if (returnedValues != null) {
                result = (int) returnedValues.get(0);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return result;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int deleteEmployeeSkill(int id, String skillIds) {

        int rowsAffected = 0;

        List<Object> returnedValues;

        List<IParameter> params = ParameterFactory.createListInstance();

        params.add(ParameterFactory.createInstance(id));
        params.add(ParameterFactory.createInstance(skillIds));
        
        params.add(ParameterFactory.createInstance(rowsAffected, IParameter.Direction.OUT, Types.INTEGER));
        returnedValues = this.dataAccess.executeNonQuery(SPROC_REMOVE_EMPLOYEE_SKILL, params);

        try {
            if (returnedValues != null) {
                rowsAffected = Integer.parseInt(returnedValues.get(0).toString());
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return rowsAffected;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IEmployee> retrieveEmployees() {

        List<IEmployee> employees = EmployeeFactory.createListInstance();
        List<IParameter> params = ParameterFactory.createListInstance();
        params.add(ParameterFactory.createInstance(null));

        try {
            CachedRowSet rs = this.dataAccess.executeFill(SPROC_SELECT_EMPLOYEES, params);
            employees = toListOfEmployees(rs);

        } catch (Exception e) {
            System.out.print(e.getMessage());
        }

        return employees;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IEmployee retrieveEmployee(int id) {
        IEmployee employee = EmployeeFactory.createInstance();

        try {
            List<IParameter> params = ParameterFactory.createListInstance();

            params.add(ParameterFactory.createInstance(id));

//            CachedRowSet rs = this.dataAccess.executeFill(this.SPROC_SELECT_EMPLOYEE, params);
            CachedRowSet rs = this.dataAccess.executeFill(this.SPROC_GET_EMPLOYEE_WITH_SKILLS, params);

            //UNKOMMENT IF ERRORS WITH GET EMPLOYEE
//            employee = mapPropsEmployeeToModel(rs);
            employee = populateEmployee(rs);

        } catch (Exception e) {
            System.out.print(e.getMessage());
        }

        return employee;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IEmployeeDTO retrieveEmployeeDetails(int id) {

        IEmployeeDTO employeeDetails = null;

        try {
            List<IParameter> params = ParameterFactory.createListInstance();

            params.add(ParameterFactory.createInstance(id));

            CachedRowSet rs = this.dataAccess.executeFill(this.SPROC_GET_EMPLOYEE_FULL_DETAILS, params);

            employeeDetails = populateEmployeeDTO(rs);

        } catch (Exception e) {
            System.out.print(e.getMessage());
        }

        return employeeDetails;

    }

    /**
     * Fills object of IEmployeeDTO with values
     *
     * @param rs CachedRowSet with values from database
     * @return employee information with all details
     * @throws SQLException
     */
    private IEmployeeDTO populateEmployeeDTO(CachedRowSet rs) throws SQLException {

        IEmployeeDTO employeeDetails = null;
        ITeam team = null;
        IEmployee employee = null;
        List<ITask> tasks = null;

        if (rs.next()) {
            employeeDetails = EmployeeDTOFactory.createInstance();
            team = TeamFactory.createInstance();
            employee = EmployeeFactory.createInstance();
            tasks = TaskFactory.createListInstance();

            employee.setId(super.getInt("id", rs));
            employee.setSin(rs.getString("sin"));
            employee.setHourlyRate(super.getDouble("hourlyRate", rs));
            employee.setFirstName(rs.getString("firstName"));
            employee.setLastName(rs.getString("lastName"));
            employee.setCreatedAt(super.getDate("createdAt", rs));
            employee.setUpdatedAt(super.getDate("updatedAt", rs));
            employee.setDeletedAt(super.getDate("deletedAt", rs));
            employee.setIsDeleted(rs.getBoolean("isDeleted"));

            if (rs.getString("Name") != null) {
                team.setName(rs.getString("Name"));
            } else {
                team.setName("");
            }

            //Populate employee skills (task)
            if (rs.getString("TaskName") != null) {
                ITask t = TaskFactory.createInstance();
                t.setName(rs.getString("TaskName"));
                tasks.add(t);

                while (rs.next() && rs.getString("TaskName") != null) {
                    ITask newTask = TaskFactory.createInstance();
                    newTask.setName(rs.getString("TaskName"));
                    tasks.add(newTask);
                }
            }

            employeeDetails.setSkills(tasks);
            employeeDetails.setEmployee(employee);
            employeeDetails.setTeam(team);
        }

        return employeeDetails;

    }

    /**
     *
     * @param rs CachedRowSet with values from database
     * @return employee information with skills
     * @throws SQLException
     */
    private IEmployee populateEmployee(CachedRowSet rs) throws SQLException {

        IEmployee employee = null;
        ITask skill = null;

        if (rs.next()) {
            employee = EmployeeFactory.createInstance();

            employee.setId(super.getInt("id", rs));
            employee.setSin(rs.getString("sin"));
            employee.setHourlyRate(super.getDouble("hourlyRate", rs));
            employee.setFirstName(rs.getString("firstName"));
            employee.setLastName(rs.getString("lastName"));
            employee.setCreatedAt(super.getDate("createdAt", rs));
            employee.setUpdatedAt(super.getDate("updatedAt", rs));
            employee.setDeletedAt(super.getDate("deletedAt", rs));
            employee.setIsDeleted(rs.getBoolean("isDeleted"));

            //Populate employee skills (task)
            if (rs.getString("skillId") != null) {
                skill = TaskFactory.createInstance();

                skill.setId(super.getInt("skillId", rs));
                skill.setName(rs.getString("name"));

                employee.getSkills().add(skill);

                while (rs.next() && rs.getString("skillId") != null) {
                    skill = TaskFactory.createInstance();
                    skill.setId(super.getInt("skillId", rs));
                    skill.setName(rs.getString("name"));
                    employee.getSkills().add(skill);
                }
            }
        }

        return employee;

    }

    /**
     * Maps properties of an employee stored in Database to the model IEmployee
     *
     * @param rs CachedRowSet with values from database
     * @return employee model with populated properties or null
     * @throws SQLException
     */
//    private IEmployee mapPropsEmployeeToModel(CachedRowSet rs) throws SQLException {
//        IEmployee employee = null;
//
//        if (rs.next()) {
//            employee = EmployeeFactory.createInstance();
//            employee.setId(super.getInt("id", rs));
//            employee.setSin(rs.getString("sin"));
//            employee.setHourlyRate(super.getDouble("hourlyRate", rs));
//            employee.setFirstName(rs.getString("firstName"));
//            employee.setLastName(rs.getString("lastName"));
//            employee.setCreatedAt(super.getDate("createdAt", rs));
//            employee.setUpdatedAt(super.getDate("updatedAt", rs));
//            employee.setDeletedAt(super.getDate("deletedAt", rs));
//            employee.setIsDeleted(rs.getBoolean("isDeleted"));
//        }
//
//        return employee;
//
//    }

    /**
     * Allows to map data from CachedRowSet to List of objects (employees)
     *
     * @param rs CachedRowSet with values from database
     * @return list of employees
     * @throws SQLException
     */
    private List<IEmployee> toListOfEmployees(CachedRowSet rs) throws SQLException {
        List<IEmployee> employees = EmployeeFactory.createListInstance();

        IEmployee employee;

        while (rs.next()) {
            employee = EmployeeFactory.createInstance();
            employee.setId(super.getInt("id", rs));
            employee.setSin(rs.getString("sin"));
            employee.setHourlyRate(super.getDouble("hourlyRate", rs));
            employee.setFirstName(rs.getString("firstName"));
            employee.setLastName(rs.getString("lastName"));
            employee.setCreatedAt(super.getDate("createdAt", rs));
            employee.setUpdatedAt(super.getDate("updatedAt", rs));
            employee.setDeletedAt(super.getDate("deletedAt", rs));
            employee.setIsDeleted(rs.getBoolean("isDeleted"));
            employees.add(employee);
        }

        return employees;

    }

}
