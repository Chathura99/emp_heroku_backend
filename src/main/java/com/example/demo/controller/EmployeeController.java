package com.example.demo.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.demo.exception.ResourceNotFound;
import com.example.demo.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;

import javax.websocket.server.PathParam;

@RestController
/*create link*/
@RequestMapping("/api/v1/")
/*for connect with react*/
@CrossOrigin(origins = "http://localhost:3000")
public class EmployeeController {
    //create object from repo
    @Autowired
    private EmployeeRepository employeeRepository;

    //create object from service
    @Autowired
    private EmployeeService employeeService;

    //get all employees
    // this.1
    @GetMapping("/employees")
    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

//    create employee rest api
    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody Employee employee){
    //return Employee model type type
        return employeeRepository.save(employee);
    }


    //using service layer to business logic
//    @PostMapping("/employees")
//    public Employee createEmployee(@RequestBody Employee employee){
//        //return Employee model type type
//        return EmployeeService.addMethod(employee);
//    }


    //get employee by is rest api - @PathVariable -> what is the data type of parameter
    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id){

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Employee not found with id : "+ id));
    return ResponseEntity.ok(employee);

    }
    //update employee rest api
    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails){

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Employee not found with id : "+ id));
        //update details
        employee.setUsername(employeeDetails.getUsername());
        employee.setEmail(employeeDetails.getEmail());
        Employee updatedEmployee = employeeRepository.save(employee);
        //show in postman
        return ResponseEntity.ok(updatedEmployee);
    }

    //delete employee rest api
    @DeleteMapping("/employees/{id}")
    //@PathVariable used to map with /employees/{id}, ResponseEntity<Map<String,Boolean>> for return some msgs
    public ResponseEntity<Map<String,Boolean>> deleteEmployee(@PathVariable Long id){
    //find record first and after that delete it
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Employee not found with id : "+ id));
    //delete it
        employeeRepository.delete(employee);
        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted",Boolean.TRUE);
        return ResponseEntity.ok(response);
    }


}
