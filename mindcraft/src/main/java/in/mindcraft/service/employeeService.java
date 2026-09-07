package in.mindcraft.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.mindcraft.entity.employee;
import in.mindcraft.repository.employeeRepository;

@Service
public class employeeService {

    private final employeeRepository employeeRepository;

    @Autowired
    public employeeService(employeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // Retrieve all employees
    public List<employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // Retrieve employee by ID
    public employee getEmployeeById(int id) {
        return employeeRepository.findById(id).orElse(null);
    }

//
//    public List<employee> getEmployeesWithSalaryUpTo50k(int maxSalary) {
//        int maxSalary = new int("50000");
//        return employeeRepository.getEmployeesWithSalaryUpTo50k(maxSalary);
//    }


    // Add a new employee
    public employee addEmployee(employee emp, String currentUser, LocalDateTime currentUser1) {
    	emp.setActive("y");
        emp.setCreatedOn(currentUser1.now());
        emp.setCreatedBy(currentUser);
        emp.setUpdateOn(currentUser1.now());
        emp.setUpdatedBy(currentUser);

        return employeeRepository.save(emp);
    }

 // Update an existing employee in employeeService.java
    public employee updateEmployee(int id, employee updatedDetails, String currentUser) {
        Optional<employee> existingOpt = employeeRepository.findById(id);

        if (existingOpt.isPresent()) {
            employee emp = existingOpt.get();

            emp.setEmpName(updatedDetails.getEmpName());
            emp.setEmpSalary(updatedDetails.getEmpSalary());
            
            // Update active status if passed
            if (updatedDetails.getActive() != null) {
                emp.setActive(updatedDetails.getActive());
            }

            emp.setUpdateOn(LocalDateTime.now());
            emp.setUpdatedBy(currentUser);

            return employeeRepository.save(emp);
        }

        return null;
    }
    

 // Delete an employee record

 public boolean deleteEmployee(int id) {

 if (employeeRepository.existsById(id)) {

 employeeRepository.deleteById(id);

 return true;

 }

 return false;

 }

 }
    