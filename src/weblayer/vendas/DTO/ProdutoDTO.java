package weblayer.vendas.DTO;


public final class ProdutoDTO
{
	
	private int id; 
	private int id_empresa;
	private int id_filial;
	private int fl_ativo;
	private int fl_cx_pallet;
	private int id_categ1;
	private int id_categ2;
	private int id_categ3;
	
	private String id_retaguarda;
	private String ds_nome_completo;
	private String ds_nome_curto;
	
	private String ds_ean13;
	private String ds_dun14;
	
	private String ds_unidademedida;
	
	
	private Double nr_peso_bruto;
	private Double nr_peso_liquido;
	
	
	public ProdutoDTO()
	{
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

	public void setid_retaguarda(String id_produto_retaguarda) {
		this.id_retaguarda = id_produto_retaguarda;
	}

	public String getid_retaguarda() {
		return id_retaguarda;
	}

	public void setds_nome_completo(String ds_nome_completo) {
		this.ds_nome_completo = ds_nome_completo;
	}

	public String getds_nome_completo() {
		return ds_nome_completo;
	}

	public void setds_nome_curto(String ds_nome_curto) {
		this.ds_nome_curto = ds_nome_curto;
	}

	public String getds_nome_curto() {
		return ds_nome_curto;
	}

	public void setnr_peso_bruto(Double nr_peso_bruto) {
		this.nr_peso_bruto= nr_peso_bruto;
	}

	public Double getnr_peso_bruto() {
		return nr_peso_bruto;
	}

	public void setnr_peso_liquido(Double nr_peso_liquido) {
		this.nr_peso_liquido= nr_peso_liquido;
	}

	public Double getnr_peso_liquido() {
		return nr_peso_liquido;
	}
	
	public void setds_ean13(String ds_ean13) {
		this.ds_ean13 = ds_ean13;
	}

	public String getds_ean13() {
		return ds_ean13;
	}

	public void setds_dun14(String ds_dun14) {
		this.ds_dun14 = ds_dun14;
	}

	public String getds_dun14() {
		return ds_dun14;
	}

	
	public void setds_unidademedida(String ds_unidademedida) {
		this.ds_unidademedida= ds_unidademedida;
	}

	public String getds_unidademedida() {
		return ds_unidademedida;
	}
	
	
	public void setfl_cx_pallet(int fl_cx_pallet) {
		this.fl_cx_pallet = fl_cx_pallet;
	}

	public int getfl_cx_pallet() {
		return fl_cx_pallet;
	}

	public void setfl_ativo(int fl_bloqueado) {
		this.fl_ativo= fl_bloqueado;
	}

	public int getfl_ativo() {
		return fl_ativo;
	}

	public void setid_categ1(int id_categ1) {
		this.id_categ1 = id_categ1;
	}

	public int getid_categ1() {
		return id_categ1;
	}

	public void setid_categ2(int id_categ2) {
		this.id_categ2 = id_categ2;
	}

	public int getid_categ2() {
		return id_categ2;
	}

	public void setid_categ3(int id_categ3) {
		this.id_categ3 = id_categ3;
	}

	public int getid_categ3() {
		return id_categ3;
	}

	
	
}
