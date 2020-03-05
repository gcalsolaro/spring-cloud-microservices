package com.gcalsolaro.organization.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gcalsolaro.organization.client.DepartmentClient;
import com.gcalsolaro.organization.client.EmployeeClient;
import com.gcalsolaro.organization.model.Organization;
import com.gcalsolaro.organization.repository.OrganizationRepository;

@RestController
public class OrganizationController {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrganizationController.class);

	@Autowired
	private OrganizationRepository repository;

	@Autowired
	private DepartmentClient departmentClient;

	@Autowired
	private EmployeeClient employeeClient;

	@RequestMapping(method = { RequestMethod.POST })
	public Organization add(@RequestBody Organization organization) {
		LOGGER.info("Organization add: {}", organization);
		return repository.add(organization);
	}

	@RequestMapping(method = { RequestMethod.GET })
	public List<Organization> findAll() {
		LOGGER.info("Organization find");
		return repository.findAll();
	}

	@RequestMapping(value = "/{id}", method = { RequestMethod.GET })
	public Organization findById(@PathVariable("id") Long id) {
		LOGGER.info("Organization find: id={}", id);
		return repository.findById(id);
	}

	@RequestMapping(value = "/{id}/with-departments", method = { RequestMethod.GET })
	public Organization findByIdWithDepartments(@PathVariable("id") Long id) {
		LOGGER.info("Organization find: id={}", id);
		Organization organization = repository.findById(id);
		organization.setDepartments(departmentClient.findByOrganization(organization.getId()));
		return organization;
	}

	@RequestMapping(value = "/{id}/with-departments-and-employees", method = { RequestMethod.GET })
	public Organization findByIdWithDepartmentsAndEmployees(@PathVariable("id") Long id) {
		LOGGER.info("Organization find: id={}", id);
		Organization organization = repository.findById(id);
		organization.setDepartments(departmentClient.findByOrganizationWithEmployees(organization.getId()));
		return organization;
	}

	@RequestMapping(value = "/{id}/with-employees", method = { RequestMethod.GET })
	public Organization findByIdWithEmployees(@PathVariable("id") Long id) {
		LOGGER.info("Organization find: id={}", id);
		Organization organization = repository.findById(id);
		organization.setEmployees(employeeClient.findByOrganization(organization.getId()));
		return organization;
	}

}
