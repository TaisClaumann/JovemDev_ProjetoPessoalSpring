package br.com.trier.projeto_pessoal_spring.services.exceptions;

public class ObjectNotFoundException extends RuntimeException{
	
	public ObjectNotFoundException(String message) {
		super(message);
	}
}
