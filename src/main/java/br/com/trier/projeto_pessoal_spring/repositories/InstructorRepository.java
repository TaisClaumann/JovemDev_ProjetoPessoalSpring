package br.com.trier.projeto_pessoal_spring.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.projeto_pessoal_spring.domain.Instructor;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Integer>{
	
	List<Instructor> findByNameContainsIgnoreCase(String name);
	Optional<Instructor> findByCpf(String cpf);
	List<Instructor> findBySalary(Double salary);
	List<Instructor> findBySalaryBetween(Double initialSalary, Double finalSalary);
}
