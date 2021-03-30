package br.com.posweb.merceariapro.models;

import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "venda_item")
public class VendaItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY )
  private Long id;

  @NotNull
  private BigDecimal quantidade;

  @NotNull
  private BigDecimal precoUnitario;

  @NotNull
  private BigDecimal precoTotal;

 
  @ManyToOne
  @NotNull
  @JoinColumn(name = "produto_id", nullable=false)
  private Produto produto;

  @ManyToOne
  @JoinColumn(name = "venda_id")
  private Venda venda;

  @Transient
  private boolean novo = false;

  public boolean isNovo() {
    return novo;
  }

  public void setNovo(boolean novo) {
    this.novo = novo;
  }

  public BigDecimal getPrecoUnitario() {
    return precoUnitario;
  }

  public void setPrecoUnitario(BigDecimal precoUnitario) {
    this.precoUnitario = precoUnitario;
  }

  public VendaItem(@NotNull BigDecimal quantidade, Venda venda) {
    super();
    this.quantidade = quantidade;
    this.venda = venda;
  }
  
  public VendaItem(@NotNull BigDecimal quantidade, Venda venda, boolean novo) {
	    super();
	    this.quantidade = quantidade;
	    this.venda = venda;
	    this.novo = novo;
	  }

  public Long getId() {
    return id;
  }

  public BigDecimal getPrecoTotal() {
    return precoTotal;
  }

  public void setPrecoTotal(BigDecimal precoTotal) {
    this.precoTotal = precoTotal;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public BigDecimal getQuantidade() {
    return quantidade;
  }

  public void setQuantidade(BigDecimal quantidade) {
    this.quantidade = quantidade;
  }

  public VendaItem(BigDecimal quantidade) {
    this.quantidade = quantidade;
  }

  @Deprecated
  public VendaItem() {}

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
    VendaItem other = (VendaItem) obj;
    if (id == null) {
      if (other.id != null) return false;
    } else if (!id.equals(other.id)) return false;
    return true;
  }

  public Venda getVenda() {
    return venda;
  }

  public void setVenda(Venda venda) {
    this.venda = venda;
  }

  public Produto getProduto() {
    return produto;
  }

  public void setProduto(Produto produto) {
    this.produto = produto;
  }

  public VendaItem(BigDecimal quantidade, Produto produto, Venda venda) {
    this.quantidade = quantidade;
    this.produto = produto;
    this.venda = venda;
  }

  public VendaItem(
		  BigDecimal quantidade,
    Produto produto,
    Venda venda,
    BigDecimal precoUnitario,
    BigDecimal precoTotal
  ) {
    super();
    this.quantidade = quantidade;
    this.precoUnitario = precoUnitario;
    this.precoTotal = precoTotal;
    this.produto = produto;
    this.venda = venda;
  }

  public VendaItem(Venda venda) {
    this.venda = venda;
  }

  public String getNomeProduto() {
    if (produto != null) {
      return produto.getNome();
    }
    return "";
  }
}
