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
import br.com.trier.projeto_pessoal_spring.services.exceptions.ObjectNotFoundException;
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
	
	@Test
	@DisplayName("Teste alterar telefone")
	@Sql({"classpath:/resources/sqls/instructor.sql"})
	@Sql({"classpath:/resources/sqls/client.sql"})
	@Sql({"classpath:/resources/sqls/telephone.sql"})
	void updateTest() {
		var telephone = new Telephone(2, new Client(1, null, null, null), null, "(48) 99656-7110");
		service.update(telephone);
		assertEquals("(48) 99656-7110", service.listAll().get(0).getTelephone());
	}
	
	@Test
	@DisplayName("Teste alterar telefone inexistente")
	@Sql({"classpath:/resources/sqls/instructor.sql"})
	@Sql({"classpath:/resources/sqls/client.sql"})
	@Sql({"classpath:/resources/sqls/telephone.sql"})
	void updateNotFoundTest() {
		var telephone = new Telephone(1, new Client(1, null, null, null), null, "(48) 99656-7110");
		var exception = assertThrows(ObjectNotFoundException.class, () -> service.update(telephone));
		assertEquals("Telefone inexistente", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste alterar telefone com número duplicado")
	@Sql({"classpath:/resources/sqls/instructor.sql"})
	@Sql({"classpath:/resources/sqls/client.sql"})
	@Sql({"classpath:/resources/sqls/telephone.sql"})
	void updateNumberInvalidTest() {
		var telephone = new Telephone(2, new Client(1, null, "120.026.539-41", null), null, "(48) 99778-7110");
		var exception = assertThrows(IntegrityViolationException.class, () -> service.update(telephone));
		assertEquals("Esse telefone já existe", exception.getMessage());
		
		var telephone2 = new Telephone(6, null, new Instructor(2, null, "123.444.789-41", null), "(48) 99777-7110");
		var exception2 = assertThrows(IntegrityViolationException.class, () -> service.update(telephone2));
		assertEquals("Esse telefone já existe", exception2.getMessage());
	}
	
	@Test
	@DisplayName("Teste deletar telefone")
	@Sql({"classpath:/resources/sqls/instructor.sql"})
	@Sql({"classpath:/resources/sqls/client.sql"})
	@Sql({"classpath:/resources/sqls/telephone.sql"})
	void deleteTest() {
		service.delete(2);
		assertEquals(4, service.listAll().size());
	}
	
	@Test
	@DisplayName("Teste deletar telefone inexistente")
	@Sql({"classpath:/resources/sqls/instructor.sql"})
	@Sql({"classpath:/resources/sqls/client.sql"})
	@Sql({"classpath:/resources/sqls/telephone.sql"})
	void deleteNotFoundTest() {
		var exception = assertThrows(ObjectNotFoundException.class, () -> service.delete(1));
		assertEquals("Telefone 1 inexistente", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar telefone pelo id")
	@Sql({"classpath:/resources/sqls/instructor.sql"})
	@Sql({"classpath:/resources/sqls/client.sql"})
	@Sql({"classpath:/resources/sqls/telephone.sql"})
	void findByIdTest() {
		var telephone = service.findById(2);
		assertEquals("Amanda", telephone.getClient().getName());
	}
	
	@Test
	@DisplayName("Teste buscar telefone por id inexistente")
	@Sql({"classpath:/resources/sqls/instructor.sql"})
	@Sql({"classpath:/resources/sqls/client.sql"})
	@Sql({"classpath:/resources/sqls/telephone.sql"})
	void findByIdNotFoundTest() {
		var exception = assertThrows(ObjectNotFoundException.class, () -> service.findById(1));
		assertEquals("Telefone 1 inexistente", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar todos os telefones")
	@Sql({"classpath:/resources/sqls/instructor.sql"})
	@Sql({"classpath:/resources/sqls/client.sql"})
	@Sql({"classpath:/resources/sqls/telephone.sql"})
	void listAllTest() {
		assertEquals(5, service.listAll().size());
		assertEquals(6, service.listAll().get(3).getId());
	}
	
	@Test
	@DisplayName("Teste buscar todos os telefones com lista vazia")
	@Sql({"classpath:/resources/sqls/instructor.sql"})
	@Sql({"classpath:/resources/sqls/client.sql"})
	void listAllEmptyTest() {
		var exception = assertThrows(ObjectNotFoundException.class, () -> service.listAll());
		assertEquals("Não há telefones cadastrados", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar telefones de um client")
	@Sql({"classpath:/resources/sqls/instructor.sql"})
	@Sql({"classpath:/resources/sqls/client.sql"})
	@Sql({"classpath:/resources/sqls/telephone.sql"})
	void findByClientTest() {
		var telephones = service.findByClient(new Client(1, null, null, null));
		assertEquals(2, telephones.size());
		assertEquals("(48) 99666-7110", telephones.get(0).getTelephone());
	}
	
	@Test
	@DisplayName("Teste buscar telefones de um client que não tem telefone")
	@Sql({"classpath:/resources/sqls/instructor.sql"})
	@Sql({"classpath:/resources/sqls/client.sql"})
	@Sql({"classpath:/resources/sqls/telephone.sql"})
	void findByClientNotFoundTest() {
		var exception = assertThrows(ObjectNotFoundException.class, () -> service.findByClient(new Client(3, null, null, null)));
		assertEquals("Cliente 3 não possui numero de telefone cadastrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar telefones de um instrutor")
	@Sql({"classpath:/resources/sqls/instructor.sql"})
	@Sql({"classpath:/resources/sqls/client.sql"})
	@Sql({"classpath:/resources/sqls/telephone.sql"})
	void findByInstructorTest() {
		var telephones = service.findByInstructor(new Instructor(1, null, null, null));
		assertEquals(1, telephones.size());
		assertEquals("(48) 99666-7110", telephones.get(0).getTelephone());
	}
	
	@Test
	@DisplayName("Teste buscar telefones de um instrutor que não tem telefone")
	@Sql({"classpath:/resources/sqls/instructor.sql"})
	@Sql({"classpath:/resources/sqls/client.sql"})
	@Sql({"classpath:/resources/sqls/telephone.sql"})
	void findByInstructorNotFoundTest() {
		var exception = assertThrows(ObjectNotFoundException.class, () -> service.findByInstructor(new Instructor(3, null, null, null)));
		assertEquals("Instrutor 3 não possui numero de telefone cadastrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar Telephones com um número de telefone")
	@Sql({"classpath:/resources/sqls/instructor.sql"})
	@Sql({"classpath:/resources/sqls/client.sql"})
	@Sql({"classpath:/resources/sqls/telephone.sql"})
	void findByTelephoneTest() {
		var telephones = service.findByTelephone("(48) 99666-7110");
		assertEquals(2, telephones.size());
	}
	
	@Test
	@DisplayName("Teste buscar Telephones com um número de telefone inexistente")
	@Sql({"classpath:/resources/sqls/instructor.sql"})
	@Sql({"classpath:/resources/sqls/client.sql"})
	@Sql({"classpath:/resources/sqls/telephone.sql"})
	void findByTelephoneNotFoundTest() {
		var exception = assertThrows(ObjectNotFoundException.class, () -> service.findByTelephone("(48) 99569-1452"));
		assertEquals("Telefone inexistente", exception.getMessage());
	}
}
