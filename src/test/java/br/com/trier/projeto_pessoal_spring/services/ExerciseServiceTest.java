package br.com.trier.projeto_pessoal_spring.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.projeto_pessoal_spring.BaseTests;
import br.com.trier.projeto_pessoal_spring.domain.Exercise;
import br.com.trier.projeto_pessoal_spring.services.exceptions.IntegrityViolationException;
import br.com.trier.projeto_pessoal_spring.services.exceptions.ObjectNotFoundException;
import jakarta.transaction.Transactional;

@Transactional
public class ExerciseServiceTest extends BaseTests{

	@Autowired
	private ExerciseService service;
	
	@Test
	@DisplayName("Teste inserir exercício")
	void insertTest() {
		var exercise = new Exercise(null, "Flexão");
		service.insert(exercise);
		assertEquals(1, service.listAll().size());
	}
	
	@Test
	@DisplayName("Teste inserir exercício inválido")
	void insertInvalidTest() {
		var exercise = new Exercise(null, "");
		var exception = assertThrows(IntegrityViolationException.class, () -> service.insert(exercise));
		assertEquals("Preencha a descrição do exercício", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste inserir exercício com descrição duplicada")
	@Sql({"classpath:/resources/sqls/exercise.sql"})
	void insertDuplicatedTest() {
		var exercise = new Exercise(null, "Cadeira Abdutora");
		var exception = assertThrows(IntegrityViolationException.class, () -> service.insert(exercise));
		assertEquals("A descrição desse exercício já existe", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste alterar exercício")
	@Sql({"classpath:/resources/sqls/exercise.sql"})
	void updateTest() {
		var exercise = new Exercise(2, "Corrida");
		service.update(exercise);
		assertEquals("Corrida", service.listAll().get(0).getDescription());
	}
	
	@Test
	@DisplayName("Teste alterar exercício inexistente")
	@Sql({"classpath:/resources/sqls/exercise.sql"})
	void updateNotFoundTest() {
		var exercise = new Exercise(1, "Corrida");
		var exception = assertThrows(ObjectNotFoundException.class, () -> service.update(exercise));
		assertEquals("Exercício inexistente", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste alterar exercício com descrição duplicada")
	@Sql({"classpath:/resources/sqls/exercise.sql"})
	void updateDuplicatedTest() {
		var exercise = new Exercise(2, "Flexão");
		var exception = assertThrows(IntegrityViolationException.class, () -> service.update(exercise));
		assertEquals("A descrição desse exercício já existe", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste deletar exercício")
	@Sql({"classpath:/resources/sqls/exercise.sql"})
	void deleteTest() {
		service.delete(2);
		assertEquals(5, service.listAll().size());
	}
	
	@Test
	@DisplayName("Teste deletar exercício inexistente")
	@Sql({"classpath:/resources/sqls/exercise.sql"})
	void deleteNotFoundTest() {
		var exception = assertThrows(ObjectNotFoundException.class, () -> service.delete(1));
		assertEquals("Exercício 1 inexistente", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar exercício pelo id")
	@Sql({"classpath:/resources/sqls/exercise.sql"})
	void findByIdTest() {
		var exercise = service.findById(2);
		assertEquals("Cadeira Abdutora", exercise.getDescription());
	}
	
	@Test
	@DisplayName("Teste buscar exercício por id inexistente")
	@Sql({"classpath:/resources/sqls/exercise.sql"})
	void findByIdNotFoundTest() {
		var exception = assertThrows(ObjectNotFoundException.class, () -> service.findById(1));
		assertEquals("Exercício 1 inexistente", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste listar todos os exercicios ordenando pelo id")
	@Sql({"classpath:/resources/sqls/exercise.sql"})
	void listAllTest() {
		assertEquals(6, service.listAll().size());
		assertEquals(4, service.listAll().get(2).getId());
	}
	
	@Test
	@DisplayName("Teste listar todos os exercicios com lista vazia")
	void listAllEmptyTest() {
		var exception = assertThrows(ObjectNotFoundException.class, () -> service.listAll());
		assertEquals("Não há exercícios cadastrados", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar exercício pela descrição")
	@Sql({"classpath:/resources/sqls/exercise.sql"})
	void findByDescriptionTest() {
		var exercises = service.findByDescription("dutora");
		assertEquals(2, exercises.size());
	}
	
	@Test
	@DisplayName("Teste buscar exercício pela descrição sem achar")
	@Sql({"classpath:/resources/sqls/exercise.sql"})
	void findByDescriptionNotFoundTest() {
		var exception = assertThrows(ObjectNotFoundException.class, () -> service.findByDescription("bom"));
		assertEquals("Exercício bom inexistente", exception.getMessage());
	}
}
