package br.com.posweb.merceariapro.controllers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
import br.com.posweb.merceariapro.dtos.AutoCompleteDTOVendas;
import br.com.posweb.merceariapro.dtos.VendasListaDTO;
import br.com.posweb.merceariapro.models.Cliente;
import br.com.posweb.merceariapro.models.Produto;
import br.com.posweb.merceariapro.models.Venda;
import br.com.posweb.merceariapro.models.VendaItem;
import br.com.posweb.merceariapro.repositorios.ClienteRepositorio;
import br.com.posweb.merceariapro.repositorios.ProdutoRepositorio;
import br.com.posweb.merceariapro.repositorios.VendaItemRepositorio;
import br.com.posweb.merceariapro.repositorios.VendaRepositorio;

@Controller
public class VendasController {

	private VendaRepositorio vendaRepositorio;
	private VendaItemRepositorio vendaItemRepositorio;	
	private ProdutoRepositorio produtoRepositorio;
	private ClienteRepositorio clienteRepositorio;
	
	private List<Produto> produtosFiltrados = new ArrayList<>();
	private List<Cliente> clientesFiltrados = new ArrayList<>();
	

	public VendasController(VendaRepositorio vendaRepositorio, VendaItemRepositorio vendaItemRepositorio, ClienteRepositorio clienteRepositorio,
			 ProdutoRepositorio produtoRepositorio) {
		this.vendaRepositorio = vendaRepositorio;
		this.produtoRepositorio = produtoRepositorio;
		this.vendaItemRepositorio = vendaItemRepositorio;
		this.clienteRepositorio = clienteRepositorio;

	}

	@GetMapping("/vendas")
	public String vendas(Model model, @RequestParam("page") Optional<Integer> pagina, @RequestParam("size") Optional<Integer> tamanho) {
		int paginaAtual = pagina.orElse(1) - 1;
		int tamanhoPagina = tamanho.orElse(2);
		
		PageRequest requisicao = PageRequest.of(paginaAtual, tamanhoPagina, Sort.by("data"));
		Page<VendasListaDTO> listaPaginada = vendaRepositorio.findAllVendasPaginado(requisicao);
		
		model.addAttribute("listaVendas", listaPaginada);
		
		int totalPaginas = listaPaginada.getTotalPages();		
		if(totalPaginas > 0) {
			List<Integer> numeroPaginas = IntStream.rangeClosed(1, totalPaginas)
					.boxed()
					.collect(Collectors.toList());
			model.addAttribute("numerosPaginas", numeroPaginas);
			System.out.print("numerosPaginas +++++++++++++++++"+numeroPaginas);
		}
		
		model.addAttribute("venda", new Venda());
		
		System.out.print("Pgainas +++++++++++++++++"+listaPaginada.getTotalPages());
		
		
		return "vendas/index";
		
//		List<Venda> vendas = vendaRepositorio.findAll();		
//		model.addAttribute("listaVendas", vendas);
//		model.addAttribute("venda", new Venda());
//		return "vendas/index";
	}

	/**
	 * Executa de um pedido da index, abre uma nova venda, j?? salva e inicia um novo item de venda.
	 * 
	 * @param model
	 * @return
	 */
	@PostMapping("/vendas/venda")
	public String venda(Model model) {
		Venda venda = new Venda(LocalDateTime.now());
		vendaRepositorio.save(venda);
		
		return vendaForm(venda, model);
	}

	/**
	 * Inicia de uma solicita????o da tela de inclus??o de itens. Essa tela s?? envia o novo item a ser inserido. Dessa forma, 
	 * esse c??digo busca os itens j?? inseridos anteriormente pra concatenar com o novo e inserir na venda.
	 * 
	 * @param id
	 * @param model
	 * @param venda
	 * @return
	 */
	@PostMapping(value="/vendas/venda/{id}", params = {"salvarVenda"})
	public String salvarVenda( @Valid @ModelAttribute("venda") Venda venda, BindingResult bindingResult, Model model ) {
		
		if (bindingResult.hasErrors()) {			
			return "vendas/form";
			
		}
	
		List<VendaItem> itensOriginais = new ArrayList<>();
		
		Optional<Venda> optional=		vendaRepositorio.findById(venda.getId());
		if(!optional.isEmpty()) {
			itensOriginais = optional.get().getItens();
			
		}
		
		itensOriginais.addAll(0, venda.getItens());
		venda.setItens(itensOriginais);
		vendaRepositorio.save(venda);		
		vendaRepositorio.flush();
		
		
		return vendaForm(venda, model);
	}

	
	
