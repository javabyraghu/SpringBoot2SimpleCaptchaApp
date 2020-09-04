package in.nareshit.raghu.service.impl;

import org.springframework.data.jpa.repository.JpaRepository;

import in.nareshit.raghu.modal.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}
