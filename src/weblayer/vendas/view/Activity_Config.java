
package weblayer.vendas.view;
import weblayer.vendas.d1.R;
import weblayer.vendas.DAO.ClienteDAO;
import weblayer.vendas.DAO.ClienteGrupoProdutoDAO;
import weblayer.vendas.DAO.FilialDAO;
import weblayer.vendas.DAO.ParametroDAO;
import weblayer.vendas.DAO.PedidoDAO;
import weblayer.vendas.DAO.PedidoItemDAO;
import weblayer.vendas.DAO.ProdutoDAO;
import weblayer.vendas.DAO.TabelaPrecoDAO;
import weblayer.vendas.DTO.ParametroDTO;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_Config extends Activity {
	
	
	TextView txtImei;
	TextView txtConta;
	TextView txtVendedor;
	TextView txtUltimaSinc;
	
    @SuppressLint("NewApi")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        
    	weblayer.toolbox.Common.DefinirTheme(this, getResources().getString(R.string.titulo_config));
    	
    	super.onCreate(savedInstanceState);
        
        if (android.os.Build.VERSION.SDK_INT >= 11)
		{
	        ActionBar actionBar = getActionBar();
	    	actionBar.setDisplayShowHomeEnabled(false);
	    	actionBar.setTitle( getResources().getString(R.string.titulo_config));
		}
        
        setContentView(R.layout.activity_config);
        
        txtImei = (TextView) findViewById(R.id.config_txt_Imei);
    	txtVendedor = (TextView) findViewById(R.id.config_txt_Vendedor);
    	txtConta= (TextView) findViewById(R.id.config_txt_Conta);
    	txtUltimaSinc = (TextView) findViewById(R.id.config_txt_UltimaSinc);
    	
        ParametroDAO.initialize(this);

        LoadConfig();
		
    }
    
    public void onBtnTestarConexao(View v)
    {
    	weblayer.toolbox.Common.TestConnection(this);
    }
    
    public void onBtnSalvarConfig(View v)
    {
    	
    	final ProgressDialog myPd_ring = ProgressDialog.show(this, getResources().getString(R.string.sinc_lbl_salvandoconfiguracao), getResources().getString(R.string.sinc_lbl_mensagem), true);
        
    	myPd_ring.setCancelable(true);
        
        boolean retorno = SalvarConfig();

        myPd_ring.dismiss();

		if (retorno)
			finish();
    	
    }

    public void onBtnLimparDados(View v)
    {
    	
    	AlertDialog.Builder adb = new AlertDialog.Builder(this);
		adb.setTitle("ATEN��O");
		adb.setMessage("Confirma a limpeza dos dados armazenados?");
		adb.setPositiveButton("Sim", new AlertDialog.OnClickListener() {
			public void onClick(DialogInterface dialog, int arg1) {
				
				 LimparDados();
			     LoadConfig();
				
			}
		});
		adb.setNegativeButton("N�o", new AlertDialog.OnClickListener() {
			public void onClick(DialogInterface dialog, int arg1) {
				//
			}

		});
		adb.show();
        
    }
    
    private void LoadConfig()
    {
    	
        ParametroDTO parametro = null;
        
        txtImei.setText(ParametroDAO.GetByKey("IMEI",""));
        
        txtVendedor.setText(ParametroDAO.GetByKey("VENDEDOR",""));
        
        txtConta.setText(ParametroDAO.GetByKey("CONTA",""));
        
        txtUltimaSinc.setText("");
        		
        
        if (ParametroDAO.GetByKey("ULTIMASINC","").length()>0)
        {
        	txtUltimaSinc.setText(ParametroDAO.GetByKey("ULTIMASINC",""));
        	txtImei.setEnabled(false);
        	txtVendedor.setEnabled(false);
        	txtConta.setEnabled(false);
        	
        }
        else
        {
        	
        	txtImei.setEnabled(true);
        	txtVendedor.setEnabled(true);
        	txtConta.setEnabled(true);
        	
        }
        
     	
    }
    
	private boolean SalvarConfig()
	    {
	    	
	    	boolean erro=false;

			ParametroDTO parametro=null;

			txtImei = (TextView) findViewById(R.id.config_txt_Imei);
			txtVendedor = (TextView) findViewById(R.id.config_txt_Vendedor);
			txtConta= (TextView) findViewById(R.id.config_txt_Conta);

			String  str =txtImei.getText().toString();
			if(str.equalsIgnoreCase(""))
			{
				txtImei.setHint("Informe o IMEI");
				txtImei.setError("Informe o IMEI");
				erro =true;
			}


			str =txtVendedor.getText().toString();
			if(str.equalsIgnoreCase(""))
			{
				txtVendedor.setHint("Informe o Vendedor");
				txtVendedor.setError("Informe o Vendedor");
				erro =true;
			}


			str =txtConta.getText().toString();
			if(str.equalsIgnoreCase(""))
			{
				txtConta.setHint("Informe a Conta");
				txtConta.setError("Informe a Conta");
				erro =true;
			}

			if (erro)
				return false;

	        if (txtImei.getText().length()>0)
	        {
	        	parametro = new ParametroDTO();
	        	parametro.setid_empresa(0);
	        	parametro.setds_chave("IMEI");
	        	parametro.setds_valor(txtImei.getText().toString());
	        	ParametroDAO.insertorupdate(parametro);
	        }
	        

	        if (txtVendedor.getText().length()>0)
	        {
	            parametro = new ParametroDTO();
	            parametro.setid_empresa(0);
	            parametro.setds_chave("VENDEDOR");
	        	parametro.setds_valor(txtVendedor.getText().toString());
	        	ParametroDAO.insertorupdate(parametro);
	        }
	        

	        if (txtConta.getText().length()>0)
	        {
	            parametro = new ParametroDTO();
	            parametro.setid_empresa(0);
	            parametro.setds_chave("CONTA");
	        	parametro.setds_valor(txtConta.getText().toString());
	        	ParametroDAO.insertorupdate(parametro);
	        }
	        
	        return true;
	        
	    }
	    
	private void LimparDados()
	{
	    	
		ParametroDAO.initialize(this);
		
		ClienteDAO.initialize(this);
		ClienteDAO.TruncateTable();
		
		ClienteGrupoProdutoDAO.initialize(this);
		ClienteGrupoProdutoDAO.TruncateTable();
		
		FilialDAO.initialize(this);
		FilialDAO.TruncateTable();
		
		PedidoDAO.initialize(this);
		PedidoDAO.TruncateTable();
		
		PedidoItemDAO.initialize(this);
		PedidoItemDAO.TruncateTable();
		
		ProdutoDAO.initialize(this); 
		ProdutoDAO.TruncateTable(); 
		
		TabelaPrecoDAO.initialize(this); 
		TabelaPrecoDAO.TruncateTable();
		
		ParametroDTO parametro = new ParametroDTO();
        parametro.setds_chave("ULTIMASINC");
    	parametro.setds_valor("");
    	ParametroDAO.insertorupdate(parametro);
    	
    	parametro.setds_chave("WEBSERVICE");
    	parametro.setds_valor("");
    	ParametroDAO.insertorupdate(parametro);
    	
    	
	}
    
}