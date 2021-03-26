package br.com.posweb.merceariapro.models;

import java.math.BigDecimal;
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
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank(message = "O campo nome do cliente não pode ser vazio")
	@Column(nullable = false)
	private String nome;

	@NotNull(message = "O campo valor não pode ser vazio")
	@Column(nullable = false)	
	private BigDecimal valor;
	


	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<@NotNull @Valid EntradaCliente> entradas = new ArrayList<>();
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

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	@Override
	public String toString() {
		return "Cliente [nome=" + nome + "]";
	}

	public Cliente(@NotBlank(message = "O campo nome do cliente não pode ser vazio") String nome,
			@NotBlank(message = "O campo valor não pode ser vazio") BigDecimal valor) {
		this.nome = nome;
		this.valor = valor;
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
