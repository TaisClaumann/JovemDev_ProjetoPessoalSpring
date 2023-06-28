package br.com.trier.projeto_pessoal_spring.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TrainingExerciseDTO {
	
	private Integer id;
	private TrainingPlanDTO trainingPlan;
	private Integer exerciseId;
	private String exerciseDescription;
	private Integer set;
	private Integer repetition;
}
