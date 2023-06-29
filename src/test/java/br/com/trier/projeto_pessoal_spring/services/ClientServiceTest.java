package br.com.trier.projeto_pessoal_spring.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.projeto_pessoal_spring.BaseTests;
import br.com.trier.projeto_pessoal_spring.domain.Client;
import br.com.trier.projeto_pessoal_spring.domain.Plan;
import br.com.trier.projeto_pessoal_spring.services.exceptions.IntegrityViolationException;
import br.com.trier.projeto_pessoal_spring.services.exceptions.ObjectNotFoundException;
import jakarta.transaction.Transactional;

@Transactional
public class ClientServiceTest extends BaseTests{
	
	@Autowired
	private ClientService service;

	@Test
	@DisplayName("Teste inserir cliente")
	@Sql({"classpath:/resources/sqls/plan.sql"})
	void insertTest() {
		var client = new Client(null, "Ana", "12345667899", new Plan(1, null, null));
		service.insert(client);
		assertEquals(1, service.listAll().size());
	}
	
	@Test
	@DisplayName("Teste inserir cliente inválido")
	@Sql({"classpath:/resources/sqls/plan.sql"})
	void insertInvalidTest() {
		var client = new Client(null, null, "12345667899", new Plan(1, null, null));
		var exception = assertThrows(IntegrityViolationException.class, () -> service.insert(client));
		assertEquals("Preencha o nome do cliente", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste inserir cliente com cpf duplicado")
	@Sql({"classpath:/resources/sqls/plan.sql"})
	void insertDuplicatedTest() {
		var client = new Client(null, "Ana", "12345667899", new Plan(1, null, null));
		service.insert(client);
		
		var client2 = new Client(null, "Bruno", "12345667899", new Plan(1, null, null));
		var exception = assertThrows(IntegrityViolationException.class, () -> service.insert(client2));
		assertEquals("O CPF desse cliente já existe", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste alterar cliente")
	@Sql({"classpath:/resources/sqls/client.sql"})
	void updateTest() {
		var client = new Client(1, "Amanda", "14253698752", new Plan(1, null, null));
		service.update(client);
		assertEquals("14253698752", service.listAll().get(0).getCpf());
	}
	
	@Test
	@DisplayName("Teste alterar cliente inexistente")
	@Sql({"classpath:/resources/sqls/client.sql"})
	void updateNotFoundTest() {
		var client = new Client(5, "Amanda", "14253698752", new Plan(1, null, null));
		var exception = assertThrows(ObjectNotFoundException.class, () -> service.update(client));
		assertEquals("Cliente inexistente", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste deletar cliente")
	@Sql({"classpath:/resources/sqls/client.sql"})
	void deleteTest() {
		service.delete(1);
		assertEquals(3, service.listAll().size());
	}
	
	@Test
	@DisplayName("Teste deletar cliente inexistente")
	@Sql({"classpath:/resources/sqls/client.sql"})
	void deleteNotFoundTest() {
		var exception = assertThrows(ObjectNotFoundException.class, () -> service.delete(5));
		assertEquals("Cliente 5 inexistente", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste listar todos os clientes ordenando pelo id")
	@Sql({"classpath:/resources/sqls/client.sql"})
	void listAllTest() {
		var clientes = service.listAll();
		assertEquals(4, clientes.size());
		assertEquals("Douglas", clientes.get(2).getName());
	}
	
	@Test
	@DisplayName("Teste listar todos os clientes com lista vazia")
	void listAllEmptyTest() {
		var exception = assertThrows(ObjectNotFoundException.class, () -> service.listAll());
		assertEquals("Não há clientes cadastrados", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar clientes pelo nome com like ignorando case")
	@Sql({"classpath:/resources/sqls/client.sql"})
	void findByNameTest() {
		var clientes = service.findByName("la");
		assertEquals(2, clientes.size());
	}
	
	@Test
	@DisplayName("Teste buscar clientes pelo nome com like sem achar")
	@Sql({"classpath:/resources/sqls/client.sql"})
	void findByNameNotFoundTest() {
		var exception = assertThrows(ObjectNotFoundException.class, () -> service.findByName("bom"));
		assertEquals("Cliente bom inexistente", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar cliente pelo cpf")
	@Sql({"classpath:/resources/sqls/client.sql"})
	void findByCpfTest() {
		var cliente = service.findByCpf("12000053925");
		assertEquals("Amanda", cliente.getName());
		
		var cliente2 = service.findByCpf("120.000.539-25");
		assertEquals("Amanda", cliente2.getName());
	}
	
	@Test
	@DisplayName("Teste buscar cliente pelo cpf sem achar")
	@Sql({"classpath:/resources/sqls/client.sql"})
	void findByCpfNotFoundTest() {
		var exception = assertThrows(ObjectNotFoundException.class, () -> service.findByCpf("14536987555"));
		assertEquals("Cliente 145.369.875-55 inexistente", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar clientes pelo plano")
	@Sql({"classpath:/resources/sqls/client.sql"})
	void findByPlanTest() {
		var clientes = service.findByPlan(new Plan(1, null, null));
		assertEquals(2, clientes.size());
	}
	
	@Test
	@DisplayName("Teste buscar clientes pelo plano sem achar")
	@Sql({"classpath:/resources/sqls/client.sql"})
	void findByPlanNotFoundTest() {
		var exception = assertThrows(ObjectNotFoundException.class, () -> service.findByPlan(new Plan(4, "Promocional", null)));
		assertEquals("Não há clientes no plano Promocional", exception.getMessage());
	}
}
