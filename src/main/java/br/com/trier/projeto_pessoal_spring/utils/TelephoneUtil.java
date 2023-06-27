package br.com.trier.projeto_pessoal_spring.utils;

import br.com.trier.projeto_pessoal_spring.services.exceptions.IntegrityViolationException;

public class TelephoneUtil {
	
	public static String formatTelephone(String telephone) {
		String digitsOnly = telephone.replaceAll("[()\\.-]", "");
		if(!digitsOnly.matches("[0-9]+")) {
			throw new IntegrityViolationException("O telefone deve conter apenas números: " + telephone);
		} else if(digitsOnly.length() != 11) {
			throw new IntegrityViolationException("O telefone deve conter 11 números: " + telephone);
		}
		return "(" + digitsOnly.substring(0, 2) + ") " +
                digitsOnly.substring(2, 7) + "-" + digitsOnly.substring(7);
	}
}
