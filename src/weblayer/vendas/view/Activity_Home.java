package weblayer.vendas.view;

import java.util.Date;

import weblayer.vendas.DAO.Categ1DAO;
import weblayer.vendas.DAO.Categ2DAO;
import weblayer.vendas.DAO.Categ3DAO;
import weblayer.vendas.DAO.ClienteDAO;
import weblayer.vendas.DAO.ClienteGrupoProdutoDAO;
import weblayer.vendas.DAO.FilialDAO;
import weblayer.vendas.DAO.ParametroDAO;
import weblayer.vendas.DAO.PedidoDAO;
import weblayer.vendas.DAO.PedidoItemDAO;
import weblayer.vendas.DAO.ProdutoDAO;
import weblayer.vendas.DAO.TabelaPrecoDAO;
import weblayer.vendas.DTO.ParametroDTO;
import weblayer.vendas.SINC.Categ1SINC;
import weblayer.vendas.SINC.Categ2SINC;
import weblayer.vendas.SINC.Categ3SINC;
import weblayer.vendas.SINC.ClienteGrupoProdutoSINC;
import weblayer.vendas.SINC.ClienteSINC;
import weblayer.vendas.SINC.DispositivoSINC;
import weblayer.vendas.SINC.FilialSINC;
import weblayer.vendas.SINC.ParametroSINC;
import weblayer.vendas.SINC.PedidoSINC;
import weblayer.vendas.SINC.ProdutoSINC;
import weblayer.vendas.SINC.TabelaPrecoSINC;
import weblayer.vendas.SINC.UrlServiceSINC;
import weblayer.vendas.d1.R;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Activity_Home extends Activity {

	private Boolean fl_sincronizado;
	private ProgressDialog progress;
	private Boolean fl_sincronizando;
	
	Button NovoPedido;
	Button Pedidos;
	Button Clientes;
	Button Produtos;

	@Override
	protected void onResume() {
		super.onResume();

		NovoPedido = (Button) findViewById(R.id.main_btn_novopedido);
		Pedidos = (Button) findViewById(R.id.main_btn_pedido);
		Clientes = (Button) findViewById(R.id.main_btn_cliente);
		Produtos = (Button) findViewById(R.id.main_btn_produto);

		VerificarIcones();

	}

	private void VerificarIcones() {

		fl_sincronizado = false;

		// Verificar a disponibilidade dos itens.
		ParametroDAO.initialize(getApplicationContext());

		String ultimasinc = ParametroDAO.GetByKey("ULTIMASINC","");
		
		if (ultimasinc.length() > 0)
			fl_sincronizado = true;

		if (!fl_sincronizado) {
			NovoPedido.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.plus_disabled, 0, 0);
			Pedidos.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.pedido_disabled, 0, 0);
			Clientes.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.cliente_disabled, 0, 0);
			Produtos.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.produto_disabled, 0, 0);

		} else {
			NovoPedido.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.plus, 0, 0);
			Pedidos.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.pedido, 0, 0);
			Clientes.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.cliente, 0, 0);
			Produtos.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.produto, 0, 0);
			
			this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
		}
		
		fl_sincronizando=false;
		
	}
	
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {

		
		weblayer.toolbox.Common.DefinirTheme(this, getResources().getString(R.string.titulo_home));
		
		super.onCreate(savedInstanceState);
		
		if (android.os.Build.VERSION.SDK_INT >= 11) {
			ActionBar actionBar = getActionBar();
			actionBar.setDisplayShowHomeEnabled(false);
			actionBar.setTitle(getResources().getString(R.string.titulo_home));
		}
			
		
		setContentView(R.layout.activity_home);

	}

	public void onButtonClicker(View v) {
		
		if (fl_sincronizando)
			return;
		
		Intent intent;

		switch (v.getId()) {

		/* Pedidos */
		case R.id.main_btn_pedido:
			if (fl_sincronizado) {
				intent = new Intent(this, Activity_Consulta_Pedido.class);
				startActivity(intent);
			}
			break;

		/* Clientes */
		case R.id.main_btn_cliente:
			if (fl_sincronizado) {
				intent = new Intent(this, Activity_Consulta_Cliente.class);
				startActivity(intent);
			}
			break;

		/* Produtos */
		case R.id.main_btn_produto:
			if (fl_sincronizado) {
				intent = new Intent(this, Activity_Consulta_Produto.class);
				startActivity(intent);
			}
			break;

		/* Config */
		case R.id.main_btn_config:

			intent = new Intent(this, Activity_Config.class);
			startActivity(intent);
			break;

		/* Sinc */
		case R.id.main_btn_sinc:
			Sinc();
			break;

		/* Novo Pedido */
		case R.id.main_btn_novopedido:
			if (fl_sincronizado) {
				intent = new Intent(this, Activity_PedidoNovo.class);
				startActivity(intent);
			}
			break;

		default:

			break;
		}
	}

	private void Sinc() {
		
		
		if (fl_sincronizando)
			return;
		
		fl_sincronizando=true;
		
		mLockScreenRotation();
		
		LongOperation processo = new LongOperation(this);
		processo.execute("");
		
	}

	private void Sincronizar(Context context) throws Exception {

		String ultimasinc = "";
		String vendedor = "";
		String imei = "";
		String codigo = "";
		String webservice="";
		int id_empresa =0;
		
		
		// Testar conex�o com a internet
		if (weblayer.toolbox.Common.RetornaConnection(this) == 0) {
			throw new Exception("Sem conex�o com a internet.");
		}

		// Buscar o webservice do c�digo do cliente..
		ParametroDAO.initialize(getApplicationContext());

		ultimasinc = ParametroDAO.GetByKey("ULTIMASINC", "2000/01/01 00:00:00");
		if (ultimasinc.length() == 0)
			ultimasinc = "2000/01/01 00:00:00";
		
		vendedor = ParametroDAO.GetByKey("VENDEDOR", "");
		imei = ParametroDAO.GetByKey("IMEI", "");
		codigo = ParametroDAO.GetByKey("CONTA", "");
		
		//Caso n�o tenha parametrizado o endere�o do webservice.
		webservice= ParametroDAO.GetByKey("WEBSERVICE", "");
		if (webservice.length() == 0)
			UrlServiceSINC.Sincronizar(this, codigo);
		
		id_empresa = Integer.parseInt(ParametroDAO.GetByKey("ID_EMPRESA", "0"));
		if (id_empresa==0)
			throw new Exception("Empresa inv�lida.");
		
		//caso n�o encontrar, apontar erro
		webservice= ParametroDAO.GetByKey("WEBSERVICE", "");
		if (webservice.length() == 0)
			throw new Exception("Conta inv�lida. Webservice inv�lido.");
		
		if (vendedor.length() == 0)
			throw new Exception("Vendedor inv�lido.");

		if (imei.length() == 0)
			throw new Exception("IMEI inv�lido.");

		// Buscar os parametros..
		DispositivoSINC.Sincronizar(this, webservice, id_empresa, vendedor, imei, ultimasinc);
		ParametroSINC.Sincronizar(this, webservice, id_empresa, vendedor, imei, ultimasinc);

		FilialSINC.Sincronizar(this, webservice, id_empresa, vendedor, imei, ultimasinc);
		ClienteSINC.Sincronizar(this, webservice, id_empresa, vendedor, imei, ultimasinc);
		ProdutoSINC.Sincronizar(this, webservice, id_empresa, vendedor, imei, ultimasinc);
		TabelaPrecoSINC.Sincronizar(this, webservice, id_empresa, vendedor, imei, ultimasinc);
		PedidoSINC.Sincronizar(this, webservice, id_empresa, vendedor, imei, ultimasinc);
		ClienteGrupoProdutoSINC.Sincronizar(this, webservice, id_empresa, vendedor, imei, ultimasinc);

		Categ1SINC.Sincronizar(this, webservice, id_empresa, vendedor, imei, ultimasinc);
		Categ2SINC.Sincronizar(this, webservice, id_empresa, vendedor, imei, ultimasinc);
		Categ3SINC.Sincronizar(this, webservice, id_empresa, vendedor, imei, ultimasinc);

		// Atualizar a data de sincroniza��o
		weblayer.vendas.DTO.ParametroDTO param = new ParametroDTO();
		param.setds_chave("ULTIMASINC");
		param.setds_valor(weblayer.toolbox.Common
				.convertDatetoString(new Date()));
		ParametroDAO.insertorupdate(param);

	}

	public void onDestroy() {
		super.onDestroy();
		if (progress != null && progress.isShowing()) {
			progress.cancel();
		}

	}

	private void mLockScreenRotation() {
		// Stop the screen orientation changing during an event
		switch (this.getResources().getConfiguration().orientation) {
		case Configuration.ORIENTATION_PORTRAIT:
			this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			break;
		case Configuration.ORIENTATION_LANDSCAPE:
			this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			break;
		}
	}

	private class LongOperation extends AsyncTask<String, Void, String> {

		private Context context;
		
		
		public LongOperation(Context context) {
			this.context = context;
		}

		@Override
		protected void onPreExecute() {

			
			progress = new ProgressDialog(context);
			progress.setTitle(R.string.lbl_sincronizacao);
			progress.setMessage(getResources().getString(R.string.lbl_porfavoraguarde));
			progress.show();

			Categ1DAO.initialize(context);
			Categ2DAO.initialize(context);
			Categ3DAO.initialize(context);
			ClienteGrupoProdutoDAO.initialize(context);
			ClienteDAO.initialize(context);
			ParametroDAO.initialize(context);
			FilialDAO.initialize(context);
			ParametroDAO.initialize(context);
			PedidoDAO.initialize(context);
			PedidoItemDAO.initialize(context);
			ProdutoDAO.initialize(context);
			TabelaPrecoDAO.initialize(context);
			
			fl_sincronizando=true;

		}

		@Override
		protected String doInBackground(String... params) {

			try {

				Sincronizar(context);

				return "Sincroniza��o finalizada.";

			} catch (Exception e) {

				return "ERRO: " + e.getMessage();
			}

		}

		@Override
		protected void onPostExecute(String result) {

			try {
				progress.dismiss();
			} catch (Exception e) {
			}

			Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG)
					.show();
			
			fl_sincronizando=false;
			
			VerificarIcones();
			
		}

		@Override
		protected void onProgressUpdate(Void... values) {
		}
	}

}