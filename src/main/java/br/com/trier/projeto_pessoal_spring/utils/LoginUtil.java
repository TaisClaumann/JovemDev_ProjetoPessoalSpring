package br.com.trier.projeto_pessoal_spring.utils;

import br.com.trier.projeto_pessoal_spring.services.exceptions.IntegrityViolationException;

public class LoginUtil {
	
	public static String validateEmail(String email) {
		String regex = "^[A-Za-z0-9._%+-]+@(gmail\\.com|gmail\\.com\\.br|hotmail\\.com|hotmail\\.com\\.br)$";
		if(!email.matches(regex)) {
			throw new IntegrityViolationException("Email inválido: " + email);
		}
		return email;
	}
	
	public static String validatePassword(String password) { 
		if(password.length() < 6) {
			throw new IntegrityViolationException("A senha precisa ter no mínimo 6 caracteres: " + password);
		} else if(!password.matches(".*[!@#$%^&*()-+=].*")) {
			throw new IntegrityViolationException("A senha precisa ter no mínimo 1 caracter especial: " + password);
		} else if(!password.matches(".*[A-Z].*")) {
			throw new IntegrityViolationException("A senha precisa ter no mínimo 1 letra maiúscula: " + password);
		} else if(!password.matches(".*\\d.*")) {
			throw new IntegrityViolationException("A senha precisa ter no mínimo 1 número: " + password);
		}
		return password;
	}
}
