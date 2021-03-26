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
		Cliente prodAux = new Cliente("Marcelo de Souza", new String("Paranavaí"));
		List<EntradaCliente> listaEntradas = new ArrayList<>();
		listaEntradas.add(new EntradaCliente(new String("Tamboara"), LocalDate.now()));
		listaEntradas.add(new EntradaCliente(new String("Alto PR"), LocalDate.now()));
		prodAux.setEntradas(listaEntradas);		
		clienteRep.save(prodAux);

		prodAux = new Cliente("Sergio Gomes", new String("Carapicuíba"));
		listaEntradas = new ArrayList<>();
		listaEntradas.add(new EntradaCliente(new String("Osasco"), LocalDate.now()));
		listaEntradas.add(new EntradaCliente(new String("Quitaúna"), LocalDate.now()));
		prodAux.setEntradas(listaEntradas);		
		clienteRep.save(prodAux);

		prodAux = new Cliente("Laisa Minelli", new String("Curitiba"));
		listaEntradas = new ArrayList<>();
		listaEntradas.add(new EntradaCliente(new String("Portão"), LocalDate.now()));
		prodAux.setEntradas(listaEntradas);		
		clienteRep.save(prodAux);

		prodAux = new Cliente("George Clooney", new String("Foz"));
		clienteRep.save(prodAux);
		clienteRep.flush();

	}
}

