package br.com.trier.projeto_pessoal_spring.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.trier.projeto_pessoal_spring.domain.Exercise;
import br.com.trier.projeto_pessoal_spring.domain.TrainingExercise;
import br.com.trier.projeto_pessoal_spring.domain.TrainingPlan;
import br.com.trier.projeto_pessoal_spring.repositories.TrainingExerciseRepository;
import br.com.trier.projeto_pessoal_spring.services.TrainingExerciseService;
import br.com.trier.projeto_pessoal_spring.services.exceptions.IntegrityViolationException;
import br.com.trier.projeto_pessoal_spring.services.exceptions.ObjectNotFoundException;

@Service
public class TrainingExerciseServiceImpl implements TrainingExerciseService{

	@Autowired
	private TrainingExerciseRepository repository;
	
	@Override
	public TrainingExercise insert(TrainingExercise trainingExercise) {
		validateTrainingExercise(trainingExercise);
		return repository.save(trainingExercise);
	}
	
	private void validateTrainingExercise(TrainingExercise trainingExercise) {
		if(trainingExercise == null) {
			throw new IntegrityViolationException("O exercício do treino não pode ser nulo");
		} else if(trainingExercise.getSet() == null || trainingExercise.getSet() == 0) {
			throw new IntegrityViolationException("A serie não pode ser nula ou igual a 0");
		} else if(trainingExercise.getRepetition() == null || trainingExercise.getRepetition() == 0) {
			throw new IntegrityViolationException("O exercício precisa ter no mínimo 1 repetição");
		}
		validateExercise(trainingExercise);
	}
	
	private void validateExercise(TrainingExercise trainingExercise) {
		List<TrainingExercise> exercises = repository.findByTrainingPlan(trainingExercise.getTrainingPlan());
		if (!exercises.isEmpty()) {
	        boolean exerciseExists = exercises.stream()
	                .anyMatch(t -> t.getExercise().equals(trainingExercise.getExercise())
	                        && !t.getId().equals(trainingExercise.getId()));

	        if (exerciseExists) {
	            throw new IntegrityViolationException(String.format("Exercício %s já está na ficha",
	                    trainingExercise.getExercise().getDescription()));
	        }
	    }
	}

	@Override
	public TrainingExercise update(TrainingExercise trainingExercise) {
		if(!listAll().contains(trainingExercise)) {
			throw new ObjectNotFoundException("O exercício-treino não consta em nenhuma ficha");
		}
		return insert(trainingExercise);
	}

	@Override
	public void delete(Integer id) {
		repository.delete(findById(id));
	}

	@Override
	public TrainingExercise findById(Integer id) {
		return repository.findById(id).orElseThrow(() 
				-> new ObjectNotFoundException("Cadastro %s inexistente".formatted(id)));
	}

	@Override
	public List<TrainingExercise> listAll() {
		if(repository.findAll().isEmpty()) {
			throw new ObjectNotFoundException("Não há exercícios de treino cadastrados");
		}
		return repository.findAll(Sort.by(Sort.Direction.ASC, "id"));
	}

	@Override
	public List<TrainingExercise> findByTrainingPlan(TrainingPlan trainingPlan) {
		if(repository.findByTrainingPlan(trainingPlan).isEmpty()) {
			throw new ObjectNotFoundException("Não há exercícios nessa ficha de treino");
		}
		return repository.findByTrainingPlan(trainingPlan);
	}

	@Override
	public List<TrainingExercise> findByExercise(Exercise exercise) {
		if(repository.findByExercise(exercise).isEmpty()) {
			throw new ObjectNotFoundException("Não há treinos com esse exercício");
		}
		return repository.findByExercise(exercise);
	}

	@Override
	public List<TrainingExercise> findBySet(Integer set) {
		if(repository.findBySet(set).isEmpty()) {
			throw new ObjectNotFoundException("Não há treinos com " + set + " serie(s)");
		}
		return repository.findBySet(set);
	}

	@Override
	public List<TrainingExercise> findByRepetition(Integer repetition) {
		if(repository.findByRepetition(repetition).isEmpty()) {
			throw new ObjectNotFoundException("Não há treinos com " + repetition + " repetição(ões)");
		}
		return repository.findByRepetition(repetition);
	}

	@Override
	public List<TrainingExercise> findBySetAndRepetition(Integer set, Integer repetition) {
		if(repository.findBySetAndRepetition(set, repetition).isEmpty()) {
			throw new ObjectNotFoundException(
					"Não há treinos com " + set + " serie(s) e " + repetition + " repetição(ões)");
		}
		return repository.findBySetAndRepetition(set, repetition);
	}
}
