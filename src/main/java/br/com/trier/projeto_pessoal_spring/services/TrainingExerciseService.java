package br.com.trier.projeto_pessoal_spring.services;

import java.util.List;

import br.com.trier.projeto_pessoal_spring.domain.Exercise;
import br.com.trier.projeto_pessoal_spring.domain.TrainingExercise;
import br.com.trier.projeto_pessoal_spring.domain.TrainingPlan;

public interface TrainingExerciseService {

	TrainingExercise insert(TrainingExercise trainingExercise);
	TrainingExercise update(TrainingExercise trainingExercise);
	void delete(Integer id);
	TrainingExercise findById(Integer id);
	List<TrainingExercise> listAll();
	List<TrainingExercise> findByTrainingPlan(TrainingPlan trainingPlan);
	List<TrainingExercise> findByExercise(Exercise exercise);
	List<TrainingExercise> findBySet(Integer set);
	List<TrainingExercise> findByRepetition(Integer repetition);
	List<TrainingExercise> findBySetAndRepetition(Integer set, Integer repetition);
}
