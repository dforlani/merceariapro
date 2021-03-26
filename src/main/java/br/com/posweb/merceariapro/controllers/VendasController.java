package br.com.posweb.merceariapro.controllers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.posweb.merceariapro.dtos.AutoCompleteDTO;
import br.com.posweb.merceariapro.models.Produto;
import br.com.posweb.merceariapro.models.Venda;
import br.com.posweb.merceariapro.models.VendaItem;
import br.com.posweb.merceariapro.repositorios.ProdutoRepositorio;
import br.com.posweb.merceariapro.repositorios.VendaItemRepositorio;
import br.com.posweb.merceariapro.repositorios.VendaRepositorio;

@Controller
public class VendasController {

	private VendaRepositorio vendaRepositorio;
	private VendaItemRepositorio vendaItemRepositorio;	
	private ProdutoRepositorio produtoRepositorio;

	
	private List<Produto> produtosFiltrados = new ArrayList<>();
	

	public VendasController(VendaRepositorio vendaRepositorio, VendaItemRepositorio vendaItemRepositorio,
			 ProdutoRepositorio produtoRepositorio) {
		this.vendaRepositorio = vendaRepositorio;
		this.produtoRepositorio = produtoRepositorio;
		this.vendaItemRepositorio = vendaItemRepositorio;

	}

	@GetMapping("/vendas")
	public String vendas(Model model) {
		List<Venda> vendas = vendaRepositorio.findAll();		
		model.addAttribute("listaVendas", vendas);
		model.addAttribute("venda", new Venda());
		return "vendas/index";
	}

	@GetMapping("/vendas/novo")
	public String novo(Model model) {

		model.addAttribute("venda", new Venda(LocalDateTime.now()));

		return "vendas/form";
	}

	@GetMapping("/vendas/{id}")
	public String alterar(@PathVariable("id") long id, Model model) {
		Optional<Venda> vendaOpt = vendaRepositorio.findById(id);
		if (vendaOpt.isEmpty()) {
			throw new IllegalArgumentException("venda inválido.");
		}

		BigDecimal total = new BigDecimal(0);
		for (VendaItem item : vendaOpt.get().getItens()) {
			total = total.add(item.getProduto().getValor().multiply(new BigDecimal(item.getQuantidade())));			
			
		}
		

		model.addAttribute("total", total);
		model.addAttribute("venda", vendaOpt.get());
		model.addAttribute("vendaItem", new VendaItem(1));

		return "vendas/form";
	}

	@PostMapping("/vendas/salvar")
	public String salvar(@Valid @ModelAttribute("venda") Venda venda, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "vendas/index";
		}
		venda.setData(LocalDateTime.now());
		vendaRepositorio.save(venda);
		model.addAttribute("venda", venda);
		model.addAttribute("vendaItem", new VendaItem(venda));
		return "redirect:/vendas/"+venda.getId();
	}

	@PostMapping("/vendas/{vendaId}/adicionarItem")
	public String adicionarItem(@PathVariable("vendaId") Long vendaId, @RequestParam("produtoId") Long produtoId,
			@ModelAttribute("vendaItem") VendaItem vendaItem, Model model) {
		// if (bindingResult.hasErrors()) {
		// return "vendas/form";
		// }

		Optional<Venda> vendaOpt = vendaRepositorio.findById(vendaId);
		Optional<Produto> produtoOpt = produtoRepositorio.findById(produtoId);

		if (!(vendaOpt.isEmpty()) && !produtoOpt.isEmpty()) {
			vendaItem.setProduto(produtoOpt.get());
			vendaItem.setvenda(vendaOpt.get());

			vendaItemRepositorio.save(vendaItem);
		} else {
			vendaItem = new VendaItem();
		}

		return "redirect:/vendas/" + vendaId;
	}

	@GetMapping("/vendas/excluir/{id}")
	public String excluir(@PathVariable("id") long id) {
		Optional<Venda> vendaOpt = vendaRepositorio.findById(id);
		if (vendaOpt.isEmpty()) {
			throw new IllegalArgumentException("venda inválido.");
		}

		vendaRepositorio.delete(vendaOpt.get());
		return "redirect:/vendas";
	}

	@GetMapping("/vendas/{idvenda}/excluirItem/{idvendaItem}")
	public String excluirItem(@PathVariable("idvenda") long idvenda,
			@PathVariable("idvendaItem") Long idvendaItem) {
		Optional<VendaItem> peditoItemOpt = vendaItemRepositorio.findById(idvendaItem);
		if (peditoItemOpt.isEmpty()) {
			throw new IllegalArgumentException("venda Item inválido.");
		}

		vendaItemRepositorio.delete(peditoItemOpt.get());
		return "redirect:/vendas/" + idvenda;
	}



	@RequestMapping("/vendas/produtosNomeAutoComplete")
	@ResponseBody
	public List<AutoCompleteDTO> produtosNomeAutoComplete(
			@RequestParam(value = "term", required = false, defaultValue = "") String term) {
		List<AutoCompleteDTO> sugestoes = new ArrayList<>();
		try {
			if (term.length() >= 2) {
				produtosFiltrados = produtoRepositorio.searchByNome(term);
			}

			for (Produto produto : produtosFiltrados) {
				if (produto.getNome().toLowerCase().contains(term.toLowerCase())) {
					sugestoes.add(new AutoCompleteDTO(produto.getNome(), Long.toString(produto.getId())));
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return sugestoes;
	}

}
