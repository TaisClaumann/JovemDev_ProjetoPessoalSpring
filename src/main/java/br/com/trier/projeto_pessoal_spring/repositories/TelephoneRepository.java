package br.com.trier.projeto_pessoal_spring.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.projeto_pessoal_spring.domain.Client;
import br.com.trier.projeto_pessoal_spring.domain.Instructor;
import br.com.trier.projeto_pessoal_spring.domain.Telephone;

@Repository
public interface TelephoneRepository extends JpaRepository<Telephone, Integer>{
	
	List<Client> findByClient(Client client);
	List<Instructor> findByInstructor(Instructor instructor);
	Optional<Telephone> findByTelephone(Telephone telephone);
}
