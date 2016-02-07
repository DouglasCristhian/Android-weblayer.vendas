package weblayer.vendas.view;

import weblayer.vendas.d1.R;
import weblayer.vendas.DAO.ProdutoDAO;
import weblayer.vendas.DTO.ProdutoDTO;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

public class Activity_Produto_Detalhe extends Activity {

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {

		weblayer.toolbox.Common.DefinirTheme(this,
				getResources().getString(R.string.titulo_produto_detalhe));

		super.onCreate(savedInstanceState);
		
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
//		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//		if (imm != null) {
//			imm.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY);
//		}

		if (android.os.Build.VERSION.SDK_INT >= 11) {
			ActionBar actionBar = getActionBar();
			actionBar.setDisplayShowHomeEnabled(false);
			actionBar.setTitle("Produto");
		}

		setContentView(R.layout.activity_detalhe_produto);

		Intent i = getIntent();
		int id_produto = i.getIntExtra("id_produto", 0);

		ProdutoDTO produto = ProdutoDAO.Get(id_produto);
		if (produto == null)
			return;

		TextView codigo = (TextView) findViewById(R.id.txtproduto_Codigo);
		codigo.setText(produto.getid_retaguarda());
		codigo.setFocusable(false);

		TextView descricao = (TextView) findViewById(R.id.txtproduto_Descricao);
		descricao.setText(produto.getds_nome_completo());
		descricao.setFocusable(false);

		TextView unidmedida = (TextView) findViewById(R.id.txtproduto_UnidMedida);
		unidmedida.setText("");
		unidmedida.setFocusable(false);

		TextView pesobruto = (TextView) findViewById(R.id.txtproduto_Pesobruto);
		pesobruto.setText(String.valueOf(produto.getnr_peso_bruto()));
		pesobruto.setFocusable(false);

		TextView pesoliquido = (TextView) findViewById(R.id.txtproduto_Pesoliquido);
		pesoliquido.setText(String.valueOf(produto.getnr_peso_liquido()));
		pesoliquido.setFocusable(false);

		TextView ean13 = (TextView) findViewById(R.id.txtproduto_Ean13);
		ean13.setText(produto.getds_ean13());
		ean13.setFocusable(false);

		TextView dun14 = (TextView) findViewById(R.id.txtproduto_Dun14);
		dun14.setText(produto.getds_dun14());
		dun14.setFocusable(false);

		TextView unidademedida = (TextView) findViewById(R.id.txtproduto_UnidMedida);
		unidademedida.setText(produto.getds_unidademedida());
		unidademedida.setFocusable(false);

		TextView status = (TextView) findViewById(R.id.txtproduto_Status);
		status.setText("INATIVO");

		if (produto.getfl_ativo() == 1)
			status.setText("ATIVO");
	}
}
