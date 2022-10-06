package com.myapp.apis.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myapp.apis.exception.ResourceNotFoundException;
import com.myapp.apis.model.Employee;
import com.myapp.apis.repository.EmployeeRepository;

@RestController
@RequestMapping("/api/")
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRespository;
	
	//get all employees
	@GetMapping("employees")
	public List<Employee> getAllEmployee(){
		return this.employeeRespository.findAll();
	}
	
	//get employee by id
	@GetMapping("employees/{id}")
	public ResponseEntity<Employee> getEmployee(@PathVariable (value="id") Long employeeId)
	   throws ResourceNotFoundException {
		
		Employee employee = this.employeeRespository.findById(employeeId).orElseThrow(() -> new ResourceNotFoundException ("Not found employee id: "+employeeId));
		return  ResponseEntity.ok().body(employee);
	}
	//save employee
	@PostMapping("employees")
	public Employee createEmployee(@Valid @RequestBody Employee employee) {
		return this.employeeRespository.save(employee);
		
	}
	//update employee
	@PutMapping("employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable(value="id") Long employeeId,
		 @RequestBody Employee employeeDetails) throws ResourceNotFoundException{
		Employee employee = this.employeeRespository.findById(employeeId).orElseThrow(() -> new ResourceNotFoundException ("Not found employee id: "+employeeId));

		employee.setEmail(employeeDetails.getEmail());
		employee.setFirstName(employeeDetails.getFirstName());
		employee.setLastName(employeeDetails.getLastName());
		return ResponseEntity.ok(this.employeeRespository.save(employee));
		
	}
	@DeleteMapping("employee/{id}")
	public Map<String,Boolean> deleteEmployee(@PathVariable(value="id") Long employeeId) throws ResourceNotFoundException{
		Employee employee = this.employeeRespository.findById(employeeId).orElseThrow(() -> new ResourceNotFoundException ("Not found employee id: "+employeeId));

		this.employeeRespository.delete(employee);
		Map<String,Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
	
}
