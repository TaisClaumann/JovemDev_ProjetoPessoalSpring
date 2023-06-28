package br.com.trier.projeto_pessoal_spring.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.projeto_pessoal_spring.BaseTests;
import br.com.trier.projeto_pessoal_spring.domain.Exercise;
import br.com.trier.projeto_pessoal_spring.domain.TrainingExercise;
import br.com.trier.projeto_pessoal_spring.domain.TrainingPlan;
import br.com.trier.projeto_pessoal_spring.services.exceptions.IntegrityViolationException;
import br.com.trier.projeto_pessoal_spring.services.exceptions.ObjectNotFoundException;
import jakarta.transaction.Transactional;

@Transactional
public class TrainingExerciseServiceTest extends BaseTests{

	@Autowired
	private TrainingExerciseService service;
	
	@Test
	@DisplayName("Teste inserir exercício-treino")
	@Sql({"classpath:/resources/sqls/exercise.sql"})
	@Sql({"classpath:/resources/sqls/banco_dados.sql"})
	void insertTest() {
		var trainingExercise = new TrainingExercise(
				null, new TrainingPlan(2, null, null, null), new Exercise(2, null), 4, 8);
		service.insert(trainingExercise);
		assertEquals(1, service.listAll().size());
	}
	
	@Test
	@DisplayName("Teste inserir exercício-treino inválido")
	@Sql({"classpath:/resources/sqls/exercise.sql"})
	@Sql({"classpath:/resources/sqls/banco_dados.sql"})
	void insertInvalidTest() {
		var trainingExercise = new TrainingExercise(
				null, new TrainingPlan(2, null, null, null), new Exercise(2, null), 0, 8);
		var exception = assertThrows(IntegrityViolationException.class, () -> service.insert(trainingExercise));
		assertEquals("A serie não pode ser nula ou igual a 0", exception.getMessage());
		
		var trainingExercise2 = new TrainingExercise(
				null, new TrainingPlan(2, null, null, null), new Exercise(2, null), 4, 0);
		var exception2 = assertThrows(IntegrityViolationException.class, () -> service.insert(trainingExercise2));
		assertEquals("O exercício precisa ter no mínimo 1 repetição", exception2.getMessage());
	}
	
