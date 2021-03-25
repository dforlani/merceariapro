package br.com.posweb.merceariapro.repositorios;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;




@Repository
public interface ProdutoRepositorio extends JpaRepository<br.com.posweb.merceariapro.models.Produto, Long> {
    @Query("select d from Produto d where lower(d.nome) like lower(concat(:termo, '%'))")
	List<br.com.posweb.merceariapro.models.Produto> searchByNome(@Param("termo") String termo);
}

