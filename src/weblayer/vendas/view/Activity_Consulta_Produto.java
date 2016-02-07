package weblayer.vendas.view;

import java.util.ArrayList;
import java.util.List;

import weblayer.vendas.d1.R;
import weblayer.vendas.DAO.ProdutoDAO;
import weblayer.vendas.DTO.ProdutoDTO;
import android.R.bool;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class Activity_Consulta_Produto extends Activity_PainelList {
	
	
	private boolean consultaproduto=false;
	private ArrayList<ProdutoDTO> m_orders = null;
	private OrderAdapter m_adapter;
	private ListView lista;

	private Spinner SpinnerCateg1;
	private SpinAdapterCateg1 AdapterCateg1;

	private Spinner SpinnerCateg2;
	private SpinAdapterCateg2 AdapterCateg2;

	private Spinner SpinnerCateg3;
	private SpinAdapterCateg3 AdapterCateg3;
	
	private String  chamada;
	private String  titulo;
	
	protected void onResume() 
	{
		super.onResume();
		
		weblayer.toolbox.Common.DefinirTheme(this, getResources().getString(R.string.titulo_produto));
		
		consultaproduto=true;
	}
	
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_consulta_produto);
		
		Intent i = getIntent();
		
		if (android.os.Build.VERSION.SDK_INT >= 11) {
			ActionBar actionBar = getActionBar();
			actionBar.setDisplayShowHomeEnabled(false);
			actionBar.setTitle(getString(R.string.titulo_produto));
		
			//Titulo customizado...
			titulo= i.getStringExtra("titulo");
			if (titulo!=null)
				actionBar.setTitle(titulo);
		
		}
		
		FillList("", 0, 0, 0);

		// Evento click da lista
		lista = getListView();
		lista.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				ItemSelecionado(arg2, arg3);

			}
		});

