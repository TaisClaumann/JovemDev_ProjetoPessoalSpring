package br.com.trier.projeto_pessoal_spring.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class InstructorDTO {
	
	private Integer id;
	private String name;
	private String cpf;
	private Double salary;
}
