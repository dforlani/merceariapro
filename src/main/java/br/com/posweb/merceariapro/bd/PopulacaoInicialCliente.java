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
		Cliente cliAux = new Cliente("Marcelo de Souza", new String("Paranavaí"), new String("marcelosouza@mail.com"), new String("(44) 9.8706-5941"));
		List<EntradaCliente> listaEntradas = new ArrayList<>();
		//listaEntradas.add(new EntradaCliente(new String("")));
		//listaEntradas.add(new EntradaCliente(new String(""), LocalDate.now()));
		cliAux.setEntradas(listaEntradas);		
		clienteRep.save(cliAux);

		cliAux = new Cliente("Sergio Gomes", new String("Carapicuíba"), new String("sergiogomes@mail.com"), new String("(11) 9.5906-8741"));
		listaEntradas = new ArrayList<>();
		//listaEntradas.add(new EntradaCliente(new String("Osasco"), LocalDate.now()));
		//listaEntradas.add(new EntradaCliente(new String("Quitaúna"), LocalDate.now()));
		cliAux.setEntradas(listaEntradas);		
		clienteRep.save(cliAux);

		cliAux = new Cliente("Laisa Minelli", new String("Curitiba"), new String("laminelli@mail.com"), new String("(88) 9.2244-4188"));
		listaEntradas = new ArrayList<>();
		//listaEntradas.add(new EntradaCliente(new String("Portão"), LocalDate.now()));
		cliAux.setEntradas(listaEntradas);		
		clienteRep.save(cliAux);

		cliAux = new Cliente("George Clooney", new String("Foz"), new String("geoclooney@mail.com"), new String("(874) 6756-9911"));
		clienteRep.save(cliAux);
		clienteRep.flush();

	}
}

