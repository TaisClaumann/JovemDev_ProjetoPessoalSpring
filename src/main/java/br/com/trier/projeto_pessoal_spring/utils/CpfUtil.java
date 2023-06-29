package br.com.trier.projeto_pessoal_spring.utils;

import br.com.trier.projeto_pessoal_spring.services.exceptions.IntegrityViolationException;

public class CpfUtil {

	public static String formatCPF(String cpf){
		validateCPF(cpf);
		return cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
	}
	
	public static void validateCPF(String cpf) {
		if(cpf == null || cpf.isBlank()) {
			throw new IntegrityViolationException("O CPF não pode ser vazio");
		}
		String digitsOnly = cpf.replaceAll("[^a-zA-Z0-9]", "");
		if(digitsOnly.matches(".*[a-zA-Z].*")) {
			throw new IntegrityViolationException("O CPF deve conter apenas números: " + cpf);
		} else if(digitsOnly.length() != 11) {
			throw new IntegrityViolationException("O CPF deve conter 11 números: " + cpf);
		}
	}
}