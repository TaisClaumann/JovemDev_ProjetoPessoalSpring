package br.com.trier.projeto_pessoal_spring.services;

import java.util.List;

import br.com.trier.projeto_pessoal_spring.domain.Instructor;

public interface InstructorService {
	
	Instructor insert(Instructor instructor);
	Instructor update(Instructor instructor);
	void delete(Integer id);
	Instructor findById(Integer id);
	List<Instructor> listAll();
	List<Instructor> findByName(String name);
	Instructor findByCpf(String cpf);
	List<Instructor> findBySalary(Double salary);
	List<Instructor> findBySalaryBetween(Double initialSalary, Double finalSalary);

}
