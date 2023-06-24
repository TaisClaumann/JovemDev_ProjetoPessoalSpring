package br.com.trier.projeto_pessoal_spring.resources.exceptions;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StandardError {
	
	private LocalDateTime time;
	private Integer status;
	private String error;
	private String url;

}
