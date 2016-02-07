package weblayer.vendas.DTO;

public final class FilialDTO {
	private int id;
	private int id_empresa;
	private String id_filial;
	private String ds_filial;
	private String ds_cnpj;
	private String ds_codmun;

	public FilialDTO() {
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

	public void setid_filial(String id_filial) {
		this.id_filial = id_filial;
	}

	public String getid_filial() {
		return id_filial;
	}

	public void setds_filial(String ds_filial) {
		this.ds_filial = ds_filial;
	}

	public String getds_filial() {
		return ds_filial;
	}

	public void setds_cnpj(String ds_cnpj) {
		this.ds_cnpj = ds_cnpj;
	}

	public String getds_cnpj() {
		return ds_cnpj;
	}

	public String getds_codmun() {
		return ds_codmun;
	}

	public void setds_codmun(String ds_codmun) {
		this.ds_codmun = ds_codmun;
	}

	@Override
	public boolean equals(Object o) 
	{          
		if (!(o instanceof FilialDTO)) 
		{  
			return false;     
		}
		
		FilialDTO lhs = (FilialDTO) o;
		
		if (this.getid()== lhs.getid())
			return true;
		
		return false;
		
	}
}
