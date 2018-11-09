package jdbc_study.dao;

import java.sql.SQLException;
import java.util.List;

import jdbc_study.dto.Department;

public interface DepartmentDao {
	List<Department> selectDepartmentByAll();
	int insertDepartment(Department department) throws SQLException;
	int delelteDepartment(Department department) throws SQLException;
	int updateDepartment(Department department) throws SQLException;
	Department selectDepartment(Department department) throws SQLException;

}
