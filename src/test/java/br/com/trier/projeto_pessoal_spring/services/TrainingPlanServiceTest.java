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
import br.com.trier.projeto_pessoal_spring.domain.TrainingPlan;
import br.com.trier.projeto_pessoal_spring.services.exceptions.IntegrityViolationException;
import br.com.trier.projeto_pessoal_spring.services.exceptions.ObjectNotFoundException;
import jakarta.transaction.Transactional;

@Transactional
public class TrainingPlanServiceTest extends BaseTests{

	@Autowired
	private TrainingPlanService service;
	
	@Test
	@DisplayName("Teste inserir ficha de treino")
	@Sql({"classpath:/resources/sqls/client.sql"})
	@Sql({"classpath:/resources/sqls/instructor.sql"})
	void insertTest() {
		var trainingPlan = new TrainingPlan(
				null, "Treino A", new Client(1, null, null, null), new Instructor(1, null, null, null));
		service.insert(trainingPlan);
		assertEquals(1, service.listAll().size());
	}
	
	@Test
	@DisplayName("Teste inserir ficha de treino inválida")
	@Sql({"classpath:/resources/sqls/client.sql"})
	@Sql({"classpath:/resources/sqls/instructor.sql"})
	void insertInvalidTest() {
		var trainingPlan = new TrainingPlan(
				null, null, new Client(1, null, null, null), new Instructor(1, null, null, null));
		var exception = assertThrows(IntegrityViolationException.class, () -> service.insert(trainingPlan));
		assertEquals("Preencha o nome da ficha", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste inserir ficha de treino com o nome duplicado")
	@Sql({"classpath:/resources/sqls/client.sql"})
	@Sql({"classpath:/resources/sqls/instructor.sql"})
	@Sql({"classpath:/resources/sqls/training_plan.sql"})
	void insertDuplicatedTest() {
		var trainingPlan = new TrainingPlan(
				null, "Treino A", new Client(1, null, null, null), new Instructor(1, null, null, null));
		var exception = assertThrows(IntegrityViolationException.class, () -> service.insert(trainingPlan));
		assertEquals("O cliente 1 já possui a ficha Treino A", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste alterar ficha de treino")
	@Sql({"classpath:/resources/sqls/client.sql"})
	@Sql({"classpath:/resources/sqls/instructor.sql"})
	@Sql({"classpath:/resources/sqls/training_plan.sql"})
	void updateTest() {
		var trainingPlan = new TrainingPlan(
				2, "Treino A", new Client(1, null, null, null), new Instructor(2, null, null, null));
		service.update(trainingPlan);
		assertEquals("Ana", service.listAll().get(0).getInstructor().getName());
	}
	
	@Test
	@DisplayName("Teste alterar ficha de treino inexistente")
	@Sql({"classpath:/resources/sqls/client.sql"})
	@Sql({"classpath:/resources/sqls/instructor.sql"})
	@Sql({"classpath:/resources/sqls/training_plan.sql"})
	void updateNotFoundTest() {
		var trainingPlan = new TrainingPlan(
				1, "Treino A", new Client(1, null, null, null), new Instructor(2, null, null, null));
		var exception = assertThrows(ObjectNotFoundException.class, () -> service.update(trainingPlan));
		assertEquals("Ficha inexistente", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste deletar ficha de treino")
	@Sql({"classpath:/resources/sqls/client.sql"})
	@Sql({"classpath:/resources/sqls/instructor.sql"})
	@Sql({"classpath:/resources/sqls/training_plan.sql"})
	void deleteTest() {
		service.delete(2);
		assertEquals(5, service.listAll().size());
	}
	
	@Test
	@DisplayName("Teste deletar ficha de treino inexistente")
	@Sql({"classpath:/resources/sqls/client.sql"})
	@Sql({"classpath:/resources/sqls/instructor.sql"})
	@Sql({"classpath:/resources/sqls/training_plan.sql"})
	void deleteNotFoundTest() {
		var exception = assertThrows(ObjectNotFoundException.class, () -> service.delete(1));
		assertEquals("Ficha 1 inexistente", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar ficha de treino pelo id")
	@Sql({"classpath:/resources/sqls/client.sql"})
	@Sql({"classpath:/resources/sqls/instructor.sql"})
	@Sql({"classpath:/resources/sqls/training_plan.sql"})
	void findByIdTest() {
		var trainingPlan = service.findById(2);
		assertEquals(1, trainingPlan.getClient().getId());
		assertEquals("Treino A", trainingPlan.getName());
	}
	
	@Test
	@DisplayName("Teste buscar ficha de treino por id inexistente")
	@Sql({"classpath:/resources/sqls/client.sql"})
	@Sql({"classpath:/resources/sqls/instructor.sql"})
	@Sql({"classpath:/resources/sqls/training_plan.sql"})
	void findByIdNotFoundTest() {
		var exception = assertThrows(ObjectNotFoundException.class, () -> service.findById(1));
		assertEquals("Ficha 1 inexistente", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste listar todas as fichas de treino")
	@Sql({"classpath:/resources/sqls/client.sql"})
	@Sql({"classpath:/resources/sqls/instructor.sql"})
	@Sql({"classpath:/resources/sqls/training_plan.sql"})
	void listAllTest() {
		assertEquals(6, service.listAll().size());
	}
	
	@Test
	@DisplayName("Teste listar todas as fichas de treino com lista vazia")
	@Sql({"classpath:/resources/sqls/client.sql"})
	@Sql({"classpath:/resources/sqls/instructor.sql"})
	void listAllEmptyTest() {
		var exception = assertThrows(ObjectNotFoundException.class, () -> service.listAll());
		assertEquals("Não há fichas de treino cadastradas", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar fichas de treino de um cliente")
	@Sql({"classpath:/resources/sqls/client.sql"})
	@Sql({"classpath:/resources/sqls/instructor.sql"})
	@Sql({"classpath:/resources/sqls/training_plan.sql"})
	void findByClientTest() {
		var trainingPlans = service.findByClient(new Client(1, null, null, null));
		assertEquals(3, trainingPlans.size());
	}
	
	@Test
	@DisplayName("Teste buscar fichas de treino de um cliente sem fichas")
	@Sql({"classpath:/resources/sqls/client.sql"})
	@Sql({"classpath:/resources/sqls/instructor.sql"})
	@Sql({"classpath:/resources/sqls/training_plan.sql"})
	void findByClientNotFoundTest() {
		var exception = assertThrows(ObjectNotFoundException.class, () 
				-> service.findByClient(new Client(4, null, null, null)));
		
		assertEquals("Não há fichas de trieno do client: 4", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar fichas de treino de um instrutor")
	@Sql({"classpath:/resources/sqls/client.sql"})
	@Sql({"classpath:/resources/sqls/instructor.sql"})
	@Sql({"classpath:/resources/sqls/training_plan.sql"})
	void findByInstructorTest() {
		var trainingPlans = service.findByInstructor(new Instructor(1, null, null, null));
		assertEquals(4, trainingPlans.size());
	}
	
	@Test
	@DisplayName("Teste buscar fichas de treino de um instrutor sem fichas")
	@Sql({"classpath:/resources/sqls/client.sql"})
	@Sql({"classpath:/resources/sqls/instructor.sql"})
	@Sql({"classpath:/resources/sqls/training_plan.sql"})
	void findByInstructorNotFoundTest() {
		var exception = assertThrows(ObjectNotFoundException.class, () 
				-> service.findByInstructor(new Instructor(3, null, null, null)));
		
		assertEquals("Não há fichas de trieno do instrutor: 3", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar fichas de treino pelo nome da ficha")
	@Sql({"classpath:/resources/sqls/client.sql"})
	@Sql({"classpath:/resources/sqls/instructor.sql"})
	@Sql({"classpath:/resources/sqls/training_plan.sql"})
	void findByNameTest() {
		var trainingPlans = service.findByName("treino A");
		assertEquals(2, trainingPlans.size());
	}
	
	@Test
	@DisplayName("Teste buscar fichas de treino pelo nome da ficha sem achar")
	@Sql({"classpath:/resources/sqls/client.sql"})
	@Sql({"classpath:/resources/sqls/instructor.sql"})
	@Sql({"classpath:/resources/sqls/training_plan.sql"})
	void findByNameNotFoundTest() {
		var exception = assertThrows(ObjectNotFoundException.class, () -> service.findByName("Treino D"));
		assertEquals("Ficha Treino D inexistente", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar ficha de treino pelo nome da ficha e pelo cliente")
	@Sql({"classpath:/resources/sqls/client.sql"})
	@Sql({"classpath:/resources/sqls/instructor.sql"})
	@Sql({"classpath:/resources/sqls/training_plan.sql"})
	void findByNameAndClientTest() {
		var trainingPlans = service.findByNameAndClient("Treino B", new Client(2, null, null, null));
		assertEquals("Treino B", trainingPlans.getName());
		assertEquals(1, trainingPlans.getInstructor().getId());
	}
	
	@Test
	@DisplayName("Teste buscar fichas de treino pelo nome da ficha e pelo client sem achar")
	@Sql({"classpath:/resources/sqls/client.sql"})
	@Sql({"classpath:/resources/sqls/instructor.sql"})
	@Sql({"classpath:/resources/sqls/training_plan.sql"})
	void findByNameAndClientNotFoundTest() {
		var exception = assertThrows(ObjectNotFoundException.class, () 
				-> service.findByNameAndClient("Treino D", new Client(1, null, null, null)));
		assertEquals("Cliente 1 não possui ficha com nome: Treino D", exception.getMessage());
	}
}
