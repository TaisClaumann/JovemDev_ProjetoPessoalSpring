package br.com.trier.projeto_pessoal_spring.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TrainingPlanDTO {
	
	private Integer id;
	private String name;
	private ClientDTO client;
	private Integer instructorId;
	private String instructorName;
	private String instructorCpf;

}
