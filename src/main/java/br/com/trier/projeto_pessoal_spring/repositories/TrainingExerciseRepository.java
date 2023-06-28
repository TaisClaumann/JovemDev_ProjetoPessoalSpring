package br.com.trier.projeto_pessoal_spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.trier.projeto_pessoal_spring.domain.Exercise;
import br.com.trier.projeto_pessoal_spring.domain.TrainingExercise;
import br.com.trier.projeto_pessoal_spring.domain.TrainingPlan;
import br.com.trier.projeto_pessoal_spring.domain.dto.ReportExerciseTrendsDTO;

@Repository
public interface TrainingExerciseRepository extends JpaRepository<TrainingExercise, Integer>{

	List<TrainingExercise> findByTrainingPlan(TrainingPlan trainingPlan);
	List<TrainingExercise> findByExercise(Exercise exercise);
	List<TrainingExercise> findBySet(Integer set);
	List<TrainingExercise> findByRepetition(Integer repetition);
	List<TrainingExercise> findBySetAndRepetition(Integer set, Integer repetition);
}
