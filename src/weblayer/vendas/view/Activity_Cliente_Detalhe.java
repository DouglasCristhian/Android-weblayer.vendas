package weblayer.vendas.view;


import weblayer.vendas.d1.R;
import weblayer.vendas.DAO.ClienteDAO;
import weblayer.vendas.DTO.ClienteDTO;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

//*Teste do Source Safe*//

public class Activity_Cliente_Detalhe extends Activity {

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		weblayer.toolbox.Common.DefinirTheme(this, "Cliente");
		
		super.onCreate(savedInstanceState);
		
		if (android.os.Build.VERSION.SDK_INT >= 11)
		{
			ActionBar actionBar = getActionBar();
			actionBar.setDisplayShowHomeEnabled(false);
			actionBar.setTitle("Cliente");
		}
		
		setContentView(R.layout.activity_detalhe_cliente);

		Intent i = getIntent();
		int id_cliente = i.getIntExtra("id_cliente", 0);

		ClienteDTO cliente = ClienteDAO.Get(id_cliente);
		if (cliente==null)
			return;

		TextView cnpj = (TextView)findViewById(R.id.txtcliente_Cnj);
		cnpj.setText(cliente.getds_cnpj());
		cnpj.setFocusable(false);

		TextView codigo = (TextView)findViewById(R.id.txtcliente_Codigo);
		codigo.setText(cliente.getid_retaguarda());
		codigo.setFocusable(false);
		
		TextView razao= (TextView)findViewById(R.id.txtcliente_Razao);
		razao.setText(cliente.getds_razao());
		razao.setFocusable(false);
		
		TextView endereco= (TextView)findViewById(R.id.txtcliente_Endereco);
		endereco.setText(cliente.getds_endereco());
		endereco.setFocusable(false);
		
		TextView cidade= (TextView)findViewById(R.id.txtcliente_Cidade);
		cidade.setText(cliente.getds_cidade() + "/" + cliente.getds_uf());
		cidade.setFocusable(false);
		
		TextView bairro= (TextView)findViewById(R.id.txtcliente_Bairro);
		bairro.setText(cliente.getds_bairro());
		bairro.setFocusable(false);
		
		TextView cep= (TextView)findViewById(R.id.txtcliente_CEP);
		cep.setText(cliente.getds_cep());
		cep.setFocusable(false);
		
		TextView contato= (TextView)findViewById(R.id.txtcliente_Contato);
		contato.setText(cliente.getds_contato());
		contato.setFocusable(false);
		
		TextView telefone= (TextView)findViewById(R.id.txtcliente_Telefone);
		telefone.setText(cliente.getds_tel1());
		telefone.setFocusable(false);
		
		TextView tabelapreco= (TextView)findViewById(R.id.txtcliente_TabelaPreco);
		tabelapreco.setText(cliente.getid_tabpreco());
		tabelapreco.setFocusable(false);
		
		TextView status= (TextView)findViewById(R.id.txtcliente_Status);
		status.setText("INATIVO");
		
		if (cliente.getfl_ativo()==1)
			status.setText("ATIVO");
		
		status.setFocusable(false);
		

	}
}
