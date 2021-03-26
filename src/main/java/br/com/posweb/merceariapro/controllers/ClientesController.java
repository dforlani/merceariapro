package br.com.posweb.merceariapro.controllers;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.posweb.merceariapro.models.Cliente;
import br.com.posweb.merceariapro.models.EntradaCliente;
import br.com.posweb.merceariapro.models.Produto;
import br.com.posweb.merceariapro.repositorios.ClienteRepositorio;

@Controller
public class ClientesController {

	private ClienteRepositorio clienteRepositorio;

	public ClientesController(ClienteRepositorio clienteRepositorio) {
		this.clienteRepositorio = clienteRepositorio;

	}

	@GetMapping("/clientes")
	public String clientes(Model model) {
		model.addAttribute("listaClientes", clienteRepositorio.findAll());
		return "clientes/index";
	}

	@SuppressWarnings("deprecation")
	@GetMapping("/clientes/novo")
	public String novo(Model model) {

		model.addAttribute("cliente", new Cliente());

		return "clientes/form";
	}

	@RequestMapping(value="/clientes/salvar", params = {"addEntrada"})
	public String addEntrada(Cliente cliente, BindingResult bindingResult) {
		cliente.addEntrada(new EntradaCliente());
		return "clientes/form";
	}
	
	@RequestMapping(value="/clientes/salvar", params = {"removerEntrada"})
	public String removerEntrada(Cliente cliente, BindingResult bindingResult, HttpServletRequest req) {
		final Integer itemIndex = Integer.valueOf(req.getParameter("removerEntrada"));
		
		cliente.removerEntrada(itemIndex.intValue());
		return "clientes/form";
	}
	
	@GetMapping("/clientes/{id}")
	public String alterar(@PathVariable("id") long id, Model model) {
		Optional<Cliente> clienteOpt = clienteRepositorio.findById(id);
		if (clienteOpt.isEmpty()) {
			throw new IllegalArgumentException("Cliente inválido.");
		}

		model.addAttribute("cliente", clienteOpt.get());

		return "clientes/form";
	}

	@PostMapping("/clientes/salvar")
	public String salvar(@Valid @ModelAttribute("cliente") Cliente cliente,
			BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "clientes/form";
		}
	
		clienteRepositorio.save(cliente);
		return "redirect:/clientes";
	}

	@GetMapping("/clientes/excluir/{id}")
	public String excluir(@PathVariable("id") long id) {
		Optional<Cliente> clienteOpt = clienteRepositorio.findById(id);
		if (clienteOpt.isEmpty()) {
			throw new IllegalArgumentException("Cliente inválido.");
		}

		clienteRepositorio.delete(clienteOpt.get());
		return "redirect:/clientes";
	}

}
