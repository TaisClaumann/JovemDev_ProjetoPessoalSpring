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
				null, new Client(1, null, null, null), new Instructor(1, null, null, null), "48962075300");
		service.insert(telephone);
		assertEquals(1, service.listAll().size());
	}
	
	@Test
	@DisplayName("Teste inserir telefone de um aluno que já consta no banco")
	@Sql({"classpath:/resources/sqls/instructor.sql"})
	@Sql({"classpath:/resources/sqls/client.sql"})
	@Sql({"classpath:/resources/sqls/telephone.sql"})
	void insertWithClientTelephoneInvalidTest() {
		var telephone = new Telephone(
				null, new Client(1, "Amanda", "120.026.539-41", null), null, "(48) 99666-7110");
		var exception = assertThrows(IntegrityViolationException.class, () -> service.insert(telephone));
		assertEquals("Esse telefone já existe", exception.getMessage());
		
		var telephone2 = new Telephone(
				null, new Client(3, "Douglas", "789.563.214-56", null), null, "(48) 99666-7110");
		var exception2 = assertThrows(IntegrityViolationException.class, () -> service.insert(telephone2));
		assertEquals("Esse telefone já existe", exception2.getMessage());
	}
	
	@Test
	@DisplayName("Teste inserir telefone de um instrutor que já consta no banco")
	@Sql({"classpath:/resources/sqls/instructor.sql"})
	@Sql({"classpath:/resources/sqls/client.sql"})
	@Sql({"classpath:/resources/sqls/telephone.sql"})
	void insertWithInstructoTelephoneInvalidTest() {
		var telephone = new Telephone(
				null, null, new Instructor(1, null, "123.456.789-41", null), "(48) 99666-7110");
		var exception = assertThrows(IntegrityViolationException.class, () -> service.insert(telephone));
		assertEquals("Esse telefone já existe", exception.getMessage());
		
		var telephone2 = new Telephone(
				null, null, new Instructor(5, null, "111.456.789-41", null), "(48) 99778-7110");
		var exception2 = assertThrows(IntegrityViolationException.class, () -> service.insert(telephone2));
		assertEquals("Esse telefone já existe", exception2.getMessage());
	}
}
