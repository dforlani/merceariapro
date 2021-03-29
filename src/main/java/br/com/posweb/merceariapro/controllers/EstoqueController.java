package br.com.posweb.merceariapro.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import br.com.posweb.merceariapro.models.Produto;
import br.com.posweb.merceariapro.repositorios.ProdutoRepositorio;

@Controller
public class EstoqueController {
	private ProdutoRepositorio produtoRepositorio;
	
	
	public EstoqueController(ProdutoRepositorio produtoRepositorio){super();this.produtoRepositorio=produtoRepositorio;}


@GetMapping("/estoque")	
	public String estoque(Model model) {
		List<Produto> produtos = produtoRepositorio.findAll();
		
		model.addAttribute("produtos", produtos);
		System.out.println("Size---------------------"+produtos.size());
		
		return "estoque/index";		
	}
	
}

