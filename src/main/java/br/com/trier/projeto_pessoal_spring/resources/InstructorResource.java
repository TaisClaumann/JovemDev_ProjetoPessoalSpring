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

import br.com.trier.projeto_pessoal_spring.domain.Instructor;
import br.com.trier.projeto_pessoal_spring.domain.dto.InstructorDTO;
import br.com.trier.projeto_pessoal_spring.services.InstructorService;

@RestController
@RequestMapping(value = "/instructors")
public class InstructorResource {
	
	@Autowired
	private InstructorService service;
	
	@PostMapping
	public ResponseEntity<InstructorDTO> insert(@RequestBody InstructorDTO instructorDTO){
		return ResponseEntity.ok(service.insert(new Instructor(instructorDTO)).toDTO());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<InstructorDTO> update(@RequestBody InstructorDTO instructorDTO, @PathVariable Integer id){
		Instructor instructor = new Instructor(instructorDTO);
		instructor.setId(id);
		return ResponseEntity.ok(service.insert(instructor).toDTO());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<InstructorDTO> findById(@PathVariable Integer id){
		return ResponseEntity.ok(service.findById(id).toDTO());
	}
	
	@GetMapping
	public ResponseEntity<List<InstructorDTO>> listAll(){
		return ResponseEntity.ok(service.listAll().stream().map(Instructor::toDTO).toList());
	}
	
	@GetMapping("/name/{name}")
	public ResponseEntity<List<InstructorDTO>> findByName(@PathVariable String name){
		return ResponseEntity.ok(service.findByName(name).stream().map(Instructor::toDTO).toList());
	}
	
	@GetMapping("/cpf/{cpf}")
	public ResponseEntity<InstructorDTO> findByCpf(@PathVariable String cpf){
		return ResponseEntity.ok(service.findByCpf(cpf).toDTO());
	}
	
	@GetMapping("/salary/{salary}")
	public ResponseEntity<List<InstructorDTO>> findBySalary(@PathVariable Double salary){
		return ResponseEntity.ok(service.findBySalary(salary).stream().map(Instructor::toDTO).toList());
	}
	
	@GetMapping("/between/{initialSalary}/{finalSalary}")
	public ResponseEntity<List<InstructorDTO>> findBySalaryBetween(@PathVariable Double initialSalary, @PathVariable Double finalSalary){
		return ResponseEntity.ok(service.findBySalaryBetween(
				initialSalary, finalSalary).stream().map(Instructor::toDTO).toList());
	}
}
