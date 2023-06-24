package br.com.trier.projeto_pessoal_spring.services.exceptions;

public class IntegrityViolationException extends RuntimeException{

	public IntegrityViolationException(String message) {
		super(message);
	}
}
