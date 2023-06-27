package br.com.trier.projeto_pessoal_spring.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TelephoneInstructorDTO {

	private Integer id;
	private Integer instructorId;
	private String instructorName;
	private String instructorCpf;
	private String telephone;
}