	@GetMapping("/vendas/{id}")
	public String alterar(@PathVariable("id") long id, Model model) {
		Optional<Venda> vendaOpt = vendaRepositorio.findById(id);
		if (vendaOpt.isEmpty()) {
			throw new IllegalArgumentException("Venda inv??lido.");
		}

		Venda venda = vendaOpt.get();
		
		return vendaForm(venda, model);
	}
	
	/**
	 * C??digo compartilhado entre as telas de vendas
	 */
	public String vendaForm(Venda venda, Model model) {
		BigDecimal total = venda.getSomatorioTotal();
		
		
		//adiciona um novo VendaItem 
//		venda.addItemVenda(new VendaItem(new BigDecimal(1), venda, true));
		VendaItem vendaItem = new VendaItem(new BigDecimal(1));
		vendaItem.setVenda(venda);
		
		model.addAttribute("total", total);
		model.addAttribute("venda", venda);
		model.addAttribute("vendaItem", vendaItem);
		

		return "vendas/form";
		
	}


	
	@PostMapping(value="/vendas/venda/{id}", params = {"adicionarItem"})
	public String adicionarItem(@ModelAttribute("venda") Venda venda, @ModelAttribute("vendaItem") @Valid VendaItem vendaItem, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "vendas/form";
		}

		venda.addItemVenda(vendaItem);
		vendaRepositorio.save(venda);		
		vendaRepositorio.flush();

		return vendaForm(venda, model);
	}
	

	@GetMapping("/vendas/excluir/{id}")
	public String excluir(@PathVariable("id") long id) {
		Optional<Venda> vendaOpt = vendaRepositorio.findById(id);
		if (vendaOpt.isEmpty()) {
			throw new IllegalArgumentException("venda inv??lido.");
		}

		vendaRepositorio.delete(vendaOpt.get());
		return "redirect:/vendas";
	}

	@GetMapping("/vendas/venda/{idvenda}/excluirItem/{idvendaItem}")
	public String excluirItem(@PathVariable("idvenda") long idvenda,
			@PathVariable("idvendaItem") Long idvendaItem) {
		Optional<VendaItem> peditoItemOpt = vendaItemRepositorio.findById(idvendaItem);
		if (peditoItemOpt.isEmpty()) {
			throw new IllegalArgumentException("venda Item inv??lido.");
		}

		vendaItemRepositorio.delete(peditoItemOpt.get());
		return "redirect:/vendas/" + idvenda;
	}



	@RequestMapping("/vendas/produtosNomeAutoComplete")
	@ResponseBody
	public List<AutoCompleteDTOVendas> produtosNomeAutoComplete(
			@RequestParam(value = "term", required = false, defaultValue = "") String term) {
		List<AutoCompleteDTOVendas> sugestoes = new ArrayList<>();
		try {
			if (term.length() >= 2) {
				produtosFiltrados = produtoRepositorio.searchByNome(term);
			}

			for (Produto produto : produtosFiltrados) {
				if (produto.getNome().toLowerCase().contains(term.toLowerCase())) {
					sugestoes.add(new AutoCompleteDTOVendas(produto.getNome(), Long.toString(produto.getId()), produto.getValor().toString()));
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return sugestoes;
	}
	
	@RequestMapping("/vendas/clientesNomeAutoComplete")
	@ResponseBody
	public List<br.com.posweb.merceariapro.dtos.AutoCompleteDTO> clientesNomeAutoComplete(
			@RequestParam(value = "term", required = false, defaultValue = "") String term) {
		List<br.com.posweb.merceariapro.dtos.AutoCompleteDTO> sugestoes = new ArrayList<>();
		try {
			if (term.length() >= 2) {
				clientesFiltrados = clienteRepositorio.searchByNome(term);
			}

			for (Cliente cliente : clientesFiltrados) {
				if (cliente.getNome().toLowerCase().contains(term.toLowerCase())) {
					sugestoes.add(new AutoCompleteDTO(cliente.getNome(), Long.toString(cliente.getId())));
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return sugestoes;
	}

}
