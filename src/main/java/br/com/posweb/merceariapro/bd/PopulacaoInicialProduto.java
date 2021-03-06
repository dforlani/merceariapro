package br.com.posweb.merceariapro.bd;

import br.com.posweb.merceariapro.models.EntradaProduto;
import br.com.posweb.merceariapro.models.Produto;
import br.com.posweb.merceariapro.models.Venda;
import br.com.posweb.merceariapro.models.VendaItem;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class PopulacaoInicialProduto implements CommandLineRunner {
  @Autowired
  private br.com.posweb.merceariapro.repositorios.VendaRepositorio vendaRepositorio;

  @Autowired
  private br.com.posweb.merceariapro.repositorios.ProdutoRepositorio produtoRep;

  @Override
  public void run(String... args) throws Exception {
    //INCLUSÃƒÆ’O AUTOMÃƒï¿½TICA DE PRODUTOS
    Produto prodAux = new Produto("Coca-Cola", new BigDecimal(1.80));
    List<EntradaProduto> listaEntradas = new ArrayList<>();
    listaEntradas.add(new EntradaProduto(new BigDecimal(15.10), LocalDate.now()));
    listaEntradas.add(new EntradaProduto(new BigDecimal(17.10), LocalDate.now()));
    listaEntradas.add(new EntradaProduto(new BigDecimal(11.10), LocalDate.now()));
    prodAux.setEntradas(listaEntradas);
    produtoRep.save(prodAux);

    prodAux = new Produto("Farinha Anaconda", new BigDecimal(6.98));
    listaEntradas = new ArrayList<>();
    listaEntradas.add(new EntradaProduto(new BigDecimal(9.10), LocalDate.now()));
    listaEntradas.add(new EntradaProduto(new BigDecimal(7.10), LocalDate.now()));
    listaEntradas.add(new EntradaProduto(new BigDecimal(1.10), LocalDate.now()));
    prodAux.setEntradas(listaEntradas);
    produtoRep.save(prodAux);

    prodAux = new Produto("Barra de Chocolate NestlÃƒÂª", new BigDecimal(7.02));
    listaEntradas = new ArrayList<>();
    listaEntradas.add(new EntradaProduto(new BigDecimal(8.1), LocalDate.now()));
    prodAux.setEntradas(listaEntradas);
    produtoRep.save(prodAux);

    prodAux = new Produto("Sabonete", new BigDecimal(2.35));
    produtoRep.save(prodAux);
    produtoRep.flush();

    //INCLUSÃƒÆ’O AUTOMÃƒï¿½TICA DE VENDAS
    Venda venda = new Venda(LocalDateTime.now());
    List<Produto> produtos = produtoRep.findAll();
    VendaItem vendaItem = new VendaItem(
      new BigDecimal(10),
      produtos.get(0),
      venda,
      new BigDecimal(10.9),
      new BigDecimal(30.60)
    );
    venda.addItemVenda(vendaItem);

    vendaRepositorio.save(venda);
    vendaRepositorio.flush();

    //INCLUSÃƒÆ’O AUTOMÃƒï¿½TICA DE VENDAS
    venda = new Venda(LocalDateTime.now());
    vendaItem =
      new VendaItem(
        new BigDecimal(10),
        produtos.get(0),
        venda,
        new BigDecimal(10.9),
        new BigDecimal(30.60)
      );
    venda.addItemVenda(vendaItem);

    vendaRepositorio.save(venda);
    vendaRepositorio.flush();

    //INCLUSÃƒÆ’O AUTOMÃƒï¿½TICA DE VENDAS
    venda = new Venda(LocalDateTime.now());

    vendaItem =
      new VendaItem(
        new BigDecimal(10),
        produtos.get(0),
        venda,
        new BigDecimal(10.9),
        new BigDecimal(30.60)
      );
    venda.addItemVenda(vendaItem);

    vendaRepositorio.save(venda);
    vendaRepositorio.flush();
  }
}
