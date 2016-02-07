package weblayer.vendas.DTO;

import java.util.ArrayList;
import java.util.Date;

public final class PedidoDTO
{
	
	private int id;
	private int id_web;
	private int id_empresa;
	private int id_filial;
	private int id_cliente;
	private int id_vendedor;
	private int id_tipo;
	private int id_filial_saida;
	private int fl_status;
	private int fl_entrada;
	
	private String ds_cliente; 			/*varchar(200)*/
	private String ds_imei; 			/*varchar(100)*/
	private String ds_status; 			/*varchar(256)*/
	private String ds_observacao; 		/*varchar(256)*/
	private String ds_observacao_nf; 	/*varchar(256)*/
	private String ds_pedido_cliente;
	
	private Date dt_inclusao_mobile;
	private Date dt_inclusao_web;
	private Date dt_inclusao_erp;
	private Date dt_alteracao;
	
	private Double vl_bruto;
	private Double vl_liquido;
	private Double vl_desconto;
	private Double vl_peso;
	private int vl_volume;
	
	private ArrayList<PedidoItemDTO> Itens;
	
	public PedidoDTO()
	{
	}

	public void setid(int id) {
		this.id = id;
	}

	public int getid() {
		return id;
	}

	public void setid_web(int id_web) {
		this.id_web = id_web;
	}

	public int getid_web() {
		return id_web;
	}
	
	public void setid_vendedor(int id_vendedor) {
		this.id_vendedor = id_vendedor;
	}

	public int getid_vendedor() {
		return id_vendedor;
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

	public void setdt_inclusao_mobile(Date dt_inclusao_mobile) {
		this.dt_inclusao_mobile= dt_inclusao_mobile;
	}

	public Date getdt_inclusao_mobile() {
		return dt_inclusao_mobile;
	}

	public void setdt_inclusao_web(Date dt_inclusao_web) {
		this.dt_inclusao_web = dt_inclusao_web;
	}

	public Date getdt_inclusao_web() {
		return dt_inclusao_web;
	}

	public void setdt_inclusao_erp(Date dt_inclusao_erp) {
		this.dt_inclusao_erp = dt_inclusao_erp;
	}

	public Date getdt_inclusao_erp() {
		return dt_inclusao_erp;
	}

	public void setdt_alteracao(Date dt_alteracao)
	{
		
		this.dt_alteracao  = dt_alteracao;
	}

	public Date getdt_alteracao() {
		
		return dt_alteracao;
		
	}

	public void setid_tipo(int id_tipo) {
		this.id_tipo = id_tipo;
	}

	public int getid_tipo() {
		return id_tipo;
	}

	public void setid_filial_saida(int id_filial_saida) {
		this.id_filial_saida = id_filial_saida;
	}

	public int getid_filial_saida() {
		return id_filial_saida;
	}

	public void setfl_status(int fl_status) {
		this.fl_status = fl_status;
	}

	public int getfl_status() {
		return fl_status;
	}
	
	public void setfl_entrada(int fl_entrada) {
		this.fl_entrada= fl_entrada;
	}

	public int getfl_entrada() {
		return fl_entrada;
	}
	
	public void setds_observacao(String ds_observacao) {
		this.ds_observacao = ds_observacao;
	}

	public String getds_observacao() {
		return ds_observacao;
	}
	
	public void setds_imei(String ds_imei) {
		this.ds_imei = ds_imei;
	}

	public String getds_imei() {
		return ds_imei;
	}
	
	public void setds_status(String ds_status) {
		this.ds_status = ds_status;
	}

	public String getds_status() {
		return ds_status;
	}

	public void setds_cliente(String ds_cliente) {
		this.ds_cliente= ds_cliente;
	}

	public String getds_cliente() {
		return ds_cliente;
	}
	
	public void setvl_bruto(Double vl_bruto) {
		this.vl_bruto = vl_bruto;
	}

	public Double getvl_bruto() {
		return vl_bruto;
	}

	public void setvl_volume(int vl_volume) {
		this.vl_volume = vl_volume;
	}

	public int getvl_volume() {
		return vl_volume;
	}

	public Double getvl_liquido() {
		return vl_liquido;
	}

	public void setvl_liquido(Double vl_liquido) {
		this.vl_liquido = vl_liquido;
	}

	public Double getvl_desconto() {
		return vl_desconto;
	}

	public void setvl_desconto(Double vl_desconto) {
		this.vl_desconto = vl_desconto;
	}

	public Double getvl_peso() {
		return vl_peso;
	}

	public void setvl_peso(Double vl_peso) {
		this.vl_peso = vl_peso;
	}

	public String getds_observacao_nf() {
		return ds_observacao_nf;
	}

	public void setds_observacao_nf(String ds_observacao_nf) {
		this.ds_observacao_nf = ds_observacao_nf;
	}

	public String getds_pedido_cliente() {
		return ds_pedido_cliente;
	}

	public void setds_pedido_cliente(String ds_pedido_cliente) {
		this.ds_pedido_cliente= ds_pedido_cliente;
	}
	
	public ArrayList<PedidoItemDTO> getItens() {
		return Itens;
	}

	public void setItens(ArrayList<PedidoItemDTO> itens) {
		Itens = itens;
	}
	
}
