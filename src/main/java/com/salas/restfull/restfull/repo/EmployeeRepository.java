package com.salas.restfull.restfull.repo;

import com.salas.restfull.restfull.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
