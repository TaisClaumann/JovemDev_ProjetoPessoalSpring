package br.com.trier.projeto_pessoal_spring.services.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.projeto_pessoal_spring.domain.Exercise;
import br.com.trier.projeto_pessoal_spring.domain.TrainingExercise;
import br.com.trier.projeto_pessoal_spring.domain.dto.ReportExerciseTrendsDTO;
import br.com.trier.projeto_pessoal_spring.repositories.ExerciseRepository;
import br.com.trier.projeto_pessoal_spring.services.ReportService;

@Service
public class ReportServiceImpl implements ReportService {
	
	@Autowired
	private ExerciseRepository exerciseRepository;

	@Override
	public List<ReportExerciseTrendsDTO> findExerciseTrends(List<TrainingExercise> trainingExercises) {
		Map<Integer, Integer> exerciseOccurrences = findExercisesOccurrences(trainingExercises);
		List<ReportExerciseTrendsDTO> exerciseTrends = new ArrayList<>();
		for (Map.Entry<Integer, Integer> entry : exerciseOccurrences.entrySet()) {
			Exercise exercise = exerciseRepository.findById(entry.getKey()).orElse(null);
				ReportExerciseTrendsDTO exerciseTrend = new ReportExerciseTrendsDTO(
						entry.getKey(), exercise.getDescription(),entry.getValue());
				exerciseTrends.add(exerciseTrend);
		}
		exerciseTrends.sort(Comparator.comparing(ReportExerciseTrendsDTO::getTrend).reversed());
		return exerciseTrends;
	}
	
	private Map<Integer, Integer> findExercisesOccurrences(List<TrainingExercise> trainingExercises){
		Map<Integer, Integer> exerciseOccurrences = new HashMap<>();
		for (TrainingExercise trainingExercise : trainingExercises) {
			Integer exerciseId = trainingExercise.getExercise().getId();
			if (exerciseOccurrences.containsKey(exerciseId)) {
				int occurrences = exerciseOccurrences.get(exerciseId);
				exerciseOccurrences.put(exerciseId, occurrences + 1);
			} else {
				exerciseOccurrences.put(exerciseId, 1);
			}
		}
		return exerciseOccurrences;
	}
}
