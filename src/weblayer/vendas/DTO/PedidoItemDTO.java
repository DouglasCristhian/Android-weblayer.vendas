package weblayer.vendas.DTO;

import java.util.Date;

public final class PedidoItemDTO 
{
	
	private int id; 
	private int id_pedido;
	private int id_empresa;
	private int id_filial;
	private int id_produto;
	private int nr_quantidade;
	private int fl_cancelado;
	private int nr_dias_condpagto;
	private int id_filial_saida;
	private int id_condpagto;
	
	private String id_produto_retaguarda;
	private String ds_produto_nome_completo;
	private String ds_produto_nome_curto;
	private String ds_numnf;
	private String ds_serienf;
	private String id_pedido_retaguarda;
	private String id_item_retaguarda;
	private String ds_observacao;
	
	private Date dt_faturamento;
	private Date dt_entrega;
	private Date dt_prevfaturamento;
	private Date dt_preventrega;
	
	private Double vl_lista;
	private Double vl_unitario;
	private Double vl_desconto;
	private Double vl_liquido;
	private Double vl_peso;
	private Double vl_desc_perc;
	  
	public PedidoItemDTO()
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

	public void setid_pedido(int id_pedido) {
		this.id_pedido = id_pedido;
	}

	public int getid_pedido() {
		return id_pedido;
	}

	public void setid_produto(int id_produto) {
		this.id_produto = id_produto;
	}

	public int getid_produto() {
		return id_produto;
	}

	public void setid_produto_retaguarda(String id_produto_retaguarda) {
		this.id_produto_retaguarda = id_produto_retaguarda;
	}

	public String getid_produto_retaguarda() {
		return id_produto_retaguarda;
	}

	public void setds_produto_nome_completo(String ds_produto_nome_completo) {
		this.ds_produto_nome_completo = ds_produto_nome_completo;
	}

	public String getds_produto_nome_completo() {
		return ds_produto_nome_completo;
	}

	public void setds_produto_nome_curto(String ds_produto_nome_curto) {
		this.ds_produto_nome_curto = ds_produto_nome_curto;
	}

	public String getds_produto_nome_curto() {
		return ds_produto_nome_curto;
	}

	public void setnr_quantidade(int nr_quantidade) {
		this.nr_quantidade = nr_quantidade;
	}

	public int getnr_quantidade() {
		return nr_quantidade;
	}

	public void setvl_unitario(Double vl_unitario) {
		this.vl_unitario = vl_unitario;
	}

	public Double getvl_unitario() {
		return vl_unitario;
	}

	public void setvl_desconto(Double vl_desconto) {
		this.vl_desconto = vl_desconto;
	}

	public Double getvl_desconto() {
		return vl_desconto;
	}

	public void setvl_liquido(Double vl_liquido) {
		this.vl_liquido = vl_liquido;
	}

	public Double getvl_liquido() {
		return vl_liquido;
	}

	public void setvl_peso(Double vl_peso) {
		this.vl_peso = vl_peso;
	}

	public Double getvl_peso() {
		return vl_peso;
	}

	public void setvl_desc_perc(Double vl_desc_perc) {
		this.vl_desc_perc = vl_desc_perc;
	}

	public Double getvl_desc_perc() {
		return vl_desc_perc;
	}

	public void setvl_lista(Double vl_lista) {
		this.vl_lista = vl_lista;
	}

	public Double getvl_lista() {
		return vl_lista;
	}

	public void setdt_faturamento(Date dt_faturamento) {
		this.dt_faturamento = dt_faturamento;
	}

	public Date getdt_faturamento() {
		return dt_faturamento;
	}

	public void setdt_entrega(Date dt_entrega) {
		this.dt_entrega = dt_entrega;
	}

	public Date getdt_entrega() {
		return dt_entrega;
	}

	public void setdt_prevfaturamento(Date dt_prevfaturamento) {
		this.dt_prevfaturamento = dt_prevfaturamento;
	}

	public Date getdt_prevfaturamento() {
		return dt_prevfaturamento;
	}

	public void setdt_preventrega(Date dt_preventrega) {
		this.dt_preventrega = dt_preventrega;
	}

	public Date getdt_preventrega() {
		return dt_preventrega;
	}

	public void setds_numnf(String ds_numnf) {
		this.ds_numnf = ds_numnf;
	}

	public String getds_numnf() {
		return ds_numnf;
	}

	public void setds_serienf(String ds_serienf) {
		this.ds_serienf = ds_serienf;
	}

	public String getds_serienf() {
		return ds_serienf;
	}

	public void setfl_cancelado(int fl_cancelado) {
		this.fl_cancelado = fl_cancelado;
	}

	public int getfl_cancelado() {
		return fl_cancelado;
	}

	public void setid_pedido_retaguarda(String id_pedido_retaguarda) {
		this.id_pedido_retaguarda = id_pedido_retaguarda;
	}

	public String getid_pedido_retaguarda() {
		return id_pedido_retaguarda;
	}

	public void setds_observacao(String ds_observacao) {
		this.ds_observacao = ds_observacao;
	}

	public String getds_observacao() {
		return ds_observacao;
	}

	public void setid_item_retaguarda(String id_item_retaguarda) {
		this.id_item_retaguarda = id_item_retaguarda;
	}

	public String getid_item_retaguarda() {
		return id_item_retaguarda;
	}

	public void setnr_dias_condpagto(int nr_dias_condpagto) {
		this.nr_dias_condpagto = nr_dias_condpagto;
	}

	public int getnr_dias_condpagto() {
		return nr_dias_condpagto;
	}

	public void setid_filial_saida(int id_filial_saida) {
		this.id_filial_saida = id_filial_saida;
	}

	public int getid_filial_saida() {
		return id_filial_saida;
	}

	public void setid_condpagto(int id_condpagto) {
		this.id_condpagto = id_condpagto;
	}

	public int getid_condpagto() {
		return id_condpagto;
	}
	
	
}
