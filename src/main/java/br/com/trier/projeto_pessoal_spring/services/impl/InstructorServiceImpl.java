package br.com.trier.projeto_pessoal_spring.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.projeto_pessoal_spring.domain.Instructor;
import br.com.trier.projeto_pessoal_spring.repositories.InstructorRepository;
import br.com.trier.projeto_pessoal_spring.services.InstructorService;
import br.com.trier.projeto_pessoal_spring.services.exceptions.IntegrityViolationException;
import br.com.trier.projeto_pessoal_spring.services.exceptions.ObjectNotFoundException;
import br.com.trier.projeto_pessoal_spring.utils.CpfUtil;

@Service
public class InstructorServiceImpl implements InstructorService{
	
	@Autowired
	private InstructorRepository repository;

	@Override
	public Instructor insert(Instructor instructor) {
		validateInstructor(instructor);
		return repository.save(instructor);
	}
	
	private void validateInstructor(Instructor instructor) {
		if(instructor == null) {
			throw new IntegrityViolationException("O instrutor não pode ser nulo");
		} else if(instructor.getName() == null || instructor.getName().isBlank()) {
			throw new IntegrityViolationException("Preencha o nome do instrutor");
		} else if(instructor.getCpf() == null || instructor.getCpf().isBlank()) {
			throw new IntegrityViolationException("Preencha o CPF do instrutor");
		} else if(instructor.getSalary() == null || instructor.getSalary().equals(0.0)) {
			throw new IntegrityViolationException("Salário inválido: R$%.2f".formatted(instructor.getSalary()));
		}
		validateCpf(instructor);
	}
	
	private void validateCpf(Instructor instructor) {
		Optional<Instructor> instructorFound = repository.findByCpf(instructor.getCpf());
		if(instructorFound.isPresent()) {
			if(instructorFound.get().getId() != instructor.getId()) {
				throw new IntegrityViolationException("O CPF desse instrutor já existe");
			}
		}
	}

	@Override
	public Instructor update(Instructor instructor) {
		if(!listAll().contains(instructor)) {
			throw new ObjectNotFoundException("Instrutor inexistente");
		}
		return insert(instructor);
	}

	@Override
	public void delete(Integer id) {
		repository.delete(findById(id));
		
	}

	@Override
	public Instructor findById(Integer id) {
		return repository.findById(id).orElseThrow(() 
				-> new ObjectNotFoundException("Instrutor %s inexistente".formatted(id)));
	}

	@Override
	public List<Instructor> listAll() {
		if(repository.findAll().isEmpty()) {
			throw new ObjectNotFoundException("Não há instrutores cadastrados");
		}
		return repository.findAll();
	}

	@Override
	public List<Instructor> findByName(String name) {
		if(repository.findByNameContainsIgnoreCase(name).isEmpty()) {
			throw new ObjectNotFoundException("Instrutor %s inexistente".formatted(name));
		}
		return repository.findByNameContainsIgnoreCase(name);
	}

	@Override
	public Instructor findByCpf(String cpf) {
		return repository.findByCpf(CpfUtil.formatCPF(cpf)).orElseThrow(() 
				-> new ObjectNotFoundException("Instrutor %s inexistente".formatted(cpf)));
	}

	@Override
	public List<Instructor> findBySalary(Double salary) {
		if(repository.findBySalary(salary).isEmpty()) {
			throw new ObjectNotFoundException(
					"Instrutor com salário R$%.2f inexistente".formatted(salary));
		}
		return repository.findBySalary(salary);
	}

	@Override
	public List<Instructor> findBySalaryBetween(Double initialSalary, Double finalSalary) {
		if(repository.findBySalaryBetween(initialSalary, finalSalary).isEmpty()) {
			throw new ObjectNotFoundException(
					"Não há instrutores com salário entre R$%.2f a R$.2f".formatted(initialSalary, finalSalary));
		}
		return repository.findBySalaryBetween(initialSalary, finalSalary);
	}
}
