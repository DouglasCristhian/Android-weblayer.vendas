package weblayer.vendas.DTO;


public final class ParametroDTO
{

	private int id;
	private int id_empresa;
	private String ds_chave;
	private String ds_valor;
	
	public ParametroDTO()
	{
	}

	 
	public void setds_valor(String ds_valor) {
		this.ds_valor = ds_valor;
	}
	
	public String getds_valor() {
		return ds_valor;
	}


	public String getds_chave() {
		return ds_chave;
	}


	public void setds_chave(String ds_chave) {
		this.ds_chave = ds_chave;
	}


	public int getid_empresa() {
		return id_empresa;
	}


	public void setid_empresa(int id_empresa) {
		this.id_empresa = id_empresa;
	}


	public int getid() {
		return id;
	}


	public void setid(int id) {
		this.id = id;
	}
	
}
