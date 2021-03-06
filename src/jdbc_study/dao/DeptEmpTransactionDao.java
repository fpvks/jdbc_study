package jdbc_study.dao;

import jdbc_study.dto.Department;
import jdbc_study.dto.Employee;

public interface DeptEmpTransactionDao {
	int transactionInsertEmployeeAndDepartment(Employee emp, Department dept);

	int transactionInsertEmployeeAndDepartmentApi(Employee emp, Department dept);
}
