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

import br.com.trier.projeto_pessoal_spring.domain.TrainingPlan;
import br.com.trier.projeto_pessoal_spring.domain.dto.TrainingPlanDTO;
import br.com.trier.projeto_pessoal_spring.services.ClientService;
import br.com.trier.projeto_pessoal_spring.services.InstructorService;
import br.com.trier.projeto_pessoal_spring.services.TrainingPlanService;

@RestController
@RequestMapping("/training-plans")
public class TrainingPlanResource {
	
	@Autowired
	private TrainingPlanService service;
	@Autowired
	private ClientService clientService;
	@Autowired
	private InstructorService instructorService;
	
	@Secured({"ROLE_ADMIN"})
	@PostMapping
	public ResponseEntity<TrainingPlanDTO> insert(@RequestBody TrainingPlanDTO trainingPlanDTO){
		return ResponseEntity.ok(service.insert(new TrainingPlan(trainingPlanDTO.getId(),
																 trainingPlanDTO.getName(),
																 clientService.findById(trainingPlanDTO.getClient().getId()),
																 instructorService.findById(trainingPlanDTO.getInstructorId()))).toDTO());
	}
	
	@Secured({"ROLE_ADMIN"})
	@PutMapping("/{id}")
	public ResponseEntity<TrainingPlanDTO> update(@PathVariable Integer id, @RequestBody TrainingPlanDTO trainingPlanDTO){
		TrainingPlan trainingPlan = new TrainingPlan(trainingPlanDTO.getId(),
				 									 trainingPlanDTO.getName(),
				 									 clientService.findById(trainingPlanDTO.getClient().getId()),
				 									 instructorService.findById(trainingPlanDTO.getInstructorId()));
		trainingPlan.setId(id);
		return ResponseEntity.ok(service.update(trainingPlan).toDTO());
	}

	@Secured({"ROLE_ADMIN"})
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/{id}")
	public ResponseEntity<TrainingPlanDTO> findById(@PathVariable Integer id){
		return ResponseEntity.ok(service.findById(id).toDTO());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping
	public ResponseEntity<List<TrainingPlanDTO>> listAll(){
		return ResponseEntity.ok(service.listAll().stream().map(TrainingPlan::toDTO).toList());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/name/{name}")
	public ResponseEntity<List<TrainingPlanDTO>> findByName(@PathVariable String name){
		return ResponseEntity.ok(service.findByName(name).stream().map(TrainingPlan::toDTO).toList());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/client/{clientId}")
	public ResponseEntity<List<TrainingPlanDTO>> findByClient(@PathVariable Integer clientId){
		return ResponseEntity.ok(service.findByClient(
				clientService.findById(clientId)).stream().map(TrainingPlan::toDTO).toList());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/instructor/{instructorId}")
	public ResponseEntity<List<TrainingPlanDTO>> findByInstructor(@PathVariable Integer instructorId){
		return ResponseEntity.ok(service.findByInstructor(
				instructorService.findById(instructorId)).stream().map(TrainingPlan::toDTO).toList());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/name-and-client/{name}/{clientId}")
	public ResponseEntity<TrainingPlanDTO> findByNameAndClient(@PathVariable Integer clientId, @PathVariable String name){
		return ResponseEntity.ok(service.findByNameAndClient(name, clientService.findById(clientId)).toDTO());
	}
}
