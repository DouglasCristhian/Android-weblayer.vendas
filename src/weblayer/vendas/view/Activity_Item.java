package weblayer.vendas.view;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;

import weblayer.vendas.d1.R;
import weblayer.vendas.DTO.FilialDTO;
import android.R.bool;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_Item extends Activity {

	int id_pedido;
	int id_item;
	int id_produto;
	String id_produto_retaguarda;
	String ds_produto_nome;

	weblayer.vendas.DTO.PedidoDTO pedido;
	weblayer.vendas.DTO.PedidoItemDTO item;

	private EditText txtproduto;
	private EditText txtquantidade;
	private EditText txtprecolista;
	private EditText txtprecovenda;
	private EditText txtdesconto;
	private EditText txttotal;

	private EditText txtpedidoerp;
	private EditText txtitemerp;
	private EditText txtprevfaturamento;
	private EditText txtpreventrega;
	private EditText txtnumeronf;
	private EditText txtserienf;
	private EditText txtfaturamento;
	private EditText txtentrega;

	private TextView lblpedidoerp;
	private TextView lblitemerp;
	private TextView lblprevfaturamento;
	private TextView lblpreventrega;
	private TextView lblnumeronf;
	private TextView lblserienf;
	private TextView lblfaturamento;
	private TextView lblentrega;

	private Button cmbpesquisaproduto;
	private Button cmbsalvar;
	private Button cmbexcluir;
	private NumberFormat z;

	private Spinner spinnerFilial;
	private SpinAdapterFilial AdapterFilial;

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {

		weblayer.toolbox.Common.DefinirTheme(this,
				getResources().getString(R.string.titulo_pedido_item));

		super.onCreate(savedInstanceState);

		if (android.os.Build.VERSION.SDK_INT >= 11) {
			ActionBar actionBar = getActionBar();
			actionBar.setDisplayShowHomeEnabled(false);
			actionBar.setTitle("Item de Pedido");
		}

		setContentView(R.layout.activity_item);

		txtproduto = (EditText) findViewById(R.id.txt_item_produto);
		txtquantidade = (EditText) findViewById(R.id.txt_item_quantidade);
		txtprecolista = (EditText) findViewById(R.id.txt_item_precolista);
		txtprecovenda = (EditText) findViewById(R.id.txt_item_precovenda);
		txtdesconto = (EditText) findViewById(R.id.txt_item_desconto);
		txttotal = (EditText) findViewById(R.id.txt_item_total);

		txtpedidoerp = (EditText) findViewById(R.id.txt_item_pedidoerp);
		txtitemerp = (EditText) findViewById(R.id.txt_item_itemerp);
		txtprevfaturamento = (EditText) findViewById(R.id.txt_item_prevfaturamento);
		txtpreventrega = (EditText) findViewById(R.id.txt_item_preventrega);
		txtnumeronf = (EditText) findViewById(R.id.txt_item_numeronf);
		txtserienf = (EditText) findViewById(R.id.txt_item_serienf);
		txtfaturamento = (EditText) findViewById(R.id.txt_item_faturamento);
		txtentrega = (EditText) findViewById(R.id.txt_item_entrega);

		lblpedidoerp = (TextView) findViewById(R.id.lbl_item_pedidoerp);
		lblitemerp = (TextView) findViewById(R.id.lbl_item_itemerp);
		lblprevfaturamento = (TextView) findViewById(R.id.lbl_item_prevfaturamento);
		lblpreventrega = (TextView) findViewById(R.id.lbl_item_preventrega);
		lblnumeronf = (TextView) findViewById(R.id.lbl_item_numeronf);
		lblserienf = (TextView) findViewById(R.id.lbl_item_serienf);
		lblfaturamento = (TextView) findViewById(R.id.lbl_item_faturamento);
		lblentrega = (TextView) findViewById(R.id.lbl_item_entrega);

		cmbpesquisaproduto = (Button) findViewById(R.id.cmb_item_buscaproduto);
		cmbsalvar = (Button) findViewById(R.id.cmb_item_salvar);
		cmbexcluir = (Button) findViewById(R.id.cmb_item_excluir);

		FillComboFilial();

		z = NumberFormat.getCurrencyInstance();

		DecimalFormatSymbols decimalFormatSymbols = ((DecimalFormat) z)
				.getDecimalFormatSymbols();
		decimalFormatSymbols.setCurrencySymbol("");
		((DecimalFormat) z).setDecimalFormatSymbols(decimalFormatSymbols);

		// Decidir sobre a inclusão
		Intent i = getIntent();
		id_pedido = i.getIntExtra("id_pedido", 0);
		id_item = i.getIntExtra("id_item", 0);

		if (id_pedido == 0)
			return;

		if (id_item == 0)
			Novo();
		else
			Editar();
		
		txtquantidade.addTextChangedListener(new TextWatcher() {

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
				try {
					AtualizarTotal();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		txtprecovenda.addTextChangedListener(new TextWatcher() {

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
				try {
					AtualizarTotal();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
//		txtquantidade.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//		      public void onFocusChange(View v, boolean hasFocus) {
//		          if (hasFocus) {
//		           getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
//		          } else if (!hasFocus) {
//		           getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
//		          }
//		      }
//		  });
		
	}

	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		
	  savedInstanceState.putInt("id_pedido", id_pedido);  
	  savedInstanceState.putInt("id_item", id_item);
	  savedInstanceState.putInt("id_produto", id_produto);
	  
	  savedInstanceState.putString("id_produto_retaguarda", id_produto_retaguarda);
	  savedInstanceState.putString("ds_produto_nome", ds_produto_nome);
	    
	  super.onSaveInstanceState(savedInstanceState);
	  
	}  
	//onRestoreInstanceState  
	    @Override  
	public void onRestoreInstanceState(Bundle savedInstanceState) {  
	  super.onRestoreInstanceState(savedInstanceState);  
	  
	  id_pedido = savedInstanceState.getInt("id_pedido");
	  id_item = savedInstanceState.getInt("id_item");
	  id_produto = savedInstanceState.getInt("id_produto");
	  
	  id_produto_retaguarda= savedInstanceState.getString("id_produto_retaguarda");
	  ds_produto_nome= savedInstanceState.getString("ds_produto_nome");
	  
	  
	}
	
	private void FillComboFilial() {
		weblayer.vendas.DAO.FilialDAO.initialize(this);

		List<weblayer.vendas.DTO.FilialDTO> filial = weblayer.vendas.DAO.FilialDAO
				.fillCombo();

		weblayer.vendas.DTO.FilialDTO[] filialArray = new weblayer.vendas.DTO.FilialDTO[filial
				.size()];
		filial.toArray(filialArray);

		AdapterFilial = new SpinAdapterFilial(this,
				android.R.layout.simple_spinner_item, filialArray);

		spinnerFilial = (Spinner) findViewById(R.id.spn_item_filial);
		spinnerFilial.setAdapter(AdapterFilial);

	}

	private void Editar() {

		weblayer.vendas.DAO.PedidoItemDAO.initialize(this);
		item = weblayer.vendas.DAO.PedidoItemDAO.Get(id_item);
		if (item == null)
			return;

		// desabilitar os botões ..
		// travar os campos ..
		cmbpesquisaproduto.setVisibility(View.GONE);

		weblayer.vendas.DAO.PedidoDAO.initialize(this);
		pedido = weblayer.vendas.DAO.PedidoDAO.Get(id_pedido);
		if (pedido == null)
			return;

		id_produto = item.getid_produto();
		id_produto_retaguarda = item.getid_item_retaguarda();
		ds_produto_nome = item.getds_produto_nome_completo();

		txtproduto.setText("[" + item.getid_produto_retaguarda() + "] "
				+ item.getds_produto_nome_completo());

		txtquantidade.setText(String.valueOf(item.getnr_quantidade()));

		txtprecolista.setText(z.format(item.getvl_lista()));
		txtprecovenda.setText(z.format(item.getvl_unitario()));
		txtdesconto.setText(z.format(item.getvl_desconto()));
		txttotal.setText(z.format(item.getvl_liquido()));

		txtpedidoerp.setText(item.getid_pedido_retaguarda());
		txtitemerp.setText(item.getid_item_retaguarda());

		if (item.getdt_prevfaturamento() != null)
			txtprevfaturamento.setText(item.getdt_prevfaturamento()
					.toLocaleString());

		if (item.getdt_preventrega() != null)
			txtpreventrega.setText(item.getdt_preventrega().toLocaleString());

		txtnumeronf.setText(item.getds_numnf());
		txtserienf.setText(item.getds_serienf());

		if (item.getdt_entrega() != null)
			txtfaturamento.setText(item.getdt_entrega().toLocaleString());

		if (item.getdt_faturamento() != null)
			txtentrega.setText(item.getdt_faturamento().toLocaleString());

		weblayer.vendas.DAO.FilialDAO.initialize(this);
		FilialDTO itemfilial = weblayer.vendas.DAO.FilialDAO.Get(item
				.getid_filial_saida());
		if (itemfilial != null) {
			spinnerFilial.setSelection(weblayer.toolbox.Common.GetIndexSpinner(
					spinnerFilial, itemfilial));
		}

		txtproduto.setFocusable(false);
		txtdesconto.setFocusable(false);
		txtprecolista.setFocusable(false);
		
		txttotal.setFocusable(false);
		spinnerFilial.setFocusable(false);
		
		
		if (pedido.getfl_status() > 0) // Pedido somente leitura
		{
			ExibirDetalhes(true);

			cmbsalvar.setVisibility(View.GONE);
			cmbexcluir.setVisibility(View.GONE);
			txtquantidade.setFocusable(false);
			txtprecovenda.setFocusable(false);
			txtproduto.setEnabled(false);
			txtquantidade.setEnabled(false);
			txtprecolista.setEnabled(false);
			txtprecovenda.setEnabled(false);
			txtdesconto.setEnabled(false);
			txttotal.setEnabled(false);

			spinnerFilial.setFocusable(false);
			spinnerFilial.setEnabled(false);
			cmbsalvar.setFocusableInTouchMode(false);

		} else {

			ExibirDetalhes(false);

			txtproduto.setEnabled(false);
			txtprecolista.setEnabled(false);
			txtdesconto.setEnabled(false);
			spinnerFilial.setEnabled(false);
			txttotal.setEnabled(false);

			txtprecovenda.setEnabled(true);
			txtquantidade.setEnabled(true);
			
			txtquantidade.setFocusableInTouchMode(true);
			txtprecovenda.setFocusableInTouchMode(true);
			txtprecovenda.setFocusable(true);
			txtquantidade.setFocusable(true);
			
			
			txtprecovenda.setSelectAllOnFocus(true);
			txtquantidade.setSelectAllOnFocus(true);
		}
		
	
		txtquantidade.requestFocus();
	
	}

	private void Novo() {

		ExibirDetalhes(false);
		
		cmbpesquisaproduto.setFocusableInTouchMode(true);
		
		cmbexcluir.setVisibility(View.GONE);
		txtproduto.setEnabled(false);
		txtproduto.setFocusable(false);
		
		txtprecolista.setFocusable(false);
		txtprecolista.setEnabled(false);

		txttotal.setFocusable(false);
		txttotal.setEnabled(false);

		txtdesconto.setEnabled(false);

		txtprecovenda.setSelectAllOnFocus(true);
		txtquantidade.setSelectAllOnFocus(true);

		txtdesconto.setFocusable(false);
		
		weblayer.vendas.DAO.PedidoDAO.initialize(this);
		pedido = weblayer.vendas.DAO.PedidoDAO.Get(id_pedido);
		if (pedido == null)
			return;
		
		//cmbpesquisaproduto.requestFocus();
		BuscarProduto();
		
	}

	private void ExibirDetalhes(Boolean status) {

		if (!status) {

			txtpedidoerp.setVisibility(View.GONE);
			txtitemerp.setVisibility(View.GONE);
			txtprevfaturamento.setVisibility(View.GONE);
			txtpreventrega.setVisibility(View.GONE);
			txtnumeronf.setVisibility(View.GONE);
			txtserienf.setVisibility(View.GONE);
			txtfaturamento.setVisibility(View.GONE);
			txtentrega.setVisibility(View.GONE);

			lblpedidoerp.setVisibility(View.GONE);
			lblitemerp.setVisibility(View.GONE);
			lblprevfaturamento.setVisibility(View.GONE);
			lblpreventrega.setVisibility(View.GONE);
			lblnumeronf.setVisibility(View.GONE);
			lblserienf.setVisibility(View.GONE);
			lblfaturamento.setVisibility(View.GONE);
			lblentrega.setVisibility(View.GONE);

		} else {

			txtpedidoerp.setVisibility(View.VISIBLE);
			txtitemerp.setVisibility(View.VISIBLE);
			txtprevfaturamento.setVisibility(View.VISIBLE);
			txtpreventrega.setVisibility(View.VISIBLE);
			txtnumeronf.setVisibility(View.VISIBLE);
			txtserienf.setVisibility(View.VISIBLE);
			txtfaturamento.setVisibility(View.VISIBLE);
			txtentrega.setVisibility(View.VISIBLE);

			lblpedidoerp.setVisibility(View.VISIBLE);
			lblitemerp.setVisibility(View.VISIBLE);
			lblprevfaturamento.setVisibility(View.VISIBLE);
			lblpreventrega.setVisibility(View.VISIBLE);
			lblnumeronf.setVisibility(View.VISIBLE);
			lblserienf.setVisibility(View.VISIBLE);
			lblfaturamento.setVisibility(View.VISIBLE);
			lblentrega.setVisibility(View.VISIBLE);
		}

	}

	
	private void BuscarProduto()
	{
		Intent i = new Intent(this, Activity_Consulta_Produto.class);
		i.putExtra("chamada", "retorno");
		i.putExtra("titulo", "Selecione o produto...");
		
		startActivityForResult(i, 1);
		
		cmbpesquisaproduto.setFocusableInTouchMode(false);
		
	}
	public void onBtnBuscarProduto(View view) {

		
		BuscarProduto();
		
	}

	private void AtualizarTotal() throws ParseException {

		if (txtquantidade.length() == 0)
			return;

		if (txtprecovenda.length() == 0)
			return;

		txtdesconto.setText(z.format(0));
		txttotal.setText(z.format(0));

		double quatidade = Double.parseDouble(txtquantidade.getText()
				.toString());

		String textoprecovenda = txtprecovenda.getText().toString();
		String textolista = txtprecolista.getText().toString();

		textoprecovenda = textoprecovenda.replace(".", "");
		textolista = textolista.replace(".", "");

		textoprecovenda = textoprecovenda.replace(",", ".");
		textolista = textolista.replace(",", ".");

		double precovenda = Double.parseDouble(textoprecovenda);

		if (txtprecolista.length() > 0) {

			double precolista = Double.parseDouble(textolista);

			double desconto = (precolista - precovenda);

			if (desconto > 0) {
				txtdesconto.setText(z.format(desconto * quatidade));
			}
		}

		txttotal.setText(z.format(precovenda * quatidade));

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		id_produto_retaguarda = "";
		ds_produto_nome = "";

		if (requestCode == 1) {

			if (resultCode == RESULT_OK) {
				id_produto = data.getIntExtra("id_produto", 0);
				id_produto_retaguarda = data
						.getStringExtra("id_produto_retaguarda");
				ds_produto_nome = data.getStringExtra("ds_nome");

				weblayer.vendas.DAO.ProdutoDAO.initialize(this);
				weblayer.vendas.DTO.ProdutoDTO produto = weblayer.vendas.DAO.ProdutoDAO
						.Get(id_produto);
				if (produto == null)
					return;

				// Definir a filial
				// *************************************************************************************************************************
				weblayer.vendas.DAO.ClienteGrupoProdutoDAO.initialize(this);
				weblayer.vendas.DTO.ClienteGrupoProdutoDTO grupo = weblayer.vendas.DAO.ClienteGrupoProdutoDAO
						.Get(pedido.getid_cliente(), produto.getid_categ1(),
								produto.getid_categ2(), produto.getid_categ3());
				if (grupo == null)
					grupo = weblayer.vendas.DAO.ClienteGrupoProdutoDAO.Get(
							pedido.getid_cliente(), produto.getid_categ1(),
							produto.getid_categ3());

				if (grupo == null) {
					Toast.makeText(
							getApplicationContext(),
							"Amarração de ProdutoXCliente não definida. Entre em contato com o suporte.",
							Toast.LENGTH_LONG).show();
					return;
				}

				weblayer.vendas.DAO.FilialDAO.initialize(this);
				FilialDTO itemfilial = weblayer.vendas.DAO.FilialDAO.Get(grupo
						.getid_filial_faturamento());
				if (itemfilial != null) {
					spinnerFilial.setSelection(weblayer.toolbox.Common
							.GetIndexSpinner(spinnerFilial, itemfilial));
				}

				spinnerFilial.setEnabled(false);
				if (grupo.getfl_trocafilial() == 1) {
					weblayer.vendas.DAO.ParametroDAO.initialize(this);
					String valor = weblayer.vendas.DAO.ParametroDAO.GetByKey(
							"VENDEDORMUDAFILIAL", "0");
					if (valor.equals("1"))
						spinnerFilial.setEnabled(true);
				}
				// *************************************************************************************************************************************************

				// atualizar o preco de lista.
				double precolista = weblayer.vendas.DAO.ProdutoDAO
						.GetPrecoLista(this, pedido.getid_cliente(), id_produto);
				txtprecolista.setText(z.format(precolista));
				txtprecovenda.setText(z.format(precolista));

				// Atualizar o total.
				try {
					AtualizarTotal();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
//				txtquantidade.requestFocus();
//				InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
//				imm.showSoftInput(txtquantidade, InputMethodManager.SHOW_IMPLICIT);
				
		        //getDialog().getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE);
				
			}

			if (resultCode == RESULT_CANCELED) {
				id_produto = 0;
				id_produto_retaguarda = "";
				ds_produto_nome = "";

				txtprecolista.setText(z.format(0));
				txtprecovenda.setText(z.format(0));

				try {
					AtualizarTotal();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			txtproduto.setText("[" + id_produto_retaguarda + "] "
					+ ds_produto_nome);

		}

	}

	public void onBtnSalvar(View view) throws Exception {

		double precolista = 0;
		double precovenda = 0;
		double total = 0;
		double desconto = 0;
		int quantidade = 0;

		// Criticar os dados
		// ***************************************************************
		String critica = "";

		// Produto
		// *************************************************************************
		if (id_produto == 0)
			critica = critica + "Produto inválido. ";

		// Filial
		// **************************************************************************
		if (spinnerFilial.getSelectedItemPosition() == 0)
			critica = critica + "Filial inválida. ";
		// **********************************************************************************

		if (txtquantidade.length() == 0) {
			critica = critica + "Quantidade inválida. ";
		}

		if (txtprecolista.length() == 0) {
			critica = critica + "Preço de lista inválido. ";
		} else

		{
			String textolista = txtprecolista.getText().toString();
			textolista = textolista.replace(".", "");
			textolista = textolista.replace(",", ".");
			precolista = Double.parseDouble(textolista);

			if (precolista <= 0)
				critica = critica + "Preço de lista inválido. ";
		}

		if (txtprecovenda.length() == 0) {
			critica = critica + "Preço de venda inválido. ";
		} else {
			String textoprecovenda = txtprecovenda.getText().toString();
			textoprecovenda = textoprecovenda.replace(".", "");
			textoprecovenda = textoprecovenda.replace(",", ".");
			precovenda = Double.parseDouble(textoprecovenda);

			if (precovenda <= 0)
				critica = critica + "Preço de venda inválido. ";
		}

		if (!critica.equals("")) {
			Toast.makeText(getApplicationContext(), critica, Toast.LENGTH_LONG)
					.show();
			return;
		}

		// Critiar o total, deve ser > 0
		if (txttotal.length() == 0) {
			critica = critica + "Valor total inválido. ";
		} else {
			String textototal = txttotal.getText().toString();
			textototal = textototal.replace(".", "");
			textototal = textototal.replace(",", ".");
			total = Double.parseDouble(textototal);

			if (total <= 0)
				critica = critica + "Total do item inválido. ";

		}
		if (!critica.equals("")) {
			Toast.makeText(getApplicationContext(), critica, Toast.LENGTH_LONG)
					.show();
			return;
		}

		String textodesconto = txtdesconto.getText().toString();
		textodesconto = textodesconto.replace(".", "");
		textodesconto = textodesconto.replace(",", ".");
		desconto = Double.parseDouble(textodesconto);

		weblayer.vendas.DTO.FilialDTO filialsaida = AdapterFilial
				.getItem(spinnerFilial.getSelectedItemPosition());

		quantidade = Integer.valueOf(txtquantidade.getText().toString());

		weblayer.vendas.DAO.ProdutoDAO.initialize(this);
		weblayer.vendas.DTO.ProdutoDTO produto = weblayer.vendas.DAO.ProdutoDAO
				.Get(id_produto);

		if (produto == null)
			return;

		// Verificar o peso máximo.
		weblayer.vendas.DAO.ParametroDAO.initialize(this);
		double pesomaximo = Double.valueOf(weblayer.vendas.DAO.ParametroDAO
				.GetByKey("N_MAXPESOBRUTO", "0"));
		double pesototal = weblayer.vendas.DAO.PedidoItemDAO.GetPesoTotal(
				id_pedido, id_item);

		if (pesototal + (quantidade * produto.getnr_peso_bruto()) > pesomaximo) {
			Toast.makeText(getApplicationContext(),
					"Peso máximo do pedido excedido (" + pesomaximo + " Kg)",
					Toast.LENGTH_LONG).show();
			return;
		}

		// Salvar o item.
		if (id_item == 0) {

			// Verificar se já existe produto cadastrado no pedido
			weblayer.vendas.DTO.PedidoItemDTO itemrepetido = weblayer.vendas.DAO.PedidoItemDAO
					.Get(id_pedido, id_produto);
			if (itemrepetido != null) {
				Toast.makeText(getApplicationContext(),
						"Item já cadastrado no pedido.", Toast.LENGTH_LONG)
						.show();
				return;
			}

			item = new weblayer.vendas.DTO.PedidoItemDTO();
			item.setid_pedido(id_pedido);
			item.setid_empresa(pedido.getid_empresa());
			item.setid_filial(pedido.getid_filial());
			item.setid_filial_saida(filialsaida.getid());
			item.setid_produto(id_produto);
			item.setid_produto_retaguarda(id_produto_retaguarda);
			item.setds_produto_nome_completo(ds_produto_nome);
			item.setnr_quantidade(quantidade);
			item.setvl_lista(precolista);
			item.setvl_unitario(precovenda);
			item.setvl_desconto(desconto);
			item.setvl_liquido(total);
			item.setvl_peso(produto.getnr_peso_bruto() * quantidade);

			weblayer.vendas.DAO.PedidoItemDAO.initialize(this);
			weblayer.vendas.DAO.PedidoItemDAO.insert(item);

		} else {
			weblayer.vendas.DAO.PedidoItemDAO.initialize(this);
			item = weblayer.vendas.DAO.PedidoItemDAO.Get(id_item);

			item.setvl_unitario(precovenda);
			item.setnr_quantidade(quantidade);
			item.setvl_desconto(desconto);
			item.setvl_liquido(total);
			item.setvl_peso(produto.getnr_peso_bruto() * quantidade);

			weblayer.vendas.DAO.PedidoItemDAO.initialize(this);
			weblayer.vendas.DAO.PedidoItemDAO.update(item);

		}

		// Atualizar os totais da capa do pedido.
		weblayer.vendas.DAO.PedidoDAO.initialize(this);
		weblayer.vendas.DAO.PedidoDAO.AtualizaTotais(this, pedido);

		setResult(RESULT_OK, null);
		finish();

	}

	public void onBtnItemExcluir(View view) {

		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		adb.setTitle("Atenção");
		adb.setMessage("Confirma a exclusão do item?");
		adb.setNegativeButton("Não", new AlertDialog.OnClickListener() {
			public void onClick(DialogInterface dialog, int arg1) {
				// Cancel Event
			}

		});

		adb.setPositiveButton("Sim", new AlertDialog.OnClickListener() {
			public void onClick(DialogInterface dialog, int arg1) {
				try {
					ExcluirItem();
					
					Toast.makeText(getApplicationContext(), "Item excluído.", Toast.LENGTH_SHORT)
					.show();
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		adb.show();

	}

	private void ExcluirItem() throws Exception {

		weblayer.vendas.DAO.PedidoItemDAO.initialize(this);
		weblayer.vendas.DAO.PedidoItemDAO.delete(item);

		// Atualizar os totais da capa do pedido.
		weblayer.vendas.DAO.PedidoDAO.initialize(this);
		weblayer.vendas.DAO.PedidoDAO.AtualizaTotais(this, pedido);

		setResult(RESULT_OK, null);
		finish();

	}

}