package br.com.trier.projeto_pessoal_spring.utils;

import br.com.trier.projeto_pessoal_spring.services.exceptions.IntegrityViolationException;

public class CpfUtil {

	public static String formatCPF(String cpf){
		String digitsOnly = cpf.replaceAll("\\D", "");
		if(!digitsOnly.matches("[0-9]+")) {
			throw new IntegrityViolationException("O CPF deve conter apenas números: " + cpf);
		} else if(digitsOnly.length() != 11) {
			throw new IntegrityViolationException("O CPF deve conter 11 números: " + cpf);
		}
		return digitsOnly.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
	}
}
