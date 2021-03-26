package br.com.posweb.merceariapro.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.posweb.merceariapro.models.Venda;

public interface VendaRepositorio extends JpaRepository<Venda, Long> {

}
