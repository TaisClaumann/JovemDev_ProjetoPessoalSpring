package br.com.trier.projeto_pessoal_spring.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.projeto_pessoal_spring.domain.Client;
import br.com.trier.projeto_pessoal_spring.domain.Plan;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer>{
	
	List<Client> findByNameContainsIgnoreCase(String name);
	Optional<Client> findByCpf(String cpf);
	List<Client> findByPlan(Plan plan);
}
