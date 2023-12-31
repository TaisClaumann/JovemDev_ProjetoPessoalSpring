package br.com.trier.projeto_pessoal_spring.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.trier.projeto_pessoal_spring.domain.Client;
import br.com.trier.projeto_pessoal_spring.domain.Instructor;
import br.com.trier.projeto_pessoal_spring.domain.Telephone;
import br.com.trier.projeto_pessoal_spring.repositories.TelephoneRepository;
import br.com.trier.projeto_pessoal_spring.services.ClientService;
import br.com.trier.projeto_pessoal_spring.services.InstructorService;
import br.com.trier.projeto_pessoal_spring.services.TelephoneService;
import br.com.trier.projeto_pessoal_spring.services.exceptions.IntegrityViolationException;
import br.com.trier.projeto_pessoal_spring.services.exceptions.ObjectNotFoundException;
import br.com.trier.projeto_pessoal_spring.utils.TelephoneUtil;

@Service
public class TelephoneServiceImpl implements TelephoneService{
	
	@Autowired
	private TelephoneRepository repository;
	
	@Override
	public Telephone insert(Telephone telephone) {
		validateTelephone(telephone);
		return repository.save(telephone);
	}
	
	private void validateTelephone(Telephone telephone) {
		if(telephone == null) {
			throw new IntegrityViolationException("O telefone não pode ser nulo");
		} 
		validatePhoneNumber(telephone);
	}
	
	private void validatePhoneNumber(Telephone telephone) {
	    boolean phoneNumberExists = repository.findByTelephone(telephone.getTelephone()).stream()
	        .filter(t -> telephone.getId() == null || !t.getId().equals(telephone.getId()))
	        .anyMatch(t -> isInstructorOrClientTelephone(telephone, t) || !hasMatchingInstructorAndClient(telephone, t));
	    
	    if (phoneNumberExists) {
	        throw new IntegrityViolationException("Esse telefone já existe");
	    }
	}

	private boolean isInstructorOrClientTelephone(Telephone telephone, Telephone existingTelephone) {
	    return telephone.getInstructor() != null && existingTelephone.getInstructor() != null &&
	            !existingTelephone.getInstructor().getCpf().equals(telephone.getInstructor().getCpf()) ||
	            telephone.getClient() != null && existingTelephone.getClient() != null &&
	            !existingTelephone.getClient().getCpf().equals(telephone.getClient().getCpf());
	}
	
	private boolean hasMatchingInstructorAndClient(Telephone telephone, Telephone existingTelephone) {
	    return telephone.getInstructor() == null && existingTelephone.getClient() == null &&
	            existingTelephone.getInstructor().getCpf().equals(telephone.getClient().getCpf()) ||
	            telephone.getClient() == null && existingTelephone.getInstructor() == null &&
	            existingTelephone.getClient().getCpf().equals(telephone.getInstructor().getCpf());
	}

	@Override
	public Telephone update(Telephone telephone) {
		if(!listAll().contains(telephone)) {
			throw new ObjectNotFoundException("Telefone inexistente");
		}
		return insert(telephone);
	}

	@Override
	public void delete(Integer id) {
		repository.delete(findById(id));
		
	}

	@Override
	public Telephone findById(Integer id) {
		return repository.findById(id).orElseThrow(() 
				-> new ObjectNotFoundException("Telefone %s inexistente".formatted(id)));
	}

	@Override
	public List<Telephone> listAll() {
		if(repository.findAll().isEmpty()) {
			throw new ObjectNotFoundException("Não há telefones cadastrados");
		}
		return repository.findAll(Sort.by(Sort.Direction.ASC, "id"));
	}

	@Override
	public List<Telephone> findByClient(Client client) {
		if(repository.findByClient(client).isEmpty()) {
			throw new ObjectNotFoundException(
					"Cliente %s não possui numero de telefone cadastrado"
					.formatted(client.getId()));
		}
		return repository.findByClient(client);
	}

	@Override
	public List<Telephone> findByInstructor(Instructor instructor) {
		if(repository.findByInstructor(instructor).isEmpty()) {
			throw new ObjectNotFoundException(
					"Instrutor %s não possui numero de telefone cadastrado"
					.formatted(instructor.getId()));
		}
		return repository.findByInstructor(instructor);
	}

	@Override
	public List<Telephone> findByTelephone(String telephone) {
		if(repository.findByTelephone(TelephoneUtil.formatTelephone(telephone)).isEmpty()) {
			throw new ObjectNotFoundException("Telefone inexistente");
		}
		return repository.findByTelephone(TelephoneUtil.formatTelephone(telephone));
	}
}
