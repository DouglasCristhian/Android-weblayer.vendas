package weblayer.vendas.DTO;

public final class TabelaPrecoDTO {

	private int id;
	private int id_empresa;
	private int id_filial;
	private int id_produto;
	private int nr_limite_formar_preco;
	
	private String ds_opcoes_cond_pagto; //varchar(30)
	private String id_tabela_preco; //varchar(10)
	
	private Double vl_preco1;
	private Double vl_preco2;

	public TabelaPrecoDTO() {
	}

	public void setid(int id) {
		this.id = id;
	}

	public int getid() {
		return id;
	}     

	public void setid_empresa(int id_empresa) {
		this.id_empresa = id_empresa;
	}

	public int getid_empresa() {
		return id_empresa;
	}

	public void setid_filial(int id_filial) {
		this.id_filial = id_filial;
	}

	public int getid_filial() {
		return id_filial;
	}

	public void setid_tabela_preco(String id_tabela_preco) {
		this.id_tabela_preco = id_tabela_preco;
	}

	public String getid_tabela_preco() {
		return id_tabela_preco;
	}

	public void setid_produto(int id_produto) {
		this.id_produto = id_produto;
	}

	public int getid_produto() {
		return id_produto;
	}

	public void setvl_preco1(Double vl_preco1) {
		this.vl_preco1 = vl_preco1;
	}

	public Double getvl_preco1() {
		return vl_preco1;
	}

	public void setvl_preco2(Double vl_preco2) {
		this.vl_preco2 = vl_preco2;
	}

	public Double getvl_preco2() {
		return vl_preco2;
	}

	public void setnr_limite_formar_preco(int nr_limite_formar_preco) {
		this.nr_limite_formar_preco = nr_limite_formar_preco;
	}

	public int getnr_limite_formar_preco() {
		return nr_limite_formar_preco;
	}

	public void setds_opcoes_cond_pagto(String ds_opcoes_cond_pagto) {
		this.ds_opcoes_cond_pagto = ds_opcoes_cond_pagto;
	}

	public String getds_opcoes_cond_pagto() {
		return ds_opcoes_cond_pagto;
	}

}
