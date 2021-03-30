package br.com.posweb.merceariapro.models;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class EntradaCliente {

	@Deprecated
	public EntradaCliente() {
	}

	public EntradaCliente(String cidade, String email, String telefone, LocalDate data) {
		super();
		this.cidade = cidade;
		this.email = email;
		this.telefone = telefone;
		this.data = data;
	}

	public EntradaCliente(Long id, String cidade, String email, String telefone, LocalDate data) {
		super();
		this.id = id;
		this.cidade = cidade;
		this.email = email;
		this.telefone = telefone;
		this.data = data;
	}

	@ManyToOne
	private Cliente cliente;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private Long id;

	@Column(nullable = false)
	@NotNull
	private String cidade;
	
	@Column(nullable = false)
	@NotNull
	private String email;

	@Column(nullable = false)
	@NotNull
	private String telefone;

	@Column(name = "data")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull
	private LocalDate data;

	
	// get
	public Long getId() {
		return id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public void setId(Long id) {
		this.id = id;
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

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((cidade == null) ? 0 : cidade.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((telefone == null) ? 0 : telefone.hashCode());
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
		
		EntradaCliente other = (EntradaCliente) obj;
		
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		
		if (cidade == null) {
			if (other.cidade != null)
				return false;
		} else if (!cidade.equals(other.cidade))
			return false;

		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		
		if (telefone == null) {
			if (other.telefone != null)
				return false;
		} else if (!telefone.equals(other.telefone))
			return false;
		
		return true;
	}

}
