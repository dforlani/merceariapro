package br.com.posweb.merceariapro.dtos;


public class AutoCompleteDTOVendas {
	
	private String label;
	private String value;
	private String valor;
	
	public AutoCompleteDTOVendas() {}
	
	public AutoCompleteDTOVendas(String label, String value, String valor) {
		super();
		this.label = label;
		this.value = value;
		this.valor = valor;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
