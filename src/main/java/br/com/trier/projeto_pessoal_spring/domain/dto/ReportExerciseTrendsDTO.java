package br.com.trier.projeto_pessoal_spring.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ReportExerciseTrendsDTO {

	private Integer exerciseId;
	private String description;
	private Integer trend;
}
