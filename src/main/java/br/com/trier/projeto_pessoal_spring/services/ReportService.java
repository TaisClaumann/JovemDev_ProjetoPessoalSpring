package br.com.trier.projeto_pessoal_spring.services;

import java.util.List;
import java.util.Map;

import br.com.trier.projeto_pessoal_spring.domain.TrainingExercise;
import br.com.trier.projeto_pessoal_spring.domain.dto.ReportExerciseTrendsDTO;

public interface ReportService {

	List<ReportExerciseTrendsDTO> findExerciseTrends(List<TrainingExercise> trainingExercises);
}
