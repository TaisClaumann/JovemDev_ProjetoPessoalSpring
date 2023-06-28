package br.com.trier.projeto_pessoal_spring.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.trier.projeto_pessoal_spring.domain.Exercise;
import br.com.trier.projeto_pessoal_spring.repositories.ExerciseRepository;
import br.com.trier.projeto_pessoal_spring.services.ExerciseService;
import br.com.trier.projeto_pessoal_spring.services.exceptions.IntegrityViolationException;
import br.com.trier.projeto_pessoal_spring.services.exceptions.ObjectNotFoundException;

@Service
public class ExerciseServiceImpl implements ExerciseService{

	@Autowired
	private ExerciseRepository repository;
	
	@Override
	public Exercise insert(Exercise exercise) {
		validateExercise(exercise);
		return repository.save(exercise);
	}

	private void validateExercise(Exercise exercise) {
		if(exercise == null){
			throw new IntegrityViolationException("O exercício não pode ser nulo");
		} else if(exercise.getDescription() == null || exercise.getDescription().isBlank()) {
			throw new IntegrityViolationException("Preencha a descrição do exercício");
		}
		validateDescription(exercise);
	}
	
	private void validateDescription(Exercise exercise) {
		Optional<Exercise> exerciceFound = repository.findByDescriptionIgnoreCase(exercise.getDescription());
		if(exerciceFound.isPresent()) {
			if(!exerciceFound.get().getId().equals(exercise.getId())) {
				throw new IntegrityViolationException("A descrição desse exercício já existe");
			}
		}
	}
	
	@Override
	public Exercise update(Exercise exercise) {
		if(!listAll().contains(exercise)) {
			throw new ObjectNotFoundException("Exercício inexistente");
		}
		return insert(exercise);
	}

	@Override
	public void delete(Integer id) {
		repository.delete(findById(id));
	}

	@Override
	public Exercise findById(Integer id) {
		return repository.findById(id).orElseThrow(() 
				-> new ObjectNotFoundException("Exercício %s inexistente".formatted(id)));
	}

	@Override
	public List<Exercise> listAll() {
		if(repository.findAll().isEmpty()) {
			throw new ObjectNotFoundException("Não há exercícios cadastrados");
		}
		return repository.findAll(Sort.by(Sort.Direction.ASC, "id"));
	}

	@Override
	public List<Exercise> findByDescription(String description) {
		if(repository.findByDescriptionContainsIgnoreCase(description).isEmpty()) {
			throw new ObjectNotFoundException("Exercício %s inexistente".formatted(description));
		}
		return repository.findByDescriptionContainsIgnoreCase(description);
	}

}
