package com.test.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import com.test.service.IEmployeeService;

@SpringBootApplication
@ComponentScan(basePackages = { "com.test.config" })
public class Main {

	private static final Logger log = LoggerFactory.getLogger(Main.class);

	@Component
	static class Runner implements CommandLineRunner {

		@Autowired
		private IEmployeeService employeeService;

		@Override
		public void run(String... args) throws Exception {
			log.info("Employee A --> " + employeeService.getEmployeeById(1));
			log.info("Employee A --> " + employeeService.getEmployeeById(1));
			log.info("Employee A --> " + employeeService.getEmployeeById(1));
		}

		public IEmployeeService getEmployeeService() {
			return employeeService;
		}

		public void setEmployeeService(IEmployeeService employeeService) {
			this.employeeService = employeeService;
		}

	}

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}
}
