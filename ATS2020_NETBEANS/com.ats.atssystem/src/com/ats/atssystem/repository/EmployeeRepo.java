package com.ats.atssystem.repository;

import com.ats.atssystem.models.EmployeeDTO;
import com.ats.atssystem.models.EmployeeDTOFactory;
import com.ats.atssystem.models.EmployeeFactory;
import com.ats.atssystem.models.IEmployee;
import com.ats.atssystem.models.IEmployeeDTO;
import com.ats.atssystem.models.ITeam;
import com.ats.atssystem.models.TeamFactory;
import com.ats.dataaccess.*;

import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

public class EmployeeRepo extends BaseRepo implements IEmployeeRepo {

    private final String SPROC_INSERT_EMPLOYEE = "CALL spAddEmployee(?,?,?,?,?);";
    private final String SPROC_SELECT_EMPLOYEES = "CALL spGetEmployee(?);";
    private final String SPROC_SELECT_EMPLOYEE = "CALL spGetEmployee(?);";
    private final String SPROC_GET_EMPLOYEE_DETAILS = "CALL spGetEmployeeDetails(?);";

    private IDAL dataAccess;

    public EmployeeRepo() {
        dataAccess = DALFactory.createInstance();
    }

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

    @Override
    public int updateEmployee(IEmployee employee) {

        int rowsAffected = 0;
//        List<Object> returnedValues;
//        List<IParameter> params = ParameterFactory.createListInstance();
//
//        params.add(ParameterFactory.createInstance(employee.getId()));
//        params.add(ParameterFactory.createInstance(employee.getFirstName()));
//        params.add(ParameterFactory.createInstance(employee.getLastName()));
//        params.add(ParameterFactory.createInstance(employee.getSin()));
//        params.add(ParameterFactory.createInstance(employee.getHourlyRate()));
//        params.add(ParameterFactory.createInstance(employee.getUpdatedAt()));
//
//
        return rowsAffected;
    }

    @Override
    public int deleteEmployee(IEmployee employee) {
        return 0;
    }

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

    @Override
    public IEmployee retrieveEmployee(int id) {
        IEmployee employee = EmployeeFactory.createInstance();

        try {
            List<IParameter> params = ParameterFactory.createListInstance();

            params.add(ParameterFactory.createInstance(id));

            CachedRowSet rs = this.dataAccess.executeFill(this.SPROC_SELECT_EMPLOYEE, params);

            employee = mapPropsEmployeeToModel(rs);

        } catch (Exception e) {
            System.out.print(e.getMessage());
        }

        return employee;
    }

    @Override
    public IEmployeeDTO retrieveEmployeeDetails(int id) {

        IEmployeeDTO employeeDetails = null;

        try {
            List<IParameter> params = ParameterFactory.createListInstance();

            params.add(ParameterFactory.createInstance(id));

            CachedRowSet rs = this.dataAccess.executeFill(this.SPROC_GET_EMPLOYEE_DETAILS, params);

            employeeDetails = populateEmployeeDTO(rs);

        } catch (Exception e) {
            System.out.print(e.getMessage());
        }

        return employeeDetails;

    }

    private IEmployeeDTO populateEmployeeDTO(CachedRowSet rs) throws SQLException {

        IEmployeeDTO employeeDetails = null;
        ITeam team = null;
        IEmployee employee = null;

        if (rs.next()) {
            employeeDetails = EmployeeDTOFactory.createInstance();
            team = TeamFactory.createInstance();
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

            team.setName(rs.getString("Name"));
            
            

            employeeDetails.setEmployee(employee);
            employeeDetails.setTeam(team);
        }

        return employeeDetails;

    }

    /**
     * Maps properties of an employee stored in Database to the model IEmployee
     *
     * @param rs
     * @return employee model with populated properties or null
     * @throws SQLException
     */
    private IEmployee mapPropsEmployeeToModel(CachedRowSet rs) throws SQLException {
        IEmployee employee = null;

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
        }

        return employee;

    }

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
