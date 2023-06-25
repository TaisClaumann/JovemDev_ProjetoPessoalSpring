package br.com.trier.projeto_pessoal_spring.domain;

import br.com.trier.projeto_pessoal_spring.domain.dto.InstructorDTO;
import br.com.trier.projeto_pessoal_spring.utils.CpfUtil;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity(name = "instrutor")
public class Instructor {
	
	@Id
	@Setter
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "nome")
	private String name;
	
	@Column(unique = true)
	private String cpf;
	
	@Column(name = "salario")
	private Double salary;

	public Instructor(InstructorDTO dto) {
		this(dto.getId(), dto.getName(), CpfUtil.formatCPF(dto.getCpf()), dto.getSalary());
	}
	
	public InstructorDTO toDTO() {
		return new InstructorDTO(id, name, cpf, salary);
	}
}
