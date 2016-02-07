package weblayer.vendas.DTO;


public final class UrlServiceDTO
{

	private int id;
	private int id_empresa;
	private String ds_empresa;
	private String ds_webservice;
	
	public UrlServiceDTO()
	{
	}
	 
	public int getid() {
		return id;
	}

	public void setid(int id) {
		this.id = id;
	}
	
	public int getid_empresa() {
		return id_empresa;
	}

	public void setid_empresa(int id_empresa) {
		this.id_empresa = id_empresa;
	}
	
	public void setds_empresa(String ds_empresa) {
		this.ds_empresa= ds_empresa;
	}
	
	public String getds_empresa() {
		return ds_empresa;
	}

	public void setds_webservice(String ds_webservice) {
		this.ds_webservice= ds_webservice;
	}
	
	public String getds_webservice() {
		return ds_webservice;
	}
	
	
}
