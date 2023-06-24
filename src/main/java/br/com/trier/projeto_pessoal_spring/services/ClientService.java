package br.com.trier.projeto_pessoal_spring.services;

import java.util.List;
import java.util.Optional;

import br.com.trier.projeto_pessoal_spring.domain.Client;
import br.com.trier.projeto_pessoal_spring.domain.Plan;

public interface ClientService {
	
	Client insert(Client client);
	Client update(Client client);
	void delete(Integer id);
	Client findById(Integer id);
	List<Client> listAll();
	List<Client> findByName(String name);
	Client findByCpf(String cpf);
	List<Client> findByPlan(Plan plan);
}
