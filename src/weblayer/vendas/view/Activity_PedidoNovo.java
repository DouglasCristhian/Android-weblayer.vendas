package weblayer.vendas.view;

import weblayer.vendas.d1.R;
import weblayer.vendas.DAO.ParametroDAO;
import weblayer.vendas.DTO.ParametroDTO;
import weblayer.vendas.DTO.PedidoDTO;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Date;

public class Activity_PedidoNovo extends Activity {

	private int id_cliente;
	private String ds_cliente;
	private String id_cliente_retaguarda;
	private String ds_canal;
	
	private TextView txtpedido_cliente;

	private Spinner spOpcao;

	Button cmbProximo;
	Button cmbBuscarCliente;

	TextView lblcliente;
	TextView lblcanal;

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {

		weblayer.toolbox.Common.DefinirTheme(this,
				getResources().getString(R.string.titulo_novopedido));

		super.onCreate(savedInstanceState);

		if (android.os.Build.VERSION.SDK_INT >= 11) {
			ActionBar actionBar = getActionBar();
			actionBar.setDisplayShowHomeEnabled(false);
			actionBar.setTitle("Novo Pedido");
		}

		setContentView(R.layout.activity_pedido_novo);
		
		
		getSharedPreferences("VENDAS", MODE_PRIVATE)
		.edit()
		.putString("mensagepedido",
				"").commit();
		
		getSharedPreferences("VENDAS", MODE_PRIVATE)
		.edit()
		.putString("mensagepedidonf",
				"").commit();
		
		
		id_cliente = 0;
		ds_cliente = "";
		id_cliente_retaguarda = "";

		// Carregando a combo
		// *************************************************************
		final String[] stringarrayspiner = { "Selecione..", "VENDA",
				"BONIFICACAO","INDUSTRIALIZACAO" };

		ArrayAdapter<String> aOpcao = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, stringarrayspiner);

		spOpcao = (Spinner) findViewById(R.id.pedido_spinner_tipo);
		spOpcao.setAdapter(aOpcao);

		txtpedido_cliente = (TextView) findViewById(R.id.pedido_txt_pedidocliente);
		// *********************************************************************************

		lblcanal = (TextView) findViewById(R.id.pedido_txt_canal);
		lblcanal.setFocusable(false);

		lblcliente = (TextView) findViewById(R.id.pedido_txt_cliente);
		lblcliente.setFocusable(false);

		cmbBuscarCliente = (Button) findViewById(R.id.pedido_btn_buscacliete);
		cmbProximo = (Button) findViewById(R.id.pedido_btn_proximo);
		
		BuscarCliente();
		
	}
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {

	  savedInstanceState.putInt("id_cliente", id_cliente);  
	  savedInstanceState.putString("ds_cliente", ds_cliente);    
	  savedInstanceState.putString("id_cliente_retaguarda", id_cliente_retaguarda);
	  savedInstanceState.putString("ds_canal", ds_canal);
	    
	  super.onSaveInstanceState(savedInstanceState);
	  
	}  
	//onRestoreInstanceState  
	    @Override  
	public void onRestoreInstanceState(Bundle savedInstanceState) {  
	  super.onRestoreInstanceState(savedInstanceState);  
	  
	  id_cliente = savedInstanceState.getInt("id_cliente");  
	  ds_cliente= savedInstanceState.getString("ds_cliente");
	  id_cliente_retaguarda= savedInstanceState.getString("id_cliente_retaguarda");
	  ds_canal= savedInstanceState.getString("ds_canal");
	  
	}
	    
	public void BuscarCliente()
	{
		// Chama a tela de pesquisa de clientes
		// retorna o código do cliente e a descrição
		Intent i = new Intent(this, Activity_Consulta_Cliente.class);
		i.putExtra("chamada", "retorno");
		i.putExtra("titulo", "Selecione o Cliente...");
		startActivityForResult(i, 1);
		
	}
	
	public void onBtnPedidoBuscaCliente(View view) {
		
		BuscarCliente();
		
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == 1) {

			if (resultCode == RESULT_OK) {
				id_cliente = data.getIntExtra("id_cliente", 0);
				id_cliente_retaguarda = data
						.getStringExtra("id_cliente_retaguarda");
				ds_cliente = data.getStringExtra("ds_cliente");
				ds_canal = data.getStringExtra("ds_canal");

				lblcliente.setText("[" + id_cliente_retaguarda + "] " + ds_cliente);
				lblcanal.setText(ds_canal);
				
				spOpcao.requestFocus();

			}

			if (resultCode == RESULT_CANCELED) {
				id_cliente = 0;
				ds_cliente = "";
				ds_canal = "";
				id_cliente_retaguarda = "";
				
			}

			

		}

	}

	public void onBtnPedidoProximo(View view) {

		// Criticar os dados
		// ***************************************************************
		String critica = "";

		// Cliente deve estar ativo
		if (id_cliente == 0)
			critica = critica + "Cliente inválido. ";

		// Tipo da venda selecionada
		if (spOpcao.getSelectedItemPosition() == 0)
			critica = critica + "Tipo de Pedido inválido. ";
		// **********************************************************************************

		if (!critica.equals("")) {
			Toast.makeText(getApplicationContext(), critica, Toast.LENGTH_LONG)
					.show();
			return;
		}

		// Criar pedido
		// *********************************************************************
		// Buscar imei do dispositivo
		// *******************************************************
		ParametroDAO.initialize(getApplicationContext());

		String imei = ParametroDAO.GetByKey("IMEI", "");
		int idvendedor = Integer.parseInt(ParametroDAO.GetByKey("IDVENDEDOR",
				"0"));

		weblayer.vendas.DTO.PedidoDTO pedido = new PedidoDTO();

		pedido.setid_cliente(id_cliente);
		pedido.setid_empresa(3);
		pedido.setid_tipo(spOpcao.getSelectedItemPosition());
		pedido.setds_imei(imei);
		pedido.setid_vendedor(idvendedor);

		pedido.setdt_inclusao_mobile(new Date());
		pedido.setfl_status(0);
		pedido.setfl_entrada(1);
		pedido.setds_cliente("[" + id_cliente_retaguarda + "] " + ds_cliente);
		pedido.setds_pedido_cliente(txtpedido_cliente.getText().toString());

		weblayer.vendas.DAO.PedidoDAO.initialize(getApplicationContext());
		weblayer.vendas.DAO.PedidoDAO.insert(pedido);
		// ************************************************************************************

		finish();

		// Chamar a tela de edição passando o código do pedido ..
		Intent a = new Intent(this, Activity_PedidoEdit.class);
		a.putExtra("id_pedido", weblayer.vendas.DAO.PedidoDAO.GetMobileID());
		a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(a);

	}

}