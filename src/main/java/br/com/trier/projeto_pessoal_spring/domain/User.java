package br.com.trier.projeto_pessoal_spring.domain;

import br.com.trier.projeto_pessoal_spring.domain.dto.UserDTO;
import br.com.trier.projeto_pessoal_spring.utils.LoginUtil;
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

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
@Entity(name = "usuario")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter
	@Column (name = "id")
	private Integer id;
	
	@Column (name = "nome")
	private String name;
	
	@Column (name = "email", unique = true)
	private String email;
	
	@Column (name = "senha")
	private String password;
	
	@Column (name = "permissoes")
	private String roles;
	
	public User(UserDTO dto) {
		this(dto.getId(), dto.getName(), LoginUtil.validateEmail(dto.getEmail()), LoginUtil.validatePassword(dto.getPassword()), dto.getRoles());
	}
	
	public UserDTO toDTO() {
		return new UserDTO(this.id, this.name, this.email, this.password, this.roles);
	}
}
