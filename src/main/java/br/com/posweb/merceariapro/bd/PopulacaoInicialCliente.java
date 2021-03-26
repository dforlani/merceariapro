package br.com.posweb.merceariapro.bd;



import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import br.com.posweb.merceariapro.models.Cliente;
import br.com.posweb.merceariapro.models.EntradaCliente;



@Component
@Transactional
public class PopulacaoInicialCliente implements CommandLineRunner {


	@Autowired
	private br.com.posweb.merceariapro.repositorios.ClienteRepositorio clienteRep;

	
	@Override
	public void run(String... args) throws Exception {
		

		//INCLUSÃO AUTOMÁTICA DE PRODUTOS 
		Cliente prodAux = new Cliente("Marcelo de Souza", new BigDecimal(1.89));
		List<EntradaCliente> listaEntradas = new ArrayList<>();
		listaEntradas.add(new EntradaCliente(new BigDecimal(15.10), LocalDate.now()));
		listaEntradas.add(new EntradaCliente(new BigDecimal(17.10), LocalDate.now()));
		listaEntradas.add(new EntradaCliente(new BigDecimal(11.10), LocalDate.now()));
		prodAux.setEntradas(listaEntradas);		
		clienteRep.save(prodAux);

		prodAux = new Cliente("Sergio Gomes", new BigDecimal(6.98));
		listaEntradas = new ArrayList<>();
		listaEntradas.add(new EntradaCliente(new BigDecimal(9.10), LocalDate.now()));
		listaEntradas.add(new EntradaCliente(new BigDecimal(7.10), LocalDate.now()));
		listaEntradas.add(new EntradaCliente(new BigDecimal(1.10),LocalDate.now()));
		prodAux.setEntradas(listaEntradas);		
		clienteRep.save(prodAux);

		prodAux = new Cliente("Laisa Minelli",new BigDecimal( 7.02));
		listaEntradas = new ArrayList<>();
		listaEntradas.add(new EntradaCliente(new BigDecimal(8.10), LocalDate.now()));
		prodAux.setEntradas(listaEntradas);		
		clienteRep.save(prodAux);

		prodAux = new Cliente("George Clooney", new BigDecimal(2.35));
		clienteRep.save(prodAux);
		clienteRep.flush();

	}
}

