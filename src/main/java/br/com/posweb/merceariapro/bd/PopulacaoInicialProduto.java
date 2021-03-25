package br.com.posweb.merceariapro.bd;



import java.math.BigDecimal;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import br.com.posweb.merceariapro.models.Produto;



@Component
@Transactional
public class PopulacaoInicialProduto implements CommandLineRunner {


	@Autowired
	private br.com.posweb.merceariapro.repositorios.ProdutoRepositorio produtoRep;

	
	@Override
	public void run(String... args) throws Exception {
		

		//INCLUSÃO AUTOMÁTICA DE PRODUTOS 
		Produto prodAux = new Produto("Coca-Cola", new BigDecimal(1.89));
		produtoRep.save(prodAux);

		prodAux = new Produto("Farinha Anaconda", new BigDecimal(6.98));
		produtoRep.save(prodAux);

		prodAux = new Produto("Barra de Chocolate Nestlê",new BigDecimal( 7.02));
		produtoRep.save(prodAux);

		prodAux = new Produto("Sabonete", new BigDecimal(2.35));
		produtoRep.save(prodAux);
		produtoRep.flush();

	}
}

