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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.projeto_pessoal_spring.domain.Plan;
import br.com.trier.projeto_pessoal_spring.services.PlanService;

@RestController
@RequestMapping(value = "plans")
public class PlanResource {
	
	@Autowired
	private PlanService service;
	
	@PostMapping
	public ResponseEntity<Plan> insert(@RequestBody Plan plan){
		return ResponseEntity.ok(service.insert(plan));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Plan> update(@PathVariable Integer id, @RequestBody Plan plan){
		plan.setId(id);
		return ResponseEntity.ok(service.update(plan));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Plan> findById(@PathVariable Integer id){
		return ResponseEntity.ok(service.findById(id));
	}
	
	@GetMapping
	public ResponseEntity<List<Plan>> listAll(){
		return ResponseEntity.ok(service.listAll());
	}
	
	@GetMapping("/price/{price}")
	public ResponseEntity<Plan> findByPrice(@PathVariable Double price){
		return ResponseEntity.ok(service.findByPrice(price));
	}
	
	@GetMapping("/description/{description}")
	public ResponseEntity<List<Plan>> findByDescription(@PathVariable String description){
		return ResponseEntity.ok(service.findByDescription(description));
	}
	
	@GetMapping("/price")
	public ResponseEntity<List<Plan>> findByPriceBetween(@RequestParam Double initialPrice, @RequestParam Double finalPrice){
		return ResponseEntity.ok(service.findByPriceBetween(initialPrice, finalPrice));
	}
}
