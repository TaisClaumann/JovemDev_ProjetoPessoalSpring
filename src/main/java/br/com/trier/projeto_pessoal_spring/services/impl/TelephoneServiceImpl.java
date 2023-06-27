package br.com.trier.projeto_pessoal_spring.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.projeto_pessoal_spring.domain.Client;
import br.com.trier.projeto_pessoal_spring.domain.Instructor;
import br.com.trier.projeto_pessoal_spring.domain.Telephone;
import br.com.trier.projeto_pessoal_spring.repositories.TelephoneRepository;
import br.com.trier.projeto_pessoal_spring.services.TelephoneService;
import br.com.trier.projeto_pessoal_spring.services.exceptions.IntegrityViolationException;

@Service
public class TelephoneServiceImpl implements TelephoneService{
	
	@Autowired
	private TelephoneRepository repository;

	@Override
	public Telephone insert(Telephone telephone) {
		validateTelephone(telephone);
		return null;
	}
	
	private void validateTelephone(Telephone telephone) {
		if(telephone == null) {
			throw new IntegrityViolationException("O telefone não pode ser nulo");
		} else if(telephone.getTelefone() == null || telephone.getTelefone().isBlank()) {
			throw new IntegrityViolationException("Preencha o número de telefone");
		}
	}
	
	private void validatePhoneNumber(Telephone telephone) {
		
	}

	@Override
	public Telephone update(Telephone telephone) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Telephone findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Telephone> listAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Telephone> findByClient(Client client) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Telephone> findByInstructor(Instructor instructor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Client findClientByTelephone(Telephone telephone) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Instructor findInstructorByTelephone(Telephone telephone) {
		// TODO Auto-generated method stub
		return null;
	}
}
