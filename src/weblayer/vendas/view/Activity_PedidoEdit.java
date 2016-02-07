package weblayer.vendas.view;

import java.util.Date;

import weblayer.vendas.DAO.ParametroDAO;
import weblayer.vendas.DAO.PedidoDAO;
import weblayer.vendas.DAO.PedidoItemDAO;
import weblayer.vendas.d1.R;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class Activity_PedidoEdit extends TabActivity {

	private Button bntsalvar;
	private Button bntfinalizar;
	private Button bntexcluir;
	private weblayer.vendas.DTO.PedidoDTO pedido;
	int id_pedido ;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		weblayer.toolbox.Common.DefinirTheme(this, getResources().getString(R.string.titulo_pedido_detalhe));
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pedido_edit);
		
		if (android.os.Build.VERSION.SDK_INT >= 11) { 
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setTitle("Pedido");
		}
		
		Resources ressources = getResources();
		TabHost tabHost = getTabHost();

		Intent i = getIntent();
		id_pedido = i.getIntExtra("id_pedido", 0);

		weblayer.vendas.DAO.PedidoDAO.initialize(this);
		pedido = weblayer.vendas.DAO.PedidoDAO.Get(id_pedido);
		if (pedido == null)
			return;

		// Capa tab
		Intent intentCapa = new Intent().setClass(this,
				Activity_PedidoCapa.class);
		intentCapa.putExtra("id_pedido", id_pedido);
		TabSpec tabSpecCapa = tabHost.newTabSpec("Capa").setIndicator("Capa")
				.setContent(intentCapa);

		// Item tab
		Intent intentItem = new Intent().setClass(this,
				Activity_PedidoItem.class);
		intentItem.putExtra("id_pedido", id_pedido);
		TabSpec tabSpecItem = tabHost.newTabSpec("Itens").setIndicator("Itens")
				.setContent(intentItem);

		// Outros tab
		Intent intentOutros = new Intent().setClass(this,
				Activity_PedidoOutro.class);
		intentOutros.putExtra("id_pedido", id_pedido);
		TabSpec tabSpecOutros = tabHost.newTabSpec("Outros")
				.setIndicator("Outros").setContent(intentOutros);

		// Adicionando as tabs
		tabHost.addTab(tabSpecCapa);
		tabHost.addTab(tabSpecItem);
		tabHost.addTab(tabSpecOutros);

		TextView x;

		x = (TextView) tabHost.getTabWidget().getChildAt(0)
				.findViewById(android.R.id.title);
		x.setTextSize(12);

		x = (TextView) tabHost.getTabWidget().getChildAt(1)
				.findViewById(android.R.id.title);
		x.setTextSize(12);

		x = (TextView) tabHost.getTabWidget().getChildAt(2)
				.findViewById(android.R.id.title);
		x.setTextSize(12);

		// definindo a tab corrente.
		if (pedido.getvl_liquido() == 0)
			tabHost.setCurrentTab(1);

		bntsalvar = (Button) findViewById(R.id.pedidosaveButton);
		bntfinalizar = (Button) findViewById(R.id.pedidodoneButton);
		bntexcluir = (Button) findViewById(R.id.pedidodeleteButton);

		// Verificar o status do pedido e ocultar os botões...
		if (pedido.getfl_status() > 0) // Pedido somente leitura
		{
			bntsalvar.setVisibility(View.GONE);
			bntfinalizar.setVisibility(View.GONE);
			bntexcluir.setVisibility(View.GONE);
			tabHost.setCurrentTab(0);
		}

	}

	public void onBtnPedidoEditSalvar(View view) throws Exception {

		weblayer.vendas.DAO.PedidoDAO.initialize(this);

		String mensagepedido = getSharedPreferences("VENDAS", MODE_PRIVATE)
				.getString("mensagepedido", "");
		
		String mensagepedidonf = getSharedPreferences("VENDAS", MODE_PRIVATE)
				.getString("mensagepedidonf", "");

		pedido.setds_observacao(mensagepedido);
		pedido.setds_observacao_nf(mensagepedidonf);

		weblayer.vendas.DAO.PedidoDAO.AtualizaTotais(this, pedido);
		weblayer.vendas.DAO.PedidoDAO.update(pedido);

		getSharedPreferences("VENDAS", MODE_PRIVATE).edit()
				.putString("refresh_pedido", "1").commit();

		// setResult(RESULT_OK,null);
		finish();
	}

	public void onBtnPedidoEditFinalizar(View view) {

		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		adb.setTitle("ATENÇÃO");
		adb.setMessage("Confirma a finalização do pedido? ");
		adb.setPositiveButton("Sim", new AlertDialog.OnClickListener() {
			public void onClick(DialogInterface dialog, int arg1) {
				
				if (!FinalizarPedido())
					return;
				
				EnviarPedido();
				
			}
		});
		adb.setNegativeButton("Não", new AlertDialog.OnClickListener() {
			public void onClick(DialogInterface dialog, int arg1) {
				//
			}

		});
		adb.show();

	}
	
	private boolean FinalizarPedido() 
	{
		
		weblayer.vendas.DAO.PedidoDAO.initialize(this);
		pedido = weblayer.vendas.DAO.PedidoDAO.Get(id_pedido);
		if (pedido == null)
			return false;
		
		//Criticar o valor do pedido;
		if (pedido.getvl_liquido() <= 0) {
			Toast.makeText(
					getApplicationContext(),
					"Valor do pedido inválido.",
					Toast.LENGTH_LONG).show();
			return false;
		}
		
		String mensagepedido = getSharedPreferences("VENDAS", MODE_PRIVATE)
				.getString("mensagepedido", "");
		String mensagepedidonf = getSharedPreferences("VENDAS", MODE_PRIVATE)
				.getString("mensagepedidonf", "");

		pedido.setds_observacao(mensagepedido);
		pedido.setds_observacao_nf(mensagepedidonf);
		pedido.setdt_inclusao_mobile(new Date());

		pedido.setfl_status(1);
		
		try {
			weblayer.vendas.DAO.PedidoDAO.AtualizaTotais(this, pedido);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		weblayer.vendas.DAO.PedidoDAO.update(pedido);

		getSharedPreferences("VENDAS", MODE_PRIVATE).edit()
				.putString("refresh_pedido", "1").commit();
		
		return true;
	}
	
	
	private void EnviarPedido()
	{
		if (!weblayer.toolbox.Common.isNetWorkActive(this))
		{	
			Activity_PedidoEdit.this.finish();
			return;
		}
		
		LongOperation processo = new LongOperation(this);
	    processo.execute("");
		
	}
	
	public void onBtnPedidoEditDeletar(View view) {

		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		adb.setTitle("[ATENÇÃO]");
		adb.setMessage("Confirma a exclusão do pedido?");
		adb.setNegativeButton("Não", new AlertDialog.OnClickListener() {
			public void onClick(DialogInterface dialog, int arg1) {
				// Cancel Event
			}

		});

		adb.setPositiveButton("Sim", new AlertDialog.OnClickListener() {
			public void onClick(DialogInterface dialog, int arg1) {
				ExcluirPedido();
			}
		});
		adb.show();

	}

	private void ExcluirPedido() {

		Intent i = getIntent();
		int id_pedido = i.getIntExtra("id_pedido", 0);

		weblayer.vendas.DAO.PedidoDAO.initialize(this);
		weblayer.vendas.DTO.PedidoDTO pedido = weblayer.vendas.DAO.PedidoDAO
				.Get(id_pedido);
		if (pedido == null)
			return;

		weblayer.vendas.DAO.PedidoDAO.delete(pedido);

		getSharedPreferences("VENDAS", MODE_PRIVATE).edit()
				.putString("refresh_pedido", "1").commit();

		finish();

	}

	private class LongOperation extends AsyncTask<String, Void, String> {
		 
		private ProgressDialog progress;
	    private Context context;
		
	    public LongOperation(Context context) {
            this.context = context;
        }
		  
		@Override
        protected void onPreExecute() {
			
			progress = new ProgressDialog(context);
			progress.setTitle("Envio de Pedido.");
			progress.setMessage("Por favor, aguarde...");
            progress.show();
            
			PedidoDAO.initialize(context);
			PedidoItemDAO.initialize(context);
        
		}
		
		@Override
        protected String doInBackground(String... params) {
              
			try {
				
				ParametroDAO.initialize(getApplicationContext());

				weblayer.vendas.SINC.PedidoSINC.EnviarPedido(context, ParametroDAO.GetByKey("WEBSERVICE", ""), Integer.parseInt(ParametroDAO.GetByKey("ID_EMPRESA", "0")), ParametroDAO.GetByKey("VENDEDOR", ""),ParametroDAO.GetByKey("IMEI", ""), pedido.getid());
				
				return "Pedido enviado.";

			} catch (Exception e) {
				
				return "ERRO: " + e.getMessage();
			}


        }      

        @Override
        protected void onPostExecute(String result) {
        	
        	progress.dismiss();
        	
        	Toast.makeText(
					getApplicationContext(),
					result,
					Toast.LENGTH_LONG).show();
        	
        	Activity_PedidoEdit.this.finish();
        
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
  }  
	

}
