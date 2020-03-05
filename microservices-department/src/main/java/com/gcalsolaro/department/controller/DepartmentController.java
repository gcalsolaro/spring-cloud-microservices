package com.gcalsolaro.department.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gcalsolaro.department.client.EmployeeClient;
import com.gcalsolaro.department.model.Department;
import com.gcalsolaro.department.repository.DepartmentRepository;

@RestController
public class DepartmentController {

	private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentController.class);

	@Autowired
	private DepartmentRepository repository;

	@Autowired
	private EmployeeClient employeeClient;

	@RequestMapping(value = "/", method = { RequestMethod.POST })
	public Department add(@RequestBody Department department) {
		LOGGER.info("Department add: {}", department);
		return repository.add(department);
	}

	@RequestMapping(value = "/{id}", method = { RequestMethod.GET })
	public Department findById(@PathVariable("id") Long id) {
		LOGGER.info("Department find: id={}", id);
		return repository.findById(id);
	}

	@RequestMapping(value = "/", method = { RequestMethod.GET })
	public List<Department> findAll() {
		LOGGER.info("Department find");
		return repository.findAll();
	}

	@RequestMapping(value = "/organization/{organizationId}", method = { RequestMethod.GET })
	public List<Department> findByOrganization(@PathVariable("organizationId") Long organizationId) {
		LOGGER.info("Department find: organizationId={}", organizationId);
		return repository.findByOrganization(organizationId);
	}

	@RequestMapping(value = "/organization/{organizationId}/with-employees", method = { RequestMethod.GET })
	public List<Department> findByOrganizationWithEmployees(@PathVariable("organizationId") Long organizationId) {
		LOGGER.info("Department find: organizationId={}", organizationId);
		List<Department> departments = repository.findByOrganization(organizationId);
		departments.forEach(d -> d.setEmployees(employeeClient.findByDepartment(d.getId())));
		return departments;
	}

}
