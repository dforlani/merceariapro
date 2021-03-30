package br.com.posweb.merceariapro.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Venda {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY )
  private Long id;

  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
  private LocalDateTime data;

  @OneToMany(
    cascade = CascadeType.ALL,
    orphanRemoval = true,
    mappedBy = "venda",
    fetch = FetchType.LAZY
  )
  @OrderBy("id DESC")
  private List<@NotNull @Valid VendaItem> itens = new ArrayList<>();

  @ManyToOne
  @Valid
  private Cliente cliente;

  @Deprecated
  public Venda() {}

  public Venda(LocalDateTime data) {
    this.data = data;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDateTime getData() {
    return data;
  }

  public void setData(LocalDateTime data) {
    this.data = data;
  }

  public Cliente getCliente() {
    return cliente;
  }

  public void setCliente(Cliente cliente) {
    this.cliente = cliente;
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
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    Venda other = (Venda) obj;
    if (id == null) {
      if (other.id != null) return false;
    } else if (!id.equals(other.id)) return false;
    return true;
  }

  public void addItemVenda(VendaItem item) {
    this.itens.add(0, item);
  }

  public void removerItemVenda(int index) {
    this.itens.remove(index);
  }

  public String getNomeCliente() {
    if (cliente != null) {
      return cliente.getNome();
    }
    return "";
  }

  /**
   * Retorna apenas item que jÃ¡ foram persistidos no banco
   *
   * @return
   */
  public List<VendaItem> getItensPersistidos() {
    List<VendaItem> persistidos = new ArrayList<>();
    itens.forEach(
      s -> {
        System.out.println(s.getId() + " =======================================");
        if (!s.isNovo()) persistidos.add(s);
      }
    );
    return persistidos;
  }
  
  public BigDecimal getSomatorioTotal() {
	  BigDecimal total = new BigDecimal(0);
	  for (VendaItem item : this.getItens()) {
			total = total.add(item.getPrecoTotal());			
		}
	  
	  return total;
  }

  public List<VendaItem> getItens() {
    return itens;
  }

  public void setItens(List<VendaItem> itens) {
    this.itens = itens;
  }
}
