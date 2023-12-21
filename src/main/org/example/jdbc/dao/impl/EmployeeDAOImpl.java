package main.org.example.jdbc.dao.impl;


import main.org.example.jdbc.dao.abs.EmployeeDAO;
import main.org.example.jdbc.dao.abs.OfficeDAO;
import main.org.example.jdbc.dao.abs.PassportDAO;
import main.org.example.jdbc.dao.abs.OfficeDAO;
import main.org.example.jdbc.dao.abs.PassportDAO;
import main.org.example.model.Employee;
import main.org.example.model.Office;
import main.org.example.util.DBUtils;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

public class EmployeeDAOImpl implements EmployeeDAO {
    private OfficeDAO officeDAO = new OfficeDAOImpl();
    private PassportDAO passportDAO = new PassportDAOImpl();

    @Override
    public boolean createEmployee(Employee employee) {

        try (Connection connection = DBUtils.getConnection();
             Statement statement = connection.createStatement()){
            String sql = "INSERT INTO `employees` ( `name`, `last_name`, `age`, `office_id`, `passport_id`, " +
                    " `created_ts`) VALUES ('" + employee.getName() + "', '" +
                    employee.getLastName() + "', '" + employee.getAge() + "', '" + employee.getOffice().getId() + "', '" +
                    employee.getPassport().getId() + "',  CURRENT_TIMESTAMP )";
            int count = statement.executeUpdate(sql);
            return count == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Employee findById(int id) {
        try (Connection connection = DBUtils.getConnection();
             Statement statement = connection.createStatement()){
            ResultSet rs = statement.executeQuery("SELECT * FROM employees where id =" + id);
            if (rs.next()) {
                return createEmployee(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean deleteById(int id) {
        try (Connection connection = DBUtils.getConnection();
             Statement statement = connection.createStatement()){
            statement.execute("DELETE FROM employees where id =" + id);
            if (findById(id) == null) {
                return true;
            }
            else return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateEmployee(Employee employee) {
        try (Connection connection = DBUtils.getConnection();
             Statement statement = connection.createStatement()){
            statement.execute("UPDATE `employees` SET `name` = '"+employee.getName()+"', `last_name` = '" + employee.getLastName() +"" +
                    "', `age` = '" + employee.getAge() + "', office_id = '" + employee.getOffice().getId() + "', `passport_id` = '" + employee.getPassport().getId() + "', `updated_ts` = CURRENT_TIMESTAMP WHERE `employees`.`id` =" + employee.getId());
            if (findById(employee.getId()).equals(employee)) {
                return true;
            }
            else return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean create(Employee type) {
        return false;
    }

    @Override
    public Employee findById(Integer key) {
        return null;
    }

    @Override
    public boolean deleteById(Integer key) {
        return false;
    }

    @Override
    public boolean update(Employee type) {
        return false;
    }

    @Override
    public Set<Employee> all() {
        Set<Employee> employees = new HashSet<>();
        Connection connection = DBUtils.getConnection();
        Statement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM employees");
            while (rs.next()) {
                employees.add(createEmployee(rs));
            }
            return employees;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBUtils.release(connection, statement, rs);
        }
    }

    @Override
    public Set<Employee> getAllByOfficeID(Office office) {
        Set<Employee> employees = new HashSet<>();
        Connection connection = DBUtils.getConnection();
        Statement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM employees WHERE office_id = " + office.getId());
            while (rs.next()) {
                employees.add(createEmployee(rs));
            }
            return employees;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBUtils.release(connection, statement, rs);
        }
    }

    private Employee createEmployee(ResultSet rs) throws SQLException {
        Employee employee = new Employee();
        employee.setId(rs.getInt("id"));
        employee.setName(rs.getString("name"));
        employee.setLastName(rs.getString("last_name"));
        employee.setAge(rs.getInt("age"));
        employee.setOffice(officeDAO.findById(rs.getInt("office_id")));
        employee.setPassport(passportDAO.findById(rs.getInt("passport_id")));
        employee.setCreatedTs(rs.getTimestamp("created_ts"));
        employee.setUpdatedTs(rs.getTimestamp("updated_ts"));
        return employee;
    }
}
