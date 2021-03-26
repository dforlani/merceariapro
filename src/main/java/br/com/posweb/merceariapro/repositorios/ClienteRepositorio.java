package br.com.posweb.merceariapro.repositorios;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;




@Repository
public interface ClienteRepositorio extends JpaRepository<br.com.posweb.merceariapro.models.Cliente, Long> {
    @Query("select d from Cliente d where lower(d.nome) like lower(concat(:termo, '%'))")
	List<br.com.posweb.merceariapro.models.Cliente> searchByNome(@Param("termo") String termo);
}

