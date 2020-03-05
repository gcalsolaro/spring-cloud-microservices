package com.gcalsolaro.organization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Component;

import com.gcalsolaro.organization.model.Organization;
import com.gcalsolaro.organization.repository.OrganizationRepository;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	OrganizationRepository repository() {
		OrganizationRepository repository = new OrganizationRepository();
		repository.add(new Organization("Microsoft", "Redmond, Washington, USA"));
		repository.add(new Organization("Oracle", "Redwood City, California, USA"));
		return repository;
	}
	
	@Component
	public class FeignClientInterceptor implements RequestInterceptor {

		private static final String AUTHORIZATION_HEADER = "Authorization";
		private static final String TOKEN_TYPE = "Bearer";

		@Override
		public void apply(RequestTemplate requestTemplate) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (authentication != null && authentication.getDetails() instanceof OAuth2AuthenticationDetails) {
				OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
				requestTemplate.header(AUTHORIZATION_HEADER, String.format("%s %s", TOKEN_TYPE, details.getTokenValue()));
			}
		}
	}
	
	@Configuration
	@EnableResourceServer
	static class ResourceServer extends ResourceServerConfigurerAdapter {

		@Override
		public void configure(HttpSecurity http) throws Exception {
			http.requestMatchers().and().authorizeRequests()
					.antMatchers(HttpMethod.GET, "/**").access("#oauth2.hasScope('read_organization') and hasRole('ROLE_ADMIN')")
					.antMatchers(HttpMethod.POST, "/**").access("#oauth2.hasScope('write_organization') and hasRole('ROLE_ADMIN')")
					.antMatchers(HttpMethod.PUT, "/**").access("#oauth2.hasScope('write_organization') and hasRole('ROLE_ADMIN')")
					.antMatchers(HttpMethod.DELETE, "/**").access("#oauth2.hasScope('write_organization') and hasRole('ROLE_ADMIN')")

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
