package br.com.posweb.merceariapro.dtos;

import java.time.LocalDateTime;

public class VendasListaDTO {
  private Long id;
  private String nome;
  private LocalDateTime data;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public LocalDateTime getData() {
    return data;
  }

  public void setData(LocalDateTime data) {
    this.data = data;
  }

  public VendasListaDTO(Long id, String nome, LocalDateTime data) {
    super();
    this.id = id;
    this.nome = nome;
    this.data = data;
  }
}
