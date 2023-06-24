package br.com.trier.projeto_pessoal_spring.utils;

import java.text.DecimalFormat;

public class FormatCpfUtil {

	public static String formatCPF(String cpf){
		DecimalFormat formatation = new DecimalFormat("00000000000");
		long numericCpf = 0L;
		try {
			numericCpf = Long.parseLong(cpf);
		} catch (Exception e) {
			return cpf;
		}
		return formatation.format(numericCpf).replaceFirst("(\\d{3})(\\d{3})(\\d{3})", "$1.$2.$3-");
	}
}
