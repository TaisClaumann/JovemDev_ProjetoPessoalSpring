package br.com.trier.projeto_pessoal_spring.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TelephoneDTO {
	
	private Integer id;
	private Integer clientId;
	private String clientName;
	private String clientCpf;
	private Integer instructorId;
	private String instructorName;
	private String instructorCpf;
	private String telephone;

}
