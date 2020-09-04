package in.nareshit.raghu.service;

import java.util.List;
import java.util.Optional;

import in.nareshit.raghu.modal.Employee;

public interface IEmployeeService {
	
	void createEmployee(Employee employee);
	List<Employee> getAllEmployees();
	Optional<Employee> getOneEmployee(Integer id);
}
