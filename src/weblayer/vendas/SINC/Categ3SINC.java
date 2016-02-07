package weblayer.vendas.SINC;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import weblayer.vendas.DAO.Categ1DAO;
import weblayer.vendas.DAO.Categ2DAO;
import weblayer.vendas.DAO.Categ3DAO;
import weblayer.vendas.DAO.ParametroDAO;
import weblayer.vendas.DAO.ProdutoDAO;
import weblayer.vendas.DTO.Categ3DTO;
import weblayer.vendas.DTO.ProdutoDTO;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class Categ3SINC {
	
	public final static String SOAP_ACTION = "http://www.weblayer.com.br/";

	public final static String WSDL_TARGET_NAMESPACE = "http://www.weblayer.com.br/";

	public Categ3SINC() {
	}

	public static ArrayList<weblayer.vendas.DTO.Categ3DTO> RetornaLista(Context context, String SOAP_ADDRESS, int empresa, String vendedor, String dispositivo, String datasinc) throws Exception {
		
		String OPERATION_NAME="RetornaCateg3";
		
		SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);
		    
		PropertyInfo pi = new PropertyInfo();
		pi.setName("empresa");
		pi.setValue(empresa);
		pi.setType(Integer.class);
		request.addProperty(pi);
		
		pi = new PropertyInfo();
		pi.setName("vendedor");
		pi.setValue(vendedor);
		pi.setType(String.class);
		request.addProperty(pi);
		
		pi = new PropertyInfo();
		pi.setName("datasinc");
		pi.setValue(datasinc);
		pi.setType(String.class);
		request.addProperty(pi);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.dotNet = true;

		envelope.setOutputSoapObject(request);

		HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
		Object response = null;
		
		try {
			
			httpTransport.call(SOAP_ACTION + OPERATION_NAME, envelope);
			response = envelope.getResponse();
		
		} catch (Exception e) {
			if (!weblayer.toolbox.Common.isNetWorkActive(context))
				throw new Exception("Sem conexão com a internet.");
			else
				throw e;
		}
		
		if (response==null)
			return null;
		
		GsonBuilder gsonb = new GsonBuilder();
		Gson gson = gsonb.create();
		ArrayList<weblayer.vendas.DTO.Categ3DTO> Objetos;
		
		try
		{
		    
			Type collectionType = new TypeToken<ArrayList<weblayer.vendas.DTO.Categ3DTO>>(){}.getType();
			Objetos = gson.fromJson(response.toString(), collectionType);
			
		}
		catch(Exception e)
		{
		    e.printStackTrace();
		    return null;
		}
		
		return Objetos;
	}
	
	public static void Sincronizar(Context context, String SOAP_ADDRESS, int id_empresa, String Vendedor, String Imei, String UltimaSinc) throws Exception
	{
		
		ArrayList<Categ3DTO> itens = RetornaLista(context, SOAP_ADDRESS, id_empresa, Vendedor, Imei, UltimaSinc);
		
		if (itens==null)
			return;
		
		for (Categ3DTO item : itens) {
			
			Categ3DTO buffer = Categ3DAO.Get(item.getid());
			if (buffer==null)
				Categ3DAO.insert(item);
			else
				Categ3DAO.update(item);
			
		}		
		
		
	}
	
}