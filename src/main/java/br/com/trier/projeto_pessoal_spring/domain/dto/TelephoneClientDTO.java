package br.com.trier.projeto_pessoal_spring.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TelephoneClientDTO {

	private Integer id;
	private Integer clientId;
	private String clientName;
	private String clientCpf;
	private String telephone;
}
