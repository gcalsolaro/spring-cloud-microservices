package com.gcalsolaro.organization.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gcalsolaro.organization.model.Department;

@FeignClient(name = "department-service")
public interface DepartmentClient {

	@RequestMapping(value = "/organization/{organizationId}", method = { RequestMethod.GET })
	public List<Department> findByOrganization(@PathVariable("organizationId") Long organizationId);

	@RequestMapping(value = "/organization/{organizationId}/with-employees", method = { RequestMethod.GET })
	public List<Department> findByOrganizationWithEmployees(@PathVariable("organizationId") Long organizationId);

}
