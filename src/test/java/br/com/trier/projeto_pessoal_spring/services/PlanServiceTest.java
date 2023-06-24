package br.com.trier.projeto_pessoal_spring.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.function.ObjDoubleConsumer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.projeto_pessoal_spring.BaseTests;
import br.com.trier.projeto_pessoal_spring.domain.Plan;
import br.com.trier.projeto_pessoal_spring.services.exceptions.IntegrityViolationException;
import br.com.trier.projeto_pessoal_spring.services.exceptions.ObjectNotFoundException;
import jakarta.transaction.Transactional;

@Transactional
public class PlanServiceTest extends BaseTests{
	
	@Autowired
	private PlanService service;
	
	@Test
	@DisplayName("Teste inserir plano")
	void insertTest() {
		var plano = new Plan(null, "Teste", 100.0);
		service.insert(plano);
		assertEquals(1, service.listAll().size());
	}

	@Test
	@DisplayName("Teste inserir plano com descrição duplicada")
	void insertDuplicatedTest() {
		var plano = new Plan(null, "Teste", 100.0);
		var plano2 = new Plan(null, "Teste", 100.0);
		var plano3 = new Plan(null, "teste", 100.0);
		
		service.insert(plano);
		
		var exception = assertThrows(IntegrityViolationException.class, () -> service.insert(plano2));
		var exception2 = assertThrows(IntegrityViolationException.class, () -> service.insert(plano3));
		
		assertEquals("A descrição desse plano já existe", exception.getMessage());
		assertEquals("A descrição desse plano já existe", exception2.getMessage());
	}
	
	@Test
	@DisplayName("Teste inserir plano inválido")
	void insertInvalidTest() {
		var plano = new Plan(null, "", 100.0);
		var exception = assertThrows(IntegrityViolationException.class, () -> service.insert(plano));
		assertEquals("Preencha a descrição do plano", exception.getMessage());
		
		var plano2 = new Plan(null, "teste", 0.0);
		var exception2 = assertThrows(IntegrityViolationException.class, () -> service.insert(plano2));
		assertEquals("O preço não deve ser nulo ou igual a 0", exception2.getMessage());
	}
	
	@Test
	@DisplayName("Teste alterar plano")
	@Sql({"classpath:/resources/sqls/plan.sql"})
	void updateTest() {
		var plano = new Plan(1, "Teste", 100.0);
		service.update(plano);
		assertEquals("Teste", service.listAll().get(0).getDescription());
	}
	
	@Test
	@DisplayName("Teste alterar plano inexistente")
	@Sql({"classpath:/resources/sqls/plan.sql"})
	void updateNotFoundTest() {
		var plano = new Plan(9, "Teste", 100.0);
		var exception = assertThrows(ObjectNotFoundException.class, () -> service.update(plano));
		assertEquals("Plano inexistente", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste deletar plano")
	@Sql({"classpath:/resources/sqls/plan.sql"})
	void deleteTest() {
		service.delete(1);
		assertEquals(3, service.listAll().size());
	}
	
	@Test
	@DisplayName("Teste deletar plano inexistente")
	@Sql({"classpath:/resources/sqls/plan.sql"})
	void deleteNotFoundTest() {
		var exception = assertThrows(ObjectNotFoundException.class, () -> service.delete(9));
		assertEquals("Plano 9 inexistente", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar plano pelo id")
	@Sql({"classpath:/resources/sqls/plan.sql"})
	void findByIdTest() {
		var plano = service.findById(1);
		assertEquals("Padrão", plano.getDescription());
	}
	
	@Test
	@DisplayName("Teste buscar plano pelo id sem achar")
	@Sql({"classpath:/resources/sqls/plan.sql"})
	void findByIdNotFoundTest() {
		var exception = assertThrows(ObjectNotFoundException.class, () -> service.findById(9));
		assertEquals("Plano 9 inexistente", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar plano pelo preço")
	@Sql({"classpath:/resources/sqls/plan.sql"})
	void findByPriceTest() {
		var plano = service.findByPrice(50.0);
		assertEquals("Colaborador", plano.getDescription());
	}
	
	@Test
	@DisplayName("Teste buscar plano pelo preço sem achar")
	@Sql({"classpath:/resources/sqls/plan.sql"})
	void findByPriceNotFoundTest() {
		var exception = assertThrows(ObjectNotFoundException.class, () -> service.findByPrice(100.0));
		assertEquals("Plano de R$100,00 inexistente", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste listar todos os planos ordenando pelo id")
	@Sql({"classpath:/resources/sqls/plan.sql"})
	void listAllTest() {
		assertEquals(4, service.listAll().size());
		assertEquals("Comerciário", service.listAll().get(2).getDescription());
	}
	
	@Test
	@DisplayName("Teste listar todos os planos com lista vazia")
	void listAllEmptyTest() {
		var exception = assertThrows(ObjectNotFoundException.class, () -> service.listAll());
		assertEquals("Não há planos cadastrados", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar planos pela descrição com like ignorando o case")
	@Sql({"classpath:/resources/sqls/plan.sql"})
	void findByDescriptionTest() {
		var planos = service.findByDescription("ci");
		assertEquals(2, planos.size());
	}
	
	@Test
	@DisplayName("Teste buscar planos pela descrição com like ignorando o case")
	@Sql({"classpath:/resources/sqls/plan.sql"})
	void findByDescriptionNotFoundTest() {
		var exception = assertThrows(ObjectNotFoundException.class, () -> service.findByDescription("bot"));
		assertEquals("Plano bot inexistente", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar planos entre margem de preco")
	@Sql({"classpath:/resources/sqls/plan.sql"})
	void findByPriceBetweenTest() {
		var planos = service.findByPriceBetween(50.0, 70.0);
		assertEquals(3, planos.size());
	}
	
	@Test
	@DisplayName("Teste buscar planos entre margem de preco sem achar")
	@Sql({"classpath:/resources/sqls/plan.sql"})
	void findByPriceBetweenNotFoundTest() {
		var exception = assertThrows(ObjectNotFoundException.class, () -> service.findByPriceBetween(100.0, 120.0));
		assertEquals("Não há planos de R$100,00 a R$120,00", exception.getMessage());
	}
}
