package in.mindcraft.entity;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
public class employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int empId;

    @NotBlank(message = "Employee name cannot be null or empty")
    private String empName;

    @NotNull(message = "Employee salary cannot be null")
    @DecimalMin(value = "10001", message = "Salary must be greater than 10000")
    private Double empSalary;

    // Accepts only "Y" or "N" (case-insensitive)
    @Pattern(regexp = "^(?i)(Y|N)$", message = "Active flag must only be 'Y' or 'N'")
    private String active;

    private LocalDateTime createdOn;
    private String createdBy;
    private LocalDateTime updateOn;
    private String updatedBy;

    public employee() {}

    // Getters and Setters
    public int getEmpId() { return empId; }
    public void setEmpId(int empId) { this.empId = empId; }

    public String getEmpName() { return empName; }
    public void setEmpName(String empName) { this.empName = empName; }

    public Double getEmpSalary() { return empSalary; }
    public void setEmpSalary(Double empSalary) { this.empSalary = empSalary; }

    public String getActive() { return active; }
    public void setActive(String active) { 
        this.active = (active != null) ? active.toUpperCase() : null; 
    }

    public LocalDateTime getCreatedOn() { return createdOn; }
    public void setCreatedOn(LocalDateTime createdOn) { this.createdOn = createdOn; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    public LocalDateTime getUpdateOn() { return updateOn; }
    public void setUpdateOn(LocalDateTime updateOn) { this.updateOn = updateOn; }

    public String getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(String updatedBy) { this.updatedBy = updatedBy; }
}