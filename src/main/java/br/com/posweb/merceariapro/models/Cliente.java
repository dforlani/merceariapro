package br.com.posweb.merceariapro.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Cliente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private Long id;

	@NotBlank(message = "O campo nome do 'cliente' não pode ser vazio")
	@Column(nullable = false)
	private String nome;

	@NotBlank(message = "O campo 'cidade' não pode ser vazio")
	@Column(nullable = false)	
	private String cidade;
	
	@NotBlank(message = "O campo 'email' não pode ser vazio")
	@Column(nullable = false)	
	private String email;
	
	@NotBlank(message = "O campo 'telefone' não pode ser vazio")
	@Column(nullable = false)	
	private String telefone;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<@NotNull @Valid EntradaCliente> entradas = new ArrayList<>();
	
	// getters and setter
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<EntradaCliente> getEntradas() {
		return entradas;
	}

	public void setEntradas(List<EntradaCliente> entradas) {
		this.entradas = entradas;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}


	@Override
	public String toString() {
		return "Cliente [nome=" + nome + "]";
	}

	public Cliente(@NotBlank(message = "O campo 'nome' do cliente não pode ser vazio") String nome,
			@NotBlank(message = "O campo 'cidade' não pode ser vazio") String cidade,
			@NotBlank(message = "O campo 'email' não pode ser vazio") String email,
			@NotBlank(message = "O campo 'telefone' não pode ser vazio") String telefone) {
		this.nome = nome;
		this.cidade = cidade;
		this.email = email;
		this.telefone = telefone;
	}
	
	public void addEntrada(EntradaCliente nova) {
		this.entradas.add(0,nova);
	}
	
	public void removerEntrada(int index) {
		this.entradas.remove(index);
	}

	@Deprecated
	public Cliente() {
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
