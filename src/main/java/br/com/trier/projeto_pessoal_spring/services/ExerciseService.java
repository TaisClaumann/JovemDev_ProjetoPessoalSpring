package br.com.trier.projeto_pessoal_spring.services;

import java.util.List;

import br.com.trier.projeto_pessoal_spring.domain.Exercise;

public interface ExerciseService {

	Exercise insert(Exercise exercise);
	Exercise update(Exercise exercise);
	void delete(Integer id);
	Exercise findById(Integer id);
	List<Exercise> listAll();
	List<Exercise> findByDescription(String description);
}
