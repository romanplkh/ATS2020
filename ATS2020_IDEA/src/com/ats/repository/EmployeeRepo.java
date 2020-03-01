package com.ats.repository;

import com.ats.dataaccess.DALFactory;
import com.ats.dataaccess.IDAL;
import com.ats.dataaccess.IParameter;
import com.ats.dataaccess.ParameterFactory;
import com.ats.models.EmployeeFactory;
import com.ats.models.IEmployee;

import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

public class EmployeeRepo extends BaseRepo implements IEmployeeRepo {

    private final String SPROC_INSERT_EMPLOYEE = "CALL spAddEmployee(?,?,?,?);";
    private final String SPROC_UPDATE_EMPLOYEE = "CALL spAddEmployee(?,?,?,?);";
    private final String SPROC_SELECT_EMPLOYEE = "CALL spAddEmployee(?,?,?,?);";
    private final String SPROC_SELECT_EMPLOYEES = "CALL spGetEmployeeLookup(?);";
    private final String SPROC_DELETE_EMPLOYEE = "CALL spAddEmployee(?,?,?,?);";


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

        try {
            CachedRowSet rs = this.dataAccess.executeFill(SPROC_SELECT_EMPLOYEES, null);
            employees = toListOfEmployees(rs);

        } catch (Exception e) {
            System.out.print(e.getMessage());
        }

        return employees;
    }

    @Override
    public IEmployee retrieveEmployee(int id) {
        List<IEmployee> employees = EmployeeFactory.createListInstance();

        try {
            List<IParameter> params = ParameterFactory.createListInstance();


            params.add(ParameterFactory.createInstance(id));

            CachedRowSet rs = this.dataAccess.executeFill(this.SPROC_SELECT_EMPLOYEE, params);

            employees = toListOfEmployees(rs);

        } catch (Exception e) {
            System.out.print(e.getMessage());
        }


        return employees.get(0);
    }


    private List<IEmployee> toListOfEmployees(CachedRowSet rs) throws SQLException {
        List<IEmployee> employees = EmployeeFactory.createListInstance();

        IEmployee employee;

        while (rs.next()) {
            employee = EmployeeFactory.createInstance();
            employee.setId(super.getInt("id", rs));
            employee.setFirstName(rs.getString("firstName"));
            employee.setLastName(rs.getString("lastName"));
            employees.add(employee);
        }

        return employees;


    }

}
