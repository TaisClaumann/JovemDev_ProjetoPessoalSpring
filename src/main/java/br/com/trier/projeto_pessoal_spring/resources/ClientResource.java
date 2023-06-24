package br.com.trier.projeto_pessoal_spring.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.projeto_pessoal_spring.domain.Client;
import br.com.trier.projeto_pessoal_spring.services.ClientService;
import br.com.trier.projeto_pessoal_spring.services.PlanService;

@RestController
@RequestMapping(value = "/client")
public class ClientResource {
	
	@Autowired
	private ClientService service;
	@Autowired
	private PlanService planService;
	
	@PostMapping
	public ResponseEntity<Client> insert(@RequestBody Client client){
		planService.findById(client.getPlan().getId());
		return ResponseEntity.ok(service.insert(client));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Client> update(@RequestBody Client client, @PathVariable Integer id){
		planService.findById(client.getPlan().getId());
		client.setId(id);
		return ResponseEntity.ok(service.update(client));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Client> findById(@PathVariable Integer id){
		return ResponseEntity.ok(service.findById(id));
	}
	
	@GetMapping
	public ResponseEntity<List<Client>> listaAll(){
		return ResponseEntity.ok(service.listAll());
	}

	@GetMapping("/name/{name}")
	public ResponseEntity<List<Client>> findByName(@PathVariable String name){
		return ResponseEntity.ok(service.findByName(name));
	}
	
	@GetMapping("/cpf/{cpf}")
	public ResponseEntity<Client> findByCpf(@PathVariable String cpf){
		return ResponseEntity.ok(service.findByCpf(cpf));
	}
	
	@GetMapping("/plan/{planId}")
	public ResponseEntity<List<Client>> findByPlan(@PathVariable Integer planId){
		return ResponseEntity.ok(service.findByPlan(planService.findById(planId)));
	}
}
