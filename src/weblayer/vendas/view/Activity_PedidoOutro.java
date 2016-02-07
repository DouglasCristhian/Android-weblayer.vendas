package weblayer.vendas.view;

import weblayer.vendas.d1.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

public class Activity_PedidoOutro extends Activity {

	EditText msgnf;
	EditText msgpedido;
	EditText msgleadtime; 
	EditText idweb;
	TextView lblmsgleadtime ;
	TextView lblidweb;
	
	NumberFormat z;
	
	protected void onResume() 
	{
		super.onResume();
		
		Intent i = getIntent();
		int id_pedido = i.getIntExtra("id_pedido", 0);

		weblayer.vendas.DAO.PedidoDAO.initialize(this);
		weblayer.vendas.DTO.PedidoDTO pedido = weblayer.vendas.DAO.PedidoDAO
				.Get(id_pedido);

		if (pedido == null)
			return;
	
	}
	

	public void onCreate(Bundle savedInstanceState) {
		
		weblayer.toolbox.Common.DefinirTheme(this,"");
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pedido_outro);

		Intent i = getIntent();
		int id_pedido = i.getIntExtra("id_pedido", 0);

		weblayer.vendas.DAO.PedidoDAO.initialize(this);
		weblayer.vendas.DTO.PedidoDTO pedido = weblayer.vendas.DAO.PedidoDAO
				.Get(id_pedido);

		if (pedido == null)
			return;

		z = NumberFormat.getCurrencyInstance();

		DecimalFormatSymbols decimalFormatSymbols = ((DecimalFormat) z)
				.getDecimalFormatSymbols();
		decimalFormatSymbols.setCurrencySymbol("");
		((DecimalFormat) z).setDecimalFormatSymbols(decimalFormatSymbols);

		msgpedido = (EditText) findViewById(R.id.txtpedidomensagempedido);
		msgnf = (EditText) findViewById(R.id.txtpedidomensagemnf);

		msgleadtime = (EditText) findViewById(R.id.txtpedidomensagemteadtime);
		idweb = (EditText) findViewById(R.id.txtpedidoidweb);

		lblmsgleadtime = (TextView) findViewById(R.id.lblpedidomensagemteadtime);
		lblidweb = (TextView) findViewById(R.id.lblpedidoidweb);
		
		msgpedido.setText(pedido.getds_observacao());
		msgnf.setText(pedido.getds_observacao_nf());

		getSharedPreferences("VENDAS", MODE_PRIVATE).edit()
				.putString("mensagepedido", pedido.getds_observacao()).commit();
		
		getSharedPreferences("VENDAS", MODE_PRIVATE).edit()
				.putString("mensagepedidonf", pedido.getds_observacao_nf())
				.commit();

		msgleadtime.setText(pedido.getds_status());
		idweb.setText(String.valueOf(pedido.getid_web()));

		// Definição do status
		if (pedido.getfl_status() > 0) // Pedido somente leitura
		{
			msgpedido.setEnabled(false);
			msgnf.setEnabled(false);
			
		}

		idweb.setVisibility(View.VISIBLE);
		lblidweb.setVisibility(View.VISIBLE);
		if (pedido.getid_web()==0)
		{
			idweb.setVisibility(View.GONE);
			lblidweb.setVisibility(View.GONE);
		}
		
		msgleadtime.setVisibility(View.VISIBLE);
		lblmsgleadtime.setVisibility(View.VISIBLE);
		if (pedido.getds_status() ==null || pedido.getds_status().toString().length()==0)
		{
			msgleadtime.setVisibility(View.GONE);
			lblmsgleadtime.setVisibility(View.GONE);
		}
		
		msgpedido.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

				getSharedPreferences("VENDAS", MODE_PRIVATE)
						.edit()
						.putString("mensagepedido",
								msgpedido.getText().toString()).commit();

			}
		});

		msgnf.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				// getPreferences(MODE_PRIVATE).edit().putString("mensagepedidonf",
				// msgnf.getText().toString()).commit();

				getSharedPreferences("VENDAS", MODE_PRIVATE)
						.edit()
						.putString("mensagepedidonf",
								msgnf.getText().toString()).commit();

			}
		});

		//

	}
}