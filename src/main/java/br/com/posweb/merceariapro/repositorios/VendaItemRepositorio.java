package br.com.posweb.merceariapro.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.posweb.merceariapro.models.VendaItem;

public interface VendaItemRepositorio extends JpaRepository<VendaItem, Long> {

}
