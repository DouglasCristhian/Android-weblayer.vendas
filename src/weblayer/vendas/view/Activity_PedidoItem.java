package weblayer.vendas.view;

import weblayer.vendas.d1.R;
import weblayer.vendas.DAO.PedidoItemDAO;
import weblayer.vendas.DTO.PedidoItemDTO;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;

public class Activity_PedidoItem extends Activity_PainelList {

	private ArrayList<PedidoItemDTO> m_orders = null;
	private OrderAdapter m_adapter;
	private ListView lista;
	private int id_pedido = 0;
	weblayer.vendas.DTO.PedidoDTO pedido;
	NumberFormat z;
	Button novoitem;
	
	 
	
	public void onCreate(Bundle savedInstanceState) {

		weblayer.toolbox.Common.DefinirTheme(this, "");

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pedido_item);

		z = NumberFormat.getCurrencyInstance();
		DecimalFormatSymbols decimalFormatSymbols = ((DecimalFormat) z)
				.getDecimalFormatSymbols();
		decimalFormatSymbols.setCurrencySymbol("");
		((DecimalFormat) z).setDecimalFormatSymbols(decimalFormatSymbols);

		Intent i = getIntent();
		id_pedido = i.getIntExtra("id_pedido", 0);

		weblayer.vendas.DAO.PedidoDAO.initialize(this);
		pedido = weblayer.vendas.DAO.PedidoDAO.Get(id_pedido);

		if (pedido == null)
			return;

		lista = getListView();
		
		getFillList();
		
		lista.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				ItemSelecionado(arg2, arg3);

			}
		});

		novoitem = (Button) findViewById(R.id.cmbitemnovoitem);

		if (pedido.getfl_status() > 0) // Pedido somente leitura
		{
			novoitem.setVisibility(View.GONE);
		}

	}

	// Novo Item
	public void onBtnItemNovoItem(View view) {

		Intent intent = new Intent(this, Activity_Item.class);
		intent.putExtra("id_pedido", id_pedido);
		intent.putExtra("id_item", 0);
		startActivityForResult(intent, 1);

	}

	private void ItemSelecionado(int position, long id) {

		PedidoItemDTO o = m_orders.get(position);

		if (o.getid()==0)
			return;
		
		Intent intent = new Intent(this, Activity_Item.class);
		intent.putExtra("id_pedido", o.getid_pedido());
		intent.putExtra("id_item", o.getid());
		startActivityForResult(intent, 1);

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == 1) {
			getFillList();
		}
	}

	private void getFillList() {

		try {
			
			PedidoItemDAO.initialize(this);
			m_orders = new ArrayList<PedidoItemDTO>();
			m_orders = (ArrayList<PedidoItemDTO>) PedidoItemDAO.fillAll(id_pedido);
			
			pedido = weblayer.vendas.DAO.PedidoDAO.Get(id_pedido);
			weblayer.vendas.DTO.PedidoItemDTO total = new PedidoItemDTO();
			total.setid(0);
			total.setvl_desconto(pedido.getvl_desconto());
			total.setvl_liquido(pedido.getvl_liquido());
			total.setvl_peso(pedido.getvl_peso());
			total.setnr_quantidade(pedido.getvl_volume());
			total.setvl_lista(pedido.getvl_bruto());
			
			m_orders.add(total);
			
			this.m_adapter = new OrderAdapter(this, R.layout.activity_pedido_item_linha, m_orders);
			setListAdapter(this.m_adapter);
			
			
			Log.i("ARRAY", "" + m_orders.size());

		} catch (Exception e) {

			Log.e("pedido", "fillpedidos", e);
		}

	}

	private class OrderAdapter extends ArrayAdapter<PedidoItemDTO> {

		private int[] colors = new int[] { 0xFFFFFFFF, 0xFFF5F5F5};
		private ArrayList<PedidoItemDTO> items;

		public OrderAdapter(Context context, int textViewResourceId,
				ArrayList<PedidoItemDTO> items) {
			super(context, textViewResourceId, items);
			this.items = items;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.activity_pedido_item_linha, null);
			}

			NumberFormat z = NumberFormat.getCurrencyInstance();

			DecimalFormatSymbols decimalFormatSymbols = ((DecimalFormat) z)
					.getDecimalFormatSymbols();
			decimalFormatSymbols.setCurrencySymbol("");
			((DecimalFormat) z).setDecimalFormatSymbols(decimalFormatSymbols);

			PedidoItemDTO o = null;
			
			int colorPos = position % colors.length;
			v.setBackgroundColor(colors[colorPos]);
			
			if (position < items.size())
				o = items.get(position);
			
			
			TextView produto = (TextView) v.findViewById(R.id.txtitemproduto);
			
			TextView quantidade1 = (TextView) v
					.findViewById(R.id.txtitemquantidade1);
			TextView precovenda = (TextView) v
					.findViewById(R.id.txtitemprecovenda);

			TextView precototal = (TextView) v
					.findViewById(R.id.txtitemprecototal);

			TextView volume = (TextView) v
					.findViewById(R.id.txtitemx);
			
			ImageView imgstatus = (ImageView) v.findViewById(R.id.imgitemstatus);
			
			if (o != null && o.getid()==0) {
				
				if (produto != null) {
					produto.setText(getResources().getString(R.string.lbl_total)+ z.format(o.getvl_lista()));
				}

				if (quantidade1 != null) {
					quantidade1.setText(getResources().getString(R.string.lbl_desconto) + z.format(o.getvl_desconto()));
				}

				if (precovenda != null) {
					precovenda.setVisibility(View.VISIBLE);
					precovenda.setText(getResources().getString(R.string.lbl_liquido) + z.format(o.getvl_liquido()));
				}

				if (precototal != null) {
					precototal.setText(getResources().getString(R.string.lbl_peso) + o.getvl_peso().toString());
				}
				
				if (volume!= null) {
					volume.setVisibility(View.VISIBLE);
					volume.setText(getResources().getString(R.string.lbl_volume) + String.valueOf(o.getnr_quantidade()));
				}

				imgstatus.setImageResource(0);
				v.setBackgroundColor(0xFFF5F5F5);
				
			}
			
			if (o != null && o.getid()>0) {
				
				volume.setVisibility(View.GONE);
				precovenda.setVisibility(View.GONE);
				
				if (produto != null) {
					produto.setText("[" + o.getid_produto_retaguarda() + "] "
							+ o.getds_produto_nome_completo());
				}

				if (quantidade1 != null) {
					quantidade1.setText("Quantidade: " + String.valueOf(o.getnr_quantidade()));
				}

				if (precovenda != null) {
					precovenda.setText("Valor Venda: " + z.format(o.getvl_unitario()));
				}

				if (precototal != null) {
					precototal.setText("Valor Total: " + z.format(o.getvl_liquido()));
				}

				if (imgstatus != null) {
					imgstatus.setImageResource(0);	
					if (o.getfl_cancelado() > 0) {
						if (o.getfl_cancelado() == 1)
							imgstatus.setImageResource(R.drawable.bola_vermelha);

						if (o.getfl_cancelado() == 2)
							imgstatus.setImageResource(R.drawable.block);
					} else {
						if (o.getdt_faturamento() != null)
							imgstatus.setImageResource(R.drawable.bola_verde);

						if (o.getdt_entrega() != null)
							imgstatus.setImageResource(R.drawable.caminhao);

					}

				}

			}

		

			return v;

		}
	}
}