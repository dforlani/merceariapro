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
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Produto {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY )
  @Column(name="produto_id")
  private Long id;

  @NotBlank(message = "O campo nome do produto nÃ£o pode ser vazio")
  @Column(nullable = false)
  private String nome;

  @NotNull(message = "O campo valor nÃƒÆ’Ã‚Â£o pode ser vazio")
  @Column(nullable = false)
  private BigDecimal valor;

  
  //@JoinColumn(name = "produto_id")
  @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<VendaItem> saidas = new ArrayList<>();

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<@NotNull @Valid EntradaProduto> entradas = new ArrayList<>();

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public List<VendaItem> getSaidas() {
    return saidas;
  }

  public void setSaidas(List<VendaItem> saidas) {
    this.saidas = saidas;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public List<EntradaProduto> getEntradas() {
    return entradas;
  }

  public void setEntradas(List<EntradaProduto> entradas) {
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
    return "Produto [nome=" + nome + "]";
  }

  public Produto(
    @NotBlank(message = "O campo nome do produto nÃ£o pode ser vazio") String nome,
    @NotBlank(message = "O campo valor nÃ£o pode ser vazio") BigDecimal valor
  ) {
    this.nome = nome;
    this.valor = valor;
  }

  public void addEntrada(EntradaProduto nova) {
    this.entradas.add(0, nova);
  }

  public void removerEntrada(int index) {
    this.entradas.remove(index);
  }

  @Deprecated
  public Produto() {}

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
    Produto other = (Produto) obj;
    if (id == null) {
      if (other.id != null) return false;
    } else if (!id.equals(other.id)) return false;
    return true;
  }

  public BigDecimal getTotalEntradas() {
    BigDecimal total = new BigDecimal(0);
    for (EntradaProduto entrada : this.getEntradas()) {
      total = total.add(entrada.getQuantidade());
    }

    return total;
  }

  public BigDecimal getTotalSaidas() {
    BigDecimal total = new BigDecimal(0);

    for (VendaItem saida : this.getSaidas()) {
      System.out.println("Saiddddddddddasssss" + saida.getQuantidade());
      total = total.add(saida.getQuantidade());
    }

    return total;
  }

  public BigDecimal getEstocado() {
    BigDecimal total = new BigDecimal(0);
    total = this.getTotalEntradas().subtract(this.getTotalSaidas());
    return total;
  }
}
