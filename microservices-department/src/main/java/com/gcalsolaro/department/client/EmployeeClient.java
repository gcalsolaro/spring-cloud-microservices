package com.gcalsolaro.department.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gcalsolaro.department.model.Employee;

@FeignClient(name = "employee-service")
public interface EmployeeClient {

	@RequestMapping(value = "/department/{departmentId}", method = { RequestMethod.GET })
	public List<Employee> findByDepartment(@PathVariable("departmentId") Long departmentId);

}
