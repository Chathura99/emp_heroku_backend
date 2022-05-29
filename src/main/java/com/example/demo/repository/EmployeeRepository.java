package com.example.demo.repository;


import com.example.demo.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
/*from model/Employee.java parameterized constructor */
//Employee-model class ,Long-primary key data type
public interface EmployeeRepository extends JpaRepository<Employee,Long>{

}
