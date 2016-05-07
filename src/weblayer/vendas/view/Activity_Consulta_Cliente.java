package weblayer.vendas.view;

import java.util.ArrayList;

import weblayer.vendas.DAO.ClienteDAO;
import weblayer.vendas.DTO.ClienteDTO;
import weblayer.vendas.d1.R;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Activity_Consulta_Cliente extends Activity_PainelList {

	private ArrayList<ClienteDTO> m_orders = null;
	private OrderAdapter m_adapter;
	private EditText pesquisa;
	private ListView lista;
	private String chamada;
	private String titulo;
	
	@Override
	protected void onResume() 
	{
		super.onResume();
		weblayer.toolbox.Common.DefinirTheme(this, getResources().getString(R.string.titulo_cliente));
	}
	
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
		Intent i = getIntent();
		
		if (android.os.Build.VERSION.SDK_INT >= 11) {
			ActionBar actionBar = getActionBar();
			actionBar.setDisplayShowHomeEnabled(false);
			actionBar.setTitle(getString(R.string.titulo_cliente));
			
			//Titulo customizado...
			titulo= i.getStringExtra("titulo");
			if (titulo!=null)
				actionBar.setTitle(titulo);
		}
		
		setContentView(R.layout.activity_consulta_cliente);
		
		//getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		//getListView().setSelector(android.R.color.darker_gray);
		
		
		chamada = i.getStringExtra("chamada");
		
		
		
		if (chamada == null)
			chamada = "consulta";

		FillList("");

		// Evento click da lista
		lista = getListView();
		lista.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				ItemSelecionado(arg2, arg3);

			}
		});

		// Pesquisa da caixa de texto
		pesquisa = (EditText) findViewById(R.id.produto_txt_pesquisa);
		pesquisa.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {

					FillList(pesquisa.getText().toString());
					return true;
				}
				return false;
			}
		});

	}

	private void ItemSelecionado(int position, long id) {

		
		
		
		ClienteDTO o = m_orders.get(position);
		// Verificar qual o tipo de chamada..

		if (chamada.equals("retorno")) {
			// retorna o cliente selecionada para quem chamou.
			Intent returnIntent = new Intent();
			returnIntent.putExtra("id_cliente", o.getid());
			returnIntent.putExtra("ds_cliente", o.getid_retaguarda() + " - "
					+ o.getds_razao());
			returnIntent.putExtra("ds_canal", o.getds_canal());
			returnIntent
					.putExtra("id_cliente_retaguarda", o.getid_retaguarda());

			setResult(RESULT_OK, returnIntent);
			finish();

		} else {
			// chamada tela de detalhe de cliente.
			Intent intent = new Intent(this, Activity_Cliente_Detalhe.class);
			intent.putExtra("id_cliente", o.getid());
			startActivity(intent);
		}
	}

	private void FillList(String Filtro) {

		try {

			ClienteDAO.initialize(this);
			m_orders = new ArrayList<ClienteDTO>();

			if (chamada.equals("retorno"))
				m_orders = (ArrayList<ClienteDTO>) ClienteDAO.fillAtivo(Filtro);
			else
				m_orders = (ArrayList<ClienteDTO>) ClienteDAO.fill(Filtro);

			
			
			this.m_adapter = new OrderAdapter(this,R.layout.activity_consulta_cliente_linha, m_orders);
			
			setListAdapter(this.m_adapter);

			Log.i("ARRAY", "" + m_orders.size());

		} catch (Exception e) {

			Log.e("produto", "fillproduto", e);
		}

	}

	private class OrderAdapter extends ArrayAdapter<ClienteDTO> {

		private int[] colors = new int[] { 0xFFFFFFFF, 0xFFF5F5F5 };

		private ArrayList<ClienteDTO> items;

		public OrderAdapter(Context context, int textViewResourceId,
				ArrayList<ClienteDTO> items) {
			super(context, textViewResourceId, items);
			this.items = items;

		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				//v.setSelected(true);
	            //v.setPressed(true);
				v = vi.inflate(R.layout.activity_consulta_cliente_linha, null);
			}
			
			
            
			ClienteDTO o = items.get(position);
			if (o != null) {

				TextView tt = (TextView) v.findViewById(R.id.toptext);
				TextView bt = (TextView) v.findViewById(R.id.bottomtext);
				TextView ft = (TextView) v.findViewById(R.id.footertext);

				ImageView im = (ImageView) v.findViewById(R.id.iconcliente);

				if (tt != null) {
					tt.setText(getResources().getString(R.string.cliente_lbl_documento)
							+ ": " + weblayer.toolbox.CPFCNPJUtil.formatCPForCPNJ(o.getds_cnpj()));

				
				}

				if (bt != null) {
					bt.setText(getResources().getString(
							R.string.cliente_lbl_codigo) + ": " + o.getid_retaguarda());

				}

				if (ft != null) {
					ft.setText(o.getds_razao());
				}

				if (o.getfl_ativo() == 1)
					im.setImageResource(weblayer.vendas.d1.R.drawable.bola_verde);
				else
					im.setImageResource(weblayer.vendas.d1.R.drawable.bola_vermelha);

			}

			int colorPos = position % colors.length;
			v.setBackgroundColor(colors[colorPos]);

			return v;
		}
	}
}