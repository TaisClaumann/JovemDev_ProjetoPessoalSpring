package br.com.trier.projeto_pessoal_spring.domain;

import br.com.trier.projeto_pessoal_spring.domain.dto.ClientDTO;
import br.com.trier.projeto_pessoal_spring.utils.CpfUtil;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity(name = "aluno")
@EqualsAndHashCode(of = "id")
public class Client {

	@Id
	@Setter
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "nome")
	private String name;
	
	@Column(unique = true)
	private String cpf;
	
	@ManyToOne
	@JoinColumn(name = "plano")
	private Plan plan;
	
	public Client(ClientDTO dto) {
		this(dto.getId(),
			 dto.getName(), 
			 CpfUtil.formatCPF(dto.getCpf()), 
			 new Plan(dto.getPlanId(), dto.getPlanDescription(), dto.getPlanPrice()));
	}
	
	public ClientDTO toDTO() {
		return new ClientDTO(id, name, cpf, plan.getId(), plan.getDescription(), plan.getPrice());
	}
}
