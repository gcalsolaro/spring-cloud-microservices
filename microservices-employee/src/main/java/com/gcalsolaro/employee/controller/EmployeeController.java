package com.gcalsolaro.employee.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gcalsolaro.employee.model.Employee;
import com.gcalsolaro.employee.repository.EmployeeRepository;

@RestController
public class EmployeeController {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	private EmployeeRepository repository;

	@RequestMapping(value = "/", method = { RequestMethod.POST })
	public Employee add(@RequestBody Employee employee) {
		LOGGER.info("Employee add: {}", employee);
		return repository.add(employee);
	}

	@RequestMapping(value = "/{id}", method = { RequestMethod.GET })
	public Employee findById(@PathVariable("id") Long id) {
		LOGGER.info("Employee find: id={}", id);
		return repository.findById(id);
	}

	@RequestMapping(value = "/", method = { RequestMethod.GET })
	public List<Employee> findAll() {
		LOGGER.info("Employee find");
		return repository.findAll();
	}

	@RequestMapping(value = "/department/{departmentId}", method = { RequestMethod.GET })
	public List<Employee> findByDepartment(@PathVariable("departmentId") Long departmentId) {
		LOGGER.info("Employee find: departmentId={}", departmentId);
		return repository.findByDepartment(departmentId);
	}

	@RequestMapping(value = "/organization/{organizationId}", method = { RequestMethod.GET })
	public List<Employee> findByOrganization(@PathVariable("organizationId") Long organizationId) {
		LOGGER.info("Employee find: organizationId={}", organizationId);
		return repository.findByOrganization(organizationId);
	}

}
