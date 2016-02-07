package weblayer.vendas.view;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import weblayer.vendas.d1.R;
import weblayer.vendas.DAO.PedidoDAO;
import weblayer.vendas.DAO.ProdutoDAO;
import weblayer.vendas.DTO.ClienteDTO;
import weblayer.vendas.DTO.PedidoDTO;
import weblayer.vendas.DTO.ProdutoDTO;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.DateTimeKeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Activity_Consulta_Pedido extends Activity_PainelList {

	private ArrayList<PedidoDTO> m_orders = null;
	private OrderAdapter m_adapter;
	private SimpleDateFormat formatodata = new SimpleDateFormat("dd/MM/yy HH:mm");
	private ListView lista;
	private String response;
	
	@Override
	protected void onResume() 
	{
		super.onResume();
		
		weblayer.toolbox.Common.DefinirTheme(this, getResources().getString(R.string.titulo_pedido));
		
		String refresh = getSharedPreferences("VENDAS", MODE_PRIVATE).getString("refresh_pedido","0");
		if (refresh.equals("1"))
			getFillList();
		
		getSharedPreferences("VENDAS", MODE_PRIVATE).edit().putString("refresh_pedido", "0").commit();
		
	}

	
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		if (android.os.Build.VERSION.SDK_INT >= 11) {
			ActionBar actionBar = getActionBar();
			actionBar.setDisplayShowHomeEnabled(false);
			actionBar.setTitle(getString(R.string.titulo_pedido));
		}
		
		setContentView(R.layout.activity_consulta_pedido);

		getSharedPreferences("VENDAS", MODE_PRIVATE).edit().putString("refresh_pedido", "1").commit();
		 
		lista =getListView();
		lista.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		        
				ItemSelecionado(arg2, arg3);
		        
			}});

	}
	
	private void ItemSelecionado(int position, long id)
	{
		
		PedidoDTO o = m_orders.get(position);
		
		Intent intent = new Intent(this, Activity_PedidoEdit.class);
		intent.putExtra("id_pedido", o.getid());
		startActivity(intent);
		
	}
	
	// Novo Pedido
	public void onBtnPedidoNovo(View view) {

		Intent intent = new Intent(this, Activity_PedidoNovo.class);
		startActivity(intent);

	}
	
	
	
	private void getFillList() {

		try {

			PedidoDAO.initialize(this); 
			m_orders = new ArrayList<PedidoDTO>();
			m_orders = (ArrayList<PedidoDTO>) PedidoDAO.fillAll();
			
			this.m_adapter = new OrderAdapter(this, R.layout.activity_consulta_pedido_linha, m_orders);
			 setListAdapter(this.m_adapter);
			 
			Log.i("ARRAY", "" + m_orders.size());

		} catch (Exception e) {

			Log.e("pedido", "fillpedidos", e);
		}

	}

	private class OrderAdapter extends ArrayAdapter<PedidoDTO> {
		
		private int[] colors = new int[] { 0xFFFFFFFF, 0xFFF5F5F5};
		private ArrayList<PedidoDTO> items;

		public OrderAdapter(Context context, int textViewResourceId, ArrayList<PedidoDTO> items) {
			super(context, textViewResourceId, items);
			this.items = items;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.activity_consulta_pedido_linha, null);
			}

			NumberFormat z = NumberFormat.getCurrencyInstance();
			
			DecimalFormatSymbols decimalFormatSymbols = ((DecimalFormat) z).getDecimalFormatSymbols();
			decimalFormatSymbols.setCurrencySymbol("");
			((DecimalFormat) z).setDecimalFormatSymbols(decimalFormatSymbols);
			
			PedidoDTO o = null;
			
			if (position < items.size())
				o = items.get(position);	
			
			if (o != null) {
				
				ImageView imgentrada =(ImageView )v.findViewById(R.id.imgentrada); 
				ImageView imgstatus =(ImageView )v.findViewById(R.id.imgstatus);
				
				TextView tt = (TextView) v.findViewById(R.id.toptext);
				TextView bt = (TextView) v.findViewById(R.id.bottomtext);
				TextView ft = (TextView) v.findViewById(R.id.footertext);
				TextView ft1 = (TextView) v.findViewById(R.id.footertext1);

				if (tt != null) {
					tt.setText(getResources().getString(
							R.string.pedido_lbl_data)
							+ ": "
							+ formatodata.format(o.getdt_inclusao_mobile()));
				}

				if (bt != null) {
					bt.setText(getResources().getString(
							R.string.pedido_lbl_cliente)
							+ ": " + o.getds_cliente());
				}

				if (ft != null) {
					ft.setText(getResources().getString(
							R.string.pedido_lbl_volume)
							+ ": " + String.valueOf(o.getvl_volume()));
				
				}

				if (ft1 != null) {
					ft1.setText(getResources().getString(
							R.string.pedido_lbl_valorliquido)
							+ ": " + z.format(o.getvl_liquido()));
				}
				
				if (imgentrada!=null)
				{
					
					if (o.getfl_entrada()==1)
						imgentrada.setImageResource(R.drawable.mobile);
						
					if (o.getfl_entrada()==2)
						imgentrada.setImageResource(R.drawable.internet);
					
					if (o.getfl_entrada()==3)
						imgentrada.setImageResource(R.drawable.process);
					
					if (o.getfl_entrada()==4)
						imgentrada.setImageResource(R.drawable.factory);
					
				}
				
				if (imgstatus!=null)
				{
					
					if (o.getfl_status()==0)
						imgstatus.setImageResource(R.drawable.alerta);
					
					if (o.getfl_status()==1)
						imgstatus.setImageResource(R.drawable.bandeira_amarela);
					
					if (o.getfl_status()==4)
						imgstatus.setImageResource(R.drawable.bandeira_azul);
					
					if (o.getfl_status()==5)
						imgstatus.setImageResource(R.drawable.bola_vermelha);
					
					if (o.getfl_status()==6)
						imgstatus.setImageResource(R.drawable.bandeira_vermelha);
					
					if (o.getfl_status()==7)
						imgstatus.setImageResource(R.drawable.bola_amarela);
					
					if (o.getfl_status()==8)
						imgstatus.setImageResource(R.drawable.bola_verde);
					
					if (o.getfl_status()==9)
						imgstatus.setImageResource(R.drawable.caminhao_alerta);
					
					if (o.getfl_status()==10)
						imgstatus.setImageResource(R.drawable.caminhao);
					
					if (o.getfl_status()==11)
						imgstatus.setImageResource(R.drawable.block);
				}
				
				
			}
			
			int colorPos = position % colors.length;
			v.setBackgroundColor(colors[colorPos]);
			
			return v;
			
		}
	}

}