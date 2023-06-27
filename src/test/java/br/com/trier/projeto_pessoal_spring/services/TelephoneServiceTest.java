package br.com.trier.projeto_pessoal_spring.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.projeto_pessoal_spring.BaseTests;
import br.com.trier.projeto_pessoal_spring.domain.Client;
import br.com.trier.projeto_pessoal_spring.domain.Instructor;
import br.com.trier.projeto_pessoal_spring.domain.Telephone;
import br.com.trier.projeto_pessoal_spring.services.exceptions.IntegrityViolationException;
import jakarta.transaction.Transactional;

@Transactional
public class TelephoneServiceTest extends BaseTests{
	
	@Autowired
	private TelephoneService service;
	
	@Test
	@DisplayName("Teste inserir telefone")
	@Sql({"classpath:/resources/sqls/client.sql"})
	@Sql({"classpath:/resources/sqls/instructor.sql"})
	void insertTest() {
		var telephone = new Telephone(
				null, new Client(1, null, null, null), new Instructor(1, null, null, null), "4896207530");
		service.insert(telephone);
		assertEquals(1, service.listAll().size());
	}
	
	@Test
	@DisplayName("Teste inserir telefone inválido")
	@Sql({"classpath:/resources/sqls/client.sql"})
	@Sql({"classpath:/resources/sqls/instructor.sql"})
	void insertInvalidTest() {
		var telephone = new Telephone(
				null, new Client(1, null, null, null), new Instructor(1, null, null, null), "");
		var exception = assertThrows(IntegrityViolationException.class, () -> service.insert(telephone));
		assertEquals("Preencha o número de telefone", exception.getMessage());
	}
}
