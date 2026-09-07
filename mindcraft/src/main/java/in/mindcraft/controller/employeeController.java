package in.mindcraft.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.mindcraft.entity.employee;
import in.mindcraft.exception.InvalidActiveFlagException;
import in.mindcraft.service.employeeService;
import jakarta.validation.Valid;

@RestController
public class employeeController {

    private final employeeService employeeService;

    @Autowired
    public employeeController(employeeService employeeService) {
        this.employeeService = employeeService;
    }

    
    @GetMapping("/employee/{id}")
    public employee getEmployeeById(@PathVariable int id) {

        return employeeService.getEmployeeById(id);
    }
    
    // GET /employee
    @GetMapping("/employee")
    public ResponseEntity<?> getEmployees(
            @RequestParam(name = "employee_id", required = false) Integer employeeId,
            @RequestParam(name = "active", required = false) String active) {

        if (employeeId == null && active == null) {
            return ResponseEntity.ok(employeeService.getAllEmployees());
        }

        // Active flag validation: only 'Y' or 'N'
        if (active != null && !active.equalsIgnoreCase("Y") && !active.equalsIgnoreCase("N")) {
            throw new InvalidActiveFlagException("Invalid active flag: '" + active + "'. Only 'Y' or 'N' are allowed.");
        }

        if (employeeId == null || active == null) {
            return ResponseEntity.badRequest().body("Both 'employee_id' and 'active' are required.");
        }

        employee emp = employeeService.getEmployeeById(employeeId);
        if (emp == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Employee not found with ID: " + employeeId);
        }

        if (emp.getActive() != null && emp.getActive().equalsIgnoreCase(active)) {
            return ResponseEntity.ok(emp);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Employee ID " + employeeId + " does not match active status: " + active);
    }

    // POST /employee - Notice the @Valid annotation
    @PostMapping("/employee")
    public ResponseEntity<employee> addEmployee(@Valid @RequestBody employee emp,LocalDateTime LocalDateTime) {
        if (emp.getActive() == null) {
            emp.setActive("Y"); // Default to Y if not passed
        }
        employee saved = employeeService.addEmployee(emp, "Admin",LocalDateTime);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
    
    @PutMapping("/employee/{id}")
    public ResponseEntity<?> updateEmployee(
            @PathVariable int id, 
            @Valid @RequestBody employee updatedDetails) {

        employee updatedEmp = employeeService.updateEmployee(id, updatedDetails, "Admin");

        if (updatedEmp == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
         
            		.body("Cannot update. Employee not found with ID: " + id);
        }

        return ResponseEntity.ok(updatedEmp);
    }

    // DELETE - Remove employee by ID
    @DeleteMapping("/employee/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable int id) {
        boolean isDeleted = employeeService.deleteEmployee(id);

        if (isDeleted) {
            return ResponseEntity.ok("Employee with ID " + id + " deleted successfully.");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Cannot delete. Employee not found with ID: " + id);
    }
}