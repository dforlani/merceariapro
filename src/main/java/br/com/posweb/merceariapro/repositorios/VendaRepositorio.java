package br.com.posweb.merceariapro.repositorios;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.posweb.merceariapro.dtos.VendasListaDTO;
import br.com.posweb.merceariapro.models.Venda;

public interface VendaRepositorio extends JpaRepository<Venda, Long> {
	  @Query("select venda, i from Venda venda "
	  + "join venda.itens i "
	  + " where venda.id like :termo "
	  + "order by i.id")
		List<br.com.posweb.merceariapro.models.Venda> searchById(@Param("termo") Long termo);
	  
	  @Query(value = "select new br.com.posweb.merceariapro.dtos.VendasListaDTO(p.id, p.cliente.nome, p.data)"
	  + "from Venda p left join p.cliente c", countQuery = "select count(p) from Venda p")
	  Page<VendasListaDTO> findAllVendasPaginado(Pageable pageable);
	  
	  

}
