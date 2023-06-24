package br.com.trier.projeto_pessoal_spring.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ClientDTO {
	
	private Integer id;
	private String name;
	private String cpf;
	private Integer planId;
	private String planDescription;
	private Double planPrice;
}
