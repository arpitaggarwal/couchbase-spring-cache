package com.test.service.impl;

import static com.test.constants.ApplicationConstants.EMPLOYEE_CACHE;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.test.domain.Employee;
import com.test.service.IEmployeeService;

@Component
public class EmployeeServiceImpl implements IEmployeeService {

	@Override
	@Cacheable(EMPLOYEE_CACHE)
	public Employee getEmployeeById(final int id) {
		System.out.println("Employee Service Called");
		return new Employee(id, "A");
	}
}
