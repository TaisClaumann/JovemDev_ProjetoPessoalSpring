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

import br.com.trier.projeto_pessoal_spring.domain.Telephone;
import br.com.trier.projeto_pessoal_spring.domain.dto.TelephoneClientDTO;
import br.com.trier.projeto_pessoal_spring.domain.dto.TelephoneDTO;
import br.com.trier.projeto_pessoal_spring.domain.dto.TelephoneInstructorDTO;
import br.com.trier.projeto_pessoal_spring.services.ClientService;
import br.com.trier.projeto_pessoal_spring.services.InstructorService;
import br.com.trier.projeto_pessoal_spring.services.TelephoneService;
import br.com.trier.projeto_pessoal_spring.utils.TelephoneUtil;

@RestController
@RequestMapping(value = "telephone")
public class TelephoneResource {

	@Autowired
	private TelephoneService service;
	@Autowired 
	private ClientService clientService;
	@Autowired
	private InstructorService instructorService;
	
	@PostMapping("/client")
	public ResponseEntity<TelephoneClientDTO> insert(@RequestBody TelephoneClientDTO telephoneDTO){
		return ResponseEntity.ok(service.insert(new Telephone(
				telephoneDTO.getId(), 
				clientService.findById(telephoneDTO.getClientId()),
				null, 
				telephoneDTO.getTelephone())).toClientDTO());
	}
	
	@PostMapping("/instructor")
	public ResponseEntity<TelephoneInstructorDTO> insert(@RequestBody TelephoneInstructorDTO telephoneDTO){
		return ResponseEntity.ok(service.insert(new Telephone(
				telephoneDTO.getId(), 
				null,
				instructorService.findById(telephoneDTO.getInstructorId()), 
				telephoneDTO.getTelephone())).toInstructorDTO());
	}
	
	@PutMapping("/client/{id}")
	public ResponseEntity<TelephoneClientDTO> update(@PathVariable Integer id, @RequestBody TelephoneClientDTO telephoneDTO){
		Telephone telephone = new Telephone(telephoneDTO.getId(), 
											clientService.findById(telephoneDTO.getClientId()), 
											null,
											telephoneDTO.getTelephone());
		telephone.setId(id);
		return ResponseEntity.ok(service.update(telephone).toClientDTO());
	}
	
	@PutMapping("/instructor/{id}")
	public ResponseEntity<TelephoneInstructorDTO> update(@PathVariable Integer id, @RequestBody TelephoneInstructorDTO telephoneDTO){
		Telephone telephone = new Telephone(telephoneDTO.getId(), 
											null, 
											instructorService.findById(telephoneDTO.getInstructorId()),
											telephoneDTO.getTelephone());
		telephone.setId(id);
		return ResponseEntity.ok(service.update(telephone).toInstructorDTO());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		 service.delete(id);
		 return ResponseEntity.ok().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<TelephoneDTO> findById(@PathVariable Integer id){
		return ResponseEntity.ok(service.findById(id).toDTO());
	}
	
	@GetMapping
	public ResponseEntity<List<TelephoneDTO>> listAll(){
		return ResponseEntity.ok(service.listAll().stream().map(Telephone::toDTO).toList());
	}
	
	@GetMapping("/client/{clientId}")
	public ResponseEntity<List<TelephoneClientDTO>> findByClient(@PathVariable Integer clientId){
		return ResponseEntity.ok(service.findByClient(
				clientService.findById(clientId)).stream().map(Telephone::toClientDTO).toList());
	}
	
	@GetMapping("/instructor/{instructorId}")
	public ResponseEntity<List<TelephoneInstructorDTO>> findByInstructor(@PathVariable Integer instructorId){
		return ResponseEntity.ok(service.findByInstructor(
				instructorService.findById(instructorId)).stream().map(Telephone::toInstructorDTO).toList());
	}
	
	@GetMapping("/telephone/{telephone}")
	public ResponseEntity<TelephoneDTO> findByTelephone(@PathVariable String telephone){
		return ResponseEntity.ok(service.findByTelephone(telephone).toDTO());
	}
}