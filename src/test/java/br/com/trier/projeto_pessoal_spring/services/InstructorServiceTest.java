package br.com.trier.projeto_pessoal_spring.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.projeto_pessoal_spring.BaseTests;
import br.com.trier.projeto_pessoal_spring.domain.Instructor;
import br.com.trier.projeto_pessoal_spring.services.exceptions.IntegrityViolationException;
import br.com.trier.projeto_pessoal_spring.services.exceptions.ObjectNotFoundException;
import jakarta.transaction.Transactional;

@Transactional
public class InstructorServiceTest extends BaseTests{

	@Autowired
	private InstructorService service;
	
	@Test
	@DisplayName("Teste inserir instrutor")
	void insertTest() {
		var instructor = new Instructor(null, "Ana Marie", "14523698744", 1000.0);
		service.insert(instructor);
		assertEquals(1, service.listAll().size());
	}
	
	@Test
	@DisplayName("Teste inserir instrutor inválido")
	void insertInvalidTest() {
		var instructor = new Instructor(null, null, "14523698744", 1000.0);
		var exception = assertThrows(IntegrityViolationException.class, () -> service.insert(instructor));
		assertEquals("Preencha o nome do instrutor", exception.getMessage());
		
		var instructor2 = new Instructor(null, "Ana Marie", null, 1000.0);
		var exception2 = assertThrows(IntegrityViolationException.class, () -> service.insert(instructor2));
		assertEquals("Preencha o CPF do instrutor", exception2.getMessage());
		
		var instructor3 = new Instructor(null, "Ana Marie", "14523698744", 0.0);
		var exception3 = assertThrows(IntegrityViolationException.class, () -> service.insert(instructor3));
		assertEquals("Salário inválido: R$0,00", exception3.getMessage());
	}
	
	@Test
	@DisplayName("Teste inserir instrutor com CPF duplicado")
	void insertDuplicatedTest() {
		var instructor = new Instructor(null, "Ana Marie", "14523698744", 1000.0);
		service.insert(instructor);
		
		var instructor2 = new Instructor(null, "Marie", "14523698744", 1500.0);
		var exception = assertThrows(IntegrityViolationException.class, () -> service.insert(instructor2));
		assertEquals("O CPF desse instrutor já existe", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste alterar instrutor")
	@Sql({"classpath:/resources/sqls/instructor.sql"})
	void updateTest() {
		var instructor = new Instructor(1, "Bob 1", "123.456.789-41", 1000.0);
		service.update(instructor);
		assertEquals("Bob 1", service.listAll().get(0).getName());
	}
	
	@Test
	@DisplayName("Teste alterar instrutor inexistente")
	@Sql({"classpath:/resources/sqls/instructor.sql"})
	void updateNotFoundTest() {
		var instructor = new Instructor(10, "Ana Marie", "12345678941", 1000.0);
		var exception = assertThrows(ObjectNotFoundException.class, () -> service.update(instructor));
		assertEquals("Instrutor inexistente", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste deletar instrutor")
	@Sql({"classpath:/resources/sqls/instructor.sql"})
	void deleteTest() {
		service.delete(1);
		assertEquals(4, service.listAll().size());
	}
	
	@Test
	@DisplayName("Teste deletar instrutor inexistente")
	@Sql({"classpath:/resources/sqls/instructor.sql"})
	void deleteNotFoundTest() {
		var exception = assertThrows(ObjectNotFoundException.class, () -> service.delete(10));
		assertEquals("Instrutor 10 inexistente", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar instrutor pelo id")
	@Sql({"classpath:/resources/sqls/instructor.sql"})
	void findByIdTest() {
		var instructor = service.findById(1);
		assertEquals("Bob", instructor.getName());
	}
	
	@Test
	@DisplayName("Teste buscar instrutor por id inexistente")
	@Sql({"classpath:/resources/sqls/instructor.sql"})
	void findByIdNotFoundTest() {
		var exception = assertThrows(ObjectNotFoundException.class, () -> service.findById(10));
		assertEquals("Instrutor 10 inexistente", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste listar todos os instrutores ordenando pelo id")
	@Sql({"classpath:/resources/sqls/instructor.sql"})
	void listAllTest() {
		assertEquals(5, service.listAll().size());
		assertEquals("Juliana", service.listAll().get(3).getName());
	}
	
	@Test
	@DisplayName("Teste listar todos os instrutores com lista vazia")
	void listAllEmptyTest() {
		var exception = assertThrows(ObjectNotFoundException.class, () -> service.listAll());
		assertEquals("Não há instrutores cadastrados", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar instrutores pelo nome com like ignorando case")
	@Sql({"classpath:/resources/sqls/instructor.sql"})
	void findByNameTest() {
		var instructors = service.findByName("Ana");
		assertEquals(2, instructors.size());
		assertEquals("123.444.789-41", instructors.get(0).getCpf());
		assertEquals("333.456.789-41", instructors.get(1).getCpf());
	}
	
	@Test
	@DisplayName("Teste buscar instrutores pelo nome com like ignorando case sem achar")
	@Sql({"classpath:/resources/sqls/instructor.sql"})
	void findByNameNotFoundTest() {
		var exception = assertThrows(ObjectNotFoundException.class, () -> service.findByName("do"));
		assertEquals("Instrutor do inexistente", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar instrutore pelo cpf")
	@Sql({"classpath:/resources/sqls/instructor.sql"})
	void findByCpfTest() {
		var instructor = service.findByCpf("123.456.789-41");
		assertEquals("Bob", instructor.getName());
		
		var instructor2 = service.findByCpf("12345678941");
		assertEquals("Bob", instructor2.getName());
	}
	
	@Test
	@DisplayName("Teste buscar instrutore pelo cpf sem achar")
	@Sql({"classpath:/resources/sqls/instructor.sql"})
	void findByCpfNotFoundTest() {
		var exception = assertThrows(ObjectNotFoundException.class, () -> service.findByCpf("11122233344"));
		assertEquals("CPF 11122233344 inexistente", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar instrutores pelo salário")
	@Sql({"classpath:/resources/sqls/instructor.sql"})
	void findBySalaryTest() {
		var instructors = service.findBySalary(1200.0);
		assertEquals("Ana", instructors.get(0).getName());
	}
	
	@Test
	@DisplayName("Teste buscar instrutores pelo salário sem achar")
	@Sql({"classpath:/resources/sqls/instructor.sql"})
	void findBySalaryNotFoundTest() {
		var exception = assertThrows(ObjectNotFoundException.class, () -> service.findBySalary(900.0));
		assertEquals("Instrutor com salário R$900,00 inexistente", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar instrutores entre margem salarial")
	@Sql({"classpath:/resources/sqls/instructor.sql"})
	void findBySalaryBetweenTest() {
		var instructors = service.findBySalaryBetween(1200.0, 1700.0);
		assertEquals(3, instructors.size());
	}
	
	@Test
	@DisplayName("Teste buscar instrutores entre margem salarial sem achar")
	@Sql({"classpath:/resources/sqls/instructor.sql"})
	void findBySalaryBetweenNotFoundTest() {
		var exception = assertThrows(ObjectNotFoundException.class, () -> service.findBySalaryBetween(100.0, 900.0));
		assertEquals("Não há instrutores com salário entre R$100,00 a R$900,00", exception.getMessage());
	}
}
