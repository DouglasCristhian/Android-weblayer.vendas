package weblayer.vendas.view;

import weblayer.vendas.d1.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
 
public class Activity_PedidoCapa extends Activity {
    
	public void onCreate(Bundle savedInstanceState) {
    	
		weblayer.toolbox.Common.DefinirTheme(this, "");
		
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_pedido_capa);
    	
    	TextView cliente =(TextView )findViewById(R.id.txtpedidocliente);
    	TextView tipo =(TextView )findViewById(R.id.txtpedidotipo);
    	TextView pedcliente =(TextView )findViewById(R.id.txtpedidopedcliente);
    	TextView canal =(TextView )findViewById(R.id.txtpedidocanal);
    	
    	Intent i = getIntent();
		int id_pedido = i.getIntExtra("id_pedido",0);
		
		weblayer.vendas.DAO.PedidoDAO.initialize(this);
		weblayer.vendas.DTO.PedidoDTO pedido =weblayer.vendas.DAO.PedidoDAO.Get(id_pedido);
		
		if (pedido==null)
			return;
		
		cliente.setText(pedido.getds_cliente());
		
		weblayer.vendas.DAO.ClienteDAO.initialize(this);
		weblayer.vendas.DTO.ClienteDTO objcliente = weblayer.vendas.DAO.ClienteDAO.Get(pedido.getid_cliente());
		if (cliente==null)
			return;
		
		canal.setText(objcliente.getds_canal());
		
		if (pedido.getid_tipo()==1)
			tipo.setText("VENDA");
		
		if (pedido.getid_tipo()==2)
			tipo.setText("BONIFICACAO");
		
		if (pedido.getid_tipo()==3)
			tipo.setText("INDUSTRIALIZACAO");
		
		pedcliente.setText(pedido.getds_pedido_cliente());

		
    }
}