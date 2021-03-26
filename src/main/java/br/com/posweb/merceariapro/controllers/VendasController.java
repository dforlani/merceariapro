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

	private VendaRepositorio pedidoRepositorio;
	private VendaItemRepositorio pedidoItemRepositorio;	
	private ProdutoRepositorio produtoRepositorio;

	
	private List<Produto> produtosFiltrados = new ArrayList<>();
	

	public VendasController(VendaRepositorio pedidoRepositorio, VendaItemRepositorio pedidoItemRepositorio,
			 ProdutoRepositorio produtoRepositorio) {
		this.pedidoRepositorio = pedidoRepositorio;
		this.produtoRepositorio = produtoRepositorio;
		this.pedidoItemRepositorio = pedidoItemRepositorio;

	}

	@GetMapping("/vendas")
	public String vendas(Model model) {
		List<Venda> vendas = pedidoRepositorio.findAll();		
		model.addAttribute("listavendas", vendas);
		model.addAttribute("pedido", new Venda());
		return "vendas/index";
	}

	@GetMapping("/vendas/novo")
	public String novo(Model model) {

		model.addAttribute("pedido", new Venda(LocalDateTime.now()));

		return "vendas/form";
	}

	@GetMapping("/vendas/{id}")
	public String alterar(@PathVariable("id") long id, Model model) {
		Optional<Venda> pedidoOpt = pedidoRepositorio.findById(id);
		if (pedidoOpt.isEmpty()) {
			throw new IllegalArgumentException("Pedido inválido.");
		}

		BigDecimal total = new BigDecimal(0);
		for (VendaItem item : pedidoOpt.get().getItens()) {
			total = total.add(item.getProduto().getValor().multiply(new BigDecimal(item.getQuantidade())));			
			
		}
		

		model.addAttribute("total", total);
		model.addAttribute("pedido", pedidoOpt.get());
		model.addAttribute("pedidoItem", new VendaItem(1));

		return "vendas/form";
	}

	@PostMapping("/vendas/salvar")
	public String salvar(@Valid @ModelAttribute("pedido") Venda pedido, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "vendas/index";
		}
		pedido.setData(LocalDateTime.now());
		pedidoRepositorio.save(pedido);
		model.addAttribute("pedido", pedido);
		model.addAttribute("pedidoItem", new VendaItem(pedido));
		return "redirect:/vendas/"+pedido.getId();
	}

	@PostMapping("/vendas/{pedidoId}/adicionarItem")
	public String adicionarItem(@PathVariable("pedidoId") Long pedidoId, @RequestParam("produtoId") Long produtoId,
			@ModelAttribute("pedidoItem") VendaItem pedidoItem, Model model) {
		// if (bindingResult.hasErrors()) {
		// return "vendas/form";
		// }

		Optional<Venda> pedidoOpt = pedidoRepositorio.findById(pedidoId);
		Optional<Produto> produtoOpt = produtoRepositorio.findById(produtoId);

		if (!(pedidoOpt.isEmpty()) && !produtoOpt.isEmpty()) {
			pedidoItem.setProduto(produtoOpt.get());
			pedidoItem.setPedido(pedidoOpt.get());

			pedidoItemRepositorio.save(pedidoItem);
		} else {
			pedidoItem = new VendaItem();
		}

		return "redirect:/vendas/" + pedidoId;
	}

	@GetMapping("/vendas/excluir/{id}")
	public String excluir(@PathVariable("id") long id) {
		Optional<Venda> pedidoOpt = pedidoRepositorio.findById(id);
		if (pedidoOpt.isEmpty()) {
			throw new IllegalArgumentException("Pedido inválido.");
		}

		pedidoRepositorio.delete(pedidoOpt.get());
		return "redirect:/vendas";
	}

	@GetMapping("/vendas/{idPedido}/excluirItem/{idPedidoItem}")
	public String excluirItem(@PathVariable("idPedido") long idPedido,
			@PathVariable("idPedidoItem") Long idPedidoItem) {
		Optional<VendaItem> peditoItemOpt = pedidoItemRepositorio.findById(idPedidoItem);
		if (peditoItemOpt.isEmpty()) {
			throw new IllegalArgumentException("Pedido Item inválido.");
		}

		pedidoItemRepositorio.delete(peditoItemOpt.get());
		return "redirect:/vendas/" + idPedido;
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