//		pesquisa = (EditText) findViewById(R.id.produto_txt_pesquisa);
//		pesquisa.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//			@Override
//			public boolean onEditorAction(TextView v, int actionId,
//					KeyEvent event) {
//				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//
//					weblayer.vendas.DTO.Categ3DTO categ3 = AdapterCateg3
//							.getItem(SpinnerCateg3.getSelectedItemPosition());
//					
//					weblayer.vendas.DTO.Categ1DTO categ1 = AdapterCateg1
//							.getItem(SpinnerCateg1.getSelectedItemPosition());
//
//					weblayer.vendas.DTO.Categ2DTO categ2 = AdapterCateg2
//							.getItem(SpinnerCateg2.getSelectedItemPosition());
//					
//					FillList(pesquisa.getText().toString(), categ1.getid(), categ2.getid(),
//							categ3.getid());
//
//					return true;
//				}
//				return false;
//			}
//		});
		
		consultaproduto=false;
		
		FillCateg3();
		
		FillCateg1();
		
		FillCateg2();
		
		
		chamada = i.getStringExtra("chamada");
		if (chamada==null)
			chamada="consulta";
		
	}

	// Linha
	private void FillCateg3() {

		weblayer.vendas.DAO.Categ3DAO.initialize(this);

		List<weblayer.vendas.DTO.Categ3DTO> categ3 = weblayer.vendas.DAO.Categ3DAO
				.fillCombo();
		weblayer.vendas.DTO.Categ3DTO[] categ3Array = new weblayer.vendas.DTO.Categ3DTO[categ3
				.size()];
		categ3.toArray(categ3Array);

		AdapterCateg3 = new SpinAdapterCateg3(this,
				android.R.layout.simple_spinner_item, categ3Array);
		SpinnerCateg3 = (Spinner) findViewById(R.id.produto_spin_categ3);
		SpinnerCateg3.setAdapter(AdapterCateg3);

		SpinnerCateg3.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> adapterView, View view,
					int position, long id) {

				weblayer.vendas.DTO.Categ3DTO categ3 = AdapterCateg3
						.getItem(position);

				FillList("", 0, 0, categ3.getid());

				FillCateg1();

			}

			public void onNothingSelected(AdapterView<?> adapter) {
			}
		});

	}

	// Família
	private void FillCateg1() {

		weblayer.vendas.DAO.Categ1DAO.initialize(this);

		weblayer.vendas.DTO.Categ3DTO categ3 = AdapterCateg3
				.getItem(SpinnerCateg3.getSelectedItemPosition());

		List<weblayer.vendas.DTO.Categ1DTO> categ1 = weblayer.vendas.DAO.Categ1DAO
				.fillCombo(categ3.getid());
		weblayer.vendas.DTO.Categ1DTO[] categ1Array = new weblayer.vendas.DTO.Categ1DTO[categ1
				.size()];
		categ1.toArray(categ1Array);

		AdapterCateg1 = new SpinAdapterCateg1(this,
				android.R.layout.simple_spinner_item, categ1Array);
		SpinnerCateg1 = (Spinner) findViewById(R.id.produto_spin_categ1);
		SpinnerCateg1.setAdapter(AdapterCateg1);

		SpinnerCateg1.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> adapterView, View view,
					int position, long id) {

				weblayer.vendas.DTO.Categ1DTO categ1 = AdapterCateg1
						.getItem(position);
				weblayer.vendas.DTO.Categ3DTO categ3 = AdapterCateg3
						.getItem(SpinnerCateg3.getSelectedItemPosition());

				FillList("", categ1.getid(), 0,
						categ3.getid());
				
				FillCateg2();
			}

			public void onNothingSelected(AdapterView<?> adapter) {
			}
		});

	}

	// Categoria
	private void FillCateg2() {

		weblayer.vendas.DAO.Categ2DAO.initialize(this);

		weblayer.vendas.DTO.Categ1DTO categ1 = AdapterCateg1.getItem(SpinnerCateg1.getSelectedItemPosition());

		List<weblayer.vendas.DTO.Categ2DTO> categ2 = weblayer.vendas.DAO.Categ2DAO.fillCombo(categ1.getid());
		
		weblayer.vendas.DTO.Categ2DTO[] categ2Array = new weblayer.vendas.DTO.Categ2DTO[categ2.size()];
		
		categ2.toArray(categ2Array);

		AdapterCateg2 = new SpinAdapterCateg2(this,
				android.R.layout.simple_spinner_item, categ2Array);
		SpinnerCateg2 = (Spinner) findViewById(R.id.produto_spin_categ2);
		SpinnerCateg2.setAdapter(AdapterCateg2);

		SpinnerCateg2.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> adapterView, View view,
					int position, long id) {

				weblayer.vendas.DTO.Categ2DTO categ2 = AdapterCateg2
						.getItem(position);
				weblayer.vendas.DTO.Categ1DTO categ1 = AdapterCateg1
						.getItem(SpinnerCateg1.getSelectedItemPosition());
				weblayer.vendas.DTO.Categ3DTO categ3 = AdapterCateg3
						.getItem(SpinnerCateg3.getSelectedItemPosition());

				FillList("", categ1.getid(),
						categ2.getid(), categ3.getid());

			}

			public void onNothingSelected(AdapterView<?> adapter) {
			}
		});

	}

	private void ItemSelecionado(int position, long id) {
		
		
		ProdutoDTO o = m_orders.get(position);
		
		if (chamada.equals("retorno"))
		{
			//retorna o cliente selecionada para quem chamou.
			 Intent returnIntent = new Intent();
			 returnIntent.putExtra("id_produto", o.getid());
			 returnIntent.putExtra("ds_nome", o.getds_nome_completo());
			 returnIntent.putExtra("id_produto_retaguarda", o.getid_retaguarda());
			 
			 setResult(RESULT_OK,returnIntent);
			 finish();
			 
		}
		else
		{
			//chamada tela de detalhe de cliente.
			Intent intent = new Intent(this, Activity_Produto_Detalhe.class);
			intent.putExtra("id_produto", o.getid());
			startActivity(intent);
		}
		
	}

	private void FillList(String Filtro, int categ1, int categ2, int categ3) {

		try {
			
			if (!consultaproduto)
				return;
			
			ProdutoDAO.initialize(this);
			m_orders = new ArrayList<ProdutoDTO>();
			
			if (chamada.equals("retorno"))
				m_orders = (ArrayList<ProdutoDTO>) ProdutoDAO.fillAtivos(Filtro, categ1,categ2, categ3);
			else
				m_orders = (ArrayList<ProdutoDTO>) ProdutoDAO.fill(Filtro, categ1,categ2, categ3);
				
			this.m_adapter = new OrderAdapter(this,
					R.layout.activity_consulta_produto_linha, m_orders);
			setListAdapter(this.m_adapter);

			Log.i("ARRAY", "" + m_orders.size());

		} catch (Exception e) {

			Log.e("produto", "fillproduto", e);
		}

	}

	private class OrderAdapter extends ArrayAdapter<ProdutoDTO> {
		
		private int[] colors = new int[] { 0xFFFFFFFF, 0xFFF5F5F5};
		private ArrayList<ProdutoDTO> items;

		public OrderAdapter(Context context, int textViewResourceId,
				ArrayList<ProdutoDTO> items) {
			super(context, textViewResourceId, items);
			this.items = items;

		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.activity_consulta_produto_linha, null);
			}
			ProdutoDTO o = items.get(position);
			if (o != null) {

				TextView tt = (TextView) v.findViewById(R.id.toptext);
				TextView bt = (TextView) v.findViewById(R.id.bottomtext);
				ImageView im = (ImageView) v.findViewById(R.id.iconproduto);

				if (tt != null) {
					tt.setText(getResources().getString(
							R.string.produto_lbl_codigo)
							+ ": " + o.getid_retaguarda());
				}

				if (bt != null) {
					bt.setText(o.getds_nome_completo());
				}

				if (o.getfl_ativo() == 1)
					im.setImageResource(R.drawable.bola_verde);
				else
					im.setImageResource(R.drawable.bola_vermelha);

			}
			
			int colorPos = position % colors.length;
			v.setBackgroundColor(colors[colorPos]);
			
			return v;
		}
	}

}
