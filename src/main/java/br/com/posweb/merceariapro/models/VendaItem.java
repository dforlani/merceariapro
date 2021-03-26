package br.com.posweb.merceariapro.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "pedido_item")
public class VendaItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int quantidade;

 
    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Venda pedido;


    public Long getId() {
        return id;
    }

    
    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }




    public VendaItem( int quantidade) {
        this.quantidade = quantidade;
      
    }

    @Deprecated
    public VendaItem() {
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
        VendaItem other = (VendaItem) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }


    public Venda getPedido() {
        return pedido;
    }

    public void setPedido(Venda pedido) {
        this.pedido = pedido;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public VendaItem(int quantidade, Produto produto, Venda pedido) {
        this.quantidade = quantidade;
        this.produto = produto;
        this.pedido = pedido;
   
    }

    public VendaItem(Venda pedido) {
        this.pedido = pedido;

    }

    public String getNomeProduto() {
		if (produto != null) {
			return produto.getNome();
		}
		return "";
	}

}