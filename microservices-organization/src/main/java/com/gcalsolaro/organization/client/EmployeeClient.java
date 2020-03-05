package com.gcalsolaro.organization.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gcalsolaro.organization.model.Employee;

@FeignClient(name = "employee-service")
public interface EmployeeClient {

	@RequestMapping(value = "/organization/{organizationId}", method = { RequestMethod.GET })
	public List<Employee> findByOrganization(@PathVariable("organizationId") Long organizationId);

}
