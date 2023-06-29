package br.com.trier.projeto_pessoal_spring.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import br.com.trier.projeto_pessoal_spring.domain.Client;
import br.com.trier.projeto_pessoal_spring.domain.Plan;
import br.com.trier.projeto_pessoal_spring.repositories.ClientRepository;
import br.com.trier.projeto_pessoal_spring.services.ClientService;
import br.com.trier.projeto_pessoal_spring.services.exceptions.IntegrityViolationException;
import br.com.trier.projeto_pessoal_spring.services.exceptions.ObjectNotFoundException;
import br.com.trier.projeto_pessoal_spring.utils.CpfUtil;

@Service
public class ClientServiceImpl implements ClientService{
	
	@Autowired
	private ClientRepository repository;
	
	@Override
	public Client insert(Client client) {
		validateClient(client);
		return repository.save(client);
	}
	
	private void validateClient(Client client) {
		if(client == null) {
			throw new IntegrityViolationException("O cliente não pode ser nulo");
		} else if(client.getName() == null || client.getName().isBlank()) {
			throw new IntegrityViolationException("Preencha o nome do cliente");
		}
		validateCpf(client);
	}
	
	private void validateCpf(Client client) {
		Optional<Client> clientFound = repository.findByCpf(client.getCpf());
		if(clientFound.isPresent()) {
			if(clientFound.get().getId() != client.getId()) {
				throw new IntegrityViolationException("O CPF desse cliente já existe");
			}
		}
	}

	@Override
	public Client update(Client client) {
		if(!listAll().contains(client)) {
			throw new ObjectNotFoundException("Cliente inexistente");
		}
		return insert(client);
	}

	@Override
	public void delete(Integer id) {
		repository.delete(findById(id));
	}

	@Override
	public Client findById(Integer id) {
		return repository.findById(id).orElseThrow(() 
				-> new ObjectNotFoundException("Cliente %s inexistente".formatted(id)));
	}

	@Override
	public List<Client> listAll() {
		if(repository.findAll().isEmpty()) {
			throw new ObjectNotFoundException("Não há clientes cadastrados");
		}
		return repository.findAll(Sort.by(Sort.Direction.ASC, "id"));
	}

	@Override
	public List<Client> findByName(String name) {
		if(repository.findByNameContainsIgnoreCase(name).isEmpty()) {
			throw new ObjectNotFoundException("Cliente %s inexistente".formatted(name));
		}
		return repository.findByNameContainsIgnoreCase(name);
	}

	@Override
	public Client findByCpf(String cpf) {
		return repository.findByCpf(CpfUtil.formatCPF(cpf)).orElseThrow(() 
				-> new ObjectNotFoundException("Cliente %s inexistente".formatted(cpf)));
	}

	@Override
	public List<Client> findByPlan(Plan plan) {
		if(repository.findByPlan(plan).isEmpty()) {
			throw new ObjectNotFoundException("Não há clientes no plano " + plan.getDescription());
		}
		return repository.findByPlan(plan);
	}
}
