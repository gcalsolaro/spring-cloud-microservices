package com.gcalsolaro.employee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

import com.gcalsolaro.employee.model.Employee;
import com.gcalsolaro.employee.repository.EmployeeRepository;

@SpringBootApplication
@EnableDiscoveryClient
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	EmployeeRepository repository() {
		EmployeeRepository repository = new EmployeeRepository();
		repository.add(new Employee(1L, 1L, "Paolo Rossi", 26, "Developer"));
		repository.add(new Employee(1L, 2L, "Giuseppe Verdi", 39, "Analyst"));
		repository.add(new Employee(2L, 3L, "Marco Neri", 30, "Manager"));
		repository.add(new Employee(2L, 4L, "Antonio Gialli", 25, "Developer"));
		return repository;
	}
	
	@Configuration
	@EnableResourceServer
	static class ResourceServer extends ResourceServerConfigurerAdapter {

		@Override
		public void configure(HttpSecurity http) throws Exception {
			http.requestMatchers().and().authorizeRequests()
					.antMatchers(HttpMethod.GET, "/**").access("#oauth2.hasScope('read_employee') and hasRole('ROLE_ADMIN')")
					.antMatchers(HttpMethod.POST, "/**").access("#oauth2.hasScope('write_employee') and hasRole('ROLE_ADMIN')")
					.antMatchers(HttpMethod.PUT, "/**").access("#oauth2.hasScope('write_employee') and hasRole('ROLE_ADMIN')")
					.antMatchers(HttpMethod.DELETE, "/**").access("#oauth2.hasScope('write_employee') and hasRole('ROLE_ADMIN')")

					.antMatchers("/metrics/**").access("#oauth2.hasScope('metrics')")
					.antMatchers("/trace/**").access("#oauth2.hasScope('trace')")
					.antMatchers("/dump/**").access("#oauth2.hasScope('dump')")
					.antMatchers("/shutdown/**").access("#oauth2.hasScope('shutdown')")
					.antMatchers("/beans/**").access("#oauth2.hasScope('beans')")
					.antMatchers("/autoconfig/**").access("#oauth2.hasScope('autoconfig')")
					.antMatchers("/configprops/**").access("#oauth2.hasScope('configprops')")
					.antMatchers("/env/**").access("#oauth2.hasScope('env')")
					.antMatchers("/mappings/**").access("#oauth2.hasScope('mappings')");

		}
	}
}
