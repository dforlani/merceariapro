package br.com.posweb.merceariapro.controllers;

import java.util.List;
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

import br.com.posweb.merceariapro.models.EntradaProduto;
import br.com.posweb.merceariapro.models.Produto;
import br.com.posweb.merceariapro.repositorios.ProdutoRepositorio;

@Controller
public class ProdutosController {

	private ProdutoRepositorio produtoRepositorio;

	public ProdutosController(ProdutoRepositorio produtoRepositorio) {
		this.produtoRepositorio = produtoRepositorio;

	}

	@GetMapping("/produtos")
	public String produtos(Model model) {
		model.addAttribute("listaProdutos", produtoRepositorio.findAll());
		return "produtos/index";
	}

	@SuppressWarnings("deprecation")
	@GetMapping("/produtos/novo")
	public String novo(Model model) {

		model.addAttribute("produto", new Produto());

		return "produtos/form";
	}

	@RequestMapping(value="/produtos/salvar", params = {"addEntrada"})
	public String addEntrada(Produto produto, BindingResult bindingResult) {
		produto.addEntrada(new EntradaProduto());
		return "produtos/form";
	}
	
	@RequestMapping(value="/produtos/salvar", params = {"removerEntrada"})
	public String removerEntrada(Produto produto, BindingResult bindingResult, HttpServletRequest req) {
		final Integer itemIndex = Integer.valueOf(req.getParameter("removerEntrada"));
		
		produto.removerEntrada(itemIndex.intValue());
		return "produtos/form";
	}
	
	@GetMapping("/produtos/{id}")
	public String alterar(@PathVariable("id") long id, Model model) {
		Optional<Produto> produtoOpt = produtoRepositorio.findById(id);
		if (produtoOpt.isEmpty()) {
			throw new IllegalArgumentException("Produto inválido.");
		}

		model.addAttribute("produto", produtoOpt.get());

		return "produtos/form";
	}

	@PostMapping("/produtos/salvar")
	public String salvar(@Valid @ModelAttribute("produto") Produto produto,
			BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "produtos/form";
		}
	
		produtoRepositorio.save(produto);
		return "redirect:/produtos";
	}

	@GetMapping("/produtos/excluir/{id}")
	public String excluir(@PathVariable("id") long id) {
		Optional<Produto> produtoOpt = produtoRepositorio.findById(id);
		if (produtoOpt.isEmpty()) {
			throw new IllegalArgumentException("Produto inválido.");
		}

		produtoRepositorio.delete(produtoOpt.get());
		return "redirect:/produtos";
	}

}
