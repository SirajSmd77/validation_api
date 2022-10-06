package com.myapp.apis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myapp.apis.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {

}
