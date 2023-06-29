package br.com.trier.projeto_pessoal_spring.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.projeto_pessoal_spring.domain.Client;
import br.com.trier.projeto_pessoal_spring.domain.dto.ClientDTO;
import br.com.trier.projeto_pessoal_spring.services.ClientService;
import br.com.trier.projeto_pessoal_spring.services.PlanService;

@RestController
@RequestMapping(value = "/clients")
public class ClientResource {
	
	@Autowired
	private ClientService service;
	@Autowired
	private PlanService planService;
	
	@Secured({"ROLE_ADMIN"})
	@PostMapping
	public ResponseEntity<ClientDTO> insert(@RequestBody ClientDTO clientDTO){
		planService.findById(clientDTO.getPlanId());
		return ResponseEntity.ok(service.insert(new Client(clientDTO)).toDTO());
	}
	
	@Secured({"ROLE_ADMIN"})
	@PutMapping("/{id}")
	public ResponseEntity<ClientDTO> update(@RequestBody ClientDTO clientDTO, @PathVariable Integer id){
		planService.findById(clientDTO.getPlanId());
		Client client = new Client(clientDTO);
		client.setId(id);
		return ResponseEntity.ok(service.update(client).toDTO());
	}
	
	@Secured({"ROLE_ADMIN"})
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/{id}")
	public ResponseEntity<ClientDTO> findById(@PathVariable Integer id){
		return ResponseEntity.ok(service.findById(id).toDTO());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping
	public ResponseEntity<List<ClientDTO>> listaAll(){
		return ResponseEntity.ok(service.listAll().stream().map(Client::toDTO).toList());
	}

	@Secured({"ROLE_USER"})
	@GetMapping("/name/{name}")
	public ResponseEntity<List<ClientDTO>> findByName(@PathVariable String name){
		return ResponseEntity.ok(service.findByName(name).stream().map(Client::toDTO).toList());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/cpf/{cpf}")
	public ResponseEntity<ClientDTO> findByCpf(@PathVariable String cpf){
		return ResponseEntity.ok(service.findByCpf(cpf).toDTO());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/plan/{planId}")
	public ResponseEntity<List<ClientDTO>> findByPlan(@PathVariable Integer planId){
		return ResponseEntity.ok(service.findByPlan(
				planService.findById(planId)).stream().map(Client::toDTO).toList());
	}
}
