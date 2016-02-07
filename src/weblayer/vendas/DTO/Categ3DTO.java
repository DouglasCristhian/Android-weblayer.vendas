package weblayer.vendas.DTO;


public final class Categ3DTO
{

	private int id;
	private int id_empresa;
	private String ds_nome;
	
	public Categ3DTO()
	{
	}
	 
	public void setds_nome(String ds_nome) {
		this.ds_nome= ds_nome;
	}
	
	public String getds_nome() {
		return ds_nome;
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
	
}
