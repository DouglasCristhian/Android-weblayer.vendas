package weblayer.vendas.DTO;


public final class ClienteGrupoProdutoDTO
{
	private int id;
	private int id_empresa;
	private int id_filial;
	private int id_cliente;
	private int id_categ1;
	private int id_categ2;
	private int id_categ3;
	private int id_filial_faturamento;
	private int fl_trocafilial;
	private int fl_ativo;
	private int nr_diasentrega;
	
	public ClienteGrupoProdutoDTO()
	{
	}
	
	public int getid()
	{
		return this.id;
	}
	public void setid(int id)
	{
		this.id = id;
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

	public void setid_cliente(int id_cliente) {
		this.id_cliente = id_cliente;
	}

	public int getid_cliente() {
		return id_cliente;
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

	public void setid_filial_faturamento(int id_filial_faturamento) {
		this.id_filial_faturamento = id_filial_faturamento;
	}

	public int getid_filial_faturamento() {
		return id_filial_faturamento;
	}

	public void setfl_trocafilial(int fl_trocafilial) {
		this.fl_trocafilial = fl_trocafilial;
	}

	public int getfl_trocafilial() {
		return fl_trocafilial;
	}

	public void setfl_ativo(int fl_ativo) {
		this.fl_ativo= fl_ativo;
	}

	public int getfl_ativo() {
		return fl_ativo;
	}

	public void setnr_diasentrega(int nr_diasentrega) {
		this.nr_diasentrega = nr_diasentrega;
	}

	public int getnr_diasentrega() {
		return nr_diasentrega;
	}
	
}
