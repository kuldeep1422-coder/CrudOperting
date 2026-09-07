package in.mindcraft.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.mindcraft.entity.employee;


@Repository
public interface employeeRepository extends JpaRepository<employee, Integer> {
	
}
