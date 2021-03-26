package br.com.posweb.merceariapro.bd;



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

import br.com.posweb.merceariapro.models.EntradaProduto;
import br.com.posweb.merceariapro.models.Produto;



@Component
@Transactional
public class PopulacaoInicialProduto3 implements CommandLineRunner {


	@Autowired
	private br.com.posweb.merceariapro.repositorios.ProdutoRepositorio produtoRep;

	
	@Override
	public void run(String... args) throws Exception {
		

		//INCLUSÃO AUTOMÁTICA DE PRODUTOS 
		Produto prodAux = new Produto("Coca-Cola", new BigDecimal(1.89));
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
		listaEntradas.add(new EntradaProduto(new BigDecimal(1.10),LocalDate.now()));
		prodAux.setEntradas(listaEntradas);		
		produtoRep.save(prodAux);

		prodAux = new Produto("Barra de Chocolate Nestlê",new BigDecimal( 7.02));
		listaEntradas = new ArrayList<>();
		listaEntradas.add(new EntradaProduto(new BigDecimal(8.10), LocalDate.now()));
		prodAux.setEntradas(listaEntradas);		
		produtoRep.save(prodAux);

		prodAux = new Produto("Sabonete", new BigDecimal(2.35));
		produtoRep.save(prodAux);
		produtoRep.flush();

	}
}

