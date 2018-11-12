package jdbc_study.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jdbc_study.dto.Department;
import jdbc_study.dto.Employee;
import jdbc_study.jdbc.LogUtil;
import jdbc_study.jdbc.MySQLJdbcUtil;

public class DeptEmpTransactionDaoImpl implements DeptEmpTransactionDao {

	@Override
	public int transactionInsertEmployeeAndDepartment(Employee emp, Department dept) {
		LogUtil.prnLog("transactionInsertEmployeeAndDepartment()");
		String deptSql = "insert into department values(?, ?, ?)";
		String empSql = "insert into employee values(?, ?, ?, ?, ?, ?)";
		PreparedStatement deptPstmt = null;
		PreparedStatement empPstmt = null;

		int result = 0;
		Connection conn = null;
		try {
			conn = MySQLJdbcUtil.getConnection();
			conn.setAutoCommit(false);

			deptPstmt = conn.prepareStatement(deptSql);
			deptPstmt.setInt(1, dept.getDeptNo());
			deptPstmt.setString(2, dept.getDeptName());
			deptPstmt.setInt(3, dept.getFloor());
			LogUtil.prnLog(deptPstmt);
			result += deptPstmt.executeUpdate();
			empPstmt = conn.prepareStatement(empSql);
			empPstmt.setInt(1, emp.getEmpNo());
			empPstmt.setString(2, emp.getEmpName());
			empPstmt.setString(3, emp.getTitle());
			empPstmt.setInt(4, emp.getManager().getEmpNo());
			empPstmt.setInt(5, emp.getSalary());
			empPstmt.setInt(6, emp.getDept().getDeptNo());
			LogUtil.prnLog(empPstmt);
			result += empPstmt.executeUpdate();

			conn.commit();
			conn.setAutoCommit(true);
			LogUtil.prnLog("commit()");
		} catch (SQLException e) {
			try {
				conn.rollback();
				conn.setAutoCommit(true);
				LogUtil.prnLog("rollback()");
			} catch (SQLException ex) {
				LogUtil.prnLog(ex.getMessage());
			}
		} finally {
			try {
				if (empPstmt != null)
					empPstmt.close();
				if (deptPstmt != null)
					deptPstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException ex) {
				LogUtil.prnLog(ex.getMessage());
			}
		}
		return result;

	}

	@Override
	public int transactionInsertEmployeeAndDepartmentApi(Employee emp, Department dept) {
		LogUtil.prnLog("transactionInsertEmployeeAndDepartmentApi()");
		DepartmentDao deptDao = new DepartmentDaoImpl();
		EmployeeDao empDao = new EmployeeDaoImpl();
		Connection conn = null;
		int result = 0;
		try{
			conn = MySQLJdbcUtil.getConnection();
			conn.setAutoCommit(false);
			result += deptDao.insertDepartment(dept);
			result += empDao.insertEmployee(emp);
			conn.commit();
			conn.setAutoCommit(true);
			LogUtil.prnLog("commit()");
		} catch (SQLException e) {
			try {
				conn.rollback();
				conn.setAutoCommit(true);
				LogUtil.prnLog("rollback()");
			} catch (SQLException ex) {
				LogUtil.prnLog(ex.getMessage());
			}
		}finally {
			try {
				if (conn != null) conn.close();
			} catch (SQLException ex) {
				LogUtil.prnLog(ex.getMessage());
			}
		}
		
		return result;
	}

}