	@Test
	@DisplayName("Teste inserir exercício-treino com exercício duplicado")
	@Sql({"classpath:/resources/sqls/exercise.sql"})
	@Sql({"classpath:/resources/sqls/banco_dados.sql"})
	@Sql({"classpath:/resources/sqls/training_exercise.sql"})
	void insertDuplicatedTest() {
		var trainingExercise = new TrainingExercise(
				null, new TrainingPlan(2, null, null, null), new Exercise(2, "Cadeira Abdutora"), 4, 8);
		var exception = assertThrows(IntegrityViolationException.class, () -> service.insert(trainingExercise));
		assertEquals("Exercício Cadeira Abdutora já está na ficha", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste alterar exercício-treino")
	@Sql({"classpath:/resources/sqls/exercise.sql"})
	@Sql({"classpath:/resources/sqls/banco_dados.sql"})
	@Sql({"classpath:/resources/sqls/training_exercise.sql"})
	void updateTest() {
		var trainingExercise = new TrainingExercise(
				2, new TrainingPlan(2, null, null, null), new Exercise(2, null), 4, 10);
		service.update(trainingExercise);
		assertEquals(10, service.listAll().get(0).getRepetition());
	}
	
	@Test
	@DisplayName("Teste alterar exercício-treino inexiste")
	@Sql({"classpath:/resources/sqls/exercise.sql"})
	@Sql({"classpath:/resources/sqls/banco_dados.sql"})
	@Sql({"classpath:/resources/sqls/training_exercise.sql"})
	void updateNotFoundTest() {
		var trainingExercise = new TrainingExercise(
				1, new TrainingPlan(2, null, null, null), new Exercise(2, null), 4, 10);
		var exception = assertThrows(ObjectNotFoundException.class, () -> service.update(trainingExercise));
		assertEquals("O exercício-treino não consta em nenhuma ficha", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste alterar exercício-treino")
	@Sql({"classpath:/resources/sqls/exercise.sql"})
	@Sql({"classpath:/resources/sqls/banco_dados.sql"})
	@Sql({"classpath:/resources/sqls/training_exercise.sql"})
	void deleteTest() {
		service.delete(2);
		assertEquals(11, service.listAll().size());
	}
	
	@Test
	@DisplayName("Teste alterar exercício-treino inexistente")
	@Sql({"classpath:/resources/sqls/exercise.sql"})
	@Sql({"classpath:/resources/sqls/banco_dados.sql"})
	@Sql({"classpath:/resources/sqls/training_exercise.sql"})
	void deleteNotFoundTest() {
		var exception = assertThrows(ObjectNotFoundException.class, () -> service.delete(1));
		assertEquals("Cadastro 1 inexistente", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar exercício-treino pelo id")
	@Sql({"classpath:/resources/sqls/exercise.sql"})
	@Sql({"classpath:/resources/sqls/banco_dados.sql"})
	@Sql({"classpath:/resources/sqls/training_exercise.sql"})
	void findByIdTest() {
		var trainingExercise = service.findById(2);
		assertEquals(2, trainingExercise.getExercise().getId());
	}
	
	@Test
	@DisplayName("Teste buscar exercício-treino por id inexistente")
	@Sql({"classpath:/resources/sqls/exercise.sql"})
	@Sql({"classpath:/resources/sqls/banco_dados.sql"})
	@Sql({"classpath:/resources/sqls/training_exercise.sql"})
	void findByIdNotFoundTest() {
		var exception = assertThrows(ObjectNotFoundException.class, () -> service.findById(1));
		assertEquals("Cadastro 1 inexistente", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste listar todos os exercícios-treino")
	@Sql({"classpath:/resources/sqls/exercise.sql"})
	@Sql({"classpath:/resources/sqls/banco_dados.sql"})
	@Sql({"classpath:/resources/sqls/training_exercise.sql"})
	void listAllTest() {
		assertEquals(12, service.listAll().size());
	}
	
	@Test
	@DisplayName("Teste listar todos os exercícios-treino com lista vazia")
	@Sql({"classpath:/resources/sqls/exercise.sql"})
	@Sql({"classpath:/resources/sqls/banco_dados.sql"})
	void listAllEmptyTest() {
		var exception = assertThrows(ObjectNotFoundException.class, () -> service.listAll());
		assertEquals("Não há exercícios de treino cadastrados", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar exercícios-treino pela ficha")
	@Sql({"classpath:/resources/sqls/exercise.sql"})
	@Sql({"classpath:/resources/sqls/banco_dados.sql"})
	@Sql({"classpath:/resources/sqls/training_exercise.sql"})
	void findByTrainingPlanTest() {
		var trainingExercises = service.findByTrainingPlan(new TrainingPlan(2, null, null, null));
		assertEquals(3, trainingExercises.size());
	}
	
	@Test
	@DisplayName("Teste buscar exercícios-treino pela ficha sem achar")
	@Sql({"classpath:/resources/sqls/exercise.sql"})
	@Sql({"classpath:/resources/sqls/banco_dados.sql"})
	void findByTrainingPlanNotFoundTest() {
		var exception = assertThrows(ObjectNotFoundException.class, () 
				-> service.findByTrainingPlan(new TrainingPlan(7, null, null, null)));
		assertEquals("Não há exercícios nessa ficha de treino", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar exercícios-treino pelo exercício")
	@Sql({"classpath:/resources/sqls/exercise.sql"})
	@Sql({"classpath:/resources/sqls/banco_dados.sql"})
	@Sql({"classpath:/resources/sqls/training_exercise.sql"})
	void findByExerciseTest() {
		var trainingExercises = service.findByExercise(new Exercise(2, null));
		assertEquals(4, trainingExercises.size());
	}
	
	@Test
	@DisplayName("Teste buscar exercícios-treino pelo exercício sem achar")
	@Sql({"classpath:/resources/sqls/exercise.sql"})
	@Sql({"classpath:/resources/sqls/banco_dados.sql"})
	void findByExerciseNotFoundTest() {
		var exception = assertThrows(ObjectNotFoundException.class, () 
				-> service.findByExercise(new Exercise(7, null)));
		assertEquals("Não há treinos com esse exercício", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar exercícios-treino por uma serie")
	@Sql({"classpath:/resources/sqls/exercise.sql"})
	@Sql({"classpath:/resources/sqls/banco_dados.sql"})
	@Sql({"classpath:/resources/sqls/training_exercise.sql"})
	void findBySetTest() {
		var trainingExercises = service.findBySet(3);
		assertEquals(2, trainingExercises.size());
	}
	
	@Test
	@DisplayName("Teste buscar exercícios-treino pelo exercício sem achar")
	@Sql({"classpath:/resources/sqls/exercise.sql"})
	@Sql({"classpath:/resources/sqls/banco_dados.sql"})
	void findBySetNotFoundTest() {
		var exception = assertThrows(ObjectNotFoundException.class, () 
				-> service.findBySet(5));
		assertEquals("Não há treinos com 5 serie(s)", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar exercícios-treino por uma repetição")
	@Sql({"classpath:/resources/sqls/exercise.sql"})
	@Sql({"classpath:/resources/sqls/banco_dados.sql"})
	@Sql({"classpath:/resources/sqls/training_exercise.sql"})
	void findByRepetitionTest() {
		var trainingExercises = service.findByRepetition(12);
		assertEquals(2, trainingExercises.size());
	}
	
	@Test
	@DisplayName("Teste buscar exercícios-treino por uma repetição sem achar")
	@Sql({"classpath:/resources/sqls/exercise.sql"})
	@Sql({"classpath:/resources/sqls/banco_dados.sql"})
	void findByRepetitionNotFoundTest() {
		var exception = assertThrows(ObjectNotFoundException.class, () 
				-> service.findByRepetition(20));
		assertEquals("Não há treinos com 20 repetição(ões)", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar exercícios-treino por uma serie e uma repetição")
	@Sql({"classpath:/resources/sqls/exercise.sql"})
	@Sql({"classpath:/resources/sqls/banco_dados.sql"})
	@Sql({"classpath:/resources/sqls/training_exercise.sql"})
	void findBySetAndRepetitionTest() {
		var trainingExercises = service.findBySetAndRepetition(3, 12);
		assertEquals(2, trainingExercises.size());
	}
	
	@Test
	@DisplayName("Teste buscar exercícios-treino por uma serie e uma repetição sem achar")
	@Sql({"classpath:/resources/sqls/exercise.sql"})
	@Sql({"classpath:/resources/sqls/banco_dados.sql"})
	void findBySetAndRepetitionNotFoundTest() {
		var exception = assertThrows(ObjectNotFoundException.class, () 
				-> service.findBySetAndRepetition(4, 12));
		assertEquals("Não há treinos com 4 serie(s) e 12 repetição(ões)", exception.getMessage());
	}
}
